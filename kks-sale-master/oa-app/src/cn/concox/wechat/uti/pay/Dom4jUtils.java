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

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


public class Dom4jUtils {

	/**
	 * 通过xml格式，查找指定的节点内容
	 * @param xml
	 * @param doc
	 * @return
	 */
	public static String findAppointDocVal(String xml,String doc){
		String val = null;
		try {
			 Document document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			val = getElementList(root,doc);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return val;
	}
	
	 /**
     * 递归遍历方法,查找指定节点内容
     *
     * @param element
     */
    public static String getElementList(Element element, String targetDoc) {
        List elements = element.elements();
        if (elements.size() == 0) {
            //没有子元素
            String xpath = element.getPath();
            String name = element.getName();
            String value = element.getTextTrim();
            if(name.equals(targetDoc)){
            	return value;
            }
        } else {
            //有子元素
            for (Iterator it = elements.iterator(); it.hasNext();) {
                Element elem = (Element) it.next();
                //递归遍历
                String result = getElementList(elem,targetDoc);
                if(result != null){
                	return result;
                }
            }
        }
        return null;
    }
}
