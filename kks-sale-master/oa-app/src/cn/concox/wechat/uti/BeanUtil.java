/*
 * Created: 2016-09-13
 * ==================================================================================================
 *
 * Jimi Technology Corp. Ltd. License, Version 1.0 
 * Copyright (c) 2009-2016 Jimi Tech. Co.,Ltd.   
 * Published by R&D Department, All rights reserved.
 * For the convenience of communicating and reusing of codes, 
 * Any java names,variables as well as comments should be made according to the regulations strictly.
 *
 * ==================================================================================================
 * This software consists of contributions made by Jimi R&D.
 * @author: Li.Shangzhi
 */
package cn.concox.wechat.uti;

/**
 * 对象常用方法工具类
 */
public final class BeanUtil {

	/**
	 * 此类不需要实例化
	 */
	private BeanUtil() {
	}

	/**
	 * 判断对象是否为null
	 *
	 * @param object
	 *            需要判断的对象
	 * @return 是否为null
	 */
	public static boolean isNull(Object object) {
		return null == object;
	}

	/**
	 * 判断对象是否不为null
	 *
	 * @param object
	 *            需要判断的对象
	 * @return 是否不为null
	 */
	public static boolean nonNull(Object object) {
		return null != object;
	}

	/**
	 * 判断对象是否为空，如果为空，直接抛出异常
	 *
	 * @param object
	 *            需要检查的对象
	 * @param errorMessage
	 *            异常信息
	 * @return 非空的对象
	 */
	public static Object requireNonNull(Object object, String errorMessage) {
		if (null == object) {
			throw new NullPointerException(errorMessage);
		}
		return object;
	}
}
