package org.imogene.epicam.client.event.create;

import org.imogene.web.client.event.ListRapportEvent;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired in order to display a Rapport form in creation mode
 * 
 * @author MEDES-IMPS
 */
public class CreateRapportEvent extends GwtEvent<CreateRapportEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	private final GwtEvent<?> closeEvent;

	public interface Handler extends EventHandler {
		void createNewRapport(GwtEvent<?> closeEvent);
	}

	public CreateRapportEvent() {
		this(new ListRapportEvent());
	}

	public CreateRapportEvent(GwtEvent<?> closeEvent) {
		this.closeEvent = closeEvent;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.createNewRapport(closeEvent);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

}
