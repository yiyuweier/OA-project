package com.oa.jbpm.decision;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

public class AskForLeaveDecisionHanderForExclusive3 implements DecisionHandler {

	
	public String decide(OpenExecution execution) {
		
		String content = (String) execution.getVariable("content");
		if(content.equals("�����쵼��׼")){
			return "to �����쵼��׼";
		}else if(content.equals("end1")){
			return "to end1";
		}else{
			return null;
		}
		
		
	}

}
