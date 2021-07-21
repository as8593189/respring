package com.core.util;

import java.util.Collection;
import java.util.Map;

/**
 * �ж��Ƿ�Ϊ��.
 * */

public class ValidateUtil {

	/**
	 * �����Ƿ�Ϊ��.
	 * */
	public static boolean arrayIsEmpty(Object[] objects) {
		return objects == null || objects.length ==0;
	}
	
	/**
	 * �ַ����Ƿ�Ϊ��.
	 * */
	public static boolean stringIsEmpty(String string) {
		return string == null || "".equals(string) || "".equals(string.trim());
	}
	
	/**
	 * Collection�Ƿ�Ϊ��.
	 * */
	public static boolean collectionIsEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}
	
	/**
	 * Map�Ƿ�Ϊ��.
	 * */
	public static boolean mapIsEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}
}
