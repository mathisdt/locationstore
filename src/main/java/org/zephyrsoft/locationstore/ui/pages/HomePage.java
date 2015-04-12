package org.zephyrsoft.locationstore.ui.pages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.vaadin.addon.vol3.OLMap;
import org.vaadin.addon.vol3.client.source.OLMapQuestLayerName;
import org.vaadin.addon.vol3.layer.OLTileLayer;
import org.vaadin.addon.vol3.source.OLMapQuestSource;
import org.zephyrsoft.locationstore.dao.LocationMapper;
import org.zephyrsoft.locationstore.model.Location;
import org.zephyrsoft.locationstore.model.Location.LocationProperties;
import org.zephyrsoft.locationstore.model.User;
import org.zephyrsoft.locationstore.ui.Pages;
import org.zephyrsoft.locationstore.ui.Roles;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;

@Secured(Roles.USER)
@SpringView(name = Pages.HOME)
public class HomePage extends HorizontalLayout implements View {
	
	private static final long serialVersionUID = 146768140068343010L;
	
	private LocationMapper locationMapper;
	
	private BeanItemContainer<Location> dataSource;
	private GeneratedPropertyContainer dataSourceWrapper;
	
	@Autowired
	public HomePage(LocationMapper locationMapper) {
		this.locationMapper = locationMapper;
		
		setSpacing(true);
		setMargin(true);
		setSizeFull();
		
		dataSource = new BeanItemContainer<>(Location.class);
		dataSourceWrapper = new GeneratedPropertyContainer(dataSource);
		
		Grid grid = new Grid(dataSourceWrapper);
		grid.setHeight(100, Unit.PERCENTAGE);
		grid.setWidth(250, Unit.PIXELS);
		addComponent(grid);
		setComponentAlignment(grid, Alignment.MIDDLE_LEFT);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		grid.getColumn(LocationProperties.INSTANT).setConverter(new Converter<String, LocalDateTime>() {
			private static final long serialVersionUID = -7843022072932913124L;
			
			@Override
			public LocalDateTime convertToModel(String value, Class<? extends LocalDateTime> targetType, Locale locale)
				throws ConversionException {
				throw new UnsupportedOperationException("not implemented");
			}
			
			@Override
			public String convertToPresentation(LocalDateTime value, Class<? extends String> targetType, Locale locale)
				throws ConversionException {
				return formatter.format(value);
			}
			
			@Override
			public Class<LocalDateTime> getModelType() {
				return LocalDateTime.class;
			}
			
			@Override
			public Class<String> getPresentationType() {
				return String.class;
			}
		});
		
		OLMap map = new OLMap();
		map.setSizeFull();
		OLMapQuestSource mapSource = new OLMapQuestSource(OLMapQuestLayerName.OSM);
		OLTileLayer layer = new OLTileLayer(mapSource);
		map.addLayer(layer);
		addComponent(map);
		setComponentAlignment(map, Alignment.MIDDLE_RIGHT);
		setExpandRatio(map, 1);
		
		dataSourceWrapper.removeContainerProperty(LocationProperties.ID);
		dataSourceWrapper.removeContainerProperty(LocationProperties.LATITUDE);
		dataSourceWrapper.removeContainerProperty(LocationProperties.LONGITUDE);
		dataSourceWrapper.removeContainerProperty(LocationProperties.INSTANT_JSON);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		refresh();
	}
	
	private void refresh() {
		dataSource.removeAllItems();
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Location> locations = locationMapper.read(user.getUsername());
		// we want the current location first
		Collections.reverse(locations);
		dataSource.addAll(locations);
	}
}
