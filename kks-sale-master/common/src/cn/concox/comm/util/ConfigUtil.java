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
 **/
package cn.concox.comm.util;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
/**
 * 加载配置
 * @author Li.Shangzhi
 * @date 2016-09-13 09:23:04
 */
public  class ConfigUtil {
    private static final Logger logger=Logger.getLogger(ConfigUtil.class);
	public static Map<String,String> dynamicConfigsCach=null;
	private static  Properties props;
	static{
		//加载properties配置
        try {
            String path= ConfigUtil.class.getClassLoader().getResource("config.properties").getPath();
            logger.info("load config from:"+path);
            InputStream propsStream = ConfigUtil.class.getClassLoader().getResourceAsStream("config.properties");
            props=new Properties();
            props.load(propsStream);
        } catch (Exception e) {
            logger.error("config.properties is Error " + e);
            e.printStackTrace();
        }
	}
	 
//	/**
//	 * 设置配置路径
//	 * @param path
//	 * @author chengxuwei
//	 */
//	public static void configure(String path){
//	     //加载properties配置
//      try {
//          log.info("load config file from :"+path);
//          InputStream propsStream = new FileInputStream(path);
//          props=new Properties();
//          props.load(propsStream);
//      } catch (Exception e) {
//          log.error("加载config.properties出错，系统错误：" + e);
//          e.printStackTrace();
//      }
//	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public static String getString(String name){
		return props.getProperty(name);
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public static int getInt(String name){
	    String strValue=getString(name);
	    return NumberUtils.toInt(strValue, 0);
		
	}

	public static void main(String[] args) {
	  logger.info("tst");
	    
    }
    
}