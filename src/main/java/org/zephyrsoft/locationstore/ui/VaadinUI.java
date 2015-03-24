package org.zephyrsoft.locationstore.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Title("Location Store")
@Theme("valo")
@SpringUI
public class VaadinUI extends UI {
	
	private static final long serialVersionUID = 2105911238435641317L;
	
	private final SpringViewProvider viewProvider;
	
	@Autowired
	public VaadinUI(SpringViewProvider viewProvider) {
		this.viewProvider = viewProvider;
	}
	
	@Override
	protected void init(final VaadinRequest request) {
		final VerticalLayout root = new VerticalLayout();
		root.setSizeFull();
		root.setMargin(true);
		root.setSpacing(true);
		setContent(root);
		
		Label title = new Label("Location Store");
		title.setStyleName(ValoTheme.LABEL_H1);
		root.addComponent(title);
		
		final Panel viewContainer = new Panel();
		viewContainer.setSizeFull();
		root.addComponent(viewContainer);
		root.setExpandRatio(viewContainer, 1.0f);
		
		// the sub-pages are managed by Spring
		Navigator navigator = new Navigator(this, viewContainer);
		navigator.addProvider(viewProvider);
		// the navigator is automatically held by this UI and available via getNavigator()
	}
	
}
