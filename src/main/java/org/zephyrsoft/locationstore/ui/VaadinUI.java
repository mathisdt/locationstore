package org.zephyrsoft.locationstore.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zephyrsoft.locationstore.model.User;
import org.zephyrsoft.locationstore.ui.pages.AdminPage;
import org.zephyrsoft.locationstore.ui.pages.HomePage;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Title("Location Store")
@Theme("valo")
@SpringUI
// SpringUI path is relative to Vaadin's base mapping configured in vaadin.servlet.urlMapping
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
		
		HorizontalLayout titleBar = new HorizontalLayout();
		titleBar.setWidth(100, Unit.PERCENTAGE);
		Label title = new Label("Location Store");
		title.setStyleName(ValoTheme.LABEL_H1);
		titleBar.addComponent(title);
		titleBar.setComponentAlignment(title, Alignment.MIDDLE_LEFT);
		
		VerticalLayout titleBarRight = new VerticalLayout();
		titleBarRight.setSpacing(true);
		titleBar.addComponent(titleBarRight);
		titleBar.setComponentAlignment(titleBarRight, Alignment.TOP_RIGHT);
		HorizontalLayout titleBarRightTop = new HorizontalLayout();
		titleBarRightTop.setSpacing(true);
		titleBarRight.addComponent(titleBarRightTop);
		titleBarRight.setComponentAlignment(titleBarRightTop, Alignment.TOP_RIGHT);
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Label userInfo = new Label((user.isAdmin() ? "Administrator " : "User ") + user.getFullname()
			+ " (" + user.getUsername() + ")");
		titleBarRightTop.addComponent(userInfo);
		titleBarRightTop.setComponentAlignment(userInfo, Alignment.MIDDLE_RIGHT);
		Button logout = new Button("log out");
		logout.setStyleName(ValoTheme.BUTTON_SMALL);
		logout.addClickListener(event -> {
			getPage().setLocation("/logout");
		});
		titleBarRightTop.addComponent(logout);
		titleBarRightTop.setComponentAlignment(logout, Alignment.MIDDLE_RIGHT);
		
		HorizontalLayout titleBarRightBottom = new HorizontalLayout();
		titleBarRightBottom.setSpacing(true);
		titleBarRight.addComponent(titleBarRightBottom);
		titleBarRight.setComponentAlignment(titleBarRightBottom, Alignment.BOTTOM_RIGHT);
		titleBarRightBottom.addComponent(new Label("Navigation:"));
		// home
		Button navHomeButton = new Button("Home");
		navHomeButton.setStyleName(ValoTheme.BUTTON_SMALL);
		navHomeButton.addClickListener(event -> getNavigator().navigateTo(Pages.HOME));
		navHomeButton.setEnabled(ViewSecurity.isAccessGranted(HomePage.class));
		titleBarRightBottom.addComponent(navHomeButton);
		// administration
		Button navAdminButton = new Button("Administration");
		navAdminButton.setStyleName(ValoTheme.BUTTON_SMALL);
		navAdminButton.addClickListener(event -> getNavigator().navigateTo(Pages.ADMINISTRATION));
		navAdminButton.setEnabled(ViewSecurity.isAccessGranted(AdminPage.class));
		titleBarRightBottom.addComponent(navAdminButton);
		
		root.addComponent(titleBar);
		
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
