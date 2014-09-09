package com.oa.action.andirod.flow;


import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.oa.action.andirod.servlet.OaServlet;
import com.oa.bean.Department;
import com.oa.bean.FlowCategory;
import com.oa.bean.FlowProcess;
import com.oa.jbpm.operation.BaseJbpmOperation;
import com.oa.service.impl.DepartmentService;
import com.oa.service.impl.FlowCategoryService;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class workplaceNewFlowServlet extends OaServlet{
	
	private Long flowProcessId;
	private String url;
	
	private String taskId;//���û��ͨ����Ҫ���·����µ�������̣���Ҫ����һ��������̽���������Ҫ��һ�����̵�taskid
	
	private String ISPASS;//�Ƿ�ͬ������
	
	@Resource
	FlowCategoryService flowcategoryService;
	@Resource
	DepartmentService departmentService;
	@Resource
	BaseJbpmOperation basejbpmoperation;

	public Long getFlowProcessId() {
		return flowProcessId;
	}

	public void setFlowProcessId(Long flowProcessId) {
		this.flowProcessId = flowProcessId;
	}
	
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String AskForLeave() throws Exception{
		
		System.out.println(flowProcessId);
//		ActionContext.getContext().getSession().put("flowProcessId", flowProcessId);
		FlowCategory flowcategory = flowcategoryService.getFlowProcessByProcessId(flowProcessId);
		Set<FlowProcess> flowprocesslist = flowcategory.getFlowProcess();
		List<Department> departmentlist = departmentService.findAll();
		ActionContext.getContext().getSession().put("departmentlist", departmentlist);
		System.out.println(flowprocesslist.size());
		for(FlowProcess flowprocess : flowprocesslist){
			System.out.println(flowprocess.getF_url());
			this.setUrl(flowprocess.getF_url()+".jsp");
		}
//		System.out.println(flowprocess.getF_name());
//		System.out.println(flowprocess.getF_url());
		
		return "success";
	}
	/*
	 * ���û��ͨ������ʱ����ʾ�û������û��ͨ�����Ƿ����µ�������롱
	 */
	public String AskForLeaveAgain(){
		System.out.println("taskId="+taskId);
		basejbpmoperation.completeTask(taskId);
		
//		FlowCategory flowcategory = flowcategoryService.getFlowProcessByProcessId(flowProcessId);
//		Set<FlowProcess> flowprocesslist = flowcategory.getFlowProcess();
//		List<Department> departmentlist = departmentService.findAll();
//		ActionContext.getContext().getSession().put("departmentlist", departmentlist);
//		System.out.println(flowprocesslist.size());
//		for(FlowProcess flowprocess : flowprocesslist){
//			System.out.println(flowprocess.getF_url());
//			this.setUrl(flowprocess.getF_url()+".jsp");
//		}
//		System.out.println(flowprocess.getF_name());
//		System.out.println(flowprocess.getF_url());
		
		outprint("{\"success\":true}");
		
		return null;
		
	}
	/*
	 * ����
	 */
	public String cancelAskForLeave(){
		System.out.println(taskId);
		
		basejbpmoperation.completeTask(taskId);
		
		outprint("{\"success\":true}");
		return null;
		
	}
	
	public String Message(){
		
		JSONArray messageJsonArray = new JSONArray();
		JSONObject messageJsonObject = new JSONObject();
		
		System.out.println("����ʵ��id="+flowProcessId);
		FlowCategory flowcategory = flowcategoryService.getFlowProcessByProcessId(flowProcessId);
		Set<FlowProcess> flowprocesslist = flowcategory.getFlowProcess();
		
//		ActionContext.getContext().getSession().put("departmentlist", departmentlist);
//		System.out.println(flowprocesslist.size());
		for(FlowProcess flowprocess : flowprocesslist){
			messageJsonObject.put("url", flowprocess.getF_url());
			messageJsonArray.add(messageJsonObject);
		}
		outprint(messageJsonArray.toString());
		return null;
	}
	
	public String Document(){
		System.out.println("����ʵ��id="+flowProcessId);
		FlowCategory flowcategory = flowcategoryService.getFlowProcessByProcessId(flowProcessId);
		Set<FlowProcess> flowprocesslist = flowcategory.getFlowProcess();
		System.out.println(flowprocesslist.size());
		for(FlowProcess flowprocess : flowprocesslist){
			System.out.println(flowprocess.getF_url());
			this.setUrl(flowprocess.getF_url()+".jsp");
		}
		return "success";

	}
	

}
