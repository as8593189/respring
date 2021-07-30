package com.core.util.test;

import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.core.annotation.Service;
import com.core.util.ClassUtil;

@Service
public class extractPacketTest {

	@Test
	@DisplayName("≤‚ ‘extractFileClass")
	public void getPacketClass() throws Exception {
		Set<Class<?>> extractPacketClass = ClassUtil.extractPacketClass("com.core.util");
		System.out.println(extractPacketClass);
		Assert.assertEquals(1, extractPacketClass.size());
	}
}
