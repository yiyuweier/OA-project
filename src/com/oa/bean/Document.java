package com.oa.bean;

public class Document {
	
	private Long id;
	private String D_name;//���������ļ���
	private String D_content;//���������ļ�����
	private String D_date;//��������ʱ��
	private String sendto;//����������������id
	private String sendtoName;//��������������������
	private String initiator;//���������ķ�����
	private String D_suggestion;//�����������������
	private String D_taskid;//����ù������������̵�id
	private String D_suptaskid;//���Ϊ������������Ϊ���в������̵ĸ����̣����ڲ�����������̨������һ������ʵ����Ȼ�����ɸ�����ʵ���������̣�
	private String D_executionid;
	private int ispass;//0��ʾ��ͬ�⣬1��ʾͬ�⣬2��Ĭ��ֵ����ʾ����û�в����ǩ
	private int type;//1��ʾ���У�2��ʾ����
	private String D_FJUrl;//��������������url
	private String D_FJName;//������������������
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getD_name() {
		return D_name;
	}
	public void setD_name(String d_name) {
		D_name = d_name;
	}
	public String getD_content() {
		return D_content;
	}
	public void setD_content(String d_content) {
		D_content = d_content;
	}
	public String getD_date() {
		return D_date;
	}
	public void setD_date(String d_date) {
		D_date = d_date;
	}
	public String getSendto() {
		return sendto;
	}
	public void setSendto(String sendto) {
		this.sendto = sendto;
	}
	public String getSendtoName() {
		return sendtoName;
	}
	public void setSendtoName(String sendtoName) {
		this.sendtoName = sendtoName;
	}
	public String getInitiator() {
		return initiator;
	}
	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}
	public String getD_suggestion() {
		return D_suggestion;
	}
	public void setD_suggestion(String d_suggestion) {
		D_suggestion = d_suggestion;
	}
	public String getD_taskid() {
		return D_taskid;
	}
	public void setD_taskid(String d_taskid) {
		D_taskid = d_taskid;
	}
	public String getD_suptaskid() {
		return D_suptaskid;
	}
	public void setD_suptaskid(String d_suptaskid) {
		D_suptaskid = d_suptaskid;
	}
	public String getD_executionid() {
		return D_executionid;
	}
	public void setD_executionid(String d_executionid) {
		D_executionid = d_executionid;
	}
	public int isIspass() {
		return ispass;
	}
	public void setIspass(int ispass) {
		this.ispass = ispass;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getD_FJUrl() {
		return D_FJUrl;
	}
	public void setD_FJUrl(String d_FJUrl) {
		D_FJUrl = d_FJUrl;
	}
	public String getD_FJName() {
		return D_FJName;
	}
	public void setD_FJName(String d_FJName) {
		D_FJName = d_FJName;
	}
	
	
	
	

}
