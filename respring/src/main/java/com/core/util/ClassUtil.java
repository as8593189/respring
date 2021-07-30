package com.core.util;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 鸣濑白羽
 * @date 2021.7.13
 * @api 用于获取包下的类集合
 * */
@Slf4j
public class ClassUtil {

	/**
	 * 文件扩展名.
	 * */
	private static final String FILE_PROTOCAL = "file";

	/**
	 * 提取包中的类。
	 * */
	public static Set<Class<?>> extractPacketClass(String packetName) throws Exception{
		//类加载器
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		//取得url
		URL url = loader.getResource(packetName.replace(".", "/"));
		if (url == null) {
			//打印日志
			log.warn("找不到所在包");
			throw new Exception("找不到所在的包");
		}
		Set<Class<?>> classSet = null;
		//过滤出文件
		if (url.getProtocol().equalsIgnoreCase(FILE_PROTOCAL)) {
			classSet = new HashSet<Class<?>>();
			File filePath = new File(url.getPath());
			//获取class文件
			extractFileClass(classSet,filePath,packetName);
		}
		return classSet;
	}

	/**
	 * 递归取得文件.
	 * */
	private static void extractFileClass(Set<Class<?>> classSet, File fileSource, String packetName) {
		// TODO Auto-generated method stub
		//递归获取文件
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
					//如果是class文件
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
				//递归时不应该直接再使用packetName 
				extractFileClass(classSet, file, packetName);
			}
		}
	}

	/**
	 * 添加进入set.
	 * */
	private  static void addFileToSet(Set<Class<?>> classSet, String absolutePath, String packetName) {
		// TODO Auto-generated method stub
		//获取class文件，添加到集合中
		absolutePath = absolutePath.replace(File.separator, ".");
		String className = absolutePath.substring(absolutePath.indexOf(packetName), absolutePath.lastIndexOf("."));
		Class<?> targetClass = loadClass(className);
		classSet.add(targetClass);	
	}

	/**
	 * 加载类.
	 * */
	private static Class<?> loadClass(String className) {
		// TODO Auto-generated method stub
		Class<?> classTemp = null;
		try {
			classTemp	 = Class.forName(className);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("无法获取到类" + e);
		}
		return classTemp;
	}

	/**
	 * 反射返回新的实例。
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
}
