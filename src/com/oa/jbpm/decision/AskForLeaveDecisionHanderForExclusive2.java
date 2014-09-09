package com.oa.jbpm.decision;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

public class AskForLeaveDecisionHanderForExclusive2 implements DecisionHandler {

	
	public String decide(OpenExecution execution) {
		
		String content = (String) execution.getVariable("content");
		if(content.equals("����")){
			return "to ����";
		}else if(content.equals("��������")){
			return "to ��������";
		}else{
			return null;
		}
		
		
	}

}
