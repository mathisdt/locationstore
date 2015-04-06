package org.zephyrsoft.locationstore.util;

import org.apache.commons.lang3.RandomStringUtils;

public class TokenUtil {
	
	private TokenUtil() {
		// not instantiable
	}
	
	public static String generate(int length) {
		return RandomStringUtils.random(length, true, true).toLowerCase();
	}
	
}
