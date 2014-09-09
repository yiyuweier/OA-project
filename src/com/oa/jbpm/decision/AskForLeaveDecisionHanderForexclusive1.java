package com.oa.jbpm.decision;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;
import org.springframework.stereotype.Repository;

@Repository
public class AskForLeaveDecisionHanderForexclusive1 implements DecisionHandler {

	
	public String decide(OpenExecution execution) {
		
		String content = (String) execution.getVariable("content");
		if(content.equals("销假")){
			return "to 销假";
		}else if(content.equals("查看结果")){
			return "to 查看结果";
		}else{
			return null;
		}
		
		
	}

}
