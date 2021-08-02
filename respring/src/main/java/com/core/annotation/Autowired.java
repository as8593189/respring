package com.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ע��ע��.
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public  @interface Autowired {

	/**
	 * �ڶ��ʵ����bean�У�Ϊ�ӿ�ָ��ĳһ�������ʵ�������ע��. value��ʾ����.
	 * */
	String value() default "";
}
