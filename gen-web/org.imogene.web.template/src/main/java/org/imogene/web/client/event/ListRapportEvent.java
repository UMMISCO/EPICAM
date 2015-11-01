package org.imogene.web.client.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired in order to display the list of Rapport form entries
 * @author MEDES-IMPS
 */
public class ListRapportEvent extends GwtEvent<ListRapportEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	private String searchText = null;

	public interface Handler extends EventHandler {
		void listRapport();
		void listRapport(String searchText);
	}

	public ListRapportEvent() {
	}

	public ListRapportEvent(String searchText) {
		this.searchText = searchText;
	}

	@Override
	protected void dispatch(Handler handler) {
		if (searchText == null)
			handler.listRapport();
		else
			handler.listRapport(searchText);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}
}
