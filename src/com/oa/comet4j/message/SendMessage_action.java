package com.oa.comet4j.message;

import com.oa.action.andirod.servlet.OaServlet;
import com.oa.comet4j.Comet4j;

public class SendMessage_action extends OaServlet{
	
	private String messagecontent;
	private String sendtopeople;
	private String sendfrompeople;
	public String getMessagecontent() {
		return messagecontent;
	}
	public void setMessagecontent(String messagecontent) {
		this.messagecontent = messagecontent;
	}
	public String getSendtopeople() {
		return sendtopeople;
	}
	public void setSendtopeople(String sendtopeople) {
		this.sendtopeople = sendtopeople;
	}
	public String getSendfrompeople() {
		return sendfrompeople;
	}
	public void setSendfrompeople(String sendfrompeople) {
		this.sendfrompeople = sendfrompeople;
	}
	
	
	public String execute(){//消息的构成：sendfrompeople+sendtopeople+messagecontent；
		String message = sendfrompeople+"::"+sendtopeople+"::"+messagecontent;
		
		System.out.println(message);
		
		Comet4j comet4j = new Comet4j();
		comet4j.sendmessage(message);
		
		outprint("{\"success\":true}");
		return null;
	}

}
