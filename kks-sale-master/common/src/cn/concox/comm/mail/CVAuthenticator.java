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
package cn.concox.comm.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class CVAuthenticator extends Authenticator {
	String userName = null;
	String password = null;

	public CVAuthenticator() {
	}

	public CVAuthenticator(String username, String password) {
		this.userName = username;
		this.password = password;
	}
	
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}
}
