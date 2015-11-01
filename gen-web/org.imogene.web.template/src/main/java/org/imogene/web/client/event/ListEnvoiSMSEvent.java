package org.imogene.web.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired in order to display the list of EnvoiSMS form entries
 * @author MEDES-IMPS
 */
public class ListEnvoiSMSEvent extends GwtEvent<ListEnvoiSMSEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	private String searchText = null;

	public interface Handler extends EventHandler {
		void listEnvoiSMS();
		void listEnvoiSMS(String searchText);
	}

	public ListEnvoiSMSEvent() {
		
	}

	public ListEnvoiSMSEvent(String searchText) {
		this.searchText = searchText;
	}

	@Override
	protected void dispatch(Handler handler) {
		if (searchText == null)
			handler.listEnvoiSMS();
		else
			handler.listEnvoiSMS(searchText);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
}
