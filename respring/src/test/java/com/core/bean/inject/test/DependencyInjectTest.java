package com.core.bean.inject.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.core.bean.BeanContainer;
import com.core.bean.inject.DepencyInjector;
import com.core.util.test.extractPacketTest;

public class DependencyInjectTest {

	@DisplayName("“¿¿µ◊¢»Î≤‚ ‘")
	@Test
	public void classDependencyInject() throws Exception {
		BeanContainer beanContainer = BeanContainer.getBeanContainerInstance();
		beanContainer.loadBean("com.core.util.test");
		Assertions.assertEquals(true, beanContainer.containerIsLoaded());
		extractPacketTest bean =(extractPacketTest)beanContainer.getBean(extractPacketTest.class);
		Assertions.assertEquals(true, bean instanceof extractPacketTest);
		Assertions.assertEquals(null, bean.getTestService());
		new DepencyInjector().doIoc();
		Assertions.assertNotEquals(null, bean.getTestService());
	}
}
