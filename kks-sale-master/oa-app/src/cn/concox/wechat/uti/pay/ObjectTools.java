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
package cn.concox.wechat.uti.pay;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * object的工具类
 * 
 * @create 2014-5-29 下午5:23:37
 */
public class ObjectTools {

	private static Logger log = Logger.getLogger(ObjectTools.class);

	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	
	private static SimpleDateFormat sd = new SimpleDateFormat(
			"yyyy-MM-dd");

	private static DecimalFormat df = new DecimalFormat("0.00");
	
	private static String APP_KEY_NAME="appKey";
	
	
	
	
	
	/**
	 * 时间格式转换
	 * 
	 * @throws ParseException 
	 * @create 2015-11-4 下午9:34:10
	 */
	public static String getDate(Date date) throws ParseException{
	 
		String time= sdf.format(date);
	 
		 
		return time;
	}
	
	/**
	 * 时间格式转换
	 * 
	 * @throws ParseException 
	 * @create 2015-11-4 下午9:34:10
	 */
	public static String getDateTwo(String date) throws ParseException{
	 
		
		Date da=sd.parse(date);
		 
		return sd.format(da);
	}
	
	
	
	/**
	 * 把object对象的属性名和值转换成map对象
	 * 
	 * @param o
	 *            要转换的对象
	 * @return 转换后的map对象
	 * 
	 */
	public static Map<String, Object> object2Map(Object o) {
		Map<String, Object> map = new HashMap<String, Object>();
		Class<?> c = o.getClass();
		Method[] methods = c.getMethods();
		for (Method m : methods) {
			String mName = m.getName();
			if (!mName.startsWith("get") || "getClass".equals(mName)) {
				continue;
			} else {
				String key = mName.replaceFirst("get", "");
				key = key.substring(0, 1).toLowerCase() + key.substring(1);
				try {
					Object valueObj = m.invoke(o);
					if (null == valueObj) {
						continue;
					}
					String value = m.invoke(o).toString();
					map.put(key, value);
				} catch (Exception e) {
					log.error(c.getName() + " 执行：" + mName + "方法出错");
					log.debug("", e);
					continue;
				}
			}
		}
		return map;
	}
	
	/**
	 * 把object对象的属性名和值转换成map对象，并进行内容encode
	 * @param o
	 * @return
	 */
	public static Map<String, String> object2MapByEncodeVal(Object o) {
		Map<String, String> map = new HashMap<String, String>();
		Class<?> c = o.getClass();
		Method[] methods = c.getMethods();
		for (Method m : methods) {
			String mName = m.getName();
			if (!mName.startsWith("get") || "getClass".equals(mName)) {
				continue;
			} else {
				String key = mName.replaceFirst("get", "");
				key = key.substring(0, 1).toLowerCase() + key.substring(1);
				try {
					Object valueObj = m.invoke(o);
					if (null == valueObj) {
						continue;
					}
					String value = m.invoke(o).toString();
					map.put(key, URLEncoder.encode(value, "utf-8"));
				} catch (Exception e) {
					log.error(c.getName() + " 执行：" + mName + "方法出错");
					log.debug("", e);
					continue;
				}
			}
		}
		return map;
	}
	 

	/**
	 * 签名 把object对象的属性名和值转换成map对象
	 * 
	 * @param o
	 *            要转换的对象
	 * @return 转换后的map对象
	 * 
	 */
	public static Map<String, String> object2MapSign(Object o) {
		Map<String, String> map = new HashMap<String, String>();
		Class<?> c = o.getClass();
		Method[] methods = c.getMethods();
		for (Method m : methods) {
			String mName = m.getName();
			if (!mName.startsWith("get") || "getClass".equals(mName)) {
				continue;
			} else {
				String key = mName.replaceFirst("get", "");
				key = key.substring(0, 1).toLowerCase() + key.substring(1);
				try {
					Object valueObj = m.invoke(o);
					if (null == valueObj) {
						continue;
					}
					String value = "";
					Object objvalue = m.invoke(o);
					Class<?> objvalueClass = objvalue.getClass();
					if (Date.class.equals(objvalueClass)) {
						value = sdf.format(objvalue);
					} else if (BigDecimal.class.equals(objvalueClass)){
						value = df.format(objvalue);
					}else {
						value = m.invoke(o).toString();
					}
					map.put(key, value);
				} catch (Exception e) {
					log.error(c.getName() + " 执行：" + mName + "方法出错");
					log.debug("", e);
					continue;
				}
			}
		}
		return map;
	}
	
	/**
	 * 签名 把object对象的属性名和值转换成map对象
	 * 
	 * @param o
	 *            要转换的对象
	 * @return 转换后的map对象
	 * 
	 */
	public static Map<String, String> object2MapSign(Object o,String appKey) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(APP_KEY_NAME, appKey);
		Class<?> c = o.getClass();
		Method[] methods = c.getMethods();
		for (Method m : methods) {
			String mName = m.getName();
			if (!mName.startsWith("get") || "getClass".equals(mName)) {
				continue;
			} else {
				String key = mName.replaceFirst("get", "");
				key = key.substring(0, 1).toLowerCase() + key.substring(1);
				try {
					Object valueObj = m.invoke(o);
					if (null == valueObj) {
						continue;
					}
					String value = "";
					Object objvalue = m.invoke(o);
					Class<?> objvalueClass = objvalue.getClass();
					if (Date.class.equals(objvalueClass)) {
						value = sdf.format(objvalue);
					} else if (BigDecimal.class.equals(objvalueClass)){
						value = df.format(objvalue);
					}else {
						value = m.invoke(o).toString();
					}
					map.put(key, value);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		return map;
	}
	
	public static void logObject(Object o) {
		Class<?> c = o.getClass();
		Method[] ms = c.getMethods();
		log.debug("--" + o.getClass().getName() + "--");
		for (Method m : ms) {
			String mName = m.getName();
			if (!mName.startsWith("get") || "getClass".equals(mName)) {
				continue;
			} else {
				try {
					log.debug(m.getName() + ":" + m.invoke(o));
				} catch (Exception e) {
					log.debug("不能输出：" + m.getName());
				}

			}
		}
		log.debug("----");
	}

	/**
	 * map转string
	 * 
	 * @create 2014-6-1 下午12:43:26
	 * @param map
	 * @param separatorMap
	 * @param separatorKeyValue
	 * @return
	 */
	public static String map2String(Map map, String separatorMap,
			String separatorKeyValue) {
		if (null == map || 0 == map.size()) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Object key : map.keySet()) {
			sb.append(key.toString());
			sb.append(separatorKeyValue);
			sb.append(map.get(key).toString());
			sb.append(separatorMap);
		}
		return sb.substring(0, sb.length() - 1);
	}

	/**
	 * string 类型转map
	 * 
	 * @create 2014-6-1 下午12:43:13
	 * @param data
	 * @param separatorMap
	 * @param separatorKeyValue
	 * @return
	 */
	public static Map<String, String> string2Map(String data,
			String separatorMap, String separatorKeyValue) {
		Map<String, String> map = new HashMap<String, String>();
		if (null == data || "".equals(data)) {
			return map;
		}
		String[] arrays = data.split(separatorMap);
		String[] keyValue = new String[2];
		for (String s : arrays) {
			keyValue = s.split(separatorKeyValue);
			map.put(keyValue[0], keyValue[1]);
		}
		return map;
	}

	/**
	 * object转string
	 * 
	 * @param o
	 * @param separatorMap
	 * @param separatorKeyValue
	 * @return
	 */
	public static String object2String(Object o, String separatorMap,
			String separatorKeyValue) {
		if (null == o) {
			return "";
		}
		Map map = object2Map(o);
		return map2String(map, separatorMap, separatorKeyValue);
	}

	public static String object2StringByEncodeVal(Object o, String separatorMap,
			String separatorKeyValue) {
		if (null == o) {
			return "";
		}
		Map map = object2MapByEncodeVal(o);
		return map2String(map, separatorMap, separatorKeyValue);
	}
	
	/**
	 * 通过反射获取某一对象指定的属性值
	 * @param obj 对象
	 * @param methodName 属性方法名 （get方法）
	 * @return String 属性值
	 */
	public static String getValueByMethod(Object obj, String methodName) {
		try {
			Method method = obj.getClass().getMethod(methodName);
			return  method.invoke(obj).toString();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过反射为某一对象的属性进行赋值操作
	 * @param obj 对象
	 * @param methodName 属性的方法名称 (set方法名)
	 * @param value 设置内容
	 * @param parameterTypes  属性的数据类型
	 * @return Object 返回已赋值对象
	 */
	public static Object setValueByMethod(Object obj, String methodName,String value, Class<?> parameterTypes) {
		Class<?> clas = obj.getClass().getSuperclass();
		Method methods;
		try {
			methods = clas.getMethod(methodName, parameterTypes);
			methods.invoke(obj, value);
		}  catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
