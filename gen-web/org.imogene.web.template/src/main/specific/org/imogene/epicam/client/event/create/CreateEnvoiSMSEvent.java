package org.imogene.epicam.client.event.create;

import org.imogene.web.client.event.ListEnvoiSMSEvent;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event that is fired in order to display a EnvoiSMS form in creation mode
 * 
 * @author MEDES-IMPS
 */
public class CreateEnvoiSMSEvent extends GwtEvent<CreateEnvoiSMSEvent.Handler> {

	public static final Type<Handler> TYPE = new Type<Handler>();

	private final GwtEvent<?> closeEvent;

	public interface Handler extends EventHandler {
		void createNewEnvoiSMS(GwtEvent<?> closeEvent);
	}

	public CreateEnvoiSMSEvent() {
		this(new ListEnvoiSMSEvent());
	}

	public CreateEnvoiSMSEvent(GwtEvent<?> closeEvent) {
		this.closeEvent = closeEvent;
	}

	@Override
	protected void dispatch(Handler handler) {
		handler.createNewEnvoiSMS(closeEvent);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<Handler> getAssociatedType() {
		return TYPE;
	}

}
