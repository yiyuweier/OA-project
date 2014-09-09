package com.oa.action.andirod.flow;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.history.HistoryActivityInstance;
import org.jbpm.api.model.ActivityCoordinates;
import org.springframework.context.ApplicationContext;

import com.oa.dao.FlowProcessDao;
import com.oa.jbpm.operation.BaseJbpmOperation;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.inject.Context;

import freemarker.template.Configuration;

public class workplaceShowFlowImageServlet {
	
//	private String flowProcessId;
	
//	public HttpServletResponse response = ServletActionContext.getResponse();
    
//    public OutputStream out = null;
	
	@Resource
	BaseJbpmOperation basejbpmoperation;
	@Resource
	FlowProcessDao flowprocessdao;

//	public String getFlowProcessId() {
//		return flowProcessId;
//	}
//
//	public void setFlowProcessId(String flowProcessId) {
//		this.flowProcessId = flowProcessId;
//	}
	
	private String flowname;
	
	public String getFlowname() {
		return flowname;
	}
	public void setFlowname(String flowname) {
		this.flowname = flowname;
	}
	/**
	 * �鿴ȫ�������̵�ͼƬ��������ʾ����ִ�еĲ���
	 * @return
	 */
	public String viewAllImage(){
//		System.out.println("flowProcessId="+flowProcessId);
//		response.setCharacterEncoding("utf-8"); 
//	    response.setContentType("image/png"); 
//		RepositoryService repositoryService = basejbpmoperation.getRepositoryService();
//		System.out.println("processid="+flowProcessId);
//		String imageName = flowprocessdao.getF_keyByF_id(flowProcessId).getF_key()+".png";
//		InputStream in = repositoryService.getResourceAsStream(flowProcessId,imageName);
//		byte[] buf = new byte[1024];
//		try {
//			out = response.getOutputStream();
//			int len;
//			while((len = in.read(buf)) != -1){
//				out.write(buf, 0, len);
//			}
//			out.flush(); 
//            out.close(); 
////            response.flushBuffer();
//            
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return "success";
		
		
		return "success";
		
	}
	/**
	 * �鿴����ִ�е��Ǹ��ط���ͼƬ
	 * @return
	 */
	public String viewImage(){
		//�鿴����ִ�е��Ǹ��ط���ͼƬ
//		System.out.println("flowProcessId="+flowProcessId);//
//		response.setCharacterEncoding("utf-8"); 
//	    response.setContentType("image/png");
//	    ProcessInstance processInstance = basejbpmoperation.getExecutionService().findProcessInstanceById(flowProcessId);//��������ʵ��id��ȡ����ʵ��
//	    String processDefid = processInstance.getProcessDefinitionId();
//	    System.out.println("���̶���id="+processDefid);
//		RepositoryService repositoryService = basejbpmoperation.getRepositoryService();
//		System.out.println("����ʵ����id="+processInstance.getId());
//		String processDefinitionid = processDefid.substring(processDefid.indexOf("-")+1,processDefid.length());//��ȡ���̶���id
//		System.out.println("���������̶���id="+processDefinitionid);
//		String imageName = flowprocessdao.getF_keyByF_id(processDefinitionid).getF_key()+".png";
//		InputStream in = repositoryService.getResourceAsStream(processDefinitionid,imageName);
//		byte[] buf = new byte[1024];
//		try {
//			out = response.getOutputStream();
//			int len;
//			while((len = in.read(buf)) != -1){
//				out.write(buf, 0, len);
//			}
//			out.flush(); 
//            out.close(); 
////            response.flushBuffer();
//            
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		
//		//��ʷ��¼
//		List<HistoryActivityInstance> HistoryActivityInstanceList = basejbpmoperation.getHistoryActivityInstanceList(flowProcessId);
////		HistoryActivityInstance history;
////		for(HistoryActivityInstance history : HistoryActivityInstanceList){
////			System.out.println(history.getActivityName());
////		}
//		ActionContext.getContext().getSession().put("HistoryActivityInstanceList", HistoryActivityInstanceList);
//		
//		return "success";
		
		return "";
	}

}
