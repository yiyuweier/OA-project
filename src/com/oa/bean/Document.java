package com.oa.bean;

public class Document {
	
	private Long id;
	private String D_name;//公文审批文件名
	private String D_content;//公文审批文件内容
	private String D_date;//公文审批时间
	private String sendto;//公文审批的审批人id
	private String sendtoName;//公文审批的审批人名字
	private String initiator;//公文审批的发起者
	private String D_suggestion;//公文审批的审批意见
	private String D_taskid;//发起该公文审批的流程的id
	private String D_suptaskid;//如果为并行审批，则为所有并行流程的父流程（对于并行审批，后台先生成一个流程实例，然后生成给流程实例的子流程）
	private String D_executionid;
	private int ispass;//0表示不同意，1表示同意，2是默认值，表示该人没有参与会签
	private int type;//1表示串行，2表示并行
	private String D_FJUrl;//公文审批附件的url
	private String D_FJName;//公文审批附件的名字
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
