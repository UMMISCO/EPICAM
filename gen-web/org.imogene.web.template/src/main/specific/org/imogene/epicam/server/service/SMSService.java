package org.imogene.epicam.server.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.imogene.epicam.domain.entity.CasTuberculose;
import org.imogene.epicam.domain.entity.OutBox;
import org.imogene.epicam.domain.entity.Patient;
import org.imogene.epicam.domain.entity.RendezVous;
import org.imogene.epicam.domain.entity.SmsPredefini;
import org.imogene.epicam.server.ImogActorUtils;
import org.imogene.epicam.server.handler.CasTuberculoseHandler;
import org.imogene.epicam.server.handler.OutBoxHandler;
import org.imogene.epicam.server.handler.PatientHandler;
import org.imogene.epicam.server.handler.SmsPredefiniHandler;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.web.server.handler.GenericHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class SMSService {

	private static final Logger LOG = Logger.getLogger(SMSServiceScheduler.class);
	
	@Autowired
	PatientHandler patientHandler;

	@Autowired
	CasTuberculoseHandler casTBHandler;

	@Autowired
	SmsPredefiniHandler smsHandler;


	@Autowired
	private OutBoxHandler outBoxHandler;

	@Autowired
	GenericHandler genericHandler;

	private String uri = "http://smswebservices.mtarget.fr/SmsWebServices/ServletSms";

	private String username;

	private String password;

	private String serviceId;

	private String method;

	private String originatingAddress;

	private String operatorid;

	private String paycode;

	public void setMethod(String method) {
		this.method = method;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}

	public void setOperatorid(String operatorid) {
		this.operatorid = operatorid;
	}

	public void setOriginatingAddress(String originatingAddress) {
		this.originatingAddress = originatingAddress;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Transactional
	public void sendSMS() {
		ImogActor actor = genericHandler.loadFromLogin("admin");
		ImogActorUtils.setCurrentActor(actor);

		List<OutBox> listSMSOutbox = outBoxHandler.listOutBoxForSMSService();

		if (listSMSOutbox == null || listSMSOutbox.size() < 1) {
			return;
		}

		for (OutBox outBox : listSMSOutbox) {
			Patient patient = outBox.getPatient();
			if (patient == null) {
				continue;
			}

			HttpClient client = new HttpClient();

			PostMethod postMethod = new PostMethod(uri);
			postMethod.setQueryString(new NameValuePair[] {
					new NameValuePair("method", method),
					new NameValuePair("username", username),
					new NameValuePair("password", password),
					new NameValuePair("serviceid", serviceId),
					new NameValuePair("originatingAddress", originatingAddress),
					new NameValuePair("operatorid", operatorid),
					new NameValuePair("paycode", paycode),
					new NameValuePair("destinationAddress", "+237" + patient.getTelephoneUn().trim()),
					new NameValuePair("msgtext", outBox.getMessage()) });
			try {
				client.executeMethod(postMethod);

				Document document = newDocumentFromInputStream(postMethod.getResponseBodyAsStream());
				XPath xPath = XPathFactory.newInstance().newXPath();
				XPathExpression codeExp = xPath.compile("/RESULT/CODE");
				XPathExpression reasonExp = xPath.compile("/RESULT/REASON");
				XPathExpression ticketExp = xPath.compile("/RESULT/TICKET");
				String code = codeExp.evaluate(document);
				String reason = reasonExp.evaluate(document);
				String ticket = ticketExp.evaluate(document);
				if (Integer.valueOf(code) != 0) {
					outBox.setStatut(String.valueOf(OutBox.Columns.STATUT_ERREUR));
				} else {
					outBox.setStatut(String.valueOf(OutBox.Columns.STATUT_SUCCES));
				}
				outBox.setReponse("Code: " + code + ", Reason: " + reason + ", ticket: " + ticket);
				outBox.setDateDernierEssai(new Date());
				genericHandler.save(outBox);
			} catch (Exception e) {
				outBox.setStatut(String.valueOf(OutBox.Columns.STATUT_ERREUR));
				outBox.setReponse(e.toString());
				outBox.setDateDernierEssai(new Date());
				genericHandler.save(outBox);
			}
		}
		ImogActorUtils.setCurrentActor(null);
	}

	private static Document newDocumentFromInputStream(InputStream in) {
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		Document ret = null;

		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		try {
			ret = builder.parse(new InputSource(in));
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	

	// just write ours methods and call in
	/**
	 * Get all sms into the database, test and send daily, weekly, monthly or punctual SMS to patients
	 * 
	 * @param
	 */

	// just write ours methods and call in
	/**
	 * Get all sms into the database, test and send daily, weekly, monthly or punctual SMS to patients
	 * 
	 * @param
	 */
	public void sensitizationSMS() {

		LOG.info("Sensitization sms is running");

		List<Patient> listPatients = patientHandler.listPatient("modified", false);
		System.out.println("*****************The list of patient in the periodic sms is : " + listPatients);
		List<SmsPredefini> listSMS = smsHandler.listSmsPredefini("modified", false);

		if (listPatients != null && listPatients.size() > 0 && listSMS != null && listSMS.size() > 0) {
			for (SmsPredefini smsPredefini : listSMS) {
				int smsPeriod = (Integer.parseInt(smsPredefini.getPeriodicite()));
				Date myDate = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(myDate);
				int DAY_OF_MONTH = calendar.get(Calendar.DAY_OF_MONTH);
				int DAY_OF_WEEK = calendar.get(Calendar.DAY_OF_WEEK);
				int HOUR_OF_DAY = calendar.get(Calendar.HOUR_OF_DAY);
				int MONTH = calendar.get(Calendar.MONTH);
				// A partir de quelle date commencer à envoyer les SMS
				switch (smsPeriod) {
				// Daily SMS
				case 0: {
					if (smsPredefini.getType().equals("0") && HOUR_OF_DAY == 10 && smsPredefini.getStatut().equals("0")) {
						System.out.println("Dayly sms is running" + new Date());
						LOG.info("Dayly sms is running" + new Date() + "SMS : " + smsPredefini.getMessage());
						for (Patient patient : listPatients) {
							OutBox outBox = new OutBox();
							outBox.setCreated(myDate);
							outBox.setPatient(patient);
							outBox.setStatut("1");
							if (smsPredefini.getMessage().getFrancais() != null)
								outBox.setMessage(smsPredefini.getMessage().getEnglish());
							else if (smsPredefini.getMessage().getEnglish() != null)
								outBox.setMessage(smsPredefini.getMessage().getFrancais());

							outBoxHandler.save(outBox, true);
						}
					}
					break;
				}
				// Weekly SMS
				case 1: {
					if (smsPredefini.getType().equals("0") && DAY_OF_WEEK == 3 && smsPredefini.getStatut().equals("0")
							&& HOUR_OF_DAY == 15) {
						System.out.println("Weekly sms is running" + new Date());
						LOG.info("Weekly sms is running" + new Date() + "SMS : " + smsPredefini.getMessage());
						for (Patient patient : listPatients) {
							OutBox outBox = new OutBox();
							outBox.setCreated(myDate);
							outBox.setStatut("1");
							outBox.setPatient(patient);
							if (smsPredefini.getMessage().getFrancais() != null)
								outBox.setMessage(smsPredefini.getMessage().getEnglish());
							else if (smsPredefini.getMessage().getEnglish() != null)
								outBox.setMessage(smsPredefini.getMessage().getFrancais());

							outBoxHandler.save(outBox, true);
						}
					}
					break;
				}
				// Monthly SMS
				case 2: {
					if (smsPredefini.getType().equals("0") && DAY_OF_MONTH == 2 && smsPredefini.getStatut().equals("0")
							&& HOUR_OF_DAY == 12) {
						System.out.println("Monthly sms is running" + new Date());
						LOG.info("Monthly sms is running" + new Date() + "SMS : " + smsPredefini.getMessage());
						for (Patient patient : listPatients) {
							OutBox outBox = new OutBox();
							outBox.setCreated(myDate);
							outBox.setStatut("1");
							outBox.setPatient(patient);
							if (smsPredefini.getMessage().getFrancais() != null)
								outBox.setMessage(smsPredefini.getMessage().getEnglish());
							else if (smsPredefini.getMessage().getEnglish() != null)
								outBox.setMessage(smsPredefini.getMessage().getFrancais());

							outBoxHandler.save(outBox, true);
						}
					}
					break;
				}// Trimester SMS
				case 3: {
					if (smsPredefini.getType().equals("0") && (MONTH == 0 || MONTH == 3 || MONTH == 6 || MONTH == 9)
							&& DAY_OF_MONTH == 4 && smsPredefini.getStatut().equals("0") && HOUR_OF_DAY == 11) {
						System.out.println("Trimester sms is running" + new Date());
						LOG.info("Trimester sms is running" + new Date() + "SMS : " + smsPredefini.getMessage());
						for (Patient patient : listPatients) {
							OutBox outBox = new OutBox();
							outBox.setCreated(myDate);
							outBox.setStatut("1");
							outBox.setPatient(patient);
							if (smsPredefini.getMessage().getFrancais() != null)
								outBox.setMessage(smsPredefini.getMessage().getEnglish());
							else if (smsPredefini.getMessage().getEnglish() != null)
								outBox.setMessage(smsPredefini.getMessage().getFrancais());

							outBoxHandler.save(outBox, true);
						}
					}
					break;
				}// Semester SMS
				case 4: {
					if (smsPredefini.getType().equals("0") && (MONTH == 0 || MONTH == 6) && DAY_OF_MONTH == 4
							&& smsPredefini.getStatut().equals("0") && HOUR_OF_DAY == 11) {
						System.out.println("Semester sms is running" + new Date());
						LOG.info("Semester sms is running" + new Date() + "SMS : " + smsPredefini.getMessage());
						for (Patient patient : listPatients) {
							OutBox outBox = new OutBox();
							outBox.setCreated(myDate);
							outBox.setStatut("1");
							outBox.setPatient(patient);
							if (smsPredefini.getMessage().getFrancais() != null)
								outBox.setMessage(smsPredefini.getMessage().getEnglish());
							else if (smsPredefini.getMessage().getEnglish() != null)
								outBox.setMessage(smsPredefini.getMessage().getFrancais());

							outBoxHandler.save(outBox, true);
						}
					}
					break;
				}// Punctual SMS
				case 5: {
					if (smsPredefini.getType().equals("0") && smsPredefini.getDateEnvoyeSMSPonctuel() == myDate
							&& (smsPredefini.getStatut().equals("0") && HOUR_OF_DAY == 9)) {
						System.out.println("Punctual sms is running" + new Date());
						LOG.info("Punctual sms is running" + new Date() + "SMS : " + smsPredefini.getMessage());
						for (Patient patient : listPatients) {
							OutBox outBox = new OutBox();
							outBox.setCreated(myDate);
							outBox.setStatut("1");
							outBox.setPatient(patient);
							if (smsPredefini.getMessage().getFrancais() != null)
								outBox.setMessage(smsPredefini.getMessage().getEnglish());
							else if (smsPredefini.getMessage().getEnglish() != null)
								outBox.setMessage(smsPredefini.getMessage().getFrancais());

							outBoxHandler.save(outBox, true);
						}
					}
					break;
				}
				default:
					break;
				}
			}

		}

	}

	/**
	 * Remind patients every month to come back to the hospital when they are lost sight 
	 */
	public void reminderSMS() {

		List<SmsPredefini> listSMS = smsHandler.listSmsPredefini("modified", false);

		List<CasTuberculose> listCasTuberculoses = casTBHandler.listCasTuberculose("modified", false);
		if (listCasTuberculoses != null && listCasTuberculoses.size() > 0 && listSMS != null && listSMS.size() > 0) {

			for (SmsPredefini smsPredefini : listSMS) {
				if (smsPredefini.getType().equals("2") && smsPredefini.getStatut().equals("0")) {
					for (CasTuberculose casTuberculose : listCasTuberculoses) {
						List<RendezVous> listRendezvous = casTuberculose.getRendezVous();
						if (listRendezvous != null && listRendezvous.size() > 0) {
							for (RendezVous rendezVous : listRendezvous) {
								Date dateRDV = rendezVous.getDateRendezVous();
								Date myDate = new Date();
								Calendar calendar = Calendar.getInstance();
								calendar.setTime(dateRDV);
								int MONTH_RENDEZVOUS = calendar.get(Calendar.MONTH);
								if (myDate.before(dateRDV) && MONTH_RENDEZVOUS > myDate.getMonth() + 3) {
									OutBox outBox = new OutBox();
									outBox.setStatut("1");
									outBox.setCreated(myDate);
									outBox.setPatient(casTuberculose.getPatient());
									if (smsPredefini.getMessage().getFrancais() != null)
										outBox.setMessage(smsPredefini.getMessage().getEnglish());
									else if (smsPredefini.getMessage().getEnglish() != null)
										outBox.setMessage(smsPredefini.getMessage().getFrancais());

									outBoxHandler.save(outBox, true);

								}

							}
						}
					}
				}
			}

		}
	}
	
	/**
	 * Send to patients SMS who resume the patient treatment by SMS
	 */
	
	public void sendPatientMedicalRecord(){
		List<SmsPredefini> listSMS = smsHandler.listSmsPredefini("modified", false);
		LOG.info("Sending medical records to patients" + new Date());

		List<CasTuberculose> listCasTuberculoses = casTBHandler.listCasTuberculose("modified", false);
		if (listCasTuberculoses != null && listCasTuberculoses.size() > 0 && listSMS != null && listSMS.size() > 0) {
			for (SmsPredefini smsPredefini : listSMS) {
				if (smsPredefini.getType().equals("3") && smsPredefini.getStatut().equals("0")) {
					for (CasTuberculose casTuberculose : listCasTuberculoses) {
						List<RendezVous> listRendezvous = casTuberculose.getRendezVous();
						if (casTuberculose.getPatient().getRecevoirCarnetTelPortable() && listRendezvous != null && listRendezvous.size() > 0 ) {
							RendezVous dernierRendezVous = listRendezvous.get(listRendezvous.size()-1);
							Date today = new Date();
							//Send medical record every 7 days after un rendez-vous
							if(Math.abs(today.getDay() - dernierRendezVous.getDateRendezVous().getDay()) == 7){
								LOG.info("Sending medical records to patients : " + casTuberculose.getIdentifiant());
									OutBox outBox = new OutBox();
									outBox.setStatut("1");
									outBox.setCreated(today);
									outBox.setPatient(casTuberculose.getPatient());
									if (smsPredefini.getMessage().getFrancais() != null)
										outBox.setMessage(smsPredefini.getMessage().getFrancais());
									else if (smsPredefini.getMessage().getEnglish() != null)
										outBox.setMessage(smsPredefini.getMessage().getEnglish());

									outBoxHandler.save(outBox, true);

								}

							}
						}
					}
				}
			}
	}
	
	public void sendQuizz(){
		
	}
	
	public void receiveQuizz(){
		
	}
	


}
