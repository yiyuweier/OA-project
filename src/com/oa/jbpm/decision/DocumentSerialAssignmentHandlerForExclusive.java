package com.oa.jbpm.decision;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.jbpm.api.Configuration;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskService;
import org.jbpm.api.jpdl.DecisionHandler;
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
public class DocumentSerialAssignmentHandlerForExclusive implements DecisionHandler {
	
	BeanFactory factory = BeanFactoryHelper.getFactory();
//	BaseJbpmOperation basejbpmoperation = (BaseJbpmOperation)factory.getBean("baseJbpmOperationimpl");
	DocumentDao documentdao = (DocumentDao)factory.getBean("documentDaoimpl");
	
//	List<String> participants;//参与者
//	
//	public List<String> getParticipants() {
//		return participants;
//	}
//	
//	public void setParticipants(List<String> participants) {
//		this.participants = participants;
//	}

	public String decide(OpenExecution execution) {
		List<String> participants = (List<String>) execution.getVariable("participants");
		String content = (String) execution.getVariable("ispass");
		if(participants.size()!=0){
			participants.remove(0);
		}
		
		System.out.println("-------------------------------------------------------------");
		for(String participant : participants){
			System.out.println(participant);
		}
		System.out.println("-------------------------------------------------------------");
		execution.setVariable("participants", participants);
		if(participants.size()!=0&&content.equals("同意")){
			execution.setVariable("SerialPeople", participants.get(0));
			return "to 会签";
		}else if(participants.size()==0&&content.equals("同意")){
			return "to 执行";
			
		}else{
			return "to 没通过";
		}
		
	}


	

}
