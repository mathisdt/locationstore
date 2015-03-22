package org.zephyrsoft.locationstore.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Location {
	
	private Long id;
	private LocalDateTime instant;
	private BigDecimal latitude;
	private BigDecimal longitude;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public LocalDateTime getInstant() {
		return instant;
	}
	
	public void setInstant(LocalDateTime instant) {
		this.instant = instant;
	}
	
	public BigDecimal getLatitude() {
		return latitude;
	}
	
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	
	public BigDecimal getLongitude() {
		return longitude;
	}
	
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	
}
