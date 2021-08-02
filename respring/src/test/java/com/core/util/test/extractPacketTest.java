package com.core.util.test;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.core.annotation.Autowired;
import com.core.annotation.Service;
import com.core.util.ClassUtil;

import lombok.Getter;

@Service
@Getter
public class extractPacketTest  {

	@Autowired
	private com.core.util.test.Test testService;
	
	@Test
	@DisplayName("≤‚ ‘extractFileClass")
	public void getPacketClass() throws Exception {
		Set<Class<?>> extractPacketClass = ClassUtil.extractPacketClass("com.core.util");
		System.out.println(extractPacketClass);
		Assertions.assertEquals(1, extractPacketClass.size());
	}
}
