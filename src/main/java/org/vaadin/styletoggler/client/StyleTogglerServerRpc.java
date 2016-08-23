package org.vaadin.styletoggler.client;

import com.vaadin.shared.communication.ServerRpc;

public interface StyleTogglerServerRpc extends ServerRpc {
	public void addStyleName();

	public void removeStyleName();
}