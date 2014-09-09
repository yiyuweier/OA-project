package com.oa.jbpm.decision;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;

public class AskForLeaveDecisionHanderForExclusive2 implements DecisionHandler {

	
	public String decide(OpenExecution execution) {
		
		String content = (String) execution.getVariable("content");
		if(content.equals("销假")){
			return "to 销假";
		}else if(content.equals("调整申请")){
			return "to 调整申请";
		}else{
			return null;
		}
		
		
	}

}
