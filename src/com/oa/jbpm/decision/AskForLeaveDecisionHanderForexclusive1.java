package com.oa.jbpm.decision;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;
import org.springframework.stereotype.Repository;

@Repository
public class AskForLeaveDecisionHanderForexclusive1 implements DecisionHandler {

	
	public String decide(OpenExecution execution) {
		
		String content = (String) execution.getVariable("content");
		if(content.equals("����")){
			return "to ����";
		}else if(content.equals("�鿴���")){
			return "to �鿴���";
		}else{
			return null;
		}
		
		
	}

}
