/*
 * Created: 2016-7-27
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
 **/
package cn.concox.comm.util;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;

/**
 * <pre>
 * 字符串工具类
 * </pre>
 */
public class StringUtil {

	public static boolean isNull(String inValue) {
		return (inValue == null);
	}

	public static boolean isEmpty(String inValue) {
		if (isNull(inValue)) {
			return true;
		} else {
			return (inValue.length() < 1);
		}
	}

	public static boolean isRealEmpty(String inValue) {
		if (!isEmpty(inValue)) {
			return (inValue.trim().length() < 1);
		}

		return true;
	}

	public static String defaultString(String inValue) {
		return defaultString(inValue, "");
	}

	public static String defaultString(String inValue, String defaultValue) {
		if (isEmpty(inValue)) {
			return defaultValue;
		} else {
			return inValue;
		}
	}

	public static String strip(String value) {
		return strip(value, "");
	}

	public static String strip(String value, String defaultValue) {
		if (isRealEmpty(value)) {
			value = defaultValue;
		} else {
			value = value.trim();
		}
		return value;
	}

	public static String connectURL(String... args) {
		StringBuilder sb = new StringBuilder();
		boolean previous = false;
		for (String arg : args) {
			if (arg == null || ("/".equals(arg.trim()))) {
				continue;
			}
			arg = arg.trim();
			if (previous && arg.startsWith("/")) {
				arg = arg.substring(1);
			}
			if (!previous && !arg.startsWith("/")) {
				sb.append("/");
			}

			sb.append(arg);
			previous = arg.endsWith("/");
		}

		return sb.toString();
	}

	public static String pad(String padStr, int size) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(padStr);
		}

		return sb.toString();
	}

	public static Long[] toLongArray(String str) {
		return toLongArray(str, ",");
	}

	public static Long[] toLongArray(String str, String delimiter) {
		String[] temp = split(str, delimiter);
		List<Long> list = new ArrayList<Long>();
		for (String aStr : temp) {
			list.add(new Long(aStr));
		}

		return (Long[]) list.toArray(new Long[list.size()]);
	}

	public static String[] split(String str) {
		return split(str, ",");
	}
	
	public static String[] split_(String str) {
		return split(str, "-");
	}

	public static String[] split(String str, String delimiter) {
		if (str == null) {
			return new String[0];
		}

		StringTokenizer st;
		if (isEmpty(delimiter)) {
			st = new StringTokenizer(str);
		} else {
			st = new StringTokenizer(str, delimiter);
		}
		List<String> tokens = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			token = token.trim();
			if (token.length() > 0) {
				tokens.add(token);
			}
		}
		return (String[]) tokens.toArray(new String[tokens.size()]);
	}

	public static String join(Long[] longArray) {
		String[] tempStringArray = null;
		if (longArray != null) {
			int len = longArray.length;
			if (len > 0) {
				tempStringArray = new String[len];

				for (int i = 0; i < len; i++) {
					Long obj = longArray[i];
					if (obj != null) {
						tempStringArray[i] = obj.toString();
					}
				}
			}
		}

		return join(tempStringArray);
	}

	public static String join(String[] strArray) {
		return join(strArray, ",");
	}

	public static String join(Object[] objectArray) {
		String[] tempStringArray = null;
		if (objectArray != null) {
			int len = objectArray.length;
			if (len > 0) {
				tempStringArray = new String[len];

				for (int i = 0; i < len; i++) {
					Object obj = objectArray[i];
					if (obj != null) {
						tempStringArray[i] = obj.toString();
					}
				}
			}
		}

		return join(tempStringArray);
	}

	public static String join(String[] strArray, String separator) {
		StringBuilder sb = new StringBuilder(300);

		if (strArray != null && strArray.length > 0) {
			separator = defaultString(separator);
			for (int i = 0, len = strArray.length; i < len; i++) {
				sb.append(i > 0 ? separator : "").append(strArray[i]);
			}
		}

		return sb.toString();
	}

	public static String[] toArray(String... strings) {
		String[] result = null;

		if (strings == null) {
			result = new String[0];
		} else {
			result = strings;
		}

		return result;
	}

	/**
	 * 此函数判断字符串是否为数字
	 * 
	 * @param 需判断字符串
	 * @return boolean值
	 */
	public static boolean isDigit(String input) {

		if (isEmpty(input)) {
			return false;
		}
		boolean reUse = false;
		for (int i = 0; i < input.length(); i++) {
			if (input.indexOf(".") == 0 || input.indexOf(".") == input.length()) {
				return false;
			}
			// 是否有多余的.
			if (input.substring(i, i + 1).equals(".")) {
				if (!reUse) {
					reUse = true;
					continue;
				} else {
					return false;
				}
			}
			if (!Character.isDigit(input.charAt(i))) {
				return false;
			}
		}
		return true;

	}

	/**
     * */
	/**
	 * 此函数判断字符串是否为整数
	 * 
	 * @param 需判断字符串
	 * @return boolean值
	 */
	public static boolean isInteger(String s) {

		if ((s != null) && (s != ""))
			return s.matches("^[0-9]*$");
		else
			return false;

	}

	/**
	 * 此函数判断字符串是否为正数
	 * 
	 * @param 需判断字符串
	 * @return boolean值
	 */
	public static boolean isPositiveNumber(String s) {

		boolean returnValue = false;
		if (isDigit(s)) {
			Long checkNumber = new Long(s);
			if (checkNumber > 0) {
				returnValue = true;
			}
		}
		return returnValue;
	}

	/**
	 * 在给定两个字符串的中间填充指定字符达到给定的长度, 当指定长度小于两个字符串相加长度时，返回两个字符串相加的值
	 * 
	 * @param headStr
	 *            前面的字符串
	 * @param tailStr
	 *            后面的字符串
	 * @param fillChar
	 *            填充的字符，当指定填充字符为空时或长度为0时，则fillChar=0
	 * @param length
	 *            返回的字符长度
	 * @return 返回计算后的字符串
	 */
	public static String fillCharByLength(String headStr, String tailStr, String fillChar, int length) {

		headStr = defaultString(headStr);
		tailStr = defaultString(tailStr);

		// 字符串头 + 填充字串 + 字符串尾部
		return headStr + pad(defaultString(fillChar, "0"), length - (headStr.length() + tailStr.length())) + tailStr;
	}

	/**
	 * 在给定字符的前面填充0达到给定的长度, 当指定长度小于两个字符串相加长度时，返回两个字符串相加的值
	 * 
	 * @param tailStr
	 *            后面的字符串
	 * @param length
	 *            返回的字符长度
	 * @return 返回计算后的字符串
	 */
	public static String fillBeforeZero(String tailStr, int length) {

		return fillCharByLength("", tailStr, "0", length);
	}

	/**
	 * 在给定两个字符串的中间填充0达到给定的长度, 当指定长度小于两个字符串相加长度时，返回两个字符串相加的值
	 * 
	 * @param headStr
	 *            前面的字符串
	 * @param tailStr
	 *            后面的字符串
	 * @param length
	 *            返回的字符长度
	 * @return 返回计算后的字符串
	 */
	public static String fillMiddleZero(String headStr, String tailStr, int length) {

		return fillCharByLength(headStr, tailStr, "0", length);
	}

	/**
	 * double型小数保留digit位数的处理
	 * 
	 * @param d
	 * @param digit
	 * @return
	 */
	public static Double decimalDouble(double d, int digit) {

		return Double.valueOf(Math.round(d * (Math.pow(10, digit)))) / Math.pow(10, digit);
	}

	/**
	 * double型小数保留2位数的处理
	 * 
	 * @param d
	 * @param digit
	 * @return
	 */
	public static Double decimalDouble(double d) {

		return decimalDouble(d, 2);
	}

	/**
	 * 将字符串中的指定字符变成JS的转义字符
	 * 
	 * @param str
	 * @param sign
	 * @return
	 */
	public static String transferToJsStr(String str, String sign) {
		/*
		 * < 小于 &lt; > 大于 &gt; & &符号 &amp; " 双引号 &quot; × 乘号 &times; ÷ 除号 &divide;
		 */
		String result = "";
		if (sign.equals("\"")) {
			result = str.replace(sign, "&quot;");
		} else if (sign.equals("<")) {
			result = str.replace(sign, "&lt;");
		} else if (sign.equals(">")) {
			result = str.replace(sign, "&gt;");
		} else if (sign.equals("&")) {
			result = str.replace(sign, "&amp;");
		} else if (sign.equals("×")) {
			result = str.replace(sign, "&times;");
		} else if (sign.equals("÷")) {
			result = str.replace(sign, "&divide;");
		}
		return result;
	}

	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String[] getids(String idArray){
		if(!isEmpty(idArray)){
			idArray=idArray.replace("null", "");
			String[] ids=idArray.split("#");
			return ids;
		}else{
			return null;
		}
	}
	
	public static List<String> strImeisList(String imeis,String split){
		List<String> strImeis=new ArrayList<String>();
		if(StringUtils.isNotBlank(imeis)&&StringUtils.isNotBlank(split)){
			String[] imeius = imeis.split(split);
			for (String imei : imeius) {
				if(!isEmpty(imei)){
					strImeis.add(imei);
				}
			}
		}
		return strImeis;
	}
	
	
	public static boolean checkStings(String[] imeis){
		if(imeis==null||imeis.length==0){
			return false;
		}
		return true;
	}
	
	public static String[] getToArray(ArrayList<String> strings){
		String[] ids=null;
		if(strings!=null && strings.size()>0){
			ids = new String[strings.size()];
			ids = strings.toArray(new String[]{});
		}
		return ids;
	}
	
	//List<String>集合转化成 , 隔开的字符串
	public static String listToString(List<String> stringList){
        if (stringList==null) {
            return null;
        }
        StringBuilder result=new StringBuilder();
        boolean flag=false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            }else {
                flag=true;
            }
            result.append(string);
        }
        return result.toString();
    }
	
	public static boolean isLong(String longVal){
		try {
			Long.parseLong(longVal);
			return true;
		} catch (NumberFormatException e) {
			return false; 
		}
	}
}
