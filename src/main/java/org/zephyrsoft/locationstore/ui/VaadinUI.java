package org.zephyrsoft.locationstore.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Title("Location Store")
@Theme("valo")
@SpringUI
public class VaadinUI extends UI {
	
	private static final long serialVersionUID = 2105911238435641317L;
	
	@Override
	protected void init(VaadinRequest request) {
		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setMargin(true);
		layout.setSizeFull();
		setContent(layout);
		
		layout.addComponent(new Button("TODO"));
	}
	
}
