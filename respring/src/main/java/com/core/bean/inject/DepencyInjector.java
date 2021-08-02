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
	 * �ڳ�ʼ�������Ҫ��һʱ���ȡ��Bean��������.
	 * */
	private BeanContainer beanContainer;
	
	/**
	 * �õ�����.
	 * */
	public DepencyInjector() {
		beanContainer = BeanContainer.getBeanContainerInstance();
	}
	
	/**
	 * ��������������bean�ĳ�Ա�����������autowire���������Ͻ���ע��
	 * */
	public void doIoc() {
		//�������е�bean
		Set<Class<?>> classes = beanContainer.getClasses();
		if (!ValidateUtil.collectionIsEmpty(classes)) {
			for (Class<?> class1 : classes) {
				//�����Ӧ�ı�autowired���γ�Ա����
				Field[] fields = class1.getDeclaredFields();
				if (!ValidateUtil.arrayIsEmpty(fields)) {
					for (Field field : fields) {
						if (field.isAnnotationPresent(Autowired.class)) {
							//��ȡautowireע��ֵ
							Autowired autowired = field.getAnnotation(Autowired.class);
							String autowiredValue = autowired.value();
							//��ȡ��Ա��������
							Class<?> type = field.getType();
							//�������л�ȡ��س�Ա������ʵ��
							Object filedValue = getFieldInstance(type,autowiredValue);
							if (filedValue == null) {
								throw new RuntimeException("�޷����ע��" + class1.getName() + "�е�" + type.getName());
							}else {
								//���ݷ����ʵ��ע�뵽��Ա������
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
			log.warn("������δ��ɳ�ʼ��");
			return;
		}
	}

	private Object getFieldInstance(Class<?> type, String autowiredValue) {
		// TODO Auto-generated method stub
		Object bean = beanContainer.getBean(type);
		if (bean != null) {
			return bean;
		}else {
			//�п��ܳ�Ա������һ���ӿڣ�����ӿ�û�����ε�����ʵ���౻������
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
			//�����ж��ʵ����,spring ��@qualifier ������autowire ���һ��ֵ��ָ��
			if (ValidateUtil.stringIsEmpty(autowiredValue)) {
				if (implementsSet.size()==1) {
					return implementsSet.iterator().next();
				}else {
					throw new RuntimeException("autowired �������ظ���ʵ���࣬��Ҫָ�������ʵ����" + type.getName());
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
