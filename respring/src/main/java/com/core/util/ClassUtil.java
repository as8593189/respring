package com.core.util;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ��������
 * @date 2021.7.13
 * @api ���ڻ�ȡ���µ��༯��
 * */
@Slf4j
public class ClassUtil {

	/**
	 * �ļ���չ��.
	 * */
	private static final String FILE_PROTOCAL = "file";

	/**
	 * ��ȡ���е��ࡣ
	 * */
	public static Set<Class<?>> extractPacketClass(String packetName) throws Exception{
		//�������
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		//ȡ��url
		URL url = loader.getResource(packetName.replace(".", "/"));
		if (url == null) {
			//��ӡ��־
			log.warn("�Ҳ������ڰ�");
			throw new Exception("�Ҳ������ڵİ�");
		}
		Set<Class<?>> classSet = null;
		//���˳��ļ�
		if (url.getProtocol().equalsIgnoreCase(FILE_PROTOCAL)) {
			classSet = new HashSet<Class<?>>();
			File filePath = new File(url.getPath());
			//��ȡclass�ļ�
			extractFileClass(classSet,filePath,packetName);
		}
		return classSet;
	}

	/**
	 * �ݹ�ȡ���ļ�.
	 * */
	private static void extractFileClass(Set<Class<?>> classSet, File fileSource, String packetName) {
		// TODO Auto-generated method stub
		//�ݹ��ȡ�ļ�
		if (!fileSource.isDirectory()) {
			return;
		}
		File[] listFiles = fileSource.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				// TODO Auto-generated method stub
				if (file.isDirectory()) {
					return true;
				}else {
					//�����class�ļ�
					 String absolutePath = file.getAbsolutePath();
					 if (absolutePath.endsWith(".class")) {
						 addFileToSet(classSet,absolutePath,packetName);
					}
				}
				return false;
			}
		});
		if (listFiles != null) {
			for (File file : listFiles) {
				//�ݹ�ʱ��Ӧ��ֱ����ʹ��packetName 
				extractFileClass(classSet, file, packetName);
			}
		}
	}

	/**
	 * ��ӽ���set.
	 * */
	private  static void addFileToSet(Set<Class<?>> classSet, String absolutePath, String packetName) {
		// TODO Auto-generated method stub
		//��ȡclass�ļ�����ӵ�������
		absolutePath = absolutePath.replace(File.separator, ".");
		String className = absolutePath.substring(absolutePath.indexOf(packetName), absolutePath.lastIndexOf("."));
		Class<?> targetClass = loadClass(className);
		classSet.add(targetClass);	
	}

	/**
	 * ������.
	 * */
	private static Class<?> loadClass(String className) {
		// TODO Auto-generated method stub
		Class<?> classTemp = null;
		try {
			classTemp	 = Class.forName(className);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("�޷���ȡ����" + e);
		}
		return classTemp;
	}

	/**
	 * ���䷵���µ�ʵ����
	 * */
	public static <T>T getNewInstance(Class<T> clazz ,boolean accisble) {
		try {
			Constructor<T> constructor = clazz.getDeclaredConstructor();
			constructor.setAccessible(accisble);
			return (T)constructor.newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("newInstance" + clazz + e);
			throw new RuntimeException();
		} 
	}
	
	/**
	 * Ϊ����������ֵ.
	 * Ϊclazz��filed��Ա��������instanceʵ��
	 * */
	public static void setFiledForClass(Object clazz,Field filed,Object instance,boolean accessible) {
		filed.setAccessible(accessible);
		try {
			filed.set(clazz, instance);
		}catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			log.error("����ʧЧ,�޷������ֵ");
			throw new RuntimeException(e);
		}
	}
	
	
}
