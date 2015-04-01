package org.zephyrsoft.locationstore.ui.pages;

import org.springframework.security.access.annotation.Secured;
import org.zephyrsoft.locationstore.ui.Pages;
import org.zephyrsoft.locationstore.ui.Roles;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@Secured(Roles.USER)
@SpringView(name = Pages.HOME)
public class HomePage extends VerticalLayout implements View {
	
	private static final long serialVersionUID = 146768140068343010L;
	
	public HomePage() {
		setSpacing(true);
		setMargin(true);
		setSizeFull();
		
		Label nothing = new Label("<div style=\"text-align:center\">nothing here yet</div>", ContentMode.HTML);
		addComponent(nothing);
		setComponentAlignment(nothing, Alignment.MIDDLE_CENTER);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO
	}
}
