package org.imogene.epicam.client.ui.workflow;


import java.util.Date;
import java.util.Vector;

import org.imogene.epicam.client.AccessManager;
import org.imogene.epicam.client.EpicamEntryPoint;
import org.imogene.epicam.client.ui.editor.RapportEditor;
import org.imogene.epicam.shared.EpicamRequestFactory;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.panel.RelationPopupPanel;
import org.imogene.web.client.ui.workflow.EditorWorkflowComposite;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.Window;

/**
 * Worflow that manages the life of a RapportProxy in the UI
 * 
 * @author MEDES-IMPS
 */
public class RapportEditorWorkflow extends EditorWorkflowComposite {

	private EpicamRequestFactory requestFactory;

	private RapportEditor editor;
	
	private boolean showGlassPanel = false;

	/**
	 * Workflow constructor for the creation of a Rapport instance
	 * 
	 * @param factory the application request factory
	 * @param titleContainer the Label that will display the workflow title
	 */
	public RapportEditorWorkflow(EpicamRequestFactory factory, Label titleContainer) {
		this(factory, titleContainer, null, null);
	}

	/**
	 * Workflow constructor for the creation of a Rapport instance
	 * 
	 * @param factory the application request factory
	 * @param titleContainer the Label that will display the workflow title
	 * @param parent the parent RelationPopupPanel when the workflow is opened from a relation field
	 * @param initField the name of the field that initiated the opening of the workflow when the workflow is opened
	 *            from a relation field
	 */
	public RapportEditorWorkflow(EpicamRequestFactory factory, Label titleContainer, RelationPopupPanel parent,
			String initField) {

		super(factory.getEventBus(), titleContainer, parent);

		requestFactory = factory;
		if (parent != null) {
			editor = new RapportEditor(factory, true);
		} else
			editor = new RapportEditor(factory);

		isNew = true;
		setEditable(true);

		setTitle(BaseNLS.constants().rapport_create_title());
		createNewRapport();

		this.setContent(editor);
	}

	/**
	 * Workflow constructor for the visualization and edition of an existing Rapport instance
	 * 
	 * @param factory the application request factory
	 * @param entityId the id of the Rapport instance to be visualized and edited
	 * @param titleContainer the Label that will display the workflow title
	 */
	public RapportEditorWorkflow(EpicamRequestFactory factory, String entityId, Label titleContainer) {
		this(factory, entityId, titleContainer, null, null);
	}

	/**
	 * Workflow constructor for the visualization and edition of an existing Rapport instance
	 * 
	 * @param factory the application request factory
	 * @param entityId the id of the Rapport instance to be visualized and edited
	 * @param titleContainer the label
	 * @param parent the parent RelationPopupPanel when the workflow is opened from a relation field
	 * @param initField the name of the field that initiated the opening of the workflow when the workflow is opened
	 *            from a relation field
	 */
	public RapportEditorWorkflow(EpicamRequestFactory factory, String entityId, Label titleContainer,
			RelationPopupPanel parent, String initField) {

		super(factory.getEventBus(), titleContainer, parent);

		requestFactory = factory;
		if (parent != null) {
			editor = new RapportEditor(factory, true);
		} else
			editor = new RapportEditor(factory);

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
	 * Create a new instance of Rapport
	 */
	private void createNewRapport() {
		/* set request context for list editor operations */
		editor.setRequestContextForListEditors(requestFactory.outBoxRequest());

		/* update field widgets in editor */
		editor.computeVisibility(null, true);
		editor.setEdited(true);
	}

	@Override
	protected void edit() {

		/* set the instance in edit mode in the editor */
		editor.setEdited(true);

		/* update field widgets in editor */
	}

	/**
	 * Persist the current instance of Rapport
	 */
//	@Override
//	protected void save() {
////		if (AccessManager.canExportPatient() && editor.validateFields()) {
//			String url="";
//			//récupération des informations depuis l'interface
//			String dateDebut = editor.getDate_debut();
//			String dateFin = editor.getDate_fin();
//			String typeRapport = editor.getTypeRapport();
////			if(typeRapport.equals("registre tb")||typeRapport.equals("tb register")){
//				url = GWT.getHostPageBaseURL()+"registreTB.pdf?ReportName=registreTB&loc=fr&ReportFormat=pdf&cdt=" + "\"jamot\"&"+"initPeriode="+dateDebut.toString()+"&"+"endPeriode="+dateFin.toString();
////			}
//			if(typeRapport.equals("registre labo")||typeRapport.equals("labo register")){
//				url = GWT.getHostPageBaseURL()+"registreLabo.pdf?ReportName=registreTB&loc=fr&ReportFormat=pdf&cdt=" +
//						"\"jamot\"&"+"initPeriode="+dateDebut.toString()+"&"+"endPeriode="+dateFin.toString();
////			}
//			Window.alert(url);
//			Window.open(url, "_blank", "");
////		}
//	}
	
	
	@Override
	protected void save() {
		String url= editor.getReportURL();
		Window.open(url, "_blank", "");
	}
	
	public void setParameter(Vector<String> parameter, String reportName){
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
		// requestFactory.getEventBus().fireEvent(new ListRapportEvent());
	}
	
	
	/**/

}
