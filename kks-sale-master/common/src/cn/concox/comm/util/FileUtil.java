/*
 * Created: 2016-7-12
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


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;



public class FileUtil {
	
	

	 public static byte[] getBytesFromFile(File file) throws IOException {
	        InputStream is = new FileInputStream(file);
	        long length = file.length();
	        	if (length > Integer.MAX_VALUE) {
	        		throw new IOException("File is to large "+file.getName());
	        }
	        byte[] bytes = new byte[(int)length];
	        int offset = 0;
	        int numRead = 0;
	        while (offset < bytes.length
	               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	            offset += numRead;
	        }
	        if (offset < bytes.length) {
	            throw new IOException("Could not completely read file "+file.getName());
	        }
	        is.close();
	        return bytes;

	    }
	

		/**
		 * 文件上传
		 * @author WG.He
		 * @Date 2013/11/22
		 * @param in
		 * @param dst
		 * @return boolean
		 */
		public static boolean copyFile(byte[] in,File dst){
			boolean copyStatus=true;
			try {
				FileCopyUtils.copy(in, dst);
	         }catch (Exception e) {
	            e.printStackTrace();
	            copyStatus=false;
	        } 
			return copyStatus;
		}
		
	
	/**
	 * 文件上传	
	 * @author TangYuping
	 * @version 2017年3月29日 下午2:57:05
	 * @param ins   文件流
	 * @param filePath 上传路径
	 * @return
	 */
	public static boolean readInputStreamToFile (InputStream ins, String filePath) throws Exception{
		FileOutputStream out = null;
		byte[] buff = new byte[4096];
		try {
			File file = new File(filePath);
			// 自动创建文件夹
			if(file.getParentFile() == null || !file.getParentFile().exists()){
				if(!file.getParentFile().mkdirs()){
					throw new RuntimeException("文件夹创建失败!");
				}
			}
			out = new FileOutputStream(file);//文件输出的路径
			int len = 0;
			while ((len = ins.read(buff)) > 0) {
				out.write(buff, 0, len);
			}
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("文件输出路径问题!");
		} catch (IOException e) {
			throw new IOException();
		} finally {
			try {
				if (ins != null) {
					ins.close();
				}
			} catch (IOException e) {
			}
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				throw new IOException();
			}
		}
		return true;
	}
	
	
	/**
	 * 文件下载
	 * @author TangYuping
	 * @version 2017年3月31日 上午11:18:33
	 * @param req
	 * @param response
	 * @param downLoadFile
	 * @param fileName
	 * @return
	 */
	public static boolean downLoadFile (HttpServletRequest req, HttpServletResponse response, 
			String filePath, String fileName) throws Exception {
			OutputStream out = null;
			InputStream ins = null;
			 try {
				 	File file = new File(filePath);// 文件路径
				  	ins  = new BufferedInputStream(new FileInputStream(filePath));
				    byte[] buffer = new byte[ins.available()];
				    ins.read(buffer);
				    
				    response.reset();
				    // 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
				    response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.replaceAll(" ", "").replaceAll(",", "、").getBytes("utf-8"),"iso8859-1"));
				    response.addHeader("Content-Length", "" + file.length());
				    out  = new BufferedOutputStream(response.getOutputStream());
				    response.setContentType("application/octet-stream");
				    out.write(buffer);// 输出文件
				   			    
	               return true;
	        }catch (Exception e) {
	        	throw new Exception();
	        }finally{
	            try {
	                if(out != null){
	                	out.flush();
	                	out.close();
	                }
	                if(ins != null){
	                	ins.close();
	                }
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	                throw new IOException();
	            }
	        }
	}
	
		
		
}
