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
	public static void init(){//��ʼ����ע�����ͨ�����ŵ�
		CometContext.getInstance().registChannel("start");
		CometContext.getInstance().registChannel("message");
		System.out.println("comet4j��ʼ����������������");
		setFlag(true);
	}
	public void sendtoAll(String sendto){//��ͨ��channel�з�������ΪҪ���͵����ˣ�ǰ�λ���ݷ��͵������ж��Լ��ǲ��ǰ����ڷ��͵��˵��б��У��Ա�����Ƿ���ܸ���Ϣ
		CometEngine engine = CometContext.getInstance().getEngine();
		engine.sendToAll(channel , sendto);
	}
	public void sendmessage(String message){//��channel1ͨ���з�����Ϣ�������ڸ�ͨ���е��û��ͻ��յ�����Ϣ
		CometEngine engine = CometContext.getInstance().getEngine();
		engine.sendToAll(channel1 , message);
		
	}
	
}
