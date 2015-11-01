package org.imogene.epicam.client.ui.panel;

import java.util.ArrayList;
import java.util.List;

import org.imogene.web.client.i18n.BaseNLS;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Panel for the Welcome page to get to the sms actions
 * 
 * @author Medes-IMPS
 */
public class SmsPanel extends Composite {

	interface Binder extends UiBinder<Widget, SmsPanel> {
	}

	private static final Binder BINDER = GWT.create(Binder.class);

	private List<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

	@UiField(provided = true)
	Image icon;
	@UiField
	VerticalPanel centerPanel;
	@UiField(provided = true)
	Label title;
	@UiField(provided = true)
	PushButton create;
	@UiField(provided = true)
	PushButton list;

	public SmsPanel() {

		this(BaseNLS.constants().envoiSMS_name());
	}

	public SmsPanel(String title, String url) {

		icon = new Image();
		if (url == null)
			icon.setVisible(false);
		else
			icon.setUrl(url);

		this.title = new Label(title);
		create = new PushButton(BaseNLS.constants().button_create());
		list = new PushButton(BaseNLS.constants().button_list());

		initWidget(BINDER.createAndBindUi(this));
	}

	public SmsPanel(String title) {
		this(title, null);
	}

	/**
	 * Adds a handler to launch the event to create an sms instance
	 * 
	 * @param handler the click handler. If null the button becomes invisible
	 */
	public void setCreateClickHandler(ClickHandler handler) {
		if (handler != null)
			registrations.add(create.addClickHandler(handler));
		else
			create.setVisible(false);
	}

	/**
	 * Adds a handler to launch the event to list the sms instances
	 * 
	 * @param handler the click handler. If null the button becomes invisible
	 */
	public void setListClickHandler(ClickHandler handler) {
		if (handler != null)
			registrations.add(list.addClickHandler(handler));
		else
			list.setVisible(false);
	}

	@Override
	protected void onUnload() {
		for (HandlerRegistration r : registrations)
			r.removeHandler();
		registrations.clear();
		super.onUnload();
	}

	public void setCreateButtonText(String text, String width) {
		if (create != null) {
			create.setText(text);
			create.getElement().getStyle().setProperty("width", width);
		}
	}

	public void addItem(Widget widget) {
		centerPanel.add(widget);
	}
}