package com.oa.comet4j;

import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;

public class Comet4j {
	
	private static boolean flag = false;
	private String channel = "start";
	private String channel1 = "message";
	public static boolean isFlag() {
		return flag;
	}
	public static void setFlag(boolean flag) {
		Comet4j.flag = flag;
	}
	public static void init(){//初始化，注册各个通道。信道
		CometContext.getInstance().registChannel("start");
		CometContext.getInstance().registChannel("message");
		System.out.println("comet4j初始化。。。。。。。");
		setFlag(true);
	}
	public void sendtoAll(String sendto){//向通道channel中发送内容为要发送到的人，前段会根据发送到的人判断自己是不是包含在发送到人的列表中，以便决定是否接受该消息
		CometEngine engine = CometContext.getInstance().getEngine();
		engine.sendToAll(channel , sendto);
	}
	public void sendmessage(String message){//在channel1通道中发送消息，所有在该通道中的用户就回收到该消息
		CometEngine engine = CometContext.getInstance().getEngine();
		engine.sendToAll(channel1 , message);
		
	}
	
}
