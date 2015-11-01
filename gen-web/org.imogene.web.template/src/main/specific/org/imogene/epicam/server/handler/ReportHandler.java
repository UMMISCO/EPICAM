package org.imogene.epicam.server.handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.birt.report.data.adapter.api.script.ReportParameter;
import org.imogene.epicam.domain.entity.CasIndex;
import org.imogene.epicam.domain.entity.DistrictSante;
import org.imogene.epicam.domain.entity.ExamenATB;
import org.imogene.epicam.domain.entity.ExamenBiologique;
import org.imogene.epicam.domain.entity.ExamenMicroscopie;
import org.imogene.epicam.domain.entity.ExamenSerologie;
import org.imogene.epicam.domain.entity.CasTuberculose;
import org.imogene.epicam.domain.entity.CentreDiagTrait;
import org.imogene.epicam.domain.entity.LaboratoireReference;
import org.imogene.epicam.domain.entity.Patient;
import org.imogene.epicam.domain.entity.Personnel;
import org.imogene.epicam.domain.entity.Region;
import org.imogene.epicam.domain.entity.PriseMedicamenteuse;
import org.imogene.epicam.domain.entity.RendezVous;
import org.imogene.epicam.domain.entity.TransfertReference;
import org.imogene.epicam.server.birtPojo.DrugDistributionReport;
import org.imogene.epicam.server.birtPojo.ParametresRapport;
import org.imogene.epicam.server.birtPojo.PatientCardForm;
//import org.imogene.epicam.server.birtPojo.Report;
import org.imogene.epicam.server.birtPojo.RegTB;
import org.imogene.epicam.server.birtPojo.TbChildRegister;
import org.imogene.epicam.server.birtPojo.TbLabRegister;
import org.imogene.epicam.server.birtPojo.TbRegister;
import org.imogene.epicam.server.birtPojo.TransfertReferenceForm;
import org.imogene.epicam.server.birtPojo.TreatmentForm;
import org.imogene.epicam.server.birtPojo.TrimesterDetectReport;
import org.imogene.epicam.server.birtPojo.TrimesterLabReport;
import org.imogene.epicam.server.birtPojo.TrimesterTreatmentReport;
import org.imogene.epicam.server.handler.CasTuberculoseHandler;
import org.imogene.epicam.server.handler.CentreDiagTraitHandler;
import org.imogene.lib.common.constants.CriteriaConstants;
import org.imogene.lib.common.criteria.BasicCriteria;
import org.imogene.lib.common.criteria.ImogConjunction;
import org.imogene.lib.common.criteria.ImogJunction;
import org.imogene.lib.common.entity.ImogActor;
import org.imogene.web.server.util.HttpSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;

import org.imogene.epicam.server.birtPojo.ExamenCrachats;

public class ReportHandler {



	
	private static final Logger LOGGER = Logger.getLogger(ReportHandler.class);
	
	@Autowired
	PatientHandler patienHandler;

	@Autowired
	CasTuberculoseHandler casTuberculoseHandler;

	@Autowired
	CentreDiagTraitHandler cdtHandler;

	@Autowired
	ExamenSerologieHandler examenSerologieHandler;

	@Autowired
	ExamenMicroscopieHandler examenMicroscopieHandler;
	
	@Autowired
	ExamenBiologiqueHandler examenBiologiqueHandler;
	
	@Autowired
	PriseMedicamenteuseHandler priseMedicamenteuseHandler;
	
	@Autowired
	PersonnelHandler personnelHandler;
	
	@Autowired
	LaboratoireReferenceHandler laboratoireHandler;
	
	@Autowired
	TransfertReferenceHandler transfertReferenceHandler;
	
	@Autowired
	RegionHandler regionHandler;
	
	@Autowired
	DistrictSanteHandler districtSanteHandler;
	
	

	public void setColumn(String column, String info) {
		if (info != null)
			column = info;
		else
			column = "";
	}
	/**
	 * 
	 * @param sortProperty
	 * @param sortOrder
	 * @param patientId
	 * @param trimester
	 * @param annee
	 * @param region
	 * @param district
	 * @param cdt
	 * @param personnel
	 * @param laboratoire
	 * @param dateDebut
	 * @param dateFin
	 * @return
	 */
	private List<CasTuberculose> filterDataByParameters(String sortProperty, boolean sortOrder, 
			String patientId, String trimester, String annee, String region, String district, String cdt, String personnel, String laboratoire, 
			String dateDebut, String dateFin){

		List<LaboratoireReference> listdLaboratoires = laboratoireHandler.listLaboratoireReference(sortProperty, sortOrder);
		List<Personnel> listdPersonnel = personnelHandler.listPersonnel(sortProperty, sortOrder);		
		
//		TbRegister tbRegister;

//		List<TbRegister> listTBRegister = new ArrayList<TbRegister>();

		List<CasTuberculose> listCasTuberculoses = null;
		
		if (patientId != null && !(patientId.trim()).equals("")) {
			System.out.println("Filtrage par patient : -------------------------------------------------------------------------------");
			ImogConjunction criterion = new ImogConjunction();
			BasicCriteria criteria = new BasicCriteria();
			criteria.setField("patient.id");
			criteria.setOperation(CriteriaConstants.STRING_OPERATOR_EQUAL);
			criteria.setValue(patientId);
			criterion.add(criteria);
			listCasTuberculoses = casTuberculoseHandler.listCasTuberculose(sortProperty, sortOrder, criterion);
		} else {
			listCasTuberculoses = casTuberculoseHandler.listCasTuberculose(sortProperty, sortOrder);
		}

		if(listCasTuberculoses!=null && listCasTuberculoses.size()>0){
		
		//Filter TB cases by CDT
		if(cdt!=null && !cdt.trim().equals("")){
			System.out.println("Filtrage par cdt : -------------------------------------------------------------------------------");
			boolean trouve = false;
			List<CasTuberculose> listCasTBTmp = new ArrayList<CasTuberculose>();
			for (CasTuberculose casTuberculose : listCasTuberculoses) {
				if(casTuberculose!=null && casTuberculose.getPatient()!=null){
				List<CentreDiagTrait> listCDT = casTuberculose.getPatient().getCentres();
				if(listCDT!=null && listCDT.size()>0){
					for (CentreDiagTrait centreDiagTrait : listCDT) {
						if(centreDiagTrait.getId().trim().equals(cdt.trim())){
							trouve = true;
							listCasTBTmp.add(casTuberculose);
						}
					}
				}
			}
		}
			if (trouve){
				listCasTuberculoses = listCasTBTmp;
			}
			else{
				listCasTuberculoses = listCasTBTmp;				
			}
		}
		//Filter TB cases by District
		if(listCasTuberculoses!=null && listCasTuberculoses.size()>0 && 
				(cdt==null || cdt.trim().equals("")) && (district!=null && !district.trim().equals(""))){
			System.out.println("Filtrage par district : -------------------------------------------------------------------------------");
			boolean trouve = false;
			List<CasTuberculose> listCasTBTmp = new ArrayList<CasTuberculose>();
			for (CasTuberculose casTuberculose : listCasTuberculoses) {
				if(casTuberculose.getPatient()!=null){
				List<CentreDiagTrait> listCDT = casTuberculose.getPatient().getCentres();
				if(listCDT!=null && listCDT.size()>0){
					for (CentreDiagTrait centreDiagTrait : listCDT) {
						if(centreDiagTrait.getDistrictSante().getId().trim().equals(district.trim())){
							trouve = true;
							listCasTBTmp.add(casTuberculose);
						}
					}
				}
			}
			}
			if (trouve){
				listCasTuberculoses = listCasTBTmp;
			}
			else{
				listCasTuberculoses = listCasTBTmp;				
			}
		}
		
		//Filter TB cases by Region
		if(listCasTuberculoses!=null && listCasTuberculoses.size()>0 && 
				(cdt==null || cdt.trim().equals("")) && (district==null || district.trim().equals("")) && (region!=null && !region.trim().equals(""))){
			System.out.println("Filtrage par région : -------------------------------------------------------------------------------");
			boolean trouve = false;
			List<CasTuberculose> listCasTBTmp = new ArrayList<CasTuberculose>();
			for (CasTuberculose casTuberculose : listCasTuberculoses) {
				if(casTuberculose.getPatient()!=null){
				List<CentreDiagTrait> listCDT = casTuberculose.getPatient().getCentres();
				if(listCDT!=null && listCDT.size()>0){
					for (CentreDiagTrait centreDiagTrait : listCDT) {
						if(centreDiagTrait.getDistrictSante().getRegion().getId().trim().equals(region.trim())){
							trouve = true;
							listCasTBTmp.add(casTuberculose);
						}
					}
				}
			}
			}
			if (trouve){
				listCasTuberculoses = listCasTBTmp;
			}
			else{
				listCasTuberculoses = listCasTBTmp;				
			}
		}		

		//Filter TB cases by personnel
		if(listCasTuberculoses!=null && listCasTuberculoses.size()>0 && 
				personnel!=null && !personnel.trim().equals("")){

			boolean trouve = false;
			List<CasTuberculose> listCasTBTmp = new ArrayList<CasTuberculose>();
			for (CasTuberculose casTuberculose : listCasTuberculoses) {

				if(casTuberculose.getPatient()!=null){
				List<CentreDiagTrait> listCDT = casTuberculose.getPatient().getCentres();

				List<Personnel> listPersonnels = personnelHandler.listPersonnel(sortProperty, sortOrder);

				if(listCDT!=null && listCDT.size()>0){

					for (CentreDiagTrait centreDiagTrait : listCDT) {
						for (Personnel p : listPersonnels) {
						if(p.getCDT()!=null && p.getId().trim().equals(personnel.trim()) && p.getCDT().getId().equals(centreDiagTrait.getId())){

							trouve = true;
							listCasTBTmp.add(casTuberculose);
						}
					}
				}
				}
			}
			}
			if (trouve){
				listCasTuberculoses = listCasTBTmp;
			}
			else{
				listCasTuberculoses = listCasTBTmp;				
			}
		}

		//Filter TB cases by Laboratoire
		if((listCasTuberculoses!=null && listCasTuberculoses.size()>0 && 
				laboratoire !=null && !laboratoire.trim().equals(""))){

			boolean trouve = false;
			List<CasTuberculose> listCasTBTmp = new ArrayList<CasTuberculose>();
			for (CasTuberculose casTuberculose : listCasTuberculoses) {

				if(casTuberculose.getPatient()!=null){
				List<CentreDiagTrait> listCDT = casTuberculose.getPatient().getCentres();
				List<LaboratoireReference> listLaboratoire =laboratoireHandler.listLaboratoireReference(sortProperty, sortOrder);
				if(listCDT!=null && listCDT.size()>0){
					for (CentreDiagTrait centreDiagTrait : listCDT) {
						for (LaboratoireReference lab: listLaboratoire) {
						if(lab.getId().trim().equals(laboratoire) && lab.getDistrictSante().getId().equals(centreDiagTrait.getDistrictSante().getId())){
							trouve = true;
							listCasTBTmp.add(casTuberculose);
						}
					}
				}
				}
			}
			}
			if (trouve){
				listCasTuberculoses = listCasTBTmp;
			}
			else{
				listCasTuberculoses = listCasTBTmp;				
			}
		}
//		//Filter TB cases by quarter
//Get the quarter
//		int quarter = ((new Date()).getMonth() / 3) + 1;
		
		if(listCasTuberculoses!=null && listCasTuberculoses.size()>0 && 
				trimester!=null && !trimester.trim().equals("")){

			int quarter =1;
			int t = 1;
			if(trimester.equals("trimestre1")){
				t = 1;
			}else{
				if(trimester.equals("trimestre2")){
					t = 2;
				}else{
					if(trimester.equals("trimestre3")){
						t = 3;
					}else{
						if(trimester.equals("trimestre4")){
							t = 4;
						}
					}
				}
			}
			System.out.println("*********************************Trimestre sélectioné : "+trimester);
//			int 
			boolean trouve = false;
			List<CasTuberculose> listCasTBTmp = new ArrayList<CasTuberculose>();
			for (CasTuberculose casTuberculose : listCasTuberculoses) {
				quarter = (((casTuberculose.getCreated()).getMonth())/3)+1;
				System.out.println("*********************************Trimestre d'enregistrement du cas : "+quarter);
				if(quarter == t){
					System.out.println("*********************************Patient inclu dans le trimestre sélectionné : "+quarter);
					trouve = true;
					listCasTBTmp.add(casTuberculose);
				}
			}
			if (trouve){
				System.out.println("Le trimestres contient des patients");
				listCasTuberculoses = listCasTBTmp;
			}
			else{
				System.out.println("Le trimestres ne contient pas des patients");
				listCasTuberculoses = listCasTBTmp;				
			}
		}
		
//		//Select by years

		if(listCasTuberculoses!=null && listCasTuberculoses.size()>0 && 
				annee!=null && !annee.trim().equals("")){
			System.out.println("Filtrage par année : -------------------------------------------------------------------------------");
			int year =0;
			int a = Integer.parseInt(annee);
			boolean trouve = false;
			List<CasTuberculose> listCasTBTmp = new ArrayList<CasTuberculose>();
			for (CasTuberculose casTuberculose : listCasTuberculoses) {
				year = (casTuberculose.getCreated()).getYear()+1900;
//				System.out.println("+++++++++++++++L'année du malade est : "+year);
				if(year == a){
					trouve = true;
					listCasTBTmp.add(casTuberculose);
				}
			}
			if (trouve){
				listCasTuberculoses = listCasTBTmp;
			}
			else{
				listCasTuberculoses = listCasTBTmp;				
			}
		}
//
//
//		//Select by intervale date
//
		if(listCasTuberculoses!=null && listCasTuberculoses.size()>0 && 
				(dateDebut!=null && !dateDebut.trim().equals("")) && (dateFin!=null && !dateFin.trim().equals(""))){

			Date dd = new Date(); // = new Date(dateDebut);
			Date df = new Date(); // = new Date(dateFin);
			Date myDate = new Date();
			
			try {
			      dd = new SimpleDateFormat("dd/MM/yyyy").parse(dateDebut.trim());
			      df = new SimpleDateFormat("dd/MM/yyyy").parse(dateFin.trim());
			    } catch (ParseException e) {
			      e.printStackTrace();
			    }
			  

			
			boolean trouve = false;
			List<CasTuberculose> listCasTBTmp = new ArrayList<CasTuberculose>();
			for (CasTuberculose casTuberculose : listCasTuberculoses) {
				myDate = casTuberculose.getCreated();
				if(myDate.before(df) && myDate.after(dd)){
					trouve = true;
					listCasTBTmp.add(casTuberculose);
				}
			}
			if (trouve){
				listCasTuberculoses = listCasTBTmp;
			}
			else{
				listCasTuberculoses = listCasTBTmp;				
			}
		}
//		else{
			if(listCasTuberculoses!=null && listCasTuberculoses.size()>0 && 
					(dateDebut!=null && !dateDebut.trim().equals("")) && (dateFin==null || dateFin.trim().equals(""))){
				
				Date dd = new Date(); // = new Date(dateDebut);
				Date df = new Date(); // = new Date(dateFin);
				Date myDate = new Date();
				
				try {
				      dd = new SimpleDateFormat("dd/MM/yyyy").parse(dateDebut.trim());
				    } catch (ParseException e) {
				      e.printStackTrace();
				    }

				
				boolean trouve = false;
				List<CasTuberculose> listCasTBTmp = new ArrayList<CasTuberculose>();
				for (CasTuberculose casTuberculose : listCasTuberculoses) {
					myDate = casTuberculose.getCreated();
					if(myDate.after(dd)){
						trouve = true;
						listCasTBTmp.add(casTuberculose);
					}
				}
				if (trouve){
					listCasTuberculoses = listCasTBTmp;
				}
				else{
					listCasTuberculoses = listCasTBTmp;				
				}
			}
//			else{
				if(listCasTuberculoses!=null && listCasTuberculoses.size()>0 && 
						(dateDebut==null || dateDebut.trim().equals("")) && (dateFin!=null && !dateFin.trim().equals(""))){
					

					Date dd = new Date(); // = new Date(dateDebut);
					Date df = new Date(); // = new Date(dateFin);
					Date myDate = new Date();
					
					try {
					      df = new SimpleDateFormat("dd/MM/yyyy").parse(dateFin.trim());
					    } catch (ParseException e) {
					      e.printStackTrace();
					    }
					
					
					boolean trouve = false;
					List<CasTuberculose> listCasTBTmp = new ArrayList<CasTuberculose>();
					for (CasTuberculose casTuberculose : listCasTuberculoses) {
						myDate = casTuberculose.getCreated();
						if(myDate.before(df)){
							trouve = true;
							listCasTBTmp.add(casTuberculose);
						}
					}
					if (trouve){
						listCasTuberculoses = listCasTBTmp;
					}
					else{
						listCasTuberculoses = listCasTBTmp;				
					}
				}
//			}
//		}
		}
		return listCasTuberculoses;
	}

	/**
	 * Get all informations to build a register
	 * 
	 * @param sortProperty
	 * @param sortOrder
	 * @return
	 */

//	@Transactional
	public List<TbRegister> getTBRegister(String sortProperty, boolean sortOrder, 
			String patientId, String trimester, String annee, String region, String district, String cdt, String personnel, String laboratoire, 
			String dateDebut, String dateFin) {		

		
		
		TbRegister tbRegister;
//
		List<TbRegister> listTBRegister = new ArrayList<TbRegister>();
//
		List<CasTuberculose> listCasTuberculoses = filterDataByParameters(sortProperty, sortOrder, patientId, trimester, annee, 
				region, district, cdt, personnel, laboratoire, dateDebut, dateFin);
		List<ExamenSerologie> listeExamenSerologies = null;
//		
//		if (patientId != null && !(patientId.trim()).equals("")) {
//			ImogConjunction criterion = new ImogConjunction();
//			BasicCriteria criteria = new BasicCriteria();
//			criteria.setField("patient.id");
//			criteria.setOperation(CriteriaConstants.STRING_OPERATOR_EQUAL);
//			criteria.setValue(patientId);
//			criterion.add(criteria);
//			listeExamenSerologies = examenSerologieHandler.listExamenSerologie(sortProperty, sortOrder, criterion);
//		} else {
//			listeExamenSerologies = examenSerologieHandler.listExamenSerologie(sortProperty, sortOrder);
//		}		
		List<ExamenMicroscopie> listExamenMicroscopies = null;
//		if (patientId != null && !(patientId.trim()).equals("")) {
//			ImogConjunction criterion = new ImogConjunction();
//			BasicCriteria criteria = new BasicCriteria();
//			criteria.setField("casTb.patient.id");
//			criteria.setOperation(CriteriaConstants.STRING_OPERATOR_EQUAL);
//			criteria.setValue(patientId);
//			criterion.add(criteria);
//			listExamenMicroscopies = examenMicroscopieHandler.listExamenMicroscopie(sortProperty, sortOrder, criterion);
//		} else {
//			listExamenMicroscopies = examenMicroscopieHandler.listExamenMicroscopie(sortProperty, sortOrder);
//		}
		Integer anneeInt = null;
		try {
			anneeInt = Integer.parseInt(annee);
		} catch (NumberFormatException e) {
			LOGGER.debug("annee is not a number");
		}
		if (anneeInt != null && trimester != null) {
			LOGGER.debug("Trimestre= " + trimester);
			if (trimester.equals("trimestre1")) {
				LOGGER.debug("First quarter of year " + anneeInt);
			}else if (trimester.equals("trimestre2")) {
				LOGGER.debug("Second quarter of year" + anneeInt);
			}else if (trimester.equals("trimestre3")) {
				LOGGER.debug("Third quarter of year " + anneeInt);
			} else if (trimester.equals("trimestre4")) {
				LOGGER.debug("Fourth quarter of year " + anneeInt);
			}
		}

//
		Date columnAnneeRegistre = new Date();
		System.out.println("Date d'édition du rapport : " + columnAnneeRegistre);
		String anneeRegistre = "" + columnAnneeRegistre.getYear();
		System.out.println("L'année obtenue : " + columnAnneeRegistre);

		System.out.println("*************************************************La liste des cas de tuberculose : "+listCasTuberculoses);
		
		for (CasTuberculose casTuberculose : listCasTuberculoses) {
			
			if(casTuberculose.getPatient()!=null){
				
			tbRegister = new TbRegister();
			tbRegister.setColumnAnneeRegistre(anneeRegistre);

			if (casTuberculose.getCreated() != null){
				tbRegister.setColumnMonth(casTuberculose.getCreated().getMonth());
				tbRegister.setColumnDateEnregistrement(""+casTuberculose.getCreated());
			}
			else
				tbRegister.setColumnDateEnregistrement("");

			if (casTuberculose.getNumRegTB() != null)
				tbRegister.setColumnNumRegLabo(casTuberculose.getNumRegTB());
			else
				tbRegister.setColumnNumRegLabo("");

			if (casTuberculose.getPatient().getNom() != null)
				tbRegister.setColumnNom(casTuberculose.getPatient().getNom());
			else
				tbRegister.setColumnNom("");

			if (casTuberculose.getPatient().getSexe() != null)
				tbRegister.setColumnSexe(casTuberculose.getPatient().getSexe());
			else
				tbRegister.setColumnSexe("");

			if (casTuberculose.getPatient().getAge() != null)
				tbRegister.setColumnAge(casTuberculose.getPatient().getAge());
			else
				tbRegister.setColumnAge(-99);

			if (casTuberculose.getPatient().getVille() != null && casTuberculose.getPatient().getTelephoneUn() != null)
				tbRegister.setColumnAdresse(casTuberculose.getPatient().getVille() + ", "
						+ casTuberculose.getPatient().getTelephoneUn());
			else {
				if (casTuberculose.getPatient().getVille() != null)
					tbRegister.setColumnAdresse(casTuberculose.getPatient().getVille());
				else {
					if (casTuberculose.getPatient().getTelephoneUn() != null)
						tbRegister.setColumnAdresse(casTuberculose.getPatient().getTelephoneUn());
					else
						tbRegister.setColumnAdresse("");
				}
			}
			//Add the list of report

			if (casTuberculose != null && casTuberculose.getPatient() != null) {
				if (casTuberculose.getPatient().getCentres() != null) {
					int index = casTuberculose.getPatient().getCentres().size();
//					System.out.println("*****************Les CDTs-La valeur de l'index est de : "
//							+ index);
					if (index >= 1)
						tbRegister.setColumnCDTMalade(casTuberculose.getPatient().getCentres().get(index - 1).getNom());
				}
			}
			if (casTuberculose.getDateDebutTraitement() != null)
				tbRegister.setColumnDateDebutTrait(""+casTuberculose.getDateDebutTraitement());
			else
				tbRegister.setColumnDateDebutTrait("");

			if (casTuberculose.getRegimePhaseInitiale() != null && casTuberculose.getRegimePhaseContinuation() != null)
				tbRegister.setColumnRegime(casTuberculose.getRegimePhaseInitiale().getNom() + ", "
						+ casTuberculose.getRegimePhaseContinuation().getNom());
			else {
				if (casTuberculose.getRegimePhaseInitiale() != null)
					tbRegister.setColumnRegime(casTuberculose.getRegimePhaseInitiale().getNom());
				else {
					if (casTuberculose.getRegimePhaseContinuation() != null)
						tbRegister.setColumnRegime(casTuberculose.getRegimePhaseContinuation().getNom());
					else
						tbRegister.setColumnRegime("");
				}
			}

//			// if(info!=null)
//			// column = info;
//			// else column="";
//
			if (casTuberculose.getFormeMaladie() != null)
				tbRegister.setColumnForme(casTuberculose.getFormeMaladie());
			else
				tbRegister.setColumnForme("");

			if (casTuberculose.getTypePatient() != null)
				tbRegister.setColumnType(casTuberculose.getTypePatient());
			else
				tbRegister.setColumnType("");

			if (casTuberculose.getDevenirMalade() != null)
				tbRegister.setColumnIssuTraitement(casTuberculose.getDevenirMalade());
			else
				tbRegister.setColumnIssuTraitement("");

			if (casTuberculose.getObservation() != null)
				tbRegister.setColumnObservation(casTuberculose.getObservation());
			else
				tbRegister.setColumnObservation("");
			//adding new element into the list
			listTBRegister.add(tbRegister);
		}
	}
		System.out.println("**********************Fin de la méthode getTBRegister : " + listTBRegister.size());
		return listTBRegister;
	}

	public List<ExamenCrachats> getExamenCrachats(String sortProperty, boolean sortOrder, String patientId, String trimester, 
			String annee, String region, String district, String cdt, String personnel, String laboratoire, String dateDebut, String dateFin){
		
		ExamenCrachats examenCrachats;
		List<ExamenCrachats> listExamenCrachats = new ArrayList<ExamenCrachats>();
		
		List<CasTuberculose> listCasTuberculoses = filterDataByParameters(sortProperty, sortOrder, patientId, trimester, annee, 
				region, district, cdt, personnel, laboratoire, dateDebut, dateFin);
		
		List<ExamenMicroscopie> listExamenMicroscopies;
		
		for (CasTuberculose casTuberculose : listCasTuberculoses) {
			
			if(casTuberculose.getPatient()!=null){
				examenCrachats = new ExamenCrachats();
				
				if (casTuberculose.getNumRegTB() != null)
					examenCrachats.setcolumnNumRegTBPatient(casTuberculose.getNumRegTB());
				else
					examenCrachats.setcolumnNumRegTBPatient("");

				if (casTuberculose.getPatient().getNom() != null)
					examenCrachats.setColumnNomPatient(casTuberculose.getPatient().getNom());
				else
					examenCrachats.setColumnNomPatient("");
				if(casTuberculose.getExamensMiscrocopies()!=null && casTuberculose.getExamensMiscrocopies().size()>0){
					listExamenMicroscopies = casTuberculose.getExamensMiscrocopies();
					//get exams and add to report
					for (ExamenMicroscopie examenMicroscopie : listExamenMicroscopies) {
						if(examenMicroscopie.getDate()!=null)
							examenCrachats.setColumnDate(examenMicroscopie.getDate());
						if(examenMicroscopie.getRaisonDepistage()!=null)
							examenCrachats.setColumnRaisonExam(examenMicroscopie.getRaisonDepistage());
						if(examenMicroscopie.getResultat()!=null)
							examenCrachats.setColumnResultat(examenMicroscopie.getResultat());
						listExamenCrachats.add(examenCrachats);						
					}
				}
				else
					listExamenCrachats.add(examenCrachats);					
			}
			
		}		
		System.out.println("xxxxxxxxxxxxxxxx La taille de la liste des examens microscopiques : "+listExamenCrachats.size());
		return listExamenCrachats;
	}

	public List<ExamenCrachats> getExamenSerologie(String sortProperty, boolean sortOrder, String patientId, String trimester, 
			String annee, String region, String district, String cdt, String personnel, String laboratoire, String dateDebut, String dateFin){
		
	
		return null;
	}

	/**
	 * Uses to get all informations usefull to build a child register
	 * 
	 * @param sortProperty
	 * @param sortOrder
	 * @return
	 */
	public List<TbLabRegister> getTBLabRegister(String sortProperty, boolean sortOrder, String patientId, String trimester, 
			String annee, String region, String district, String cdt, String personnel, String laboratoire, String dateDebut, String dateFin) {

		TbLabRegister tbLabRegister;

		List<TbLabRegister> listtbTbLabRegisters = new ArrayList<TbLabRegister>();
		List<CasTuberculose> listCasTuberculoses = this.filterDataByParameters(sortProperty, sortOrder, patientId, trimester, 
				annee, region, district, cdt, personnel, laboratoire, dateDebut, dateFin);
		List<ExamenMicroscopie> listExamenMicroscopies = examenMicroscopieHandler
				.listExamenMicroscopie(sortProperty, sortOrder);
		List<ExamenSerologie> listeExamenSerologies = examenSerologieHandler
				.listExamenSerologie(sortProperty, sortOrder);

		for (CasTuberculose casTuberculose : listCasTuberculoses) {
			if(casTuberculose.getPatient()!=null){
			tbLabRegister = new TbLabRegister();

			if (casTuberculose.getCreated() != null){
				tbLabRegister.setColumnMonth(casTuberculose.getCreated().getMonth());
				tbLabRegister.setColumnDate(""+casTuberculose.getCreated());
			}
			else
				tbLabRegister.setColumnDate("");

			if (casTuberculose.getNumRegTB() != null)
				tbLabRegister.setColumnNumLab(casTuberculose.getNumRegTB());
			else
				tbLabRegister.setColumnNumLab("");

			if (casTuberculose.getPatient().getNom() != null)
				tbLabRegister
						.setColumnNom(casTuberculose.getPatient().getNom());
			else
				tbLabRegister.setColumnNom("");

			if (casTuberculose.getPatient().getSexe() != null)
				tbLabRegister.setColumnSexe(casTuberculose.getPatient()
						.getSexe());
			else
				tbLabRegister.setColumnSexe("");

			if (casTuberculose.getPatient().getAge() != null)
				tbLabRegister
						.setColumnAge(casTuberculose.getPatient().getAge());
			else
				tbLabRegister.setColumnAge(-99);

			if (casTuberculose.getPatient().getVille() != null
					&& casTuberculose.getPatient().getTelephoneUn() != null)
				tbLabRegister.setColumnAdresse(casTuberculose.getPatient()
						.getVille()
						+ ", "
						+ casTuberculose.getPatient().getTelephoneUn());
			else {
				if (casTuberculose.getPatient().getVille() != null)
					tbLabRegister.setColumnAdresse(casTuberculose.getPatient()
							.getVille());
				else {
					if (casTuberculose.getPatient().getTelephoneUn() != null)
						tbLabRegister.setColumnAdresse(casTuberculose
								.getPatient().getTelephoneUn());
					else
						tbLabRegister.setColumnAdresse("");
				}
			}

			if (casTuberculose != null && casTuberculose.getPatient() != null) {
				if (casTuberculose.getPatient().getCentres() != null) {
					int index = casTuberculose.getPatient().getCentres().size();
					System.out
							.println("--------------------------------------------La valeur de l'index est de : "
									+ index);
					if (index >= 1)
						tbLabRegister.setColumnNomCDT(casTuberculose
								.getPatient().getCentres().get(index - 1)
								.getNom());
				}
				if (casTuberculose.getObservation() != null)
					tbLabRegister.setColumnObservations(casTuberculose
							.getObservation());
				else
					tbLabRegister.setColumnObservations("");

				// **************************************Les examens sont les
				// éléménts les plus importantes ici**************************//
				// //If sereologie is not null, then try to find if another
				// exams
				// are not null and add in the list
				if (casTuberculose.getPatient().getSerologies() != null
						&& casTuberculose.getPatient().getSerologies().size() > 0) {
					listeExamenSerologies = casTuberculose.getPatient().getSerologies();
					for (ExamenSerologie examenSerologie : listeExamenSerologies) {

						tbLabRegister.setColumnDateSerologie(""
								+ examenSerologie.getDateTest());
						tbLabRegister
								.setColumnResultatSerologie(examenSerologie
										.getResultatVIH());// +", CD4 : "+examenSerologie.getResultatCD4());
						tbLabRegister.setColumnNombreCD4Serologie(""
								+ examenSerologie.getResultatCD4());

						if (casTuberculose.getExamensMiscrocopies() != null
								&& casTuberculose.getExamensMiscrocopies()
										.size() > 0) {
							listExamenMicroscopies = casTuberculose.getExamensMiscrocopies();
							for (ExamenMicroscopie examenMicroscopie : listExamenMicroscopies) {
								tbLabRegister.setColumnDateExamenCrachat(""
										+ examenMicroscopie.getDate());
								tbLabRegister
										.setColumnResultatExamenCrachat(examenMicroscopie
												.getResultat());
								listtbTbLabRegisters.add(tbLabRegister);
							}
						} else
							listtbTbLabRegisters.add(tbLabRegister);
					}
				}
				// // See if ATB exam list is not null, then, find serology and
				// add
				// to the list
				else {
					if (casTuberculose.getExamensMiscrocopies() != null
							&& casTuberculose.getExamensMiscrocopies().size() > 0) {
						System.out
								.println("-------------------Examen microscopie : "
										+ casTuberculose
												.getExamensMiscrocopies()
												.get(0).getDate());
						listExamenMicroscopies = casTuberculose.getExamensMiscrocopies();
						for (ExamenMicroscopie examenMicroscopie : listExamenMicroscopies) {
							tbLabRegister.setColumnDateExamenCrachat(""
									+ examenMicroscopie.getDate());
							tbLabRegister
									.setColumnResultatExamenCrachat(examenMicroscopie
											.getResultat());
							// Test if there are some serology
							if (casTuberculose.getPatient().getSerologies() != null
									&& casTuberculose.getPatient()
											.getSerologies().size() > 0) {

								listeExamenSerologies = casTuberculose.getPatient().getSerologies();
								for (ExamenSerologie examenSerologie : listeExamenSerologies) {
									tbLabRegister.setColumnDateSerologie(""
											+ examenSerologie.getDateTest());
									tbLabRegister
											.setColumnResultatSerologie(examenSerologie
													.getResultatVIH());// +", CD4 : "+examenSerologie.getResultatCD4());
									tbLabRegister
											.setColumnNombreCD4Serologie(""
													+ examenSerologie
															.getResultatCD4());
									listtbTbLabRegisters.add(tbLabRegister);
								}
							} else {
								listtbTbLabRegisters.add(tbLabRegister);
							}
						}

					} else
						listtbTbLabRegisters.add(tbLabRegister);
					System.out.println("La taille de la liste : "
							+ listtbTbLabRegisters.size());
				}
			}
		}
		}
		//

		return listtbTbLabRegisters;
	}
/**
 * 
 * @param sortProperty
 * @param sortOrder
 * @param patientId
 * @param trimester
 * @param annee
 * @param region
 * @param district
 * @param cdt
 * @param personnel
 * @param laboratoire
 * @param dateDebut
 * @param dateFin
 * @return
 */
	public List<TbChildRegister> getTBChildRegister(String sortProperty, boolean sortOrder,String  patientId, String trimester, String annee, 
			String region, String district, String cdt, String personnel, String laboratoire, String dateDebut, String dateFin) {

		TbChildRegister tbChildRegister;

		List<TbChildRegister> listTbChildRegisters = new ArrayList<TbChildRegister>();
		// Get all patients when thier age is less or equal to 15
		List<CasTuberculose> listCasTuberculoses = getChilTB(this.filterDataByParameters(sortProperty, sortOrder, patientId, trimester, annee, 
				region, district, cdt, personnel, laboratoire, dateDebut, dateFin));
		List<ExamenMicroscopie> listExamenMicroscopies = examenMicroscopieHandler
				.listExamenMicroscopie(sortProperty, sortOrder);
		List<ExamenSerologie> listeExamenSerologies = examenSerologieHandler
				.listExamenSerologie(sortProperty, sortOrder);

		for (CasTuberculose casTuberculose : listCasTuberculoses) {
			tbChildRegister = new TbChildRegister();

			if (casTuberculose.getCreated() != null){
				tbChildRegister.setColumnMonth(casTuberculose.getCreated().getMonth());
				tbChildRegister.setColumnDate(""+ casTuberculose.getCreated());
			}
			else
				tbChildRegister.setColumnDate("");

			if (casTuberculose.getNumRegTB() != null)
				tbChildRegister
						.setColumnNumero(casTuberculose.getNumRegTB());
			else
				tbChildRegister.setColumnNumero("");

			if (casTuberculose.getPatient().getNom() != null)
				tbChildRegister.setColumnNom(casTuberculose.getPatient()
						.getNom());
			else
				tbChildRegister.setColumnNom("");

			if (casTuberculose.getPatient().getSexe() != null)
				tbChildRegister.setColumnSexe(casTuberculose.getPatient()
						.getSexe());
			else
				tbChildRegister.setColumnSexe("");

			if (casTuberculose.getPatient().getAge() != null)
				tbChildRegister.setColumnAge(casTuberculose.getPatient()
						.getAge());
			else
				tbChildRegister.setColumnAge(-99);

			if (casTuberculose.getPatient().getVille() != null
					&& casTuberculose.getPatient().getTelephoneUn() != null)
				tbChildRegister.setColumnAdresse(casTuberculose.getPatient()
						.getVille()
						+ ", "
						+ casTuberculose.getPatient().getTelephoneUn());
			else {
				if (casTuberculose.getPatient().getVille() != null)
					tbChildRegister.setColumnAdresse(casTuberculose
							.getPatient().getVille());
				else {
					if (casTuberculose.getPatient().getTelephoneUn() != null)
						tbChildRegister.setColumnAdresse(casTuberculose
								.getPatient().getTelephoneUn());
					else
						tbChildRegister.setColumnAdresse("");
				}
			}

			if (casTuberculose != null && casTuberculose.getPatient() != null) {
				if (casTuberculose.getPatient().getCentres() != null) {
					int index = casTuberculose.getPatient().getCentres().size();
					System.out
							.println("--------------------------------------------La valeur de l'index est de : "
									+ index);
					if (index >= 1)
						tbChildRegister.setColumnNomCDT(casTuberculose
								.getPatient().getCentres().get(index - 1)
								.getNom());
				}
			}
			if (casTuberculose.getDateDebutTraitement() != null)
				tbChildRegister.setColumnDateDebutTrait(""
						+ casTuberculose.getDateDebutTraitement().getDate());
			else
				tbChildRegister.setColumnDateDebutTrait("");

			if (casTuberculose.getRegimePhaseInitiale() != null
					&& casTuberculose.getRegimePhaseContinuation() != null)
				tbChildRegister.setColumnRegime(casTuberculose
						.getRegimePhaseInitiale().getNom()
						+ ", "
						+ casTuberculose.getRegimePhaseContinuation().getNom());
			else {
				if (casTuberculose.getRegimePhaseInitiale() != null)
					tbChildRegister.setColumnRegime(casTuberculose
							.getRegimePhaseInitiale().getNom());
				else {
					if (casTuberculose.getRegimePhaseContinuation() != null)
						tbChildRegister.setColumnRegime(casTuberculose
								.getRegimePhaseContinuation().getNom());
					else
						tbChildRegister.setColumnRegime("");
				}
			}

			// // if(info!=null)
			// // column = info;
			// // else column="";
			//
			if (casTuberculose.getFormeMaladie() != null)
				tbChildRegister
						.setColumnForme(casTuberculose.getFormeMaladie());
			else
				tbChildRegister.setColumnForme("");

			if (casTuberculose.getTypePatient() != null)
				tbChildRegister.setColumnType(casTuberculose.getTypePatient());
			else
				tbChildRegister.setColumnType("");

			if (casTuberculose.getDevenirMalade() != null)
				tbChildRegister.setColumnIssuTraitement(casTuberculose
						.getDevenirMalade());
			else
				tbChildRegister.setColumnIssuTraitement("");

			if (casTuberculose.getObservation() != null)
				tbChildRegister.setColumnObservations(casTuberculose
						.getObservation());// ;(casTuberculose.getObservation());
			else
				tbChildRegister.setColumnObservations("");

			// Affiche la liste des cas d'index

			if (casTuberculose.getPatient().getCasIndex() != null) {
				List<CasIndex> listCasIndex = casTuberculose.getPatient()
						.getCasIndex();
				String nomCasIndex = "";
				String typeRelation = "";
				for (CasIndex casIndex : listCasIndex) {
					nomCasIndex = nomCasIndex + ";  "
							+ casIndex.getPatient().getNom();
					typeRelation = typeRelation + ";  "
							+ casIndex.getTypeRelation();
				}
				tbChildRegister.setColumnNomCasIndex(nomCasIndex);
				tbChildRegister.setColumnTypeRelation(typeRelation);

			}
			listTbChildRegisters.add(tbChildRegister);
		}

		return listTbChildRegisters;

	}

	/**
	 * Get all patient when thier age is less than 15 years old
	 * 
	 * @param listTB
	 * @return
	 */
	public List<CasTuberculose> getChilTB(List<CasTuberculose> listTB) {
		List<CasTuberculose> lisTBChild = new ArrayList<CasTuberculose>();
		System.out.println("xxxxxxxxxxxLa liste à filtrer pour le registre de la tb de l'enfant : "+listTB);
		for (CasTuberculose casTuberculose : listTB) {
			System.out.println("Le patient du cas de tb : "+casTuberculose.getPatient());
			if (casTuberculose.getPatient()!=null && casTuberculose.getPatient().getAge() != null
					&& casTuberculose.getPatient().getAge() <= 15)
				lisTBChild.add(casTuberculose);
		}

		return lisTBChild;
	}
/**
 * 
 * @param sortProperty
 * @param sortOrder
 * @return
 */
	public List<TrimesterDetectReport> getTrimDetectReportData(String sortProperty, boolean sortOrder,String  patientId, String trimester, String annee, 
			String region, String district, String cdt, String personnel, String laboratoire, String dateDebut, String dateFin) {
		List<TrimesterDetectReport> report = new ArrayList<TrimesterDetectReport>();
		TrimesterDetectReport trimesterReport;
		List<CasTuberculose> listCasTuberculoses = this.filterDataByParameters(sortProperty, sortOrder, patientId, trimester, annee, region, district, 
				cdt, personnel, laboratoire, dateDebut, dateFin);
		List<ExamenSerologie> listeExamenSerologies = examenSerologieHandler
				.listExamenSerologie(sortProperty, sortOrder);
		List<ExamenMicroscopie> listExamenMicroscopies = examenMicroscopieHandler
				.listExamenMicroscopie(sortProperty, sortOrder);

		//For statistics
		//For type of cases
		int nouveauCas = 0;
		int rechuttes = 0;
		int echecs = 0;
		int repriseTrait = 0;
		
		int tpmMoinsInf15 = 0;
		int tpmMoinsSup15 = 0;
		int tep = 0;
		int totalDesCas = 0;
		int cas0_4 = 0;
		int cas5_14 = 0;
		int vihTPMPlusNouveauxFait = 0;
		int vihTPMPlusPositif = 0;
		

		trimesterReport = new TrimesterDetectReport();
		for (CasTuberculose casTuberculose : listCasTuberculoses) {
			if(casTuberculose.getPatient()!=null){
			
			totalDesCas++;
			
			int typeCAs = Integer.parseInt(casTuberculose.getTypePatient());
			LOGGER.debug("xxxxxxxxxx Type de cas : "+typeCAs);
			switch (typeCAs) {
			case 0:
				nouveauCas++;
				LOGGER.debug("xxxxxxxxxNombre de nouveaux cas : "+nouveauCas);
				break;
			case 1:
				repriseTrait++;
				LOGGER.debug("xxxxxxxxxNombre de retraitement cas : "+repriseTrait);
				break;
			case 2:
				echecs++;
				break;
			case 3:
				rechuttes++;
				break;
			default:
				break;
			}

			if(casTuberculose.getFormeMaladie()!=null && casTuberculose.getFormeMaladie().equals("2")){
				tep++;
			}
			LOGGER.debug("-------------------------------patient age : "+casTuberculose.getPatient().getAge()+"and patient name : "+casTuberculose.getPatient().getNom()
					+"Forme de la maladie : "+casTuberculose.getFormeMaladie());
//			if(casTuberculose.getPatient().getAge()!=null && casTuberculose.getPatient().getAge()<15){
			if(casTuberculose.getPatient().getAge()!=null && casTuberculose.getPatient().getAge()<15){
				tpmMoinsInf15++;
			}
			if(casTuberculose.getPatient().getAge()!=null && casTuberculose.getPatient().getAge()>=15){
				tpmMoinsSup15++;
			}
			

			if(casTuberculose.getPatient().getAge()!=null && casTuberculose.getPatient().getAge()>0 && casTuberculose.getPatient().getAge()<=4){
				cas0_4++;
			}
			if(casTuberculose.getPatient().getAge()!=null && casTuberculose.getPatient().getAge()>=5 && casTuberculose.getPatient().getAge()<=14){
				cas5_14++;
			}
			
			List<ExamenSerologie> listSerologies = casTuberculose.getPatient().getSerologies();
			if(listSerologies !=null && listSerologies.size()>0){
				
				for (ExamenSerologie examenSerologie : listSerologies) {
					if(examenSerologie!=null){
						vihTPMPlusNouveauxFait++;
					}
					if(examenSerologie.getResultatVIH()!=null && examenSerologie.getResultatVIH().equals("0")){
						vihTPMPlusPositif++;
					}
				}
				
			}
			trimesterReport.setColumnNouveauCas(nouveauCas);
			trimesterReport.setColumnRechuttes(rechuttes);
			trimesterReport.setColumnEchecs(echecs);
			trimesterReport.setColumnRepriseTrait(repriseTrait);
			
			trimesterReport.setColumnTpmMoinsInf15(tpmMoinsInf15);
			trimesterReport.setColumnTpmMoinsSup15(tpmMoinsSup15);
			
			trimesterReport.setColumnTep(tep);

			trimesterReport.setColumnTotalDesCas(totalDesCas);
			
			trimesterReport.setColumnCas0_4(cas0_4);
			trimesterReport.setColumnCas5_14(cas5_14);
			
			trimesterReport.setColumnVihTPMPlusPositif(vihTPMPlusPositif);
			trimesterReport.setColumnVihTPMPlusNouveauxFait(vihTPMPlusNouveauxFait);
			
			
			LOGGER.debug("Nombre de nouveaux cas : "+trimesterReport.getColumnNouveauCas());
			 
		}
		}
		report.add(trimesterReport);
		return report;

	}
	//For trimester detect report graphics

	public List<TrimesterDetectReport> getTrimDetectReport(String sortProperty, boolean sortOrder,String  patientId, String trimester, String annee, 
			String region, String district, String cdt, String personnel, String laboratoire, String dateDebut, String dateFin) {
		List<TrimesterDetectReport> report = new ArrayList<TrimesterDetectReport>();
		TrimesterDetectReport trimesterReport;
		List<CasTuberculose> listCasTuberculoses = this.filterDataByParameters(sortProperty, sortOrder, patientId, trimester, annee, region, district, 
				cdt, personnel, laboratoire, dateDebut, dateFin);
		List<ExamenSerologie> listeExamenSerologies = examenSerologieHandler
				.listExamenSerologie(sortProperty, sortOrder);
		List<ExamenMicroscopie> listExamenMicroscopies = examenMicroscopieHandler
				.listExamenMicroscopie(sortProperty, sortOrder);

		//For statistics
		//For type of cases
		int nouveauCas = 0;
		int rechuttes = 0;
		int echecs = 0;
		int repriseTrait = 0;
		
		int tpmMoinsInf15 = 0;
		int tpmMoinsSup15 = 0;
		int tep = 0;
		int totalDesCas = 0;
		int cas0_4 = 0;
		int cas5_14 = 0;
		int vihTPMPlusNouveauxFait = 0;
		int vihTPMPlusPositif = 0;
		
		
		for (CasTuberculose casTuberculose : listCasTuberculoses) {
			if(casTuberculose.getPatient()!=null){
			trimesterReport = new TrimesterDetectReport();
			// private String columnDateEnregistrement;
			
			if(casTuberculose.getCreated()!=null){
				trimesterReport.setColumnMonth(casTuberculose.getCreated().getMonth());
				trimesterReport.setColumnDateEnregistrement(casTuberculose.getCreated());
			}
			
			totalDesCas++;
			
			if(casTuberculose.getIdentifiant()!=null)
				trimesterReport.setColumnNumTB(casTuberculose.getIdentifiant());
			
			if (casTuberculose.getPatient().getAge() != null){
				trimesterReport.setColumnAge(casTuberculose.getPatient().getAge());
				trimesterReport.setColumnTrancheAge(casTuberculose.getPatient().getAge());
			}
			else
				trimesterReport.setColumnAge(-99);

			if (casTuberculose.getPatient().getSexe() != null){
				trimesterReport.setColumnSexe(casTuberculose.getPatient().getSexe());
				trimesterReport.setColumnReadableSexe(casTuberculose.getPatient().getSexe());
			}
			else
				trimesterReport.setColumnSexe("");

			if (casTuberculose.getTypePatient() != null)
				trimesterReport.setColumnTypePatient(casTuberculose
						.getTypePatient());
			else
				trimesterReport.setColumnTypePatient("");

			if (casTuberculose.getFormeMaladie() != null)
				trimesterReport.setColumnFormeMaladie(casTuberculose
						.getFormeMaladie());
			else
				trimesterReport.setColumnFormeMaladie("");

			int typeCAs = Integer.parseInt(casTuberculose.getTypePatient());
			LOGGER.debug("xxxxxxxxxx Type de cas : "+typeCAs);
			switch (typeCAs) {
			case 0:
				nouveauCas++;
				LOGGER.debug("xxxxxxxxxNombre de nouveaux cas : "+nouveauCas);
				break;
			case 1:
				repriseTrait++;
				LOGGER.debug("xxxxxxxxxNombre de retraitement cas : "+repriseTrait);
				break;
			case 2:
				echecs++;
				break;
			case 3:
				rechuttes++;
				break;
			default:
				break;
			}

			if(casTuberculose.getFormeMaladie()!=null && casTuberculose.getFormeMaladie().equals("2")){
				tep++;
			}
			LOGGER.debug("-------------------------------patient age : "+casTuberculose.getPatient().getAge()+"and patient name : "+casTuberculose.getPatient().getNom()
					+"Forme de la maladie : "+casTuberculose.getFormeMaladie());
//			if(casTuberculose.getPatient().getAge()!=null && casTuberculose.getPatient().getAge()<15){
			if(casTuberculose.getPatient().getAge()!=null && casTuberculose.getPatient().getAge()<15){
				tpmMoinsInf15++;
			}
			if(casTuberculose.getPatient().getAge()!=null && casTuberculose.getPatient().getAge()>=15){
				tpmMoinsSup15++;
			}
			

			if(casTuberculose.getPatient().getAge()!=null && casTuberculose.getPatient().getAge()>0 && casTuberculose.getPatient().getAge()<=4){
				cas0_4++;
			}
			if(casTuberculose.getPatient().getAge()!=null && casTuberculose.getPatient().getAge()>=5 && casTuberculose.getPatient().getAge()<=14){
				cas5_14++;
			}
			
			List<ExamenSerologie> listSerologies = casTuberculose.getPatient().getSerologies();
			if(listSerologies !=null && listSerologies.size()>0){
				
				for (ExamenSerologie examenSerologie : listSerologies) {
					if(examenSerologie!=null){
						vihTPMPlusNouveauxFait++;
					}
					if(examenSerologie.getResultatVIH()!=null && examenSerologie.getResultatVIH().equals("0")){
						vihTPMPlusPositif++;
					}
				}
				
			}
			trimesterReport.setColumnNouveauCas(nouveauCas);
			trimesterReport.setColumnRechuttes(rechuttes);
			trimesterReport.setColumnEchecs(echecs);
			trimesterReport.setColumnRepriseTrait(repriseTrait);
			
			trimesterReport.setColumnTpmMoinsInf15(tpmMoinsInf15);
			trimesterReport.setColumnTpmMoinsSup15(tpmMoinsSup15);
			
			trimesterReport.setColumnTep(tep);

			trimesterReport.setColumnTotalDesCas(totalDesCas);
			
			trimesterReport.setColumnCas0_4(cas0_4);
			trimesterReport.setColumnCas5_14(cas5_14);
			
			trimesterReport.setColumnVihTPMPlusPositif(vihTPMPlusPositif);
			trimesterReport.setColumnVihTPMPlusNouveauxFait(vihTPMPlusNouveauxFait);
			
			
			LOGGER.debug("Nombre de nouveaux cas : "+trimesterReport.getColumnNouveauCas());
			 
			report.add(trimesterReport);
		}
		}
		return report;

	}
	
	/**
		 * 
		 * @param sortProperty
		 * @param sortOrder
		 * @param patientId
		 * @param trimester
		 * @param annee
		 * @param region
		 * @param district
		 * @param cdt
		 * @param personnel
		 * @param laboratoire
		 * @param dateDebut
		 * @param dateFin
		 * @return
		 */

	public List<TrimesterTreatmentReport> getTrimesterTreatmentReport(String sortProperty, boolean sortOrder,String  patientId, String trimester, String annee, 
			String region, String district, String cdt, String personnel, String laboratoire, String dateDebut, String dateFin) {
		List<TrimesterTreatmentReport> report = new ArrayList<TrimesterTreatmentReport>();
		
		List<CasTuberculose> listcCasTuberculoses = this.filterDataByParameters(sortProperty, sortOrder, patientId, 
				trimester, annee, region, district, cdt, personnel, laboratoire, dateDebut, dateFin);
				
		TrimesterTreatmentReport trimesterReport;
		for (CasTuberculose casTuberculose : listcCasTuberculoses) {
			trimesterReport = new TrimesterTreatmentReport(); 

			if(casTuberculose.getIdentifiant()!=null){
				trimesterReport.setColumnID(casTuberculose.getIdentifiant());
			}

			if(casTuberculose.getCreated()!=null){
				trimesterReport.setColumnMonth(casTuberculose.getCreated().getMonth());
				trimesterReport.setColumnDateEnregistrement(casTuberculose.getCreated());
			}

			if(casTuberculose.getTypePatient()!=null){
				trimesterReport.setColumnTypePatient(casTuberculose.getTypePatient());
			}
			
			if(casTuberculose.getDevenirMalade()!=null){
				trimesterReport.setColumnIssuTraitement(casTuberculose.getDevenirMalade());
			}
			report.add(trimesterReport);
		}
		return report;

	}
/**
 * 
 * @param sortProperty
 * @param sortOrder
 * @param patientId
 * @param trimester
 * @param annee
 * @param region
 * @param district
 * @param cdt
 * @param personnel
 * @param laboratoire
 * @param dateDebut
 * @param dateFin
 * @return
 */
	public List<TrimesterLabReport> getTrimesterLabReport(String sortProperty, boolean sortOrder,String  patientId, String trimester, String annee, 
			String region, String district, String cdt, String personnel, String laboratoire, String dateDebut, String dateFin) {
		
		List<TrimesterLabReport> report = new ArrayList<TrimesterLabReport>();
		
		List<CasTuberculose> listCasTuberculoses = this.filterDataByParameters(sortProperty, sortOrder, patientId, 
				trimester, annee, region, district, cdt, personnel, laboratoire, dateDebut, dateFin);
				
//		List<ExamenMicroscopie> listExamenMicroscopies;
		
//		List<CentreDiagTrait> listCDT = cdtHandler.listCentreDiagTrait(sortProperty, sortOrder);
		List<CentreDiagTrait> listCDT = new ArrayList<CentreDiagTrait>();
		List<ExamenMicroscopie> listBAAR;

		int nbExamen = 0;
		
		int nbDepistage=0;
		int nbDepistagePositif=0;
		int nbDepistageRare=0;
		int nbDepistageNegatif=0;

		int nbSuivi=0;
		int nbSuiviPositif=0;
		int nbSuiviRare=0;
		int nbSuiviNegatif=0;
		
		//Construction of the list of CDTs to be shown
		
		for (CasTuberculose casTuberculose : listCasTuberculoses) {
			if(casTuberculose.getPatient()!=null && casTuberculose.getPatient().getCentres()!=null){
				for (CentreDiagTrait centreDiagTrait : casTuberculose.getPatient().getCentres()) {
					if(!listCDT.contains(centreDiagTrait)){
						listCDT.add(centreDiagTrait);
					}
				}
			}
		}
		
		System.out.println("xxxxxxxxxxxxxxxxxxxxxAprès construction"+listCDT);
		if(listCDT!=null && listCDT.size()>0){			
		for (CentreDiagTrait centreDiagTrait : listCDT) {
			
			TrimesterLabReport trimesterReport = new TrimesterLabReport();
			
			nbExamen = 0;
			
			nbDepistage=0;
			nbDepistagePositif=0;
			nbDepistageRare=0;
			nbDepistageNegatif=0;

			nbSuivi=0;
			nbSuiviPositif=0;
			nbSuiviRare=0;
			nbSuiviNegatif=0;
			
//			trimesterReport.setColumnMonth(casTuberculose.getCreated().getMonth());
			trimesterReport.setColumnCDT(centreDiagTrait.getNom());
			
			for (CasTuberculose casTuberculose : listCasTuberculoses) {
				
				listBAAR = casTuberculose.getExamensMiscrocopies();
				LOGGER.debug("-------------Liste des examens : "+casTuberculose.getExamensMiscrocopies());
				for (ExamenMicroscopie examenBAAR : listBAAR) {
					if(examenBAAR.getCDT()!=null && centreDiagTrait!=null && examenBAAR.getCDT().getNom().trim().equals(centreDiagTrait.getNom().trim())){
						nbExamen++;
						if(examenBAAR.getRaisonDepistage().trim().equals("0"))
							nbDepistage++;
						if(examenBAAR.getRaisonDepistage().trim().equals("1"))
							nbSuivi++;
						//results
						if(examenBAAR.getRaisonDepistage().trim().equals("0") && examenBAAR.getResultat().equals("0"))
							nbDepistageNegatif++;
						if(examenBAAR.getRaisonDepistage().trim().equals("0") && examenBAAR.getResultat().equals("1"))
							nbDepistageRare++;
	
						if(examenBAAR.getRaisonDepistage().trim().equals("0") && (examenBAAR.getResultat().equals("2")
								|| examenBAAR.getResultat().equals("3") || examenBAAR.getResultat().equals("4")))
							nbDepistagePositif++;
						//following
	
						if(examenBAAR.getRaisonDepistage().trim().equals("1") && examenBAAR.getResultat().equals("0"))
							nbSuiviNegatif++;
						if(examenBAAR.getRaisonDepistage().trim().equals("1") && examenBAAR.getResultat().equals("1"))
							nbSuiviRare++;
	
						if(examenBAAR.getRaisonDepistage().trim().equals("1") && (examenBAAR.getResultat().equals("2")
								|| examenBAAR.getResultat().equals("3") || examenBAAR.getResultat().equals("4")))
							nbSuiviPositif++;
					
				}
								
				}
				
			}

			trimesterReport.setColumnNBExamenBAAR(nbExamen);
			trimesterReport.setColumnNBDepistageBAARNegatif(nbDepistageNegatif);
			trimesterReport.setColumnNBDepistageBAARPositif(nbDepistagePositif);
			trimesterReport.setColumnNBDepistageBAARRare(nbDepistageRare);
			trimesterReport.setColumnNBDespistageBAAR(nbDepistage);


			trimesterReport.setColumnNBSuiviBAARNegatif(nbSuiviNegatif);
			trimesterReport.setColumnNBSuiviBAARPositif(nbSuiviPositif);
			trimesterReport.setColumnNBSuiviBAARRare(nbSuiviRare);
			trimesterReport.setColumnNBSuiviBAAR(nbSuivi);
			

			report.add(trimesterReport);

			}
		}
		System.out.println("Taille du rapport : "+report.size());
		return report;
	}
	
/**
 * 
 * @param sortProperty
 * @param sortOrder
 * @param patientId
 * @param trimester
 * @param annee
 * @param region
 * @param district
 * @param cdt
 * @param personnel
 * @param laboratoire
 * @param dateDebut
 * @param dateFin
 * @return
 */

	public List<TrimesterLabReport> getTrimesterLabReportForChart(String sortProperty, boolean sortOrder,String  patientId, String trimester, String annee, 
			String region, String district, String cdt, String personnel, String laboratoire, String dateDebut, String dateFin) {
		
		List<TrimesterLabReport> report = new ArrayList<TrimesterLabReport>();
		
		List<CasTuberculose> listCasTuberculoses = this.filterDataByParameters(sortProperty, sortOrder, patientId, 
				trimester, annee, region, district, cdt, personnel, laboratoire, dateDebut, dateFin);
				
		TrimesterLabReport trimesterReport = new TrimesterLabReport();
//		List<ExamenMicroscopie> listExamenMicroscopies;
		
		List<CentreDiagTrait> listCDT = cdtHandler.listCentreDiagTrait(sortProperty, sortOrder);
		List<ExamenMicroscopie> listBAAR;
		
		for (CentreDiagTrait centreDiagTrait : listCDT) {			
			for (CasTuberculose casTuberculose : listCasTuberculoses) {
				listBAAR = casTuberculose.getExamensMiscrocopies();
				for (ExamenMicroscopie examenBAAR : listBAAR) {
					if(examenBAAR.getCDT()!=null && centreDiagTrait!=null && examenBAAR.getCDT().getNom().trim().equals(centreDiagTrait.getNom().trim())){
						trimesterReport.setColumnCDT(examenBAAR.getCDT().getNom());
						trimesterReport.setColumnMonth(examenBAAR.getDate().getMonth());
						trimesterReport.setColumnRaisonExamenBAAR(examenBAAR.getRaisonDepistage());
						trimesterReport.setColumnResultatExamenBAAR(examenBAAR.getResultat());
						report.add(trimesterReport);
				}
								
				}
				
			}
		}
		System.out.println("Taille du rapport : "+report.size());
		return report;
	}


	/**
	 * 
	 * @param sortProperty
	 * @param sortOrder
	 * @param patientId
	 * @param trimester
	 * @param annee
	 * @param region
	 * @param district
	 * @param cdt
	 * @param personnel
	 * @param laboratoire
	 * @param dateDebut
	 * @param dateFin
	 * @return
	 */
	
	public List<PatientCardForm> getPatientCardFormPrincial(String sortProperty, boolean sortOrder,String  patientId, String trimester, String annee, 
			String region, String district, String cdt, String personnel, String laboratoire, String dateDebut, String dateFin) {
		

		System.out.println("*****************************************Les paramètres sélectionnées : \n"
				+ "1- Région : "+region+"\n"
				+"2- District : "+district
				+"3- CDT : "+cdt);
		
		
		List <PatientCardForm> form = new ArrayList<PatientCardForm>();		
		
		List<CasTuberculose> listCasTuberculoses = this.filterDataByParameters(sortProperty, sortOrder, patientId, trimester, annee, region, 
				district, cdt, personnel, laboratoire, dateDebut, dateFin);

		PatientCardForm patientCard;

		for (CasTuberculose casTuberculose : listCasTuberculoses) {
			
			System.out.println("xxxx Le cas de tuberculose n'est pas null et est de : "+listCasTuberculoses.get(0).getPatient().getNom());
					
					patientCard = new PatientCardForm();
					
					patientCard.setColumnMonth(casTuberculose.getCreated().getMonth());
					
					if(casTuberculose.getNumRegTB()!=null)
						patientCard.setColumnNumRegTB(casTuberculose.getNumRegTB());
					else patientCard.setNumRegTB("");
					
					if(casTuberculose.getPatient().getNom()!=null)
						patientCard.setColumnNom(casTuberculose.getPatient().getNom());
					else patientCard.setColumnNom("");
					
					if(casTuberculose.getPatient().getSexe()!=null)
						patientCard.setColumnSexe(casTuberculose.getPatient().getSexe());
					else patientCard.setColumnSexe("");
					
					if(casTuberculose.getDateDebutTraitement()!=null)
						patientCard.setColumnDateDebutTrait(""+casTuberculose.getDateDebutTraitement());
		
					if(casTuberculose.getPatient().getAge()!=null)
						patientCard.setColumnAge(""+casTuberculose.getPatient().getAge());
					
					if (casTuberculose.getPatient().getVille() != null
							&& casTuberculose.getPatient().getTelephoneUn() != null)
						patientCard.setColumnAdresse(casTuberculose.getPatient()
								.getVille()
								+ ", "
								+ casTuberculose.getPatient().getTelephoneUn());
					else {
						if (casTuberculose.getPatient().getVille() != null)
							patientCard.setColumnAdresse(casTuberculose.getPatient()
									.getVille());
						else {
							if (casTuberculose.getPatient().getTelephoneUn() != null)
								patientCard.setColumnAdresse(casTuberculose.getPatient()
										.getTelephoneUn());
							else
								patientCard.setColumnAdresse("");
						}
					
				}
					
					if(casTuberculose.getPatient().getCentres()!=null){
						for (CentreDiagTrait centreDiagTrait : casTuberculose.getPatient().getCentres()) {
							patientCard.setColumnCDT(centreDiagTrait.getNom() + ", ");
						}
					}
					
					if(casTuberculose.getFormeMaladie()!=null)
						patientCard.setColumnFormeMaladie(casTuberculose.getFormeMaladie());
					else patientCard.setColumnFormeMaladie("");
					if(casTuberculose.getTypePatient()!=null)
						patientCard.setColumnTypePatient(casTuberculose.getTypePatient());
					else patientCard.setColumnTypePatient("");
					if(casTuberculose.getObservation()!=null)
						patientCard.setColumnObservations(casTuberculose.getObservation());
					else patientCard.setColumnObservations("");
					
					form.add(patientCard);
			}
		return form;
	}
	
	/**
	 * 
	 * @param sortProperty
	 * @param sortOrder
	 * @param patientId
	 * @param trimester
	 * @param annee
	 * @param region
	 * @param district
	 * @param cdt
	 * @param personnel
	 * @param laboratoire
	 * @param dateDebut
	 * @param dateFin
	 * @return
	 */
	
	public List<PatientCardForm> getPatientCardFormBiologique(String sortProperty, boolean sortOrder,String  patientId, String trimester, String annee, 
			String region, String district, String cdt, String personnel, String laboratoire, String dateDebut, String dateFin) {
		
		List <PatientCardForm> form = new ArrayList<PatientCardForm>();		
		List<CasTuberculose> listCasTuberculoses = this.filterDataByParameters(sortProperty, sortOrder, patientId, trimester, annee, region, 
				district, cdt, personnel, laboratoire, dateDebut, dateFin);
		
		List<ExamenBiologique> listExBiologiques = new ArrayList<ExamenBiologique>();
//		List <PatientCardForm> form = new ArrayList<PatientCardForm>();		
		PatientCardForm patientCard;
		
		for (CasTuberculose casTuberculose : listCasTuberculoses) {
			if(casTuberculose!=null && casTuberculose.getPatient()!=null && casTuberculose.getPatient().getExamensBiologiques()!=null){
				for (ExamenBiologique examenBiologique : casTuberculose.getPatient().getExamensBiologiques()) {
					if(!listExBiologiques.contains(examenBiologique)){
							listExBiologiques.add(examenBiologique);
						}
				}
			}
		}
		
		if(listExBiologiques!=null && listExBiologiques.size()>0){
			
			for (ExamenBiologique examenBiologique : listExBiologiques) {
				patientCard = new PatientCardForm();
				
				if(examenBiologique.getDate()!=null)
					patientCard.setColumnDataPoidPatient(""+examenBiologique.getDate());
				else 
					patientCard.setColumnDataPoidPatient("");
				
				if(examenBiologique.getPoids()!=null)
					patientCard.setColumnPoidsPatient(examenBiologique.getPoids());
//				else 
//					patientCard.setColumnPoidsPatient(-99);
	
				form.add(patientCard);
	
			}
		}
		return form;			
	}
	
	/**
	 * 
	 * @param sortProperty
	 * @param sortOrder
	 * @param patientId
	 * @param trimester
	 * @param annee
	 * @param region
	 * @param district
	 * @param cdt
	 * @param personnel
	 * @param laboratoire
	 * @param dateDebut
	 * @param dateFin
	 * @return
	 */
	
	public List<PatientCardForm> getPatientCardFormMiscroscopie(String sortProperty, boolean sortOrder,String  patientId, String trimester, String annee, 
			String region, String district, String cdt, String personnel, String laboratoire, String dateDebut, String dateFin) {
		
		List <PatientCardForm> form = new ArrayList<PatientCardForm>();		
		List<CasTuberculose> listCasTuberculoses = this.filterDataByParameters(sortProperty, sortOrder, patientId, trimester, annee, region, 
				district, cdt, personnel, laboratoire, dateDebut, dateFin);
		
		List<ExamenMicroscopie> listExamenMicroscopie = new ArrayList<ExamenMicroscopie>();
		PatientCardForm patientCard;
		
		for (CasTuberculose casTuberculose : listCasTuberculoses) {
			if(casTuberculose!=null && casTuberculose.getPatient()!=null && casTuberculose.getExamensMiscrocopies()!=null){
				for (ExamenMicroscopie examenMicroscopie : casTuberculose.getExamensMiscrocopies()) {
					if(!listExamenMicroscopie.contains(examenMicroscopie)){
							listExamenMicroscopie.add(examenMicroscopie);
						}
				}
			}
		}
		
		if(listExamenMicroscopie!=null && listExamenMicroscopie.size()>0){
			
			for (ExamenMicroscopie examenMicroscopie : listExamenMicroscopie) {
				patientCard = new PatientCardForm();
				
				if(examenMicroscopie.getDate()!=null)
					patientCard.setColumnDateExamenCrachat(""+examenMicroscopie.getDate());
				else 
					patientCard.setColumnDateExamenCrachat("");
				
				if(examenMicroscopie.getRaisonDepistage()!=null)
					patientCard.setColumnRaisonExamenCrachat(examenMicroscopie.getRaisonDepistage());
				else 
					patientCard.setColumnRaisonExamenCrachat("");

				if(examenMicroscopie.getResultat()!=null)
					patientCard.setColumnResultatExamenCrachat(examenMicroscopie.getResultat());
				else 
					patientCard.setColumnResultatExamenCrachat("");
	
				form.add(patientCard);
	
			}
		}
		return form;			
	}
	
/**
 * 
 * @param sortProperty
 * @param sortOrder
 * @param patientId
 * @param trimester
 * @param annee
 * @param region
 * @param district
 * @param cdt
 * @param personnel
 * @param laboratoire
 * @param dateDebut
 * @param dateFin
 * @return
 */
	

	public List<PatientCardForm> getPatientCardFormTraitementInit(String sortProperty, boolean sortOrder,String  patientId, String trimester, String annee, 
			String region, String district, String cdt, String personnel, String laboratoire, String dateDebut, String dateFin) {
		
		List <PatientCardForm> form = new ArrayList<PatientCardForm>();		
		List<CasTuberculose> listCasTuberculoses = this.filterDataByParameters(sortProperty, sortOrder, patientId, trimester, annee, region, 
				district, cdt, personnel, laboratoire, dateDebut, dateFin);

		List<PriseMedicamenteuse> listTraPriseMedicamenteusesPhaseInit = new ArrayList<PriseMedicamenteuse>();

		PatientCardForm patientCard;
		
		for (CasTuberculose casTuberculose : listCasTuberculoses) {
			if(casTuberculose!=null && casTuberculose.getPatient()!=null && casTuberculose.getPriseMedicamenteusePhaseInitiale()!=null){
				for (PriseMedicamenteuse priseMedicamenteuse : casTuberculose.getPriseMedicamenteusePhaseInitiale()) {
					if(!listTraPriseMedicamenteusesPhaseInit.contains(priseMedicamenteuse)){
							listTraPriseMedicamenteusesPhaseInit.add(priseMedicamenteuse);
						}
				}
			}
		}
		if(listTraPriseMedicamenteusesPhaseInit!=null && listTraPriseMedicamenteusesPhaseInit.size()>0){
			
			for (PriseMedicamenteuse priseMedicamenteuse : listTraPriseMedicamenteusesPhaseInit) {
				
				patientCard = new PatientCardForm();
				
				patientCard.setColumnRegimePhaseInitiale("Initial");
				
				if(priseMedicamenteuse.getDateEffective()!=null)
					patientCard.setColumnDateEffectivePhaseInitiale(""+priseMedicamenteuse.getDateEffective());
				else 
					patientCard.setColumnDateEffectivePhaseInitiale("");
				
				
				form.add(patientCard);
			}
		}
		return form;			
	}
/**
 * 
 * @param sortProperty
 * @param sortOrder
 * @param patientId
 * @param trimester
 * @param annee
 * @param region
 * @param district
 * @param cdt
 * @param personnel
 * @param laboratoire
 * @param dateDebut
 * @param dateFin
 * @return
 */
	public List<PatientCardForm> getPatientCardFormTraitementCont(String sortProperty, boolean sortOrder,String  patientId, String trimester, String annee, 
			String region, String district, String cdt, String personnel, String laboratoire, String dateDebut, String dateFin) {
		
		List <PatientCardForm> form = new ArrayList<PatientCardForm>();		
		List<CasTuberculose> listCasTuberculoses = this.filterDataByParameters(sortProperty, sortOrder, patientId, trimester, annee, region, 
				district, cdt, personnel, laboratoire, dateDebut, dateFin);

		
		List<PriseMedicamenteuse> listTraPriseMedicamenteusesPhaseCont = new ArrayList<PriseMedicamenteuse>();

		PatientCardForm patientCard;
		
		for (CasTuberculose casTuberculose : listCasTuberculoses) {
			if(casTuberculose!=null && casTuberculose.getPatient()!=null && casTuberculose.getPriseMedicamenteusePhaseContinuation()!=null){
				for (PriseMedicamenteuse priseMedicamenteuse : casTuberculose.getPriseMedicamenteusePhaseContinuation()) {
					if(!listTraPriseMedicamenteusesPhaseCont.contains(priseMedicamenteuse)){
							listTraPriseMedicamenteusesPhaseCont.add(priseMedicamenteuse);
						}
				}
			}
		}
		
		System.out.println("******************************** Liste prise médicaments continuation : "+listTraPriseMedicamenteusesPhaseCont);
		
		if(listTraPriseMedicamenteusesPhaseCont!=null && listTraPriseMedicamenteusesPhaseCont.size()>0){
			
			for (PriseMedicamenteuse priseMedicamenteuse : listTraPriseMedicamenteusesPhaseCont) {
				
				patientCard = new PatientCardForm();
				
				patientCard.setColumnRegimePhaseContinuation("Continuation");
				
				if(priseMedicamenteuse.getDateEffective()!=null){
					System.out.println("Date éffective différent de null");
					patientCard.setColumnDateEffectivePhaseContinuation(""+priseMedicamenteuse.getDateEffective());
				}
				else 
					patientCard.setColumnDateEffectivePhaseContinuation("");
				
				form.add(patientCard);
			}
		}
		return form;			
	}
	/**
	 * 
	 * @param sortProperty
	 * @param sortOrder
	 * @param patientId
	 * @param trimester
	 * @param annee
	 * @param region
	 * @param district
	 * @param cdt
	 * @param personnel
	 * @param laboratoire
	 * @param dateDebut
	 * @param dateFin
	 * @return
	 */
	public List<PatientCardForm> getPatientCardFormTraitementRDV(String sortProperty, boolean sortOrder,String  patientId, String trimester, String annee, 
			String region, String district, String cdt, String personnel, String laboratoire, String dateDebut, String dateFin) {
		
		List <PatientCardForm> form = new ArrayList<PatientCardForm>();		
		List<CasTuberculose> listCasTuberculoses = this.filterDataByParameters(sortProperty, sortOrder, patientId, trimester, annee, region, 
				district, cdt, personnel, laboratoire, dateDebut, dateFin);

		List<RendezVous> listrRendezVous = new ArrayList<RendezVous>();

		PatientCardForm patientCard;
		
		for (CasTuberculose casTuberculose : listCasTuberculoses) {
			if(casTuberculose!=null && casTuberculose.getPatient()!=null && casTuberculose.getRendezVous()!=null){
				for (RendezVous rendezVous : casTuberculose.getRendezVous()) {
					if(!listrRendezVous.contains(rendezVous)){
						listrRendezVous.add(rendezVous);
						}
				}
			}
		}
		if(listrRendezVous!=null && listrRendezVous.size()>0){
			
			for (RendezVous rendezVous : listrRendezVous) {
				
				patientCard = new PatientCardForm();
				
				patientCard.setColumnRegimePhaseInitiale("Initial");
				
				if(rendezVous.getDateRendezVous()!=null)
					patientCard.setColumnDateRDV(rendezVous.getDateRendezVous());
//				else 
//					patientCard.getDateRendezVous("");
				
				if(rendezVous.getHonore()!=null)
					patientCard.setColumnHonoree(""+rendezVous.getHonore());
				
				form.add(patientCard);
			}
		}
		return form;			
	}
	
	public List<TreatmentForm> getTreatmentForm(String sortProperty, boolean sortOrder,String  patientId, String trimester, String annee, 
			String region, String district, String cdt, String personnel, String laboratoire, String dateDebut, String dateFin) {
		List<TreatmentForm> form = new ArrayList<TreatmentForm>();	
		List<CasTuberculose> listCasTuberculoses = casTuberculoseHandler.listCasTuberculose(sortProperty, sortOrder);
		List<ExamenBiologique> listExBiologiques = examenBiologiqueHandler.listExamenBiologique(sortProperty, sortOrder);
		List<ExamenMicroscopie> listExamenMicroscopies = examenMicroscopieHandler.listExamenMicroscopie(sortProperty, sortOrder);
		
		TreatmentForm treatmenForm;
		for (CasTuberculose casTuberculose : listCasTuberculoses) {
			treatmenForm = new TreatmentForm();
			
			treatmenForm.setColumnMonth(casTuberculose.getCreated().getMonth());
			
			if(casTuberculose.getIdentifiant()!=null)
				treatmenForm.setColumnNumRegTB(casTuberculose.getIdentifiant());
			else treatmenForm.setColumnNumRegTB("");
			
			if(casTuberculose.getPatient().getNom()!=null)
				treatmenForm.setColumnNom(casTuberculose.getPatient().getNom());
			else treatmenForm.setColumnNom("");
			
			if(casTuberculose.getPatient().getSexe()!=null)
				treatmenForm.setColumnSexe(casTuberculose.getPatient().getSexe());
			else treatmenForm.setColumnSexe("");
//			
//			if(casTuberculose.getPatient().getAge()!=null)
//				tre
			
//			if(casTuberculose.getDateDebutTraitement()!=null)
//				treatmenForm.setColumnDateDebutTrait(""+casTuberculose.getDateDebutTraitement());

			if (casTuberculose.getPatient().getVille() != null
					&& casTuberculose.getPatient().getTelephoneUn() != null)
				treatmenForm.setColumnAdresse(casTuberculose.getPatient()
						.getVille()
						+ ", "
						+ casTuberculose.getPatient().getTelephoneUn());
			else {
				if (casTuberculose.getPatient().getVille() != null)
					treatmenForm.setColumnAdresse(casTuberculose.getPatient()
							.getVille());
				else {
					if (casTuberculose.getPatient().getTelephoneUn() != null)
						treatmenForm.setColumnAdresse(casTuberculose.getPatient()
								.getTelephoneUn());
					else
						treatmenForm.setColumnAdresse("");
				}
			
		}
			if(casTuberculose.getFormeMaladie()!=null)
				treatmenForm.setColumnFormeMaladie(casTuberculose.getFormeMaladie());
			else treatmenForm.setColumnFormeMaladie("");
			
			if(casTuberculose.getTypePatient()!=null)
				treatmenForm.setColumnTypePatient(casTuberculose.getTypePatient());
			else treatmenForm.setColumnTypePatient("");
			
			if(casTuberculose.getObservation()!=null)
				treatmenForm.setColumnObservations(casTuberculose.getObservation());
			else treatmenForm.setColumnObservations("");
			
			//For exams
			if (casTuberculose.getPatient().getExamensBiologiques() != null
					&& casTuberculose.getPatient().getExamensBiologiques().size() > 0) {

				for (ExamenBiologique examenBiologique : listExBiologiques) {
					
					treatmenForm.setColumnDataPoidPatient(""+examenBiologique.getDate());
					treatmenForm.setColumnPoidsPatient(examenBiologique.getPoids());

					if (casTuberculose.getExamensMiscrocopies() != null	&& casTuberculose.getExamensMiscrocopies().size() > 0) {
						for (ExamenMicroscopie examenMicroscopie : listExamenMicroscopies) {
							treatmenForm.setColumnDateExamenCrachat(""
									+ examenMicroscopie.getDate());
							treatmenForm
									.setColumnResultatExamenCrachat(examenMicroscopie
											.getResultat());
							form.add(treatmenForm);
						}
					} else
						form.add(treatmenForm);
				}
			}
			//Elseddd
			else {
				
				if (casTuberculose.getExamensMiscrocopies() != null
						&& casTuberculose.getExamensMiscrocopies().size() > 0) {
					System.out
							.println("-------------------Examen microscopie : "
									+ casTuberculose
											.getExamensMiscrocopies()
											.get(0).getDate());
					for (ExamenMicroscopie examenMicroscopie : listExamenMicroscopies) {
						treatmenForm.setColumnDateExamenCrachat(""
								+ examenMicroscopie.getDate());
						treatmenForm
								.setColumnResultatExamenCrachat(examenMicroscopie
										.getResultat());
						// Test if there are some serology
						if (casTuberculose.getPatient().getExamensBiologiques() != null
								&& casTuberculose.getPatient()
										.getExamensBiologiques().size() > 0) {

							for (ExamenBiologique examenBiologique : listExBiologiques) {
								treatmenForm.setColumnDataPoidPatient(""
										+ examenBiologique.getDate());
								treatmenForm
										.setColumnPoidsPatient(examenBiologique
												.getPoids());// +", CD4 : "+examenSerologie.getResultatCD4());
								
								form.add(treatmenForm);
							}
						} else {
							form.add(treatmenForm);
						}
					}

				} else
					form.add(treatmenForm);
				System.out.println("La taille de la liste : "
						+ form.size());
			}
			//Add the treatment of patient. For any treatment, add patient
			//A revoir car modele pose pb
			if(casTuberculose.getPriseMedicamenteusePhaseInitiale()!=null){
				List<PriseMedicamenteuse> listMedicamenteuses = casTuberculose.getPriseMedicamenteusePhaseInitiale();
				for (PriseMedicamenteuse priseMedicamenteuse : listMedicamenteuses) {
					treatmenForm.setColumnDateEffectivePhaseInitiale(""+priseMedicamenteuse.getDateEffective());
					treatmenForm.setColumnRegimePhaseInitiale("Initiale");
					treatmenForm.setColumnMedicamentsPhaseInitiale("Cotrimoxazole");
					if(casTuberculose.getPriseMedicamenteusePhaseContinuation()!=null && casTuberculose.getPriseMedicamenteusePhaseContinuation().size()>0){
						List<PriseMedicamenteuse> listeMedicamenteusesContinue = casTuberculose.getPriseMedicamenteusePhaseContinuation();
						for (PriseMedicamenteuse priseMedicamenteuse2 : listeMedicamenteusesContinue) {
							treatmenForm.setColumnDateEffectivePhaseInitiale(""+priseMedicamenteuse2.getDateEffective());
							treatmenForm.setColumnRegimePhaseInitiale("Continue");
							treatmenForm.setColumnMedicamentsPhaseInitiale("Cotrimoxazole");
							form.add(treatmenForm);
						}
					}else form.add(treatmenForm);
				}
			}
		}
		
		
		
		return form;

	}
	public List<ExamenCrachats> exams(String sortProperty, boolean sortOrder,String  patientId, String trimester, String annee, 
			String region, String district, String cdt, String personnel, String laboratoire, String dateDebut, String dateFin) {
		
		List<ExamenCrachats> listExams = new ArrayList<ExamenCrachats>();
		List<CasTuberculose> listCasTuberculoses = this.filterDataByParameters(sortProperty, sortOrder, patientId, trimester, annee, region, 
				district, cdt, personnel, laboratoire, dateDebut, dateFin);
		for (CasTuberculose casTuberculose : listCasTuberculoses) {
		List<ExamenMicroscopie> listExamenMicroscopies = casTuberculose.getExamensMiscrocopies();
		if(listExamenMicroscopies!=null && listExamenMicroscopies.size()!=0){
			for (ExamenMicroscopie examenMicroscopie : listExamenMicroscopies) {
				ExamenCrachats examenCrachats = new ExamenCrachats();
				if(examenMicroscopie.getDate()!=null)
					examenCrachats.setColumnDate(examenMicroscopie.getDate());
				if(examenMicroscopie.getRaisonDepistage()!=null)
					examenCrachats.setColumnRaisonExam(examenMicroscopie.getRaisonDepistage());
				if(examenMicroscopie.getResultat()!=null)
					examenCrachats.setColumnResultat(examenMicroscopie.getResultat());
				listExams.add(examenCrachats);
			}
		}
		}
		return listExams;
		
	}
	
	public List<TransfertReferenceForm> getTransfRefForm(String sortProperty, boolean sortOrder,String  patientId, String trimester, String annee, 
			String region, String district, String cdt, String personnel, String laboratoire, String dateDebut, String dateFin) {
		List<CasTuberculose> listCasTuberculoses = this.filterDataByParameters(sortProperty, sortOrder, patientId, trimester, annee, region, 
				district, cdt, personnel, laboratoire, dateDebut, dateFin);
		List<TransfertReference> listtTransfertReferences = transfertReferenceHandler.listTransfertReference(sortProperty, sortOrder);
		List<Personnel> listPersonnel = personnelHandler.listPersonnel(sortProperty, sortOrder);
		List<TransfertReferenceForm> form = new ArrayList<TransfertReferenceForm>();
		System.out.println("Liste des cas de tuberculose : "+listCasTuberculoses + "  "+listCasTuberculoses.get(0).getPatient().getNom());
//		System.out.println("------------Liste des transfert/ref : "+listtTransfertReferences);
		for (CasTuberculose casTuberculose : listCasTuberculoses) {
			TransfertReferenceForm newTransfertReference = new TransfertReferenceForm();
			for (TransfertReference transfertReference : listtTransfertReferences) {
//				System.out.println("List of all transfert reference : "+transfertReference);
//				System.out.println("Patient : T/R : "+transfertReference.getPatient().getIdentifiant()+" ---C/P : "+casTuberculose.getPatient().getIdentifiant());
				if(transfertReference.getPatient().getIdentifiant().trim().equals(casTuberculose.getPatient().getIdentifiant())){
					System.out.println("There is one trasfert/reference corresponding to what i'am loking for");

					if(transfertReference.getNature()!=null){
						newTransfertReference.setColumnTypeOP(transfertReference.getNature());
//						if(transfertReference.getNature().trim().equals("0"))
//							newTransfertReference.setColumnTypeOP("Transfert");
//						else newTransfertReference.setColumnTypeOP("Reference");
					}
//					if(transfertReference.getNature()!=null && transfertReference.getNature().equals("1")){
//						System.out.println("*****************Une référence a été sélectioné");
//						newTransfertReference.setColumnTypeOP("Reference");
//					}
//					else newTransfertReference.setColumnTypeOP("");
					if(transfertReference.getCDTDepart().getNom()!=null)
						newTransfertReference.setColumnCDTDepart(transfertReference.getCDTDepart().getNom());
					else newTransfertReference.setColumnCDTDepart("");
					if(transfertReference.getCDTArrivee().getNom()!=null)
						newTransfertReference.setColumnCDTArrive(transfertReference.getCDTArrivee().getNom());
					else newTransfertReference.setColumnCDTArrive("");
					if(casTuberculose.getNumRegTB()!=null)
						newTransfertReference.setColumnNumRegTBDepart(casTuberculose.getNumRegTB());
					else newTransfertReference.setColumnNumRegTBDepart("");
					if(casTuberculose.getNumRegTB()!=null)
						newTransfertReference.setColumnNumRegTBArrivee(casTuberculose.getNumRegTB());
					else newTransfertReference.setColumnNumRegTBArrivee("");
					if(casTuberculose.getPatient().getNom()!=null)
						newTransfertReference.setColumnNom(casTuberculose.getPatient().getNom());
					else newTransfertReference.setColumnNom("");
					if(casTuberculose.getPatient().getAge()!=null)
						newTransfertReference.setColumnAge(casTuberculose.getPatient().getAge());
					else newTransfertReference.setColumnAge(-99);
					if(casTuberculose.getPatient().getSexe()!=null)
						newTransfertReference.setColumnSexe(casTuberculose.getPatient().getSexe());
					else newTransfertReference.setColumnSexe("");
					if(casTuberculose.getFormeMaladie()!=null)
						newTransfertReference.setColumnFormeMaladie(casTuberculose.getFormeMaladie());
					else newTransfertReference.setColumnFormeMaladie("");
					if(casTuberculose.getTypePatient()!=null)
//						if(casTuberculose.getTypePatientPrecisions()!=null || !casTuberculose.getTypePatientPrecisions().trim().equals(""))
							newTransfertReference.setColumnTypeMaladie(casTuberculose.getTypePatient());//+"  "+casTuberculose.getTypePatientPrecisions());
						else newTransfertReference.setColumnTypeMaladie(casTuberculose.getTypePatient());
//					else newTransfertReference.setColumnTypeMaladie("");
//					//for treatment
					if (casTuberculose.getPatient().getVille() != null
							&& casTuberculose.getPatient().getTelephoneUn() != null)
						newTransfertReference.setColumnAdresse(casTuberculose.getPatient()
								.getVille()
								+ ", "
								+ casTuberculose.getPatient().getTelephoneUn());
					else {
						if (casTuberculose.getPatient().getVille() != null)
							newTransfertReference.setColumnAdresse(casTuberculose.getPatient()
									.getVille());
						else {
							if (casTuberculose.getPatient().getTelephoneUn() != null)
								newTransfertReference.setColumnAdresse(casTuberculose.getPatient()
										.getTelephoneUn());
							else
								newTransfertReference.setColumnAdresse("");
						}
					
				}
					if(casTuberculose.getDateDebutTraitement()!=null){
						newTransfertReference.setColumnTraitementCommence("traitement commence");
						newTransfertReference.setColumnDateDebutTraitement(casTuberculose.getDateDebutTraitement());
						if(casTuberculose.getRegimePhaseInitiale()!=null && casTuberculose.getRegimePhaseContinuation()!=null)
							newTransfertReference.setColumnTraitementPrescrit(casTuberculose.getRegimePhaseInitiale().getNom()+", "+casTuberculose.getRegimePhaseContinuation().getNom());
						else{
							if(casTuberculose.getRegimePhaseInitiale()!=null)
								newTransfertReference.setColumnTraitementPrescrit(casTuberculose.getRegimePhaseInitiale().getNom());
							
						}
					}
					if(transfertReference.getDateDepart()!=null)
						newTransfertReference.setColumnDateReference(transfertReference.getDateDepart());
					if(transfertReference.getDateArrivee()!=null)
						newTransfertReference.setColumnDateArriveeMalade(transfertReference.getDateArrivee());
////					//Agent référent
////					private String columnNomAgentRecoit;					
//					//For exams
//					List<ExamenMicroscopie> listExamenMicroscopies = casTuberculose.getExamensMiscrocopies();
//					if(listExamenMicroscopies!=null && listExamenMicroscopies.size()!=0){
//						for (ExamenMicroscopie examenMicroscopie : listExamenMicroscopies) {
//							if(examenMicroscopie.getDate()!=null)
//								newTransfertReference.setColumnDateExamen(examenMicroscopie.getDate());
//							if(examenMicroscopie.getRaisonDepistage()!=null)
//								newTransfertReference.setColumnRaisonExamen(examenMicroscopie.getRaisonDepistage());
//							if(examenMicroscopie.getResultat()!=null)
//								newTransfertReference.setColumnResultatExamen(examenMicroscopie.getResultat());
//							form.add(newTransfertReference);
//						}
//					}
//					else {
//						form.add(newTransfertReference);
//					}
					form.add(newTransfertReference);
				}
			}
		}
		System.out.println(form);
		return form;

	}
	public List<DrugDistributionReport> getDrugDistribForm(String sortProperty,
			boolean sortOrder) {
		List<DrugDistributionReport> form = new ArrayList<DrugDistributionReport>();
		return form;

	}

	public List<ExamenCrachats> getExamenCrachats(String sortProperty,
			boolean sortOrder) {
		List<ExamenCrachats> examen = new ArrayList<ExamenCrachats>();
		return examen;

	}

	public List<ExamenSerologie> getExamenSerologie(String sortProperty,
			boolean sortOrder) {
		List<ExamenSerologie> examen = new ArrayList<ExamenSerologie>();
		return examen;

	}

	// Toutes les méthodes d'accès à la BD doivent être transactionnels

//	@Transactional
	public List<RegTB> getRegTB(String sortProperty, boolean sortOrder) {
		List<RegTB> listCasTb = new ArrayList<RegTB>();
		RegTB registre = new RegTB();
		List<CasTuberculose> listCasTuberculoses = casTuberculoseHandler
				.listCasTuberculose(sortProperty, sortOrder);
		// for (CasTuberculose casTuberculose : listCasTuberculoses) {
		// registre.setColumnIdPatient(getString(casTuberculose.getIdentifiant()));
		// registre.setColumnNomPatient(getString(casTuberculose.getPatient().getNom()));
		// registre.setColumnAgePatient((Integer)getNumber(casTuberculose.getPatient().getAge()));
		// registre.setColumnSexePatient(getString(casTuberculose.getPatient().getSexe()));
		// registre.setColumnProfessionPatient(getString(casTuberculose.getPatient().getProfession()));
		//
		// listCasTb.add(registre);
		// }
		// Window.alert("Methode getReg");
		return listCasTb;
	}

	// return an empty string if string passing in parameter is null. If not,
	// return this string

	public String getString(String string) {
		if (string == null)
			return "";
		else
			return string;
	}

	// return a number if object passing in parameter is not null and -1 if not
	public Number getNumber(Number number) {
		if (number == null)
			return -1;
		else
			return number;
	}

	// Parsing date in string format and return null if date is null
	public String getDate(Date date) {
		if (date == null)
			return "";
		else
			return DateTimeFormat.getFormat("dd/MM/yyyy").format(date);
	}
	/**
	 * 
	 * @param sortProperty
	 * @param sortOrder
	 * @param patientId
	 * @param trimester
	 * @param annee
	 * @param region
	 * @param district
	 * @param cdt
	 * @param personnel
	 * @param laboratoire
	 * @param dateDebut
	 * @param dateFin
	 * @return
	 */
	public List<ParametresRapport> getRepParameters(String sortProperty, boolean sortOrder,String  patientId, String trimester, String annee, 
			String region, String district, String cdt, String personnel, String laboratoire, String dateDebut, String dateFin) {
		
		List<CasTuberculose> listCasTuberculoses = this.filterDataByParameters(sortProperty, sortOrder, patientId, trimester, annee, region, 
				district, cdt, personnel, laboratoire, dateDebut, dateFin);
		
		ParametresRapport parametresRapport = new ParametresRapport();
		
		List<ParametresRapport> listParametresRapports = new ArrayList<ParametresRapport>();
		
		//For the regions
		
		if(region!=null && !region.trim().equals("") && listCasTuberculoses!=null && listCasTuberculoses.get(0)!=null
				&& listCasTuberculoses.get(0).getPatient()!=null && listCasTuberculoses.get(0).getPatient().getCentres().get(0)!=null){
			
			parametresRapport.setColumnRegion(listCasTuberculoses.get(0).getPatient().getCentres().get(0).getRegion().getNom().getFrancais());
		}
		
		else 
			parametresRapport.setColumnRegion("");
		
		//For districts

		if(district!=null && !district.trim().equals("") && listCasTuberculoses!=null && listCasTuberculoses.get(0)!=null
				&& listCasTuberculoses.get(0).getPatient()!=null && listCasTuberculoses.get(0).getPatient().getCentres().get(0)!=null){
			
			parametresRapport.setColumnDistrict(listCasTuberculoses.get(0).getPatient().getCentres().get(0).getDistrictSante()
					.getNom().getFrancais());
		}
		
		else 
			parametresRapport.setColumnDistrict("");
		

		//For CDT

		if(district!=null && !district.trim().equals("") && listCasTuberculoses!=null && listCasTuberculoses.get(0)!=null
				&& listCasTuberculoses.get(0).getPatient()!=null && listCasTuberculoses.get(0).getPatient().getCentres().get(0)!=null){
			
			parametresRapport.setColumnCDT(listCasTuberculoses.get(0).getPatient().getCentres().get(0).getNom());
		}
		
		else 
			parametresRapport.setColumnCDT("");
		
		listParametresRapports.add(parametresRapport);
		
		return listParametresRapports;
		
	}

}