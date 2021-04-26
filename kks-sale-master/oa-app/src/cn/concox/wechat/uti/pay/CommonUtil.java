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

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cn.concox.wechat.uti.WeChatGlobals.WeChatInfo;


public class CommonUtil {

	private static Logger log = Logger.getLogger(CommonUtil.class);

	/**
	 * 获取时间戳 从1970年1月1日00：00：00至今的秒数
	 * 
	 * @return
	 */
	public static String timestamp() {
		Long time = System.currentTimeMillis() / 1000;
		return Long.toString(time);
	}

	/**
	 * 路径 + 对象 拼接成完整路径
	 * 
	 * @param path
	 *            路径
	 * @param obj
	 *            对象
	 * @return 拼接成完整路径
	 */
	public static String pathParameterJoin(String path, Object obj) {
		StringBuffer urlSb = new StringBuffer(path);
		try {
			if (obj != null) {
				Field[] fields = obj.getClass().getDeclaredFields();
				if (null != fields && fields.length != 0) {
					urlSb.append("?");
					for (int i = 0; i < fields.length; i++) {
						Field f = fields[i];
						f.setAccessible(true);
						if (i == (fields.length - 1)) {
							urlSb.append(f.getName().toLowerCase() + "=" + f.get(obj));
						} else {
							urlSb.append(f.getName().toLowerCase() + "=" + f.get(obj) + "&");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return urlSb.toString();
	}

	/**
	 * 路径 + 对象+过滤 拼接成完整路径
	 * 
	 * @param path
	 *            路径
	 * @param obj
	 *            对象
	 * @param params
	 *            过滤参数，参数名称
	 * @return 完整路径
	 */
	public static String pathParameterJoin(String path, Object obj, List params) {
		StringBuffer urlSb = new StringBuffer(path);
		try {
			if (obj != null) {
				Field[] fields = obj.getClass().getDeclaredFields();
				if (null != fields && fields.length != 0) {
					urlSb.append("?");
					for (int i = 0; i < fields.length; i++) {
						Field f = fields[i];
						f.setAccessible(true);
						if (null != params && params.size() > 0 && params.contains(f.getName())) {
							continue;
						}
						if (i == (fields.length - 1)) {
							urlSb.append(f.getName().toLowerCase() + "=" + f.get(obj));
						} else {
							urlSb.append(f.getName().toLowerCase() + "=" + f.get(obj) + "&");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return urlSb.toString();
	}

	/**
	 * 对象属性拼接
	 * 
	 * @param obj
	 *            拼接对象
	 * @return 拼接后字符串
	 */
	public static String parameterJoin(Object obj) {
		StringBuffer urlSb = new StringBuffer();
		try {
			if (obj != null) {
				Field[] fields = obj.getClass().getDeclaredFields();
				if (null != fields && fields.length != 0) {
					for (int j = 0; j < fields.length; j++) {
						Field f = fields[j];
						f.setAccessible(true);
						if (j == (fields.length - 1)) {
							urlSb.append(f.getName().toLowerCase() + "=" + f.get(obj));
						} else {
							urlSb.append(f.getName().toLowerCase() + "=" + f.get(obj) + "&");
						}
					}
				}
				if (urlSb.toString().lastIndexOf("&") == urlSb.toString().length() - 1) {
					return urlSb.toString().substring(0, urlSb.toString().length() - 1);
				} else {
					return urlSb.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 对象属性拼接,过滤掉指定参数值
	 * 
	 * @author 黄韦谋
	 * @crate 2014年6月24日 下午2:33:05
	 * @param obj
	 *            拼接对象
	 * @param exclude
	 *            排除参数
	 * @return 拼接后字符串
	 */
	public static String parameterJoin(Object obj, List params) {
		StringBuffer urlSb = new StringBuffer();
		try {
			if (obj != null) {
				Field[] fields = obj.getClass().getDeclaredFields();
				if (null != fields && fields.length != 0) {
					for (int j = 0; j < fields.length; j++) {
						Field f = fields[j];
						f.setAccessible(true);
						if (null != params && params.size() > 0 && params.contains(f.getName())) {
							continue;
						}
						if (j == (fields.length - 1)) {
							urlSb.append(f.getName().toLowerCase() + "=" + f.get(obj));
						} else {
							urlSb.append(f.getName().toLowerCase() + "=" + f.get(obj) + "&");
						}
					}
				}
				if (urlSb.toString().lastIndexOf("&") == urlSb.toString().length() - 1) {
					return urlSb.toString().substring(0, urlSb.toString().length() - 1);
				} else {
					return urlSb.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 创建随机字符串
	 * 
	 * @param length
	 *            指定长度
	 * @return
	 */
	public static String CreateNoncestr(int length) {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < length; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	/**
	 * 创建随机字符串 固定16位
	 * 
	 * @author 黄韦谋
	 * @crate 2014年6月27日 上午10:32:23
	 * @return
	 */
	public static String CreateNoncestr() {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < 16; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	/**
	 * 对map集合的参数进行开头字母ASCII编码排序，然后再生成key=value，进行拼接
	 * 
	 * @param parameters
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String FormatQueryParaMap(Map<String, Object> parameters, Boolean isEncode)
			throws UnsupportedEncodingException {

		String buff = "";
		List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(parameters.entrySet());

		Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
			public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
				return (o1.getKey()).toString().compareTo(o2.getKey());
			}
		});

		for (int i = 0; i < infoIds.size(); i++) {
			Map.Entry<String, Object> item = infoIds.get(i);
			if (item.getKey() != "") {
				if (isEncode) {
					buff += item.getKey() + "=" + URLEncoder.encode(item.getValue().toString(), "utf-8") + "&";
				} else {
					buff += item.getKey() + "=" + item.getValue() + "&";
				}
			}
		}
		if (buff.isEmpty() == false) {
			buff = buff.substring(0, buff.length() - 1);
		}
		return buff;
	}

	/**
	 * 判断是否是数字类型
	 * 
	 * @param str
	 * @return
	 */
	public static boolean IsNumeric(String str) {
		if (str.matches("\\d *")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * map 转化成xml格式
	 * 
	 * @param arr
	 * @return
	 */
	public static String ArrayToXml(HashMap<String, String> arr) {
		String xml = "<xml>";

		Iterator<Entry<String, String>> iter = arr.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			String key = entry.getKey();
			String val = entry.getValue();
			if (IsNumeric(val)) {
				xml += "<" + key + ">" + val + "</" + key + ">";

			} else
				xml += "<" + key + "><![CDATA[" + val + "]]></" + key + ">";
		}

		xml += "</xml>";
		return xml;
	}

	/**
	 * 把对象 转化成map集合，可进行参数过滤
	 * @param obj
	 * @param params
	 * @return
	 */
	public static Map<String, String> objectIntoMap(Object obj, List<Object> params) {
		if (obj != null) {
			try {
				Map<String, String> map = new HashMap<String, String>();
				Field[] fields = obj.getClass().getDeclaredFields();
				if (null != fields && fields.length != 0) {
					for (int j = 0; j < fields.length; j++) {
						Field f = fields[j];
						f.setAccessible(true);
						if (null != params && params.size() > 0  && params.contains(f.getName())) {
							continue;
						} else {
							if (null != f.get(obj)) {
								map.put(f.getName().toLowerCase(), f.get(obj).toString());
							}
						}
					}
					return map;
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	/**
	 * 测试用，遍历对象属性值
	 * 
	 * @param obj
	 */
	public static void attributeTraversal(Object obj) {
		if (obj == null) {
			log.info("对象为空!");
		} else {
			try {
				Field[] fields = obj.getClass().getDeclaredFields();
				String className = obj.getClass().getSimpleName();
				if (fields != null && fields.length > 0) {
					for (int i = 0; i < fields.length; i++) {
						Field field = fields[i];
						field.setAccessible(true);
						if (null == field.get(obj)) {
							continue;
						}
						log.info(className + ":" + field.getName() + "=" + field.get(obj));
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public static Map<String, Object> Dom2Map(Document doc) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (doc == null)
			return map;
		Element root = doc.getRootElement();
		for (Iterator iterator = root.elementIterator(); iterator.hasNext();) {
			Element e = (Element) iterator.next();
			// System.out.println(e.getName());
			List list = e.elements();
			if (list.size() > 0) {
				map.put(e.getName(), Dom2Map(e));
			} else
				map.put(e.getName(), e.getText());
		}
		return map;
	}

	public static Map Dom2Map(Element e) {
		Map map = new HashMap();
		List list = e.elements();
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Element iter = (Element) list.get(i);
				List mapList = new ArrayList();

				if (iter.elements().size() > 0) {
					Map m = Dom2Map(iter);
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName().equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(m);
						}
						if (obj.getClass().getName().equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(m);
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), m);
				} else {
					if (map.get(iter.getName()) != null) {
						Object obj = map.get(iter.getName());
						if (!obj.getClass().getName().equals("java.util.ArrayList")) {
							mapList = new ArrayList();
							mapList.add(obj);
							mapList.add(iter.getText());
						}
						if (obj.getClass().getName().equals("java.util.ArrayList")) {
							mapList = (List) obj;
							mapList.add(iter.getText());
						}
						map.put(iter.getName(), mapList);
					} else
						map.put(iter.getName(), iter.getText());
				}
			}
		} else
			map.put(e.getName(), e.getText());
		return map;
	}

	/**
	 * 验证接收的xml是否是微信平台发送过来，是否合法，可进行属性过滤
	 * 
	 * @param xml
	 * @return
	 * @throws DocumentException
	 * @throws UnsupportedEncodingException 
	 */
	public static boolean verifyPostData(String xml) throws DocumentException, UnsupportedEncodingException {
		String status = Dom4jUtils.findAppointDocVal(xml, "return_code");
		if ("SUCCESS".equals(status)) {
			Document document = DocumentHelper.parseText(xml);
			Map<String, Object> map = CommonUtil.Dom2Map(document);
			String sign = (String) map.remove("sign");
			log.debug(sign);
			String text = CommonUtil.FormatQueryParaMap(map, false);
			Boolean result = MD5.verify(text, WeChatInfo.MCH_KEY, sign, WeChatInfo.CHARSET);
			if(result){
				String result_code = Dom4jUtils.findAppointDocVal(xml, "result_code");
				if("SUCCESS".equals(result_code)){
					return true;
				}else{
					String err_code = Dom4jUtils.findAppointDocVal(xml, "err_code");
					String err_msg = Dom4jUtils.findAppointDocVal(xml, "err_code_des");
					log.error("接收通知错误["+err_code+":"+err_msg+"]" );
					return false;
				}
			}else{
				String err_msg = Dom4jUtils.findAppointDocVal(xml, "return_msg");
				log.error("接收通知错误["+err_msg+"]" );
			}
			return result;
		} 
		return false;
	}
}
