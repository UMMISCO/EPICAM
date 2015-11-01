package org.imogene.epicam.server.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import org.apache.log4j.Logger;
import org.imogene.epicam.domain.entity.CasTuberculose;
import org.imogene.epicam.domain.entity.OutBox;
import org.imogene.epicam.domain.entity.Patient;
import org.imogene.epicam.domain.entity.RendezVous;
import org.imogene.epicam.domain.entity.SmsPredefini;
import org.imogene.epicam.server.handler.CasTuberculoseHandler;
import org.imogene.epicam.server.handler.OutBoxHandler;
import org.imogene.epicam.server.handler.PatientHandler;
import org.imogene.epicam.server.handler.SmsPredefiniHandler;
import org.imogene.web.server.startup.StartupManager.StartupJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.transaction.annotation.Transactional;

public class SMSServiceScheduler implements StartupJob {

	private static final Logger LOG = Logger.getLogger(SMSServiceScheduler.class);

	@Autowired
	PatientHandler patientHandler;

	@Autowired
	CasTuberculoseHandler casTBHandler;

	@Autowired
	SmsPredefiniHandler smsHandler;

	@Autowired
	OutBoxHandler outBoxHandler;

	@Autowired
	private TaskScheduler scheduler;

	@Autowired
	private SMSService smsService;

	private ScheduledFuture<?> scheduledFuture;

	private int delay = 10000;

	public void setDelay(int delay) {
		this.delay = delay;
	}


	@Transactional
	public void schedule() {
		LOG.info("Scheduling SMS service: delay(ms): " + delay);
		scheduledFuture = scheduler.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				LOG.info("Sending SMS: " + new Date());
				smsService.sendSMS();
				smsService.sendPatientMedicalRecord();
				smsService.sensitizationSMS();
				smsService.reminderSMS();

			}
		}, new Date(System.currentTimeMillis() + delay), delay);
	}

	public void cancel() {
		if (scheduledFuture != null) {
			LOG.info("Cancel scheduled SMS service");
			scheduledFuture.cancel(false);
			scheduledFuture = null;
		}
	}

	@Override
	@Transactional
	public void run() {
		schedule();
	}

}
