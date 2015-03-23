package org.zephyrsoft.locationstore.dao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates the interfaces that should be transformed into Spring beans by
 * {@link org.mybatis.spring.annotation.MapperScan}.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MapperInterface {
	// nichts hier
}
