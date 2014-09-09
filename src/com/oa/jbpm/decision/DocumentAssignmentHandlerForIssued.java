package com.oa.jbpm.decision;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.jbpm.api.Configuration;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskService;
import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;
import org.jbpm.api.task.Participation;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.task.OpenTask;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa.bean.Document;
import com.oa.bean.User;
import com.oa.dao.DocumentDao;
import com.oa.dao.impl.DocumentDaoimpl;
import com.oa.jbpm.operation.BaseJbpmOperation;
import com.opensymphony.xwork2.ActionContext;
import com.oa.jbpm.decision.BeanFactoryHelper;

@Repository
public class DocumentAssignmentHandlerForIssued implements AssignmentHandler {


	BeanFactory factory = BeanFactoryHelper.getFactory();
	BaseJbpmOperation basejbpmoperation = (BaseJbpmOperation)factory.getBean("baseJbpmOperationimpl");
	DocumentDao documentdao = (DocumentDao)factory.getBean("documentDaoimpl");
	
	String[] participants;//参与者
	
	public String[] getParticipants() {
		return participants;
	}
	
	public void setParticipants(String[] participants) {
		this.participants = participants;
	}
	
//	@Resource
//	BaseJbpmOperation basejbpmoperation;
//	@Resource
//	DocumentDao documentdao;
	
//	ProcessEngine processEngine=Configuration.getProcessEngine();  
//    TaskService taskService=processEngine.getTaskService();  
//	TaskService taskService = basejbpmoperation.getTaskService();

	public void assign(Assignable assignable, OpenExecution execution) throws Exception {
		
//		if(basejbpmoperation==null){
//			System.out.println("basejbpmoperation is null");
//		}
		TaskService taskService = basejbpmoperation.getTaskService();
		
		String pid = execution.getProcessInstance().getId();
		
		String executionId = execution.getId();
		
		System.out.println("pid="+pid+"************************************************");
		System.out.println("executionId="+executionId+"********************************");
		
		Task task = taskService.createTaskQuery().processInstanceId(pid).activityName(execution.getName()).uniqueResult();
		
		
		participants  = (String[]) taskService.getVariable(task.getId(), "issued_participants");
		
		String Document_name = (String) taskService.getVariable(task.getId(), "D_name");
		
		String Document_content = (String) taskService.getVariable(task.getId(), "D_content");
//		String Document_content = (String) ActionContext.getContext().getSession().get("D_content");
		
		//String sendto = null;
		
		User user = (User) ActionContext.getContext().getSession().get("user");
		String beginuser = user.getU_name();
		
		if(participants!=null){
			for(String participant : participants){
//				Task subTask = ((OpenTask)task).createSubTask();
				TaskImpl t = (TaskImpl)task;   //此处必须将task强制类型转换为TaskImpl
				String activityName = t.getActivityName();
                TaskImpl subTask = t.createSubTask();
				subTask.setAssignee(participant);
				subTask.setName(Document_name);
				subTask.setActivityName(activityName);
				subTask.setExecution(execution);
				subTask.setVariable("name", Document_name);
				subTask.setVariable("content", Document_content);
				taskService.addTaskParticipatingUser(task.getId(), participant, Participation.CLIENT);
//				sendto = sendto + participant;
				
				
				Document document = new Document();
				document.setD_name(Document_name);
//				document.setD_content(Document_content);
				document.setSendto(participant);
				document.setInitiator(beginuser);
				Date time = new Date();
				document.setD_date(time.toString());
				document.setD_taskid(subTask.getId());
				document.setD_executionid(pid);
				document.setIspass(2);
				document.setType(3);
				documentdao.save(document);
			}
			
//			Document document = new Document();
//			document.setD_name(Document_name);
//			document.setD_content(Document_content);
//			document.setSendto(sendto);
//			document.setInitiator(beginuser);
//			Date time = new Date();
//			document.setD_date(time.toString());
//			document.setD_taskid(task.getId());
//			document.setD_executionid(pid);
//			documentdao.save(document);
		}

	}

}
