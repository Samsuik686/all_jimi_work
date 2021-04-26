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

public interface ErrorHandler {

    /**
     * Called for errors thrown during initialization.
     */
    public void handleErrorOnInit( final MemcachedClient client ,
                                   final Throwable error );

    /**
     * Called for errors thrown during {@link MemcachedClient#get(String)} and related methods.
     */
    public void handleErrorOnGet( final MemcachedClient client ,
                                  final Throwable error ,
                                  final String cacheKey );

    /**
     * Called for errors thrown during {@link MemcachedClient#getMulti(String)} and related methods.
     */
    public void handleErrorOnGet( final MemcachedClient client ,
                                  final Throwable error ,
                                  final String[] cacheKeys );

    /**
     * Called for errors thrown during {@link MemcachedClient#set(String,Object)} and related methods.
     */
    public void handleErrorOnSet( final MemcachedClient client ,
                                  final Throwable error ,
                                  final String cacheKey );

    /**
     * Called for errors thrown during {@link MemcachedClient#delete(String)} and related methods.
     */
    public void handleErrorOnDelete( final MemcachedClient client ,
                                     final Throwable error ,
                                     final String cacheKey );

    /**
     * Called for errors thrown during {@link MemcachedClient#flushAll()} and related methods.
     */
    public void handleErrorOnFlush( final MemcachedClient client ,
                                    final Throwable error );

    /**
     * Called for errors thrown during {@link MemcachedClient#stats()} and related methods.
     */
    public void handleErrorOnStats( final MemcachedClient client ,
                                    final Throwable error );

} // interface
