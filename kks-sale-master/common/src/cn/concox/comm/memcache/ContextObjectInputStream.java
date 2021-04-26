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
package cn.concox.comm.memcache;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

public class ContextObjectInputStream extends ObjectInputStream { 

	ClassLoader mLoader;
    
	public ContextObjectInputStream( InputStream in, ClassLoader loader ) throws IOException, SecurityException {
		super( in );
		mLoader = loader;
	}
	
	protected Class resolveClass( ObjectStreamClass v ) throws IOException, ClassNotFoundException {
		if ( mLoader == null )
			return super.resolveClass( v );
		else
			return Class.forName( v.getName(), true, mLoader );
	}
}
