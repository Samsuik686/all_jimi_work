/*
 * Created: 2012-9-11 
 * ==================================================================================================
 *
 * Cicove Technology Corp. Ltd. License, Version 1.0
 *
 * Copyright (c) 2011-2012 Cicove Tech. Co.,Ltd.   
 * 
 * Published by R&D Department, All rights reserved.
 * 
 * For the convenience of communicating and reusing of codes, 
 * Any java names,variables as well as comments should be made according to the regulations strictly.
 *
 * ==================================================================================================
 *
 * This software consists of contributions made by Cicove R&D.
 * @author: lizhongjie
 */
package cn.concox.comm.memcache;


public class MemUtil {
	
	protected static MemcachedClient mem = new MemcachedClient();
	
	static{
		String[] servers = { "127.0.0.1:11211" };   
		SockIOPool pool = SockIOPool.getInstance();   
		pool.setServers(servers);   
		pool.initialize();	
	}
	
	public static boolean set(String key,Object value){
		return mem.set(key, value);
	}
	
	public static Object get(String key){
		return mem.get(key);
	}
	
	 public static void main1(String[] args) {
		 
		 int time=25714; 
		 Long start=System.currentTimeMillis();
		 for (int i = 0; i < 1000000; i++) {
			 mem.set(String.valueOf(i),"");
		 }
		 Long end=System.currentTimeMillis();
		 System.out.println(end-start-time);
		 
		
		//System.out.println(mem.get("175345"));
		//System.out.println(mem.stats().get("curr_items"));
	}
	 
 public static void main(String[] args) {
		 
//		 int time=25714; 
//		 Long start=System.currentTimeMillis();
//		 for (int i = 0; i < 1000000; i++) {
//			 mem.set(String.valueOf(i),getCharAndNumr(100));
//		 }
//		 Long end=System.currentTimeMillis();
//		 System.out.println(end-start-time);
	
	   mem.add("bbb", "abcd");
	    System.out.println(mem.stats().size());
	    
		//System.out.println(mem.stats().get("curr_items"));
	}
	 
	 
	 
}
