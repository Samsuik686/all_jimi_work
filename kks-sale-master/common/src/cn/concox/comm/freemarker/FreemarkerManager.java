/*
 * Created: 2016-7-18
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
 * @file: FreemarkerManager.java
 */
package cn.concox.comm.freemarker;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import freemarker.template.Template;

/**
 * 生成数据并下载 
 * 
 * @author Li.Shangzhi
 * @date 2016年7月18日
 */
public class FreemarkerManager {
	/**
	 * @param request
	 * @param response
	 * @param exportFile
	 * @param fileName
	 * @param template
	 * @param data
	 */
	@SuppressWarnings("rawtypes")
	public static void down(HttpServletRequest request, HttpServletResponse response,String exportFile,String fileName,Template template,Map data){
		FileOutputStream fileOutputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		FileInputStream fis = null;
		BufferedInputStream buff = null;
		OutputStream myout = null; 
		Writer writer = null;
		try {
			File staticFile = new File(exportFile);
			File staticDirectory = staticFile.getParentFile();
			if (!staticDirectory.exists()) {
				staticDirectory.mkdirs();
			}
			fileOutputStream = new FileOutputStream(staticFile);
			outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
			writer = new BufferedWriter(outputStreamWriter);
			template.process(data, writer);
			writer.flush();
			
			//设置response的编码方式
			response.setContentType("application/x-msdownload");
			//设置附加文件名
			response.setHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes("gb2312"), "iso-8859-1"));

			//读出文件到i/o流
			fis = new FileInputStream(staticFile);
			buff = new BufferedInputStream(fis);

			byte[] b = new byte[1024];//相当于我们的缓存

			long k = 0;//该值用于计算当前实际下载了多少字节

			//从response对象中得到输出流,准备下载

			myout = response.getOutputStream();

			//开始循环下载
			while (k < staticFile.length())
			{
				int j = buff.read(b, 0, 1024);
				k += j;
				//将b中的数据写到客户端的内存
				myout.write(b, 0, j);
			}
			//将写入到客户端的内存的数据,刷新到磁盘
			myout.flush();
			myout.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(writer);
			IOUtils.closeQuietly(outputStreamWriter);
			IOUtils.closeQuietly(fileOutputStream);
			IOUtils.closeQuietly(fis);
			IOUtils.closeQuietly(buff);
			IOUtils.closeQuietly(myout);
		}
	}

}
