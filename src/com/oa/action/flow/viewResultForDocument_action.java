package com.oa.action.flow;

import javax.annotation.Resource;

import com.oa.jbpm.operation.BaseJbpmOperation;

public class viewResultForDocument_action {
	
	private String D_taskid;

	public String getD_taskid() {
		return D_taskid;
	}

	public void setD_taskid(String d_taskid) {
		D_taskid = d_taskid;
	}
	
	@Resource
	BaseJbpmOperation basejbpmoperation;
	
	
	public String execute(){
		System.out.println("ÈÎÎñµÄtaskid="+D_taskid);
		basejbpmoperation.completeTask(D_taskid);
		return "success";
	}

}
