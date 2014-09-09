package com.oa.bean;

import org.springframework.stereotype.Component;

@Component
public class Message {
	
	private Long id;
	private String M_name;//通知公告名称
	private String M_content;//通知公告内容
	private String initiator;//通知公告的发起者
	private String sendto;//通知公告发送到的人
	private String sendtoName;//公文审批的审批人名字
	private String M_date;//发送通知公告的时间
	private String M_FJUrl;//通知公告附件的地址
	private String M_FJName;//附件的名称
	private boolean state;//通知公告的状态（是否已读）
	private String execution_id;//通知公告的流程的流程实例id
	private String task_id;//流程具体执行到每步的id
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getM_name() {
		return M_name;
	}
	public void setM_name(String m_name) {
		M_name = m_name;
	}
	public String getM_content() {
		return M_content;
	}
	public void setM_content(String m_content) {
		M_content = m_content;
	}
	public String getInitiator() {
		return initiator;
	}
	public void setInitiator(String initiator) {
		this.initiator = initiator;
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
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public String getExecution_id() {
		return execution_id;
	}
	public void setExecution_id(String execution_id) {
		this.execution_id = execution_id;
	}
	public String getTask_id() {
		return task_id;
	}
	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}
	public String getM_date() {
		return M_date;
	}
	public void setM_date(String m_date) {
		M_date = m_date;
	}
	public String getM_FJUrl() {
		return M_FJUrl;
	}
	public void setM_FJUrl(String m_FJUrl) {
		M_FJUrl = m_FJUrl;
	}
	public String getM_FJName() {
		return M_FJName;
	}
	public void setM_FJName(String m_FJName) {
		M_FJName = m_FJName;
	}
	
	
	
	
	
	

}
