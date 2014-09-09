package com.oa.action.andirod.flow;

import javax.annotation.Resource;

import com.oa.jbpm.operation.BaseJbpmOperation;
import com.opensymphony.xwork2.ActionContext;

public class DocumentForeditorServlet {
	private String Document_name;
	private String Document_content;
	private int Document_type;
	private Long D_taskid;
	public String getDocument_name() {
		return Document_name;
	}
	public void setDocument_name(String document_name) {
		Document_name = document_name;
	}
	public String getDocument_content() {
		return Document_content;
	}
	public void setDocument_content(String document_content) {
		Document_content = document_content;
	}
	public int getDocument_type() {
		return Document_type;
	}
	public void setDocument_type(int document_type) {
		Document_type = document_type;
	}
	
	public Long getD_taskid() {
		return D_taskid;
	}
	public void setD_taskid(Long d_taskid) {
		D_taskid = d_taskid;
	}
	
	@Resource
	BaseJbpmOperation basejbpmoperation;
	
	public String execute(){
		basejbpmoperation.getTaskService().completeTask(D_taskid.toString(), "to end1");
		System.out.println("taskid为="+D_taskid.toString()+"的任务已经完成");
		ActionContext.getContext().getSession().put("Document_name", Document_name);
		ActionContext.getContext().getSession().put("Document_content", Document_content);
		ActionContext.getContext().getSession().put("Document_type", Document_type);
		ActionContext.getContext().getSession().put("D_taskid", D_taskid);
		return "success";
	}

}
