package com.core.bean;

import java.util.concurrent.ConcurrentHashMap;

import com.core.util.ValidateUtil;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * bean ����.
 * */

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanContainer {

	/**
	 * ����.
	 * */
	private ConcurrentHashMap<Class<?>, Object> beanMap = new ConcurrentHashMap<Class<?>, Object>();
	
	/**
	 * ����״̬.
	 * */
	private boolean isLoaded = false;
	
	/**
	 * ��֤�����Ƿ��Ѿ�������.
	 * */
	public  synchronized boolean containerIsLoaded() {
		return isLoaded;
	}
	
	/**
	 * ���ص���.(ö���е�����)
	 * */
	public static BeanContainer getBeanContainerInstance() {
		return ContainerHodler.Hodler.instacance;
	}
	
	/**
	 * ö���ڲ���.
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
