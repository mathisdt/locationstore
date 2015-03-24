package org.zephyrsoft.locationstore.ui;

public enum Pages {
	
	HOME(""),
	ADMINISTRATION("admin");
	
	private final String navigationTarget;
	
	private Pages(String navigationTarget) {
		this.navigationTarget = navigationTarget;
	}
	
	public String getNavigationTarget() {
		return navigationTarget;
	}
	
}
