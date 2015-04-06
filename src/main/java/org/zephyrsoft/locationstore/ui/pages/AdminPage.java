package org.zephyrsoft.locationstore.ui.pages;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.vaadin.dialogs.ConfirmDialog;
import org.zephyrsoft.locationstore.dao.TokenMapper;
import org.zephyrsoft.locationstore.dao.TransactionalDao;
import org.zephyrsoft.locationstore.dao.UserMapper;
import org.zephyrsoft.locationstore.model.Token;
import org.zephyrsoft.locationstore.model.Token.TokenProperties;
import org.zephyrsoft.locationstore.model.User;
import org.zephyrsoft.locationstore.model.User.UserProperties;
import org.zephyrsoft.locationstore.ui.Pages;
import org.zephyrsoft.locationstore.ui.Roles;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.MethodProperty;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.HtmlRenderer;

@Secured(Roles.ADMIN)
@SpringView(name = Pages.ADMINISTRATION)
public class AdminPage extends VerticalLayout implements View {
	
	private static final long serialVersionUID = 146768140068343010L;
	
	private static final String TOKENCOUNT = "tokencount";
	
	private BeanItemContainer<User> dataSource;
	private GeneratedPropertyContainer dataSourceWrapper;
	private UserMapper userMapper;
	private TokenMapper tokenMapper;
	private TransactionalDao transactionalDao;
	
	@Autowired
	public AdminPage(UserMapper userMapper, TokenMapper tokenMapper, TransactionalDao transactionalDao) {
		this.userMapper = userMapper;
		this.tokenMapper = tokenMapper;
		this.transactionalDao = transactionalDao;
		dataSource = new BeanItemContainer<>(User.class);
		dataSourceWrapper = new GeneratedPropertyContainer(dataSource);
		
		setSpacing(true);
		setMargin(true);
		setSizeFull();
		
		Grid grid = new Grid(dataSourceWrapper);
		grid.setWidth(100, Unit.PERCENTAGE);
		grid.setHeight(100, Unit.PERCENTAGE);
		addComponent(grid);
		setExpandRatio(grid, 1);
		HorizontalLayout buttonBar = new HorizontalLayout();
		buttonBar.setSpacing(true);
		addComponent(buttonBar);
		setComponentAlignment(buttonBar, Alignment.BOTTOM_RIGHT);
		Button add = new Button("Add");
		add.addClickListener(event -> {
			showPopup(null);
		});
		buttonBar.addComponent(add);
		buttonBar.setComponentAlignment(add, Alignment.MIDDLE_RIGHT);
		Button edit = new Button("Edit");
		edit.addClickListener(event -> {
			showPopup((User) grid.getSelectedRow());
		});
		edit.setEnabled(false);
		buttonBar.addComponent(edit);
		buttonBar.setComponentAlignment(edit, Alignment.MIDDLE_RIGHT);
		Button delete = new Button("Delete");
		delete.addClickListener(event -> {
			User userToDelete = (User) grid.getSelectedRow();
			ConfirmDialog.show(getUI(), "Question", "Really delete user " + userToDelete.getFullname() + " ("
				+ userToDelete.getUsername() + ") including all saved locations and tokens?", "Yes, delete",
				"No, cancel", dialog -> {
					if (dialog.isConfirmed()) {
						transactionalDao.deleteUserCompletely(userToDelete);
						refresh();
					}
				});
		});
		delete.setEnabled(false);
		buttonBar.addComponent(delete);
		buttonBar.setComponentAlignment(delete, Alignment.MIDDLE_RIGHT);
		
		grid.setSelectionMode(SelectionMode.SINGLE);
		grid.addSelectionListener(event -> {
			edit.setEnabled(event.getSelected().size() > 0);
			delete.setEnabled(event.getSelected().size() > 0);
		});
		
		dataSourceWrapper.addGeneratedProperty(TOKENCOUNT, new PropertyValueGenerator<Long>() {
			private static final long serialVersionUID = -7903718427056116642L;
			
			@Override
			public Long getValue(Item item, Object itemId, Object propertyId) {
				String username = ((User) itemId).getUsername();
				return tokenMapper.count(username);
			}
			
			@Override
			public Class<Long> getType() {
				return Long.class;
			}
		});
		dataSourceWrapper.removeContainerProperty(UserProperties.ACCOUNT_NON_EXPIRED);
		dataSourceWrapper.removeContainerProperty(UserProperties.ACCOUNT_NON_LOCKED);
		dataSourceWrapper.removeContainerProperty(UserProperties.AUTHORITIES);
		dataSourceWrapper.removeContainerProperty(UserProperties.CREDENTIALS_NON_EXPIRED);
		dataSourceWrapper.removeContainerProperty(UserProperties.ENABLED);
		dataSourceWrapper.removeContainerProperty(UserProperties.ID);
		dataSourceWrapper.removeContainerProperty(UserProperties.PASSWORD);
		dataSourceWrapper.removeContainerProperty(UserProperties.ROLES);
		grid.setColumnOrder(UserProperties.FULLNAME, UserProperties.USERNAME, TOKENCOUNT, UserProperties.ADMIN);
		
		grid.getColumn(UserProperties.FULLNAME).setHeaderCaption("Full Name");
		grid.getColumn(UserProperties.USERNAME).setHeaderCaption("User Name");
		grid.getColumn(TOKENCOUNT).setHeaderCaption("Defined Tokens");
		grid.getColumn(UserProperties.ADMIN).setHeaderCaption("Administrator?");
		grid.getColumn(UserProperties.ADMIN).setRenderer(new HtmlRenderer(), new Converter<String, Boolean>() {
			private static final long serialVersionUID = -4025419889363944262L;
			
			@Override
			public Boolean convertToModel(String value, Class<? extends Boolean> targetType, Locale locale)
				throws com.vaadin.data.util.converter.Converter.ConversionException {
				throw new UnsupportedOperationException("not implemented");
			}
			
			@Override
			public String convertToPresentation(Boolean value, Class<? extends String> targetType, Locale locale)
				throws com.vaadin.data.util.converter.Converter.ConversionException {
				return "<input type=\"checkbox\" disabled=\"disabled\" "
					+ (Boolean.TRUE.equals(value) ? "checked=\"checked\"" : "") + "/>";
			}
			
			@Override
			public Class<Boolean> getModelType() {
				return Boolean.class;
			}
			
			@Override
			public Class<String> getPresentationType() {
				return String.class;
			}
		});
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		refresh();
	}
	
	private void refresh() {
		dataSource.removeAllItems();
		dataSource.addAll(userMapper.readAll());
	}
	
	private void showPopup(User userArg) {
		String title = "Edit User";
		Set<Token> tokens = new HashSet<>();
		
		final User user;
		if (userArg == null) {
			user = new User();
			title = "Add User";
		} else {
			user = userArg;
			tokens.addAll(tokenMapper.read(user.getUsername()));
		}
		
		// TODO externalize window & content definition?
		Window popup = new Window(title);
		popup.setModal(true);
		popup.setWidth(350, Unit.PIXELS);
		
		VerticalLayout popupContent = new VerticalLayout();
		popupContent.setSpacing(true);
		popupContent.setMargin(true);
		popup.setContent(popupContent);
		
		final Property<String> fullnameProperty = new MethodProperty<>(user, UserProperties.FULLNAME);
		TextField fullname = new TextField(fullnameProperty);
		fullname.setImmediate(true);
		fullname.setNullRepresentation("");
		fullname.setDescription("Full Name");
		fullname.setInputPrompt("Full Name");
		popupContent.addComponent(fullname);
		
		final Property<String> usernameProperty = new MethodProperty<>(user, UserProperties.USERNAME);
		TextField username = new TextField(usernameProperty);
		username.setImmediate(true);
		username.setNullRepresentation("");
		username.setDescription("User Name");
		username.setInputPrompt("User Name");
		popupContent.addComponent(username);
		
		final Property<String> passwordProperty = new MethodProperty<>(user, UserProperties.PASSWORD);
		PasswordField password = new PasswordField(passwordProperty);
		password.setImmediate(true);
		password.setNullRepresentation("");
		password.setDescription("Password");
		password.setInputPrompt("Password");
		popupContent.addComponent(password);
		
		final Property<Boolean> adminProperty = new MethodProperty<>(user, UserProperties.ADMIN);
		CheckBox admin = new CheckBox("Administrator", adminProperty);
		admin.setImmediate(true);
		admin.setDescription("Administrator");
		popupContent.addComponent(admin);
		
		// TODO token list with add/remove buttons
		BeanItemContainer<Token> tokenDataSource = new BeanItemContainer<>(Token.class);
		tokenDataSource.addAll(tokens);
		GeneratedPropertyContainer tokenDataSourceWrapper = new GeneratedPropertyContainer(tokenDataSource);
		tokenDataSourceWrapper.removeContainerProperty(TokenProperties.ID);
		Grid tokenGrid = new Grid(tokenDataSourceWrapper);
		popupContent.addComponent(tokenGrid);
		tokenGrid.setWidth(100, Unit.PERCENTAGE);
		tokenGrid.setHeight(200, Unit.PIXELS);
		HorizontalLayout buttonBar = new HorizontalLayout();
		popupContent.addComponent(buttonBar);
		popupContent.setComponentAlignment(buttonBar, Alignment.MIDDLE_RIGHT);
		buttonBar.setSpacing(true);
		Button add = new Button("Add");
		add.addClickListener(event -> {
			// TODO
		});
		buttonBar.addComponent(add);
		buttonBar.setComponentAlignment(add, Alignment.MIDDLE_RIGHT);
		Button delete = new Button("Delete");
		delete.addClickListener(event -> {
			Token tokenToDelete = (Token) tokenGrid.getSelectedRow();
			ConfirmDialog.show(getUI(), "Question", "Really delete token \"" + tokenToDelete.getToken() + "\"?",
				"Yes, delete", "No, cancel", dialog -> {
					if (dialog.isConfirmed()) {
						tokens.remove(tokenToDelete);
						tokenDataSource.removeItem(tokenToDelete);
					}
				});
		});
		delete.setEnabled(false);
		buttonBar.addComponent(delete);
		buttonBar.setComponentAlignment(delete, Alignment.MIDDLE_RIGHT);
		
		tokenGrid.setSelectionMode(SelectionMode.SINGLE);
		tokenGrid.addSelectionListener(event -> {
			delete.setEnabled(event.getSelected().size() > 0);
		});
		
		Button save = new Button("Save");
		save.addClickListener(event -> {
			transactionalDao.saveUserWithTokens(user, tokens);
			getUI().removeWindow(popup);
			refresh();
		});
		popupContent.addComponent(save);
		
		popup.center();
		getUI().addWindow(popup);
	}
}
