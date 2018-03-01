package com.kollect.etl.entity;

public class EmailSettings {

	  boolean sendEmail; String userAuthentication, userName, pass, host, recipient, subject, msg, subjErr, msgErr;
	  int port;

	public EmailSettings(boolean sendEmail, String userAuthentication, String userName, String pass, String host,
			String recipient, int port, String subject, String msg, String subjErr, String msgErr) {
		super();
		this.sendEmail = sendEmail;
		this.userAuthentication = userAuthentication;
		this.userName = userName;
		this.pass = pass;
		this.host = host;
		this.recipient = recipient;
		this.port = port;
		this.subject = subject;
		this.msg = msg;
		this.subjErr = subjErr;
		this.msgErr = msgErr;
	}
	  
}
