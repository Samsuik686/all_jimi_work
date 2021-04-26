package cn.concox.comm.mail.other;

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
