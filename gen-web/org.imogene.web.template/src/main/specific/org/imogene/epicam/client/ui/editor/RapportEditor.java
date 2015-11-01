package org.imogene.epicam.client.ui.editor;

import java.util.ArrayList;
import java.util.List;

import org.imogene.epicam.client.AccessManager;
import org.imogene.epicam.client.EpicamRenderer;
import org.imogene.epicam.client.dataprovider.CentreDiagTraitDataProvider;
import org.imogene.epicam.client.dataprovider.DistrictSanteDataProvider;
import org.imogene.epicam.client.dataprovider.LaboratoireReferenceDataProvider;
import org.imogene.epicam.client.dataprovider.PatientDataProvider;
import org.imogene.epicam.client.dataprovider.PersonnelDataProvider;
import org.imogene.epicam.client.dataprovider.RegionDataProvider;
import org.imogene.epicam.client.event.save.SaveCentreDiagTraitEvent;
import org.imogene.epicam.client.event.save.SaveDistrictSanteEvent;
import org.imogene.epicam.client.event.save.SaveRegionEvent;
import org.imogene.epicam.client.i18n.NLS;
import org.imogene.epicam.client.ui.filter.RegionFilterPanel;
import org.imogene.epicam.client.ui.workflow.panel.CentreDiagTraitFormPanel;
import org.imogene.epicam.client.ui.workflow.panel.DistrictSanteFormPanel;
import org.imogene.epicam.client.ui.workflow.panel.RegionFormPanel;
import org.imogene.epicam.domain.entity.CentreDiagTrait;
import org.imogene.epicam.domain.entity.DistrictSante;
import org.imogene.epicam.shared.EpicamRequestFactory;
import org.imogene.epicam.shared.constants.EpicamBirtConstants;
import org.imogene.epicam.shared.constants.EpicamEnumConstants;
import org.imogene.epicam.shared.proxy.CentreDiagTraitProxy;
import org.imogene.epicam.shared.proxy.DistrictSanteProxy;
import org.imogene.epicam.shared.proxy.LaboratoireReferenceProxy;
import org.imogene.epicam.shared.proxy.PatientProxy;
import org.imogene.epicam.shared.proxy.PersonnelProxy;
import org.imogene.epicam.shared.proxy.RegionProxy;
import org.imogene.web.client.event.FieldValueChangeEvent;
import org.imogene.web.client.i18n.BaseNLS;
import org.imogene.web.client.ui.field.ImogDateBox;
import org.imogene.web.client.ui.field.ImogField;
import org.imogene.web.client.ui.field.ImogSingleEnumBox;
import org.imogene.web.client.ui.field.ImogTextBox;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * Editor that provides the UI components that allow a RapportProxy to be viewed and edited
 * 
 * @author MEDES-IMPS
 */
public class RapportEditor extends Composite {

	interface Binder extends UiBinder<Widget, RapportEditor> {
	}

	private static final Binder BINDER = GWT.create(Binder.class);

	private static final String RAPPORT_TRIMESTRE_TRIMESTRE1 = "trimestre1";
	private static final String RAPPORT_TRIMESTRE_TRIMESTRE2 = "trimestre2";
	private static final String RAPPORT_TRIMESTRE_TRIMESTRE3 = "trimestre3";
	private static final String RAPPORT_TRIMESTRE_TRIMESTRE4 = "trimestre4";
	
//	parameters for type of reports

	private static final String REGISTRETB = "RegTB";
	private static final String REGISTRETBENFANT = "RegTBEfants";
	private static final String REGISTRELABO = "RegLabo";
	private static final String RAPPORTTRIMDESPIST = "RapTrimDepist";
	private static final String RAPPORTTRIMTRAIT = "RapTrimTraitement";
	private static final String RAPPORTTRIMLABO = "RapTrimLabo";
	private static final String CARTEPAT = "CartePatient";
	private static final String FICHETRAIT = "FicheTraitement";
	private static final String FICHETRANSREF = "FicheTransRef"; 
	private static final String RAPDISTRIBMEDIC = "RapDistribMedicament";	


	protected final EpicamRequestFactory requestFactory;
	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

	private boolean hideButtons = false;

	/* Filtre section widgets */
	@UiField
	@Ignore
	FieldGroupPanel filtreSection;
	@UiField
	ImogSingleEnumBox typeRapport;

	@UiField(provided = true)
	ImogSingleRelationBox<RegionProxy> region;
	private RegionDataProvider regionDataProvider;
	@UiField(provided = true)
	ImogSingleRelationBox<DistrictSanteProxy> district;
	private DistrictSanteDataProvider districtSanteDataProvider;
	@UiField(provided = true)
	ImogSingleRelationBox<CentreDiagTraitProxy> cdt;
	private CentreDiagTraitDataProvider cdtDataProvider;
	
	@UiField(provided = true)
	ImogSingleRelationBox<PersonnelProxy> personnel;
	private PersonnelDataProvider personnelDataProvider;
	
	@UiField (provided = true)
	ImogSingleRelationBox<LaboratoireReferenceProxy> laboratoire;
	private LaboratoireReferenceDataProvider laboratoireReferenceDataProvider;
	
	@UiField (provided = true)
	ImogSingleRelationBox<PatientProxy> patient;
	private PatientDataProvider patientDataProvider;

	
	/* Periode section widgets */
	@UiField
	@Ignore
	FieldGroupPanel periodeSection;
	@UiField
	ImogSingleEnumBox trimestre;
	@UiField
	ImogTextBox annee;
	@UiField
	ImogDateBox date_debut;
	@UiField
	ImogDateBox date_fin;

	/**
	 * Constructor
	 * 
	 * @param factory the application request factory
	 * @param hideButtons true if the relation field buttons shall be hidden
	 */
	public RapportEditor(EpicamRequestFactory factory, boolean hideButtons) {

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
	public RapportEditor(EpicamRequestFactory factory) {
		this(factory, false);
	}

	/**
	 * Sets the properties of the fields
	 */

		
	
	private void properties() {
		/* Filtre section widgets */
		filtreSection.setGroupTitle(BaseNLS.constants().rapport_group_filtre());
		//Region
		region.setLabel(BaseNLS.constants().rapport_field_region());
//		region.setLabel(NLS.constants().region_field_nom());
		//the values of region affects the values of other fields
		region.notifyChanges(requestFactory.getEventBus());
		//District
		district.setLabel(BaseNLS.constants().rapport_field_district());
		district.notifyChanges(requestFactory.getEventBus());
		//CDT
		cdt.setLabel(BaseNLS.constants().rapport_field_cdt());
		cdt.notifyChanges(requestFactory.getEventBus());
		
		personnel.setLabel(BaseNLS.constants().rapport_field_personnel());
		personnel.notifyChanges(requestFactory.getEventBus());
		
		laboratoire.setLabel(BaseNLS.constants().rapport_field_laboratoire());
		laboratoire.notifyChanges(requestFactory.getEventBus());
		
		patient.setLabel(BaseNLS.constants().rapport_field_patient());
		patient.notifyChanges(requestFactory.getEventBus());

		

		typeRapport.setLabel(BaseNLS.constants().rapport_field_type_rapport());
		typeRapport.addItem(REGISTRETB, BaseNLS.constants().rapport_field_type_rapport_tb_reg_option1());
		typeRapport.addItem(REGISTRETBENFANT, BaseNLS.constants().rapport_field_type_rapport_child_reg_option2());
		typeRapport.addItem(REGISTRELABO, BaseNLS.constants().rapport_field_type_rapport_lab_reg_option3());
		typeRapport.addItem(RAPPORTTRIMDESPIST, BaseNLS.constants().rapport_field_type_rapport_trim_testing_option4());
		typeRapport.addItem(RAPPORTTRIMTRAIT, BaseNLS.constants().rapport_field_type_rapport_trim_treatment_option5());
		typeRapport.addItem(RAPPORTTRIMLABO, BaseNLS.constants().rapport_field_type_rapport_trim_lab_option6());
		typeRapport.addItem(CARTEPAT, BaseNLS.constants().rapport_field_type_rapport_patient_card_option7());
		typeRapport.addItem(FICHETRAIT, BaseNLS.constants().rapport_field_type_rapport_treatment_form_option8());
		typeRapport.addItem(FICHETRANSREF, BaseNLS.constants().rapport_field_type_rapport_transRef_form_option9());
		typeRapport.addItem(RAPDISTRIBMEDIC, BaseNLS.constants().rapport_field_type_rapport_drug_Distrib_option10());
		
		
		
		/* Periode section widgets */
		periodeSection.setGroupTitle(BaseNLS.constants().rapport_group_periode());
		trimestre.setLabel(BaseNLS.constants().rapport_field_trimestre());
		trimestre.addItem(RAPPORT_TRIMESTRE_TRIMESTRE1, BaseNLS.constants().rapport_trimestre_trimestre1_option());
		trimestre.addItem(RAPPORT_TRIMESTRE_TRIMESTRE2, BaseNLS.constants().rapport_trimestre_trimestre2_option());
		trimestre.addItem(RAPPORT_TRIMESTRE_TRIMESTRE3, BaseNLS.constants().rapport_trimestre_trimestre3_option());
		trimestre.addItem(RAPPORT_TRIMESTRE_TRIMESTRE4, BaseNLS.constants().rapport_trimestre_trimestre4_option());
		annee.setLabel(BaseNLS.constants().rapport_field_annee());
		date_debut.setLabel(BaseNLS.constants().rapport_field_date_debut());
		date_fin.setLabel(BaseNLS.constants().rapport_field_date_fin());
		
	}

	/**
	 * Configures the widgets that manage relation fields
	 */

	
	private void setRelationFields() {

		/* field  region */
		regionDataProvider = new RegionDataProvider(requestFactory);
		if (hideButtons) // in popup, relation buttons hidden
			region = new ImogSingleRelationBox<RegionProxy>(regionDataProvider, EpicamRenderer.get(), true);
		else {
			// in wrapper panel, relation buttons enabled												
			if (AccessManager.canCreateRegion()	&& AccessManager.canEditRegion())
				region = new ImogSingleRelationBox<RegionProxy>(regionDataProvider, EpicamRenderer.get());
			else
				region = new ImogSingleRelationBox<RegionProxy>(false, regionDataProvider, EpicamRenderer.get());
		}

		/* field  districtSante */
		districtSanteDataProvider = new DistrictSanteDataProvider(requestFactory);
		if (hideButtons) // in popup, relation buttons hidden
			district = new ImogSingleRelationBox<DistrictSanteProxy>(districtSanteDataProvider, EpicamRenderer.get(), true);
		else {// in wrapper panel, relation buttons enabled												
			if (AccessManager.canCreateDistrictSante() && AccessManager.canEditDistrictSante())
				district = new ImogSingleRelationBox<DistrictSanteProxy>(districtSanteDataProvider, EpicamRenderer.get());
			else
				district = new ImogSingleRelationBox<DistrictSanteProxy>(false, districtSanteDataProvider, EpicamRenderer.get());
		}

		/* field  CDT */
		cdtDataProvider = new CentreDiagTraitDataProvider(requestFactory);
		if (hideButtons) // in popup, relation buttons hidden
			cdt = new ImogSingleRelationBox<CentreDiagTraitProxy>(cdtDataProvider, EpicamRenderer.get(), true);
		else {// in wrapper panel, relation buttons enabled												
			if (AccessManager.canCreateCentreDiagTrait() && AccessManager.canEditCentreDiagTrait())
				cdt = new ImogSingleRelationBox<CentreDiagTraitProxy>(cdtDataProvider, EpicamRenderer.get());
			else
				cdt = new ImogSingleRelationBox<CentreDiagTraitProxy>(false, cdtDataProvider, EpicamRenderer.get());
		}

		/* field  Personnel */
		personnelDataProvider = new PersonnelDataProvider(requestFactory);
		if (hideButtons) // in popup, relation buttons hidden
			personnel = new ImogSingleRelationBox<PersonnelProxy>(personnelDataProvider, EpicamRenderer.get(), true);
		else {// in wrapper panel, relation buttons enabled												
			if (AccessManager.canCreateCentreDiagTrait() && AccessManager.canEditCentreDiagTrait())
				personnel = new ImogSingleRelationBox<PersonnelProxy>(personnelDataProvider, EpicamRenderer.get());
			else
				personnel = new ImogSingleRelationBox<PersonnelProxy>(false, personnelDataProvider, EpicamRenderer.get());
		}

		/* field  Personnel */
		laboratoireReferenceDataProvider = new LaboratoireReferenceDataProvider(requestFactory);
		if (hideButtons) // in popup, relation buttons hidden
			laboratoire = new ImogSingleRelationBox<LaboratoireReferenceProxy>(laboratoireReferenceDataProvider, EpicamRenderer.get(), true);
		else {// in wrapper panel, relation buttons enabled												
			if (AccessManager.canCreateCentreDiagTrait() && AccessManager.canEditCentreDiagTrait())
				laboratoire = new ImogSingleRelationBox<LaboratoireReferenceProxy>(laboratoireReferenceDataProvider, EpicamRenderer.get());
			else
				laboratoire = new ImogSingleRelationBox<LaboratoireReferenceProxy>(false, laboratoireReferenceDataProvider, EpicamRenderer.get());
		}

		/* field  Personnel */
		patientDataProvider = new PatientDataProvider(requestFactory);
		if (hideButtons) // in popup, relation buttons hidden
			patient = new ImogSingleRelationBox<PatientProxy>(patientDataProvider, EpicamRenderer.get(), true);
		else {// in wrapper panel, relation buttons enabled												
			if (AccessManager.canCreateCentreDiagTrait() && AccessManager.canEditCentreDiagTrait())
				patient = new ImogSingleRelationBox<PatientProxy>(patientDataProvider, EpicamRenderer.get());
			else
				patient = new ImogSingleRelationBox<PatientProxy>(false, patientDataProvider, EpicamRenderer.get());
		}
		
		
		
//		if (hideButtons) // in popup, relation buttons hidden
//			region = new ImogMultiRelationBox<RegionProxy>(regionDataProvider, EpicamRenderer.get(), true);
//		else {// in wrapper panel, relation buttons enabled
//			if (AccessManager.canCreateRegion() && AccessManager.canEditRegion())
//				region = new ImogMultiRelationBox<RegionProxy>(regionDataProvider, EpicamRenderer.get(), null);
//			else
//				region = new ImogMultiRelationBox<RegionProxy>(false, regionDataProvider, EpicamRenderer.get(), null);
//		}
//		region.setPopUpTitle(NLS.constants().region_select_title());
//		region.setFilterPanel(new RegionFilterPanel());

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

		/* Filtre section widgets */
		region.setEdited(isEdited);
		district.setEdited(isEdited);
		cdt.setEdited(isEdited);
		typeRapport.setEdited(isEdited);
		
		personnel.setEdited(true);
		patient.setEdited(true);
		laboratoire.setEdited(true);

		/* Periode section widgets */
		trimestre.setEdited(isEdited);
		annee.setEdited(isEdited);
		date_debut.setEdited(isEdited);
		date_fin.setEdited(isEdited);

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
						
						/* DistrictSante list content depends on the value of field Region */
						if (field.equals(region)) {
							clearDistrictWidget();
							getDistrictFilteredByRegion(region.getValue());

						}
						/* CDT list content depends on the value of field DistrictSante */
						if (field.equals(district)) {
							clearCDTWidget();
							getCDTFilteredByDistrict(district.getValue());

							if (district.getValue() != null) {
								RegionProxy proxy = district.getValue()
										.getRegion();
								region.setValue(proxy);
							}

						}

						if (field.equals(cdt)) {
							CentreDiagTraitProxy cdtValue = cdt.getValue();
							if (cdtValue != null) {
								RegionProxy regionValue = cdtValue.getRegion();
								region.setValue(regionValue);
								DistrictSanteProxy districtValue = cdtValue
										.getDistrictSante();
								district.setValue(districtValue);
							}
						}

						/*Filter by personnel, laboratoire reference and patient*/
//						if(field.equals(personnel)){
//							PersonnelProxy personnelValue = personnel.getValue();
//							if(personnelValue !=null){
//								RegionProxy regionValue = personnelValue.getRegion();
//								
//							}
//						}
//						if (field.equals(cdt)) {
//							CentreDiagTraitProxy cdtValue = cdt.getValue();
//							if (cdtValue != null) {
//								RegionProxy regionValue = cdtValue.getRegion();
//								region.setValue(regionValue);
//								DistrictSanteProxy districtValue = cdtValue
//										.getDistrictSante();
//								district.setValue(districtValue);
//							}
//						}

						/* if trimester is selected, then period will not be enabled*/
						if(field.equals(trimestre)){
							if(trimestre.getValue()!=null){
								date_debut.setEdited(false);
								date_fin.setEdited(false);
							}
							else {
								if(trimestre.getValue()==null||trimestre.getValue().trim()==""){

								date_debut.setEdited(true);
								date_fin.setEdited(true);
							
								}
							}
						}
						
					}
				}));
	}

	/**
	 * Computes the field visibility
	 */
	public void computeVisibility(ImogField<?> source, boolean allValidation) {

	}

	/**
	 * Filters the content of the RelationField DistrictSante by the value of 
	 * the RelationField Region
	 * @param region the value of 
	 * the RelationField Region 
	 */
	public void getDistrictFilteredByRegion(RegionProxy pRegion) {

		if (pRegion != null) {
			if (!hideButtons)
				district.hideButtons(false);
			districtSanteDataProvider.setFilterCriteria(pRegion.getId(),
					"region.id");
		} else {
			districtSanteDataProvider.setIsFiltered(false);
		}
		getCDTFilteredByRegion(pRegion);
	}

	/**
	 * Filters the content of the RelationField CDT by the value of 
	 * the RelationField DistrictSante
	 * @param districtSante the value of 
	 * the RelationField DistrictSante 
	 */
	public void getCDTFilteredByDistrict(DistrictSanteProxy pDistrictSante) {

		if (pDistrictSante != null) {
			if (!hideButtons)
				cdt.hideButtons(false);
			cdtDataProvider.setFilterCriteria(pDistrictSante.getId(),
					"districtSante.id");
		}
	}

	public void getCDTFilteredByRegion(RegionProxy pRegion) {

		if (pRegion != null) {
			if (!hideButtons)
				cdt.hideButtons(false);
			cdtDataProvider.setFilterCriteria(pRegion.getId(), "region.id");
		} else {
			cdtDataProvider.setIsFiltered(false);
		}

	}
	
	/**
	 * Setter to inject a Region value
	 * @param value the value to be injected into the editor
	 * @param isLocked true if relation field shall be locked (non editable)
	 */
	public void setRegion(RegionProxy value, boolean isLocked) {
		region.setLocked(isLocked);
		region.setValue(value);

		// Field DistrictSante depends on the value of field region
		clearDistrictWidget();
		getDistrictFilteredByRegion(value);
	}
	/** Widget update for field district */
	private void clearRegionWidget() {
		region.clear();
		clearDistrictWidget();
	}
	
	/**
	 * Setter to inject a DistrictSante value
	 * @param value the value to be injected into the editor
	 * @param isLocked true if relation field shall be locked (non editable)
	 */
	public void setDistrictSante(DistrictSanteProxy value, boolean isLocked) {
		district.setLocked(isLocked);
		district.setValue(value);

		// Field CDT depends on the value of field districtSante
		clearCDTWidget();
		getCDTFilteredByDistrict(value);
	}

	/** Widget update for field region */
	private void clearDistrictWidget() {
		district.clear();
		clearCDTWidget();
	}
	
	/**
	 * Setter to inject a CentreDiagTrait value
	 * @param value the value to be injected into the editor
	 * @param isLocked true if relation field shall be locked (non editable)
	 */
	public void setCDT(CentreDiagTraitProxy value, boolean isLocked) {
		cdt.setLocked(isLocked);
		cdt.setValue(value);

	}
	/** Widget update for field region */
	private void clearCDTWidget() {
		cdt.clear();
	}

	/**
	 * Configures the handlers of the widgets that manage relation fields
	 */
	private void setRelationHandlers() {

		/* 'Information' button for field Region */
		region.setViewClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (region.getValue() != null) {
					RelationPopupPanel relationPopup = new RelationPopupPanel();
					RegionFormPanel form = new RegionFormPanel(requestFactory,
							region.getValue().getId(), relationPopup, "region");
					relationPopup.addWidget(form);
					relationPopup.show();
				}
			}
		});
		
		/* 'Add' button for field Region */
		region.setAddClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RelationPopupPanel relationPopup = new RelationPopupPanel();
				RegionFormPanel form = new RegionFormPanel(requestFactory,
						null, relationPopup, "region");
				/* common fields */

				relationPopup.addWidget(form);
				relationPopup.show();
			}
		});
		/* SaveEvent handler when a Region is created or updated from the relation field Region */
		registrations.add(requestFactory.getEventBus().addHandler(
				SaveRegionEvent.TYPE, new SaveRegionEvent.Handler() {
					@Override
					public void saveRegion(RegionProxy value) {
						region.setValue(value);
					}
					@Override
					public void saveRegion(RegionProxy value, String initField) {
						if (initField.equals("region"))
							region.setValue(value, true);
					}
				}));

		/* 'Information' button for field DistrictSante */
		district.setViewClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (district.getValue() != null) {
					RelationPopupPanel relationPopup = new RelationPopupPanel();
					DistrictSanteFormPanel form = new DistrictSanteFormPanel(
							requestFactory, district.getValue().getId(),
							relationPopup, "districtSante");
					relationPopup.addWidget(form);
					relationPopup.show();
				}
			}
		});
		/* 'Add' button for field DistrictSante */
		district.setAddClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RelationPopupPanel relationPopup = new RelationPopupPanel();
				DistrictSanteFormPanel form = new DistrictSanteFormPanel(
						requestFactory, null, relationPopup, "districtSante");
				/* common fields */

				relationPopup.addWidget(form);
				relationPopup.show();
			}
		});
		/* SaveEvent handler when a DistrictSante is created or updated from the relation field DistrictSante */
		registrations.add(requestFactory.getEventBus().addHandler(
				SaveDistrictSanteEvent.TYPE,
				new SaveDistrictSanteEvent.Handler() {
					@Override
					public void saveDistrictSante(DistrictSanteProxy value) {
						district.setValue(value);
					}
					@Override
					public void saveDistrictSante(DistrictSanteProxy value,
							String initField) {
						if (initField.equals("districtSante"))
							district.setValue(value, true);
					}
				}));

		/* 'Information' button for field CDT */
		cdt.setViewClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (cdt.getValue() != null) {
					RelationPopupPanel relationPopup = new RelationPopupPanel();
					CentreDiagTraitFormPanel form = new CentreDiagTraitFormPanel(
							requestFactory, cdt.getValue().getId(),
							relationPopup, "CDT");
					relationPopup.addWidget(form);
					relationPopup.show();
				}
			}
		});

		/* 'Add' button for field CDT */
		cdt.setAddClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RelationPopupPanel relationPopup = new RelationPopupPanel();
				CentreDiagTraitFormPanel form = new CentreDiagTraitFormPanel(
						requestFactory, null, relationPopup, "CDT");
				/* common fields */

				relationPopup.addWidget(form);
				relationPopup.show();
			}
		});
		/* SaveEvent handler when a CentreDiagTrait is created or updated from the relation field CDT */
		registrations.add(requestFactory.getEventBus().addHandler(
				SaveCentreDiagTraitEvent.TYPE,
				new SaveCentreDiagTraitEvent.Handler() {
					@Override
					public void saveCentreDiagTrait(CentreDiagTraitProxy value) {
						cdt.setValue(value);
					}
					@Override
					public void saveCentreDiagTrait(CentreDiagTraitProxy value,
							String initField) {
						if (initField.equals("CDT"))
							cdt.setValue(value, true);
					}
				}));
	}

	
	
	/**
	 * Validate fields values
	 */
	public boolean validateFields() {
		
		//User must select one type of report
		if(typeRapport.getValue() == null){
			Window.alert("Un type de rapport doit être sélectioné");
			return false;
		}else return true;
	}

	/**
	 */
	private void setAllLabelWith(String width) {

		/* Filtre field group */
		region.setLabelWidth(width);
		district.setLabelWidth(width);
		cdt.setLabelWidth(width);

		/* Periode field group */
		trimestre.setLabelWidth(width);
		annee.setLabelWidth(width);
		date_debut.setLabelWidth(width);
		date_fin.setLabelWidth(width);
		typeRapport.setLabelWidth(width);
		
		personnel.setLabelWidth(width);
		laboratoire.setLabelWidth(width);
		patient.setLabelWidth(width);

	}

	/**
	 */
	private void setAllBoxWith(int width) {

		/* Filtre field group */
		region.setBoxWidth(width);
		district.setBoxWidth(width);
		cdt.setBoxWidth(width);

		/* Periode field group */
		trimestre.setBoxWidth(width);
		typeRapport.setBoxWidth(width);
		annee.setBoxWidth(width);
		
		personnel.setBoxWidth(width);
		laboratoire.setBoxWidth(width);
		patient.setBoxWidth(width);

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
	
	public String getDate_debut() {
		return DateTimeFormat.getFormat("dd/MM/yyyy").format(date_debut.getValue());
	}

	public String getDate_fin() {
		// TODO Auto-generated method stub
		return DateTimeFormat.getFormat("dd/MM/yyyy").format(date_fin.getValue());
	}

	public String getTypeRapport() {
		return typeRapport.getValue();
	}

	/**
	 * 
	 * @return
	 */
		public String getReportURL(){
			
			String locale = EpicamBirtConstants.REPORT_LOCALE+"="+NLS.constants().locale();
			
			String url = GWT.getHostPageBaseURL()+getNameRapport()+".pdf?ReportName="+getNameRapport()+"&"+locale+"&ReportFormat=pdf";

		if (region.getValue() != null)
			url = url + "&region=" + region.getValue().getId();

		if (district.getValue() != null)
			url = url + "&district=" + district.getValue().getId();

		if (cdt.getValue() != null)
			url = url + "&cdt=" + cdt.getValue().getId();

		if (personnel.getValue() != null)
			url = url + "&personnel=" + personnel.getValue().getId();

		if (laboratoire.getValue() != null)
			url = url + "&laboratoire=" + laboratoire.getValue().getId();

		if (patient.getValue() != null)
			url = url + "&patient=" + patient.getValue().getId();

		if (trimestre.getValue() != null)
			url = url + "&trimestre=" + trimestre.getValue();

		if (annee.getValue() != null)
			url = url + "&annee=" + annee.getValue();

		if (date_debut.getValue() != null)
			url = url + "&dateDebut="+ getDate_debut();

		if (date_fin.getValue() != null)
			url = url + "&dateFin="+getDate_fin();

		return url;
	}
	/*
	 * Take a type of report in parameter and return the name of the report to be show
	 */
	
	public String getNameRapport(){
		String typeReport = getTypeRapport();
		String nameReport = "";

		if(typeReport.equals("RegTB")){
			nameReport = "atbRegister";
		}
		if(typeReport.equals("RegTBEfants")){
			nameReport = "atbChildRegister";
			
		}
		if(typeReport.equals("RegLabo")){
			nameReport = "atbLabRegister";
			
		}
		if(typeReport.equals("RapTrimDepist")){
			nameReport = "atrimesterDetectReport";
			
		}
		if(typeReport.equals("RapTrimTraitement")){
			nameReport = "atrimesterTreatmentReport";
			
		}
		if(typeReport.equals("RapTrimLabo")){
			nameReport = "atrimesterLabReport";
			
		}
		if(typeReport.equals("CartePatient")){
			nameReport = "apatientCardForm";
			
		}
		if(typeReport.equals("FicheTraitement")){
			nameReport = "atreatmentForm";
			
		}
		if(typeReport.equals("FicheTransRef")){
			nameReport = "atransfertReferenceForm";
			
		}
		if(typeReport.equals("RapDistribMedicament")){
			nameReport = "adrugDistributionReport";
			
		}
		return nameReport;
	}
	
	//Method who add two strings if they are not nul and not empty
	
	public String setParameter(String parameter, String identifiant){

		String url="";
		if(parameter!=null){
			if(parameter.trim()!="")
				url="&"+identifiant+"="+parameter;
		}
		
		return url;
	}
	
	
}
