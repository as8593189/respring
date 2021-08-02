package com.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注入注解.
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public  @interface Autowired {

	/**
	 * 在多个实现类bean中，为接口指定某一个具体的实现类进行注入. value表示类名.
	 * */
	String value() default "";
}
