package com.core.bean.inject;

import java.lang.reflect.Field;
import java.util.Set;


import com.core.annotation.Autowired;
import com.core.bean.BeanContainer;
import com.core.util.ClassUtil;
import com.core.util.ValidateUtil;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class DepencyInjector {

	/**
	 * 在初始化本类后要第一时间获取到Bean容器单例.
	 * */
	private BeanContainer beanContainer;
	
	/**
	 * 得到单例.
	 * */
	public DepencyInjector() {
		beanContainer = BeanContainer.getBeanContainerInstance();
	}
	
	/**
	 * 遍历容器中所有bean的成员变量，如果被autowire修饰则马上进行注入
	 * */
	public void doIoc() {
		//遍历所有的bean
		Set<Class<?>> classes = beanContainer.getClasses();
		if (!ValidateUtil.collectionIsEmpty(classes)) {
			for (Class<?> class1 : classes) {
				//获得相应的被autowired修饰成员变量
				Field[] fields = class1.getDeclaredFields();
				if (!ValidateUtil.arrayIsEmpty(fields)) {
					for (Field field : fields) {
						if (field.isAnnotationPresent(Autowired.class)) {
							//获取autowire注解值
							Autowired autowired = field.getAnnotation(Autowired.class);
							String autowiredValue = autowired.value();
							//获取成员变量类型
							Class<?> type = field.getType();
							//在容器中获取相关成员变量的实例
							Object filedValue = getFieldInstance(type,autowiredValue);
							if (filedValue == null) {
								throw new RuntimeException("无法完成注入" + class1.getName() + "中的" + type.getName());
							}else {
								//依据反射把实例注入到成员变量中
								Object bean = beanContainer.getBean(class1);
								ClassUtil.setFiledForClass(bean, field, filedValue, true);
								
							}
						}
					}
				}else {
					continue;
				}
			}
		}else {
			log.warn("容器尚未完成初始化");
			return;
		}
	}

	private Object getFieldInstance(Class<?> type, String autowiredValue) {
		// TODO Auto-generated method stub
		Object bean = beanContainer.getBean(type);
		if (bean != null) {
			return bean;
		}else {
			//有可能成员变量是一个接口，这个接口没有修饰但是其实现类被修饰了
			Class<?> implentClass = getImplentClassBean(type,autowiredValue);
			if (implentClass != null) {
				return beanContainer.getBean(implentClass);
			}else {
				return null;
			}
		}
	}

	private Class<?> getImplentClassBean(Class<?> type, String autowiredValue) {
		// TODO Auto-generated method stub
		Set<Class<?>> implementsSet = beanContainer.getSupperOrInterfaceBeanClass(type);
		if (!ValidateUtil.collectionIsEmpty(implementsSet)) {
			//可能有多个实现类,spring 有@qualifier 这里用autowire 添加一个值来指定
			if (ValidateUtil.stringIsEmpty(autowiredValue)) {
				if (implementsSet.size()==1) {
					return implementsSet.iterator().next();
				}else {
					throw new RuntimeException("autowired 发现了重复的实现类，需要指定具体的实现类" + type.getName());
				}
			}else {
				for (Class<?> class1 : implementsSet) {
					if (class1.getSimpleName().equals(autowiredValue)) {
						return class1;
					}
				}
			}
		}
		return null;
	}
}
