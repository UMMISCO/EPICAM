package org.imogene.epicam.client.event.view;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired in order to display a Adresse form in view mode
 * 
 * @author MEDES-IMPS
 */
public class ViewEpicamMapEvent extends GwtEvent<ViewEpicamMapEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	public interface Handler extends EventHandler {
		void viewMap();
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.viewMap();
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
}
