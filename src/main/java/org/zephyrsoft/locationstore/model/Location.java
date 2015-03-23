package org.zephyrsoft.locationstore.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Location {
	
	private Long id;
	private LocalDateTime instant;
	private BigDecimal latitude;
	private BigDecimal longitude;
	
	public Location() {
		// for MyBatis
	}
	
	public Location(final BigDecimal latitude, final BigDecimal longitude) {
		this.instant = LocalDateTime.now();
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(final Long id) {
		this.id = id;
	}
	
	@JsonGetter("instant")
	public String getInstantJson() {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.GERMANY).format(instant);
	}
	
	@JsonSetter("instant")
	public void setInstantJson(String instant) {
		this.instant = LocalDateTime.from(
			DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.GERMANY).parse(instant));
	}
	
	public LocalDateTime getInstant() {
		return instant;
	}
	
	public void setInstant(final LocalDateTime instant) {
		this.instant = instant;
	}
	
	public BigDecimal getLatitude() {
		return latitude;
	}
	
	public void setLatitude(final BigDecimal latitude) {
		this.latitude = latitude;
	}
	
	public BigDecimal getLongitude() {
		return longitude;
	}
	
	public void setLongitude(final BigDecimal longitude) {
		this.longitude = longitude;
	}
	
}
