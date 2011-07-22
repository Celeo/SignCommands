package com.celeo.signcommands;

public class Mail {
	
	String from;
	String to;
	String message;
	
	public Mail(String f, String t, String m) {
		this.from = f;
		this.to = f;
		this.message = m;
	}
	
	public String getFrom() {
		return this.from;
	}
	
	public String getTo() {
		return this.to;
	}
	
	public String getMessage() {
		return this.message;
	}
	
}