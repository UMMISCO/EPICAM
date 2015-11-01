package org.imogene.epicam.client.ui.maps;

import java.util.List;

import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.Map;
import org.gwtopenmaps.openlayers.client.MapOptions;
import org.gwtopenmaps.openlayers.client.MapWidget;
import org.gwtopenmaps.openlayers.client.Projection;
import org.gwtopenmaps.openlayers.client.Style;
import org.gwtopenmaps.openlayers.client.control.LayerSwitcher;
import org.gwtopenmaps.openlayers.client.control.Measure;
import org.gwtopenmaps.openlayers.client.control.MeasureOptions;
import org.gwtopenmaps.openlayers.client.control.MousePosition;
import org.gwtopenmaps.openlayers.client.control.MousePositionOptions;
import org.gwtopenmaps.openlayers.client.control.MousePositionOutput;
import org.gwtopenmaps.openlayers.client.control.ScaleLine;
import org.gwtopenmaps.openlayers.client.event.MeasureEvent;
import org.gwtopenmaps.openlayers.client.event.MeasureListener;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.format.KML;
import org.gwtopenmaps.openlayers.client.geometry.Point;
import org.gwtopenmaps.openlayers.client.handler.PathHandler;
import org.gwtopenmaps.openlayers.client.layer.EmptyLayer;
import org.gwtopenmaps.openlayers.client.layer.EmptyLayer.Options;
import org.gwtopenmaps.openlayers.client.layer.JsonLayerCreator;
import org.gwtopenmaps.openlayers.client.layer.Vector;
import org.gwtopenmaps.openlayers.client.layer.VectorOptions;
import org.gwtopenmaps.openlayers.client.protocol.HTTPProtocol;
import org.gwtopenmaps.openlayers.client.protocol.HTTPProtocolOptions;
import org.gwtopenmaps.openlayers.client.protocol.Protocol;
import org.gwtopenmaps.openlayers.client.strategy.FixedStrategy;
import org.gwtopenmaps.openlayers.client.strategy.Strategy;
import org.imogene.epicam.shared.EpicamRequestFactory;
import org.imogene.epicam.shared.proxy.CentreDiagTraitProxy;
import org.imogene.epicam.shared.proxy.LieuDitProxy;
import org.imogene.epicam.shared.request.CentreDiagTraitRequest;
import org.imogene.epicam.shared.request.LieuDitRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;

public class EpicamMap extends PopupPanel {
	private static final Projection DEFAULT_PROJECTION = new Projection("EPSG:4326");

	private final EpicamRequestFactory requestFactory;

	private MapWidget mapWidget;

	public EpicamMap(EpicamRequestFactory requestFactory) {

		this.requestFactory = requestFactory;

		MapOptions defaultMapOptions = new MapOptions();
		defaultMapOptions.setNumZoomLevels(16);

		mapWidget = new MapWidget("1250px", "700px", defaultMapOptions);

		Options options = new Options();

		EmptyLayer emptyLayer = new EmptyLayer("Base Layer", options);
		emptyLayer.setIsBaseLayer(true);

		Vector cmrRegions = this.getGeoJsonVectorLayer("Régions", "maps/regions.geojson");
//		Vector cmrDepartements = this.getGeoJsonVectorLayer("Départements", "maps/departements.geojson");
//		Vector cmrArrondissements = this.getGeoJsonVectorLayer("Arrondissements", "maps/arrondissements.json");
		Vector cmrCS = this.getGeoJsonVectorLayer("CS", "maps/healthCenters.geojson");

		// Show mouse position
		this.showMousePosition(mapWidget.getMap());

		// Calculate distance between 2 points
		this.distanceMesure(mapWidget.getMap());

		mapWidget.getMap().addLayer(emptyLayer);
		mapWidget.getMap().addLayer(cmrRegions);
//		mapWidget.getMap().addLayer(cmrDepartements);
//		mapWidget.getMap().addLayer(cmrArrondissements);
		mapWidget.getMap().addLayer(cmrCS);

		mapWidget.getMap().addControl(new LayerSwitcher());

		mapWidget.getMap().addControl(new ScaleLine());

		LonLat lonLat = new LonLat(13, 7);
		lonLat.transform(DEFAULT_PROJECTION.getProjectionCode(), mapWidget.getMap().getProjection());
		mapWidget.getMap().setCenter(lonLat, 6);

		final Vector cdtLayer = new Vector("CDT Layer");
		cdtLayer.setIsBaseLayer(false);
		CentreDiagTraitRequest cdtRequest = requestFactory.centreDiagTraitRequest();
		Request<List<CentreDiagTraitProxy>> listCDT = cdtRequest.listCentreDiagTrait(null, false);
		listCDT.with("coordonnees");
		listCDT.fire(new Receiver<List<CentreDiagTraitProxy>>() {
			@Override
			public void onSuccess(List<CentreDiagTraitProxy> response) {
				for (CentreDiagTraitProxy proxy : response) {
					if (proxy.getCoordonnees() != null) {
						Point point = new Point(proxy.getCoordonnees().getLongitude(), proxy.getCoordonnees()
								.getLatitude());
						Style style = new Style();
						style.setFontSize("8");
						style.setLabel(proxy.getNom());
						style.setFontWeight("bold");
						style.setFontFamily("arial");
						style.setFill(true);
						style.setFillColor("#00B69E");
						VectorFeature cdtFeature = new VectorFeature(point);
						cdtFeature.setStyle(style);
						cdtLayer.addFeature(cdtFeature);
					}
				}
			}
		});
		
		mapWidget.getMap().addLayer(cdtLayer);

		
		
		final Vector lieuDitLayer = new Vector("Lieux dit");
		lieuDitLayer.setIsBaseLayer(false);
		LieuDitRequest lieuDitRequest = requestFactory.lieuDitRequest();
		Request<List<LieuDitProxy>> listLieuxDit = lieuDitRequest.listLieuDit(null, false);
		listLieuxDit.with("coordonnees");
		listLieuxDit.fire(new Receiver<List<LieuDitProxy>>() {
			@Override
			public void onSuccess(List<LieuDitProxy> response) {

				for (LieuDitProxy proxy : response) {
					System.out.println("Méthode lieu dit");
					Point point = new Point(proxy.getCoordonnees().getLongitude(), proxy.getCoordonnees().getLatitude());

					Style style = new Style();
					style.setFontSize("8");
					style.setLabel(proxy.getNom());
					style.setFontWeight("italic");
					style.setFontFamily("arial");
					style.setFill(true);
					style.setFillColor("#7500DC");

					VectorFeature lieuDitFeature = new VectorFeature(point);
					lieuDitFeature.setStyle(style);

					lieuDitLayer.addFeature(lieuDitFeature);
				}
			}
		});

		int left = (Window.getClientWidth() - 1250) / 4;
		int top = (Window.getClientHeight() - 700) / 4;
		this.setPopupPosition(left, top);

		mapWidget.getMap().addLayer(lieuDitLayer);

		this.add(mapWidget);

	}

	/**
	 * Takes a layer name and the relative layer path of a geoJson file in the project and return a vector layer
	 * 
	 * @param layerName
	 * @param filePath
	 * @return
	 */
	public Vector getGeoJsonVectorLayer(String layerName, String filePath) {
		Vector geoJsonVector = new JsonLayerCreator().createLayerFromJson(layerName, GWT.getModuleBaseURL() + filePath);
		geoJsonVector.setIsVisible(false);
		return geoJsonVector;
	}

	/**
	 * Takes a map, add control to capture user click and mesures distance between 2 point
	 * 
	 * @param map
	 */

	public void distanceMesure(final Map map) {
		// Add a mesure control to the map.
		MeasureOptions measureOptions = new MeasureOptions();
		measureOptions.setPersist(true);
		measureOptions.setGeodesic(true);
		// Mesures now
		Measure measure = new Measure(new PathHandler(), measureOptions);
		map.addControl(measure);
		// Add a listener when tracing
		measure.addMeasureListener(new MeasureListener() {
			@Override
			public void onMeasure(MeasureEvent eventObject) {
				Window.alert("Measures distance is " + eventObject.getMeasure() + " " + eventObject.getUnits());
			}
		});
		//voir comment activer lorsque l'utilisateur clique sur un bouton particulier
		measure.activate();
	}

	public void showMousePosition(final Map map) {
		MousePositionOutput mousePositionOutput = new MousePositionOutput() {
			@Override
			public String format(LonLat lonLat, Map map) {
				String out = "";
				out += "<b>Longitude : <b> ";
				out += lonLat.lon() + "<br />";
				out += "<b>Latitude : <b>";
				out += lonLat.lat();
				return out;
			}
		};
		MousePositionOptions mousePositionOptions = new MousePositionOptions();
		// Rename to setformatoutput
		mousePositionOptions.setFormatOutput(mousePositionOutput);
		map.addControl(new MousePosition(mousePositionOptions));
	}

	/**
	 * Takes a layer name and the relative layer path of a KML file in the project and return a vector layer
	 * 
	 * @param layerName
	 * @param filePath
	 * @return
	 */
	public Vector getKMLVectorLayer(String layerName, String filePath) {
		VectorOptions kmlOptions = new VectorOptions();
		kmlOptions.setStrategies(new Strategy[] { new FixedStrategy() });
		HTTPProtocolOptions protocolOptions = new HTTPProtocolOptions();
		protocolOptions.setUrl(GWT.getModuleBaseURL() + filePath);
		KML kml = new KML();
		kml.setExtractStyles(true);
		kml.setExtractAttributes(true);
		kml.setMaxDepth(2);
		protocolOptions.setFormat(kml);
		Protocol protocol = new HTTPProtocol(protocolOptions);
		kmlOptions.setProtocol(protocol);
		Vector kmlLayer = new Vector(layerName, kmlOptions);
		return kmlLayer;
	}

}
