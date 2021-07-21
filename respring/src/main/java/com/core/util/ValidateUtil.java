package com.core.util;

import java.util.Collection;
import java.util.Map;

/**
 * 判断是否为空.
 * */

public class ValidateUtil {

	/**
	 * 数组是否为空.
	 * */
	public static boolean arrayIsEmpty(Object[] objects) {
		return objects == null || objects.length ==0;
	}
	
	/**
	 * 字符串是否为空.
	 * */
	public static boolean stringIsEmpty(String string) {
		return string == null || "".equals(string) || "".equals(string.trim());
	}
	
	/**
	 * Collection是否为空.
	 * */
	public static boolean collectionIsEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}
	
	/**
	 * Map是否为空.
	 * */
	public static boolean mapIsEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}
}
