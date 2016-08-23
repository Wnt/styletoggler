package org.vaadin.styletoggler.client;

import org.vaadin.styletoggler.StyleToggler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.ComponentConnector;
import com.vaadin.client.ServerConnector;
import com.vaadin.client.extensions.AbstractExtensionConnector;
import com.vaadin.client.ui.button.ButtonConnector;
import com.vaadin.shared.ui.Connect;

@Connect(StyleToggler.class)
public class StyleTogglerConnector extends AbstractExtensionConnector {

	private static final long serialVersionUID = 1L;

	@Override
	protected void extend(ServerConnector target) {
		((ButtonConnector) target).getWidget().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				toggleStyle();
			}
		});
	}

	protected void toggleStyle() {
		ComponentConnector targetConnector = ((ComponentConnector) getState().target);
		Widget targetWidget = targetConnector.getWidget();
		String currentStyles = targetWidget.getStyleName();
		String styleName = getState().styleName;

		StyleTogglerServerRpc rpc =
            getRpcProxy(StyleTogglerServerRpc.class);
		if (currentStyles.contains(styleName)) {
			targetWidget.removeStyleName(styleName);
			rpc.removeStyleName();
		}
		else {
			targetWidget.addStyleName(getState().styleName);
			rpc.addStyleName();
		}
	}

	@Override
	public StyleTogglerState getState() {
		return (StyleTogglerState) super.getState();
	}

}
