package com.core.conf;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

import com.core.annotation.Component;
import com.core.annotation.Controller;
import com.core.annotation.Repository;
import com.core.annotation.Service;

/**
 * 常量.
 * */

public class BeanConf {

	/**
	 * 自定义的四个注解.
	 * */
	final static public List<Class<? extends Annotation>> BEAN_ANNOTATION =
			Arrays.asList(Component.class,Controller.class,Repository.class,Service.class);
}
