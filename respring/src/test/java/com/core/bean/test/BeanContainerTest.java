package com.core.bean.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.core.annotation.Service;
import com.core.bean.BeanContainer;
import com.core.util.test.extractPacketTest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BeanContainerTest {

	private static BeanContainer beanContainer;
	
	@BeforeAll
	public static void init() {
		beanContainer = BeanContainer.getBeanContainerInstance();
	}
	
	@Order(1)
	@Test
	@DisplayName("创建并加载容器")
	public void loadBean() throws Exception {
		Assertions.assertEquals(beanContainer.containerIsLoaded(), false);
		beanContainer.loadBean("com.core.util.test");
		Assertions.assertEquals(beanContainer.getContainerSize().intValue(), 1);
		Assertions.assertEquals(beanContainer.containerIsLoaded(), true);
	}
	
	@Order(2)
	@Test
	@DisplayName("能否获取到bean")
	public void getBean() {
		extractPacketTest ex = (extractPacketTest) beanContainer.getBean(extractPacketTest.class);
		Assertions.assertEquals(true, ex instanceof extractPacketTest);
		BeanContainerTest bt = (BeanContainerTest) beanContainer.getBean(BeanContainerTest.class);
		Assertions.assertEquals(null, bt);
	}
	
	@Order(3)
	@Test
	@DisplayName("获取到被注解标记的bean")
	public void getBeanByAnnotation() {
		Assertions.assertEquals(true, beanContainer.containerIsLoaded());
		Assertions.assertEquals(1, beanContainer.getAnnotationBeanClass(Service.class).size());
	}
	
	
}
