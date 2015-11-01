package org.imogene.epicam.client.ui.editor;

import java.util.ArrayList;
import java.util.List;

import org.imogene.epicam.client.AccessManager;
import org.imogene.epicam.client.EpicamRenderer;
import org.imogene.epicam.client.dataprovider.PatientDataProvider;
import org.imogene.epicam.client.dataprovider.SmsPredefiniDataProvider;
import org.imogene.epicam.client.event.save.SavePatientEvent;
import org.imogene.epicam.client.event.save.SaveSmsPredefiniEvent;
import org.imogene.epicam.client.i18n.NLS;
import org.imogene.epicam.client.ui.filter.PatientFilterPanel;
import org.imogene.epicam.client.ui.workflow.panel.PatientFormPanel;
import org.imogene.epicam.client.ui.workflow.panel.SmsPredefiniFormPanel;
import org.imogene.epicam.shared.EpicamRequestFactory;
import org.imogene.epicam.shared.proxy.PatientProxy;
import org.imogene.epicam.shared.proxy.SmsPredefiniProxy;
import org.imogene.web.client.event.FieldValueChangeEvent;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.field.ImogField;
import org.imogene.web.client.ui.field.group.FieldGroupPanel;
import org.imogene.web.client.ui.field.relation.multi.ImogMultiRelationBox;
import org.imogene.web.client.ui.field.relation.single.ImogSingleRelationBox;
import org.imogene.web.client.ui.panel.RelationPopupPanel;
import org.imogene.web.shared.request.ImogEntityRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Ignore;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * Editor that provides the UI components that allow a EnvoiSMSProxy to be viewed and edited
 * 
 * @author MEDES-IMPS
 */
public class EnvoiSMSEditor extends Composite {

	interface Binder extends UiBinder<Widget, EnvoiSMSEditor> {
	}

	private static final Binder BINDER = GWT.create(Binder.class);

	protected final EpicamRequestFactory requestFactory;
	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

	private boolean hideButtons = false;

	/* EnvoiSms section widgets */
	@UiField
	@Ignore
	FieldGroupPanel envoiSmsSection;
	@UiField(provided = true)
	ImogMultiRelationBox<PatientProxy> patient;
	@UiField(provided = true)
	ImogSingleRelationBox<SmsPredefiniProxy> sms;

	/**
	 * Constructor
	 * 
	 * @param factory the application request factory
	 * @param hideButtons true if the relation field buttons shall be hidden
	 */
	public EnvoiSMSEditor(EpicamRequestFactory factory, boolean hideButtons) {

		this.requestFactory = factory;
		this.hideButtons = hideButtons;

		setRelationFields();

		initWidget(BINDER.createAndBindUi(this));

		properties();
	}

	/**
	 * Constructor
	 * 
	 * @param factory the application request factory
	 */
	public EnvoiSMSEditor(EpicamRequestFactory factory) {
		this(factory, false);
	}

	/**
	 * Sets the properties of the fields
	 */
	private void properties() {
		/* EnvoiSMSPatient section widgets */
		envoiSmsSection.setGroupTitle(BaseNLS.constants().envoiSMS_group_envoiSMSPatient());
		patient.setLabel(BaseNLS.constants().envoiSMS_field_patient());
		sms.setLabel(BaseNLS.constants().envoiSMS_field_sms());
	}

	/**
	 * Configures the widgets that manage relation fields
	 */
	private void setRelationFields() {

		/* field patient */
		PatientDataProvider patientDataProvider;
		patientDataProvider = new PatientDataProvider(requestFactory);
		if (hideButtons) // in popup, relation buttons hidden
			patient = new ImogMultiRelationBox<PatientProxy>(patientDataProvider, EpicamRenderer.get(), true);
		else {// in wrapper panel, relation buttons enabled
			if (AccessManager.canCreatePatient() && AccessManager.canEditPatient())
				patient = new ImogMultiRelationBox<PatientProxy>(patientDataProvider, EpicamRenderer.get(), null);
			else
				patient = new ImogMultiRelationBox<PatientProxy>(false, patientDataProvider, EpicamRenderer.get(), null);
		}
		patient.setPopUpTitle(NLS.constants().patient_select_title());
		patient.setFilterPanel(new PatientFilterPanel());

		/* field sms */
		SmsPredefiniDataProvider smsDataProvider;
		smsDataProvider = new SmsPredefiniDataProvider(requestFactory);
		if (hideButtons) // in popup, relation buttons hidden
			sms = new ImogSingleRelationBox<SmsPredefiniProxy>(smsDataProvider, EpicamRenderer.get(), true);
		else {// in wrapper panel, relation buttons enabled
			if (AccessManager.canCreateSmsPredefini() && AccessManager.canEditSmsPredefini())
				sms = new ImogSingleRelationBox<SmsPredefiniProxy>(smsDataProvider, EpicamRenderer.get());
			else
				sms = new ImogSingleRelationBox<SmsPredefiniProxy>(false, smsDataProvider, EpicamRenderer.get());
		}

	}

	/**
	 * Sets the edition mode
	 * 
	 * @param isEdited true to enable the edition of the form
	 */
	public void setEdited(boolean isEdited) {

		if (isEdited)
			setFieldEditAccess();
		else
			setFieldReadAccess();

		/* EnvoiSms section widgets */
		patient.setEdited(isEdited);
		sms.setEdited(isEdited);

	}

	/**
	 * Configures the visibility of the fields in view mode depending on the user privileges
	 */
	private void setFieldReadAccess() {

	}

	/**
	 * Configures the visibility of the fields in edit mode depending on the user privileges
	 */
	private void setFieldEditAccess() {

	}

	/**
	 * Sets the Request Context for the List Editors.
	 * 
	 * IMPORTANT: We are passing an ImogEntityRequest rather than a specific entity request because this editor can be
	 * used in embedded forms where it may be difficult to now in advance with request will be used.
	 */
	public void setRequestContextForListEditors(ImogEntityRequest ctx) {
	}

	/**
	 * Manages editor updates when a field value changes
	 */
	private void setFieldValueChangeHandler() {

		registrations.add(requestFactory.getEventBus().addHandler(FieldValueChangeEvent.TYPE,
				new FieldValueChangeEvent.Handler() {
					@Override
					public void onValueChange(ImogField<?> field) {

						// field dependent visibility management
						computeVisibility(field, false);

					}
				}));
	}

	/**
	 * Computes the field visibility
	 */
	public void computeVisibility(ImogField<?> source, boolean allValidation) {

	}

	/** Widget update for field patient */
	private void clearPatientWidget() {
		patient.emptyList();
	}

	/**
	 * Setter to inject a SmsPredefini value
	 * 
	 * @param value the value to be injected into the editor
	 * @param isLocked true if relation field shall be locked (non editable)
	 */
	public void setSms(SmsPredefiniProxy value, boolean isLocked) {
		sms.setLocked(isLocked);
		sms.setValue(value);

	}

	/** Widget update for field sms */
	private void clearSmsWidget() {
		sms.clear();
	}

	/**
	 * Configures the handlers of the widgets that manage relation fields
	 */
	private void setRelationHandlers() {

		/* 'Information' button for field patient */
		patient.setViewClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				if (!patient.isEmpty() && patient.getSelectedEntities().size() > 0) {

					PatientProxy value = patient.getSelectedEntities().get(0);
					RelationPopupPanel relationPopup = new RelationPopupPanel();
					PatientFormPanel form = new PatientFormPanel(requestFactory, value.getId(), relationPopup,
							"patient");
					relationPopup.addWidget(form);
					relationPopup.show();
				}
			}
		});

		/* 'Add' button for field patient */
		patient.setAddClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				RelationPopupPanel relationPopup = new RelationPopupPanel();
				PatientFormPanel form = new PatientFormPanel(requestFactory, null, relationPopup, "patient");
				/* common fields */

				relationPopup.addWidget(form);
				relationPopup.show();
			}
		});

		/* SaveEvent handler when a Patient is created or updated from the relation field patient */
		registrations.add(requestFactory.getEventBus().addHandler(SavePatientEvent.TYPE,
				new SavePatientEvent.Handler() {
					@Override
					public void savePatient(PatientProxy value) {
						if (!patient.isPresent(value))
							patient.addValue(value);
					}

					public void savePatient(PatientProxy value, String initField) {
						if (initField.equals("patient")) {
							if (patient.isPresent(value))
								patient.replaceValue(value);
							else
								patient.addValue(value, true);
						}
					}
				}));

		/* 'Information' button for field sms */
		sms.setViewClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (sms.getValue() != null) {
					RelationPopupPanel relationPopup = new RelationPopupPanel();
					SmsPredefiniFormPanel form = new SmsPredefiniFormPanel(requestFactory, sms.getValue().getId(),
							relationPopup, "sms");
					relationPopup.addWidget(form);
					relationPopup.show();
				}
			}
		});

		/* 'Add' button for field sms */
		sms.setAddClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RelationPopupPanel relationPopup = new RelationPopupPanel();
				SmsPredefiniFormPanel form = new SmsPredefiniFormPanel(requestFactory, null, relationPopup, "sms");
				/* common fields */

				relationPopup.addWidget(form);
				relationPopup.show();
			}
		});

		/* SaveEvent handler when a SmsPredefini is created or updated from the relation field sms */
		registrations.add(requestFactory.getEventBus().addHandler(SaveSmsPredefiniEvent.TYPE,
				new SaveSmsPredefiniEvent.Handler() {
					@Override
					public void saveSmsPredefini(SmsPredefiniProxy value) {
						sms.setValue(value);
					}

					@Override
					public void saveSmsPredefini(SmsPredefiniProxy value, String initField) {
						if (initField.equals("sms"))
							sms.setValue(value, true);
					}
				}));

	}

	/**
	 * Validate fields values
	 */
	public void validateFields() {

	}

	/**
	 */
	private void setAllLabelWith(String width) {

		/* EnvoiSms field group */
		patient.setLabelWidth(width);
		sms.setLabelWidth(width);

	}

	/**
	 */
	private void setAllBoxWith(int width) {

		/* EnvoiSms field group */
		patient.setBoxWidth(width);
		sms.setBoxWidth(width);

	}

	@Override
	protected void onUnload() {
		for (HandlerRegistration r : registrations)
			r.removeHandler();
		registrations.clear();
		super.onUnload();
	}

	@Override
	protected void onLoad() {
		setRelationHandlers();
		setFieldValueChangeHandler();
		super.onLoad();
	}

	public List<PatientProxy> getPatientList() {
		return patient.getValue();
	}

	public SmsPredefiniProxy getSms() {

		return sms.getValue();
	}

}
