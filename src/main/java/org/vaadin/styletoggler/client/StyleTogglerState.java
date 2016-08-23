package org.vaadin.styletoggler.client;

import com.vaadin.shared.Connector;
import com.vaadin.shared.communication.SharedState;

public class StyleTogglerState extends SharedState {
	
	public Connector target = null;
	public String styleName = null;

}
