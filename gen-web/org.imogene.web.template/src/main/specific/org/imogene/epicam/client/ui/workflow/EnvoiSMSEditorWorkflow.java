package org.imogene.epicam.client.ui.workflow;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.imogene.epicam.client.EpicamEntryPoint;
import org.imogene.epicam.client.EpicamRenderer;
import org.imogene.epicam.client.event.save.SaveOutBoxEvent;
import org.imogene.epicam.client.ui.editor.EnvoiSMSEditor;
import org.imogene.epicam.shared.EpicamRequestFactory;
import org.imogene.epicam.shared.proxy.OutBoxProxy;
import org.imogene.epicam.shared.proxy.PatientProxy;
import org.imogene.epicam.shared.proxy.SmsPredefiniProxy;
import org.imogene.epicam.shared.request.OutBoxRequest;
import org.imogene.web.client.event.ListEnvoiSMSEvent;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.field.error.ImogConstraintViolation;
import org.imogene.web.client.ui.panel.RelationPopupPanel;
import org.imogene.web.client.ui.workflow.EditorWorkflowComposite;
import org.imogene.web.client.util.ImogKeyGenerator;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.web.bindery.requestfactory.shared.BaseProxy;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

/**
 * Worflow that manages the life of a EnvoiSMSProxy in the UI
 * 
 * @author MEDES-IMPS
 */
public class EnvoiSMSEditorWorkflow extends EditorWorkflowComposite {

	private EpicamRequestFactory requestFactory;

	private EnvoiSMSEditor editor;
	private String initField;
	private boolean showGlassPanel = false;
	private OutBoxRequest outboxRequest;
	public OutBoxProxy outboxproxy;

	/**
	 * Workflow constructor for the creation of a EnvoiSMS instance
	 * 
	 * @param factory the application request factory
	 * @param titleContainer the Label that will display the workflow title
	 */
	public EnvoiSMSEditorWorkflow(EpicamRequestFactory factory, Label titleContainer) {
		this(factory, titleContainer, null, null);
	}

	/**
	 * Workflow constructor for the creation of a EnvoiSMS instance
	 * 
	 * @param factory the application request factory
	 * @param titleContainer the Label that will display the workflow title
	 * @param parent the parent RelationPopupPanel when the workflow is opened from a relation field
	 * @param initField the name of the field that initiated the opening of the workflow when the workflow is opened
	 *            from a relation field
	 */
	public EnvoiSMSEditorWorkflow(EpicamRequestFactory factory, Label titleContainer, RelationPopupPanel parent,
			String initField) {

		super(factory.getEventBus(), titleContainer, parent);

		requestFactory = factory;
		if (parent != null) {
			editor = new EnvoiSMSEditor(factory, true);
			this.initField = initField;
		} else
			editor = new EnvoiSMSEditor(factory);

		isNew = true;
		setEditable(true);

		setTitle(BaseNLS.constants().envoiSMS_create_title());

		createNewEnvoiSMS();

		this.setContent(editor);
	}

	/**
	 * Workflow constructor for the visualization and edition of an existing EnvoiSMS instance
	 * 
	 * @param factory the application request factory
	 * @param entityId the id of the EnvoiSMS instance to be visualized and edited
	 * @param titleContainer the Label that will display the workflow title
	 */
	public EnvoiSMSEditorWorkflow(EpicamRequestFactory factory, String entityId, Label titleContainer) {
		this(factory, entityId, titleContainer, null, null);
	}

	/**
	 * Workflow constructor for the visualization and edition of an existing EnvoiSMS instance
	 * 
	 * @param factory the application request factory
	 * @param entityId the id of the EnvoiSMS instance to be visualized and edited
	 * @param titleContainer the label
	 * @param parent the parent RelationPopupPanel when the workflow is opened from a relation field
	 * @param initField the name of the field that initiated the opening of the workflow when the workflow is opened
	 *            from a relation field
	 */
	public EnvoiSMSEditorWorkflow(EpicamRequestFactory factory, String entityId, Label titleContainer,
			RelationPopupPanel parent, String initField) {

		super(factory.getEventBus(), titleContainer, parent);

		requestFactory = factory;
		if (parent != null) {
			editor = new EnvoiSMSEditor(factory, true);
			this.initField = initField;
		} else
			editor = new EnvoiSMSEditor(factory);

		setModifiable(false);
		isNew = false;
		setEditable(false);

		this.setContent(editor);
		showGlassPanel = true;
	}

	@Override
	protected void onAttach() {
		super.onAttach();
		if (showGlassPanel) {
			EpicamEntryPoint.GP.showAndAdapt(this);
		}
	}

	/**
	 * Create a new instance of EnvoiSMS
	 */
	private void createNewEnvoiSMS() {

		/* set request context for list editor operations */
		editor.setRequestContextForListEditors(requestFactory.outBoxRequest());

		/* update field widgets in editor */
		editor.computeVisibility(null, true);
		editor.setEdited(true);
	}

	/**
	 * Edit the current instance of EnvoiSMS in editor
	 */
	@Override
	protected void edit() {

		/* set the instance in edit mode in the editor */
		editor.setEdited(true);

		/* update field widgets in editor */
	}

	/**
	 * Persist the current instance of EnvoiSMS
	 */
	@Override
	protected void save() {

		List<PatientProxy> listPatients = editor.getPatientList();
		SmsPredefiniProxy sms = editor.getSms();
		outboxRequest = requestFactory.outBoxRequest();
		outboxproxy = outboxRequest.create(OutBoxProxy.class);
		for (PatientProxy pat : listPatients) {
			outboxproxy.setId(ImogKeyGenerator.generateKeyId("OUTBOX"));
			outboxproxy.setPatient(pat);
			outboxproxy.setMessage(sms.getMessage().getFrancais().toString());

			outboxproxy.setStatut("1");
			Request<Void> saveRequest = outboxRequest.save(outboxproxy, isNew);

			saveRequest.to(new Receiver<Void>() {
				@Override
				public void onSuccess(Void response) {
					requestFactory.getEventBus().fireEvent(new SaveOutBoxEvent(outboxproxy, initField));
					closeForm();
				}

				@Override
				public void onConstraintViolation(Set<ConstraintViolation<?>> errors) {
					if (errors != null && errors.size() > 0) {
						// convert ConstraintViolation to get localized messages
						EpicamRenderer renderer = EpicamRenderer.get();
						Set<ConstraintViolation<?>> imogErrors = new HashSet<ConstraintViolation<?>>();
						for (ConstraintViolation<?> error : errors) {
							ImogConstraintViolation violation = new ImogConstraintViolation();
							violation.setLeafBean((BaseProxy) error.getLeafBean());
							violation.setPropertyPath(error.getPropertyPath());
							violation.setRootBean((BaseProxy) error.getRootBean());
							violation.setMessage(renderer.getI18nErrorMessage(error.getMessage()));
							imogErrors.add(violation);
						}

					}
				}

				@Override
				public void onFailure(ServerFailure error) {
					Window.alert("Error updating the EnvoiSMS");
					super.onFailure(error);
				}
			});
		}
		outboxRequest.fire();

	}

	@Override
	protected void cancel() {
		if (parent != null)
			parent.hide();
		else {
			requestFactory.getEventBus().fireEvent(closeEvent);

		}

	}

	@Override
	protected void returnToList() {
		requestFactory.getEventBus().fireEvent(new ListEnvoiSMSEvent());
	}

	/**
	 * Setter to inject a SmsPredefini value
	 * 
	 * @param value the value to be injected
	 * @param isLocked true if relation field shall be locked (non editable)
	 */
	public void setSms(SmsPredefiniProxy value, boolean isLocked) {
		editor.setSms(value, isLocked);
	}

}
