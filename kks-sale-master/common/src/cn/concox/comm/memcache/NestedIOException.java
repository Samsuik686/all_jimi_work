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

import java.io.*;

 
public class NestedIOException extends IOException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5782870691080454259L;

	/**
     * Create a new <code>NestedIOException</code> instance.
     * @param cause object of type throwable
     */
    public NestedIOException( Throwable cause ) {
        super( cause.getMessage() );
        super.initCause( cause );
    }

    public NestedIOException( String message, Throwable cause ) {
        super( message );
        initCause( cause );
    }
}
