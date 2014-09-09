package com.oa.bean;

public class Announcements {
	
	private Long id;
	private String A_name;//通知公告的名称
	private String A_content;//通知公告的内容
	private String A_time;//发起通知公告的时间
	private String A_FJUrl;//通知公告附件的地址
	private String A_FJName;//附件的文件名
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getA_name() {
		return A_name;
	}
	public void setA_name(String a_name) {
		A_name = a_name;
	}
	public String getA_content() {
		return A_content;
	}
	public void setA_content(String a_content) {
		A_content = a_content;
	}
	public String getA_time() {
		return A_time;
	}
	public void setA_time(String a_time) {
		A_time = a_time;
	}
	public String getA_FJUrl() {
		return A_FJUrl;
	}
	public void setA_FJUrl(String a_FJUrl) {
		A_FJUrl = a_FJUrl;
	}
	public String getA_FJName() {
		return A_FJName;
	}
	public void setA_FJName(String a_FJName) {
		A_FJName = a_FJName;
	}
	
	
	
	
	

}
