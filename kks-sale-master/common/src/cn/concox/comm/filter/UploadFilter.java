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
package cn.concox.comm.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.concox.comm.vo.ItemVo;


/*****
 * 
 * 匹配所有的上传 URL
 * @author hl
 *
 */
public class UploadFilter implements Filter{

	@Override
	public void destroy() {
	}
	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");
		if (request!=null && 
			request.getContentType()!=null &&
			request.getContentType().indexOf(OAMss.upForm)>-1) {
			
//			System.out.print(request.getContentType());
			DiskFileItemFactory factory = new DiskFileItemFactory();
			String path = request.getRealPath("/");
			factory.setRepository(new File(path));
			factory.setSizeThreshold(1024*1024) ;
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				List<FileItem> list = (List<FileItem>)upload.parseRequest((HttpServletRequest)request);
				for(FileItem item : list)
				{
					String name = item.getFieldName();
					if(item.isFormField())
					{
						String value = item.getString("UTF-8");
						request.setAttribute(name, value);
					}
					else
					{
						//是上传 字段
						String value = item.getName() ;
						int start = value.lastIndexOf("\\");
						String filename = value.substring(start+1);
						//byte[] files = FileUtil.getBytesFromFile(new File(path,filename));
						ItemVo  om = new ItemVo();
						om.setItem(item);
						om.setName(filename);
						request.setAttribute(item.getFieldName(), om);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		chain.doFilter(request, response);
	}
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
		
		
		
		
	}
}

class OAMss{
	protected static final String upForm = "multipart/form-data";
	
	public FileItem getItem() {
		return item;
	}
	public void setItem(FileItem item) {
		this.item = item;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private FileItem item;
	private String name;
}


