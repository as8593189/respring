package com.core.util.test;

import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.core.util.ClassUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class extractPacketTest {

	@Test
	@DisplayName("≤‚ ‘extractFileClass")
	public void getPacketClass() throws Exception {
		Set<Class<?>> extractPacketClass = ClassUtil.extractPacketClass("com.core.util");
		System.out.println(extractPacketClass);
		Assert.assertEquals(1, extractPacketClass.size());
	}
}
