package org.zephyrsoft.locationstore.ui;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Title("Location Store")
@Theme("valo")
@SpringUI(path = "/login")
// SpringUI path is relative to Vaadin's base mapping configured in vaadin.servlet.urlMapping
public class LoginUI extends UI implements ClickListener {
	
	private static final long serialVersionUID = -8667709390052949671L;
	
	private final AuthenticationProvider authenticationProvider;
	
	private VerticalLayout layout;
	private TextField username;
	private PasswordField password;
	private Button btnLogin;
	
	@Autowired
	public LoginUI(AuthenticationProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
		
		layout = new VerticalLayout();
		layout.setSizeFull();
		
		VerticalLayout loginLayout = new VerticalLayout();
		loginLayout.setSizeUndefined();
		loginLayout.setSpacing(true);
		Label login = new Label("Location Store");
		login.setStyleName(ValoTheme.LABEL_H2);
		loginLayout.addComponent(login);
		
		username = new TextField();
		username.setDescription("username");
		username.setInputPrompt("username");
		password = new PasswordField();
		password.setDescription("password");
		password.setInputPrompt("password");
		btnLogin = new Button("Login");
		btnLogin.addClickListener(this);
		
		loginLayout.addComponent(username);
		loginLayout.setComponentAlignment(username, Alignment.TOP_RIGHT);
		loginLayout.addComponent(password);
		loginLayout.setComponentAlignment(password, Alignment.MIDDLE_RIGHT);
		loginLayout.addComponent(btnLogin);
		loginLayout.setComponentAlignment(btnLogin, Alignment.BOTTOM_RIGHT);
		
		layout.addComponent(loginLayout);
		layout.setComponentAlignment(loginLayout, Alignment.MIDDLE_CENTER);
		
		addShortcutListener(new ShortcutListener("listen for enter key", ShortcutAction.KeyCode.ENTER, null) {
			private static final long serialVersionUID = -3058605260341096586L;
			
			@Override
			public void handleAction(Object sender, Object target) {
				if (target == username && StringUtils.isNotBlank(username.getValue())) {
					password.focus();
				} else if (target == password && StringUtils.isNotBlank(password.getValue())) {
					btnLogin.click();
				}
			}
		});
	}
	
	@Override
	protected void init(VaadinRequest request) {
		setContent(layout);
		username.focus();
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		try {
			UsernamePasswordAuthenticationToken token =
				new UsernamePasswordAuthenticationToken(username.getValue(), DigestUtils.sha256Hex(password.getValue()));
			Authentication authentication = authenticationProvider.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			getPage().setLocation("/");
		} catch (AuthenticationException e) {
			Notification.show("Login failed.", e.getMessage(), Type.ERROR_MESSAGE);
			SecurityContextHolder.getContext().setAuthentication(null);
		}
	}
	
}
