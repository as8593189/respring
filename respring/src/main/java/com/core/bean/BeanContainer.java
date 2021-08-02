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
	public   boolean containerIsLoaded() {
		return isLoaded;
	}
	
	/**
	 * 获取数量.
	 * */
	public  Integer getContainerSize() {
		return beanMap.size();
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
	
	/**
	 * 加载bean进入容器.
	 * @throws Exception 
	 * */
	public synchronized void  loadBean(String packetName) throws Exception {
		if (isLoaded) {
			log.warn("该"+packetName+"类已经被加载");
			return;
		}
		Set<Class<?>> extractPacketClass = ClassUtil.extractPacketClass(packetName);
		if (ValidateUtil.collectionIsEmpty(extractPacketClass)) {
			log.warn("得不到的"+packetName+"类");
			return;
		}else {
			//如果一个类被自定义注解修饰
			for (Class<?> class1 : extractPacketClass) {
				for (Class<? extends Annotation> class2 : BeanConf.BEAN_ANNOTATION) {
					if (class1.isAnnotationPresent(class2)) {
						//目标类本身为键,并且创建一个新的实例给他
						beanMap.put(class1, ClassUtil.getNewInstance(class1, true));
					}
				}
			}
		}
		//认为容器已经被加载
		isLoaded = true;
	}
	
	/**
	 * 添加bean.
	 * */
	public Object addBean(Class<?> clazz,Object bean) {
		return beanMap.put(clazz, bean);
	}
	
	/**
	 * 删除bean.
	 * */
	public Object removeBean(Class<?> clazz) {
		return beanMap.remove(clazz);
	}
	
	/**
	 * 获取bean.
	 * */
	public Object getBean(Class<?> clazz) {
		return beanMap.get(clazz);
	}
	
	/**
	 * 获得所有的bean.
	 * */
	public Set<Object> getBeans() {
		return new HashSet<Object>(beanMap.values());
	}
	
	/**
	 * 获得所有的class.
	 * */
	public Set<Class<?>> getClasses(){
		return beanMap.keySet();
	}
	
	/**
	 * 筛选出注解过的class.
	 * */
	public Set<Class<?>> getAnnotationBeanClass(Class<? extends Annotation> annotation){
		Set<Class<?>> classes = getClasses();
		if (ValidateUtil.collectionIsEmpty(classes)) {
			log.warn("容器为空");
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
	 * 通过父类/接口获得其子类bean 不包含本身
	 * */
	public Set<Class<?>> getSupperOrInterfaceBeanClass(Class<?> supperOrInterface){
		Set<Class<?>> classes = getClasses();
		if (ValidateUtil.collectionIsEmpty(classes)) {
			log.warn("容器为空");
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
