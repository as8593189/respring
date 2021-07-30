package com.core.bean.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.core.bean.BeanContainer;

public class BeanContainerTest {

	private static BeanContainer beanContainer;
	
	@BeforeAll
	public static void init() {
		beanContainer = BeanContainer.getBeanContainerInstance();
	}
	
	@Test
	public void loadBean() throws Exception {
		Assertions.assertEquals(beanContainer.containerIsLoaded(), false);
		beanContainer.loadBean("com.core.util.test");
		Assertions.assertEquals(beanContainer.getContainerSize().intValue(), 1);
		Assertions.assertEquals(beanContainer.containerIsLoaded(), true);
	}
}
