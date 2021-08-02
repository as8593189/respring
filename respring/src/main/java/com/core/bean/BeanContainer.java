package com.core.bean;


import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.core.conf.BeanConf;
import com.core.util.ClassUtil;
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
	public   boolean containerIsLoaded() {
		return isLoaded;
	}
	
	/**
	 * ��ȡ����.
	 * */
	public  Integer getContainerSize() {
		return beanMap.size();
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
	
	/**
	 * ����bean��������.
	 * @throws Exception 
	 * */
	public synchronized void  loadBean(String packetName) throws Exception {
		if (isLoaded) {
			log.warn("��"+packetName+"���Ѿ�������");
			return;
		}
		Set<Class<?>> extractPacketClass = ClassUtil.extractPacketClass(packetName);
		if (ValidateUtil.collectionIsEmpty(extractPacketClass)) {
			log.warn("�ò�����"+packetName+"��");
			return;
		}else {
			//���һ���౻�Զ���ע������
			for (Class<?> class1 : extractPacketClass) {
				for (Class<? extends Annotation> class2 : BeanConf.BEAN_ANNOTATION) {
					if (class1.isAnnotationPresent(class2)) {
						//Ŀ���౾��Ϊ��,���Ҵ���һ���µ�ʵ������
						beanMap.put(class1, ClassUtil.getNewInstance(class1, true));
					}
				}
			}
		}
		//��Ϊ�����Ѿ�������
		isLoaded = true;
	}
	
	/**
	 * ���bean.
	 * */
	public Object addBean(Class<?> clazz,Object bean) {
		return beanMap.put(clazz, bean);
	}
	
	/**
	 * ɾ��bean.
	 * */
	public Object removeBean(Class<?> clazz) {
		return beanMap.remove(clazz);
	}
	
	/**
	 * ��ȡbean.
	 * */
	public Object getBean(Class<?> clazz) {
		return beanMap.get(clazz);
	}
	
	/**
	 * ������е�bean.
	 * */
	public Set<Object> getBeans() {
		return new HashSet<Object>(beanMap.values());
	}
	
	/**
	 * ������е�class.
	 * */
	public Set<Class<?>> getClasses(){
		return beanMap.keySet();
	}
	
	/**
	 * ɸѡ��ע�����class.
	 * */
	public Set<Class<?>> getAnnotationBeanClass(Class<? extends Annotation> annotation){
		Set<Class<?>> classes = getClasses();
		if (ValidateUtil.collectionIsEmpty(classes)) {
			log.warn("����Ϊ��");
			return null;
		}
		Set<Class<?>> res = new HashSet<Class<?>>();
		for (Class<?> class1 : classes) {
			if (class1.isAnnotationPresent(annotation)) {
				res.add(class1);
			}
		}
		return res.size()==0?null:res;
	}
	
	/**
	 * ͨ������/�ӿڻ��������bean ����������
	 * */
	public Set<Class<?>> getSupperOrInterfaceBeanClass(Class<?> supperOrInterface){
		Set<Class<?>> classes = getClasses();
		if (ValidateUtil.collectionIsEmpty(classes)) {
			log.warn("����Ϊ��");
			return null;
		}
		Set<Class<?>> res = new HashSet<Class<?>>();
		for (Class<?> class1 : classes) {
			if (supperOrInterface.isAssignableFrom(class1) && !class1.equals(supperOrInterface)) {
				res.add(class1);
			}
		}
		return res.size()==0?null:res;
	}
}
