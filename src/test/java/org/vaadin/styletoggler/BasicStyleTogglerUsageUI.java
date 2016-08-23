package org.vaadin.styletoggler;

import org.vaadin.addonhelpers.AbstractTest;

import com.vaadin.annotations.Theme;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@Theme("test-theme")
public class BasicStyleTogglerUsageUI extends AbstractTest {

	private VerticalLayout layout;

	@Override
	public Component getTestComponent() {
		Button enhancedButton = new Button("This button toggles the style already on the client-side");
		Button normalButton = new Button("This button toggles the style on the server-side",
				new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						String currentStyles = layout.getStyleName();
						if (currentStyles.contains("red-bg-color")) {
							layout.removeStyleName("red-bg-color");
						} else {
							layout.addStyleName("red-bg-color");
						}
					}
				});
		layout = new VerticalLayout(enhancedButton, normalButton, new Label(
				"The resposiveness difference is quite noticable on slow connections or "
				+ "if you enable network throtling in your browser's developer tools"));
		layout.setSpacing(true);
		layout.setMargin(true);
		new StyleToggler(enhancedButton, layout, "red-bg-color");

		return layout;
	}

}
