package com.core.bean;

import java.util.concurrent.ConcurrentHashMap;

import com.core.util.ValidateUtil;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * bean 容器.
 * */

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanContainer {

	/**
	 * 容器.
	 * */
	private ConcurrentHashMap<Class<?>, Object> beanMap = new ConcurrentHashMap<Class<?>, Object>();
	
	/**
	 * 容器状态.
	 * */
	private boolean isLoaded = false;
	
	/**
	 * 验证容器是否已经被加载.
	 * */
	public  synchronized boolean containerIsLoaded() {
		return isLoaded;
	}
	
	/**
	 * 返回单例.(枚举中的属性)
	 * */
	public static BeanContainer getBeanContainerInstance() {
		return ContainerHodler.Hodler.instacance;
	}
	
	/**
	 * 枚举内部类.
	 * */
	private enum ContainerHodler{
		Hodler;
		private BeanContainer instacance;
		private ContainerHodler() {
			// TODO Auto-generated constructor stub
			instacance = new BeanContainer(); 
		}
		
	}
}
