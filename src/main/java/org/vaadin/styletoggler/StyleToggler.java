package org.vaadin.styletoggler;

import org.vaadin.styletoggler.client.StyleTogglerServerRpc;
import org.vaadin.styletoggler.client.StyleTogglerState;

import com.vaadin.server.AbstractExtension;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

/**
 * Vaadin button extension which toggle CSS style names on the client-side.
 * 
 * @author jonni
 *
 */
public class StyleToggler extends AbstractExtension {
	private static final long serialVersionUID = 1L;
	StyleTogglerServerRpc rpc = new StyleTogglerServerRpc() {
		private static final long serialVersionUID = 1L;

		@Override
		public void removeStyleName() {
			((Component) getState().target).removeStyleName(getState().styleName);
		}

		@Override
		public void addStyleName() {
			((Component) getState().target).addStyleName(getState().styleName);

		}
	};

	/**
	 * Adds a client-side extension to the Button which click toggles the
	 * StyleName on and off in the target Component.
	 * 
	 * @param button
	 * @param target
	 * @param styleName
	 */
	public StyleToggler(Button button, Component target, String styleName) {
		if (target == null) {
			throw new IllegalArgumentException("target component cannot be null");
		}
		if (styleName == null) {
			throw new IllegalArgumentException("style name cannot be null");
		}
		super.extend(button);
		setTarget(target);
		setStyleName(styleName);

		registerRpc(rpc);
	}

	private void setStyleName(String styleName) {
		getState().styleName = styleName;
	}

	private void setTarget(Component target) {
		getState().target = target;
	}

	@Override
	public StyleTogglerState getState() {
		return (StyleTogglerState) super.getState();
	}

}
