package com.oa.bean;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

/**
 * �������
 * @author Administrator
 *
 */
@Component
public class FlowCategory {
	
	private Long id;//���̷���id
	private String F_name;//���̷�������
	
	private Set<FlowProcess> flowProcess = new HashSet<FlowProcess>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getF_name() {
		return F_name;
	}
	public void setF_name(String f_name) {
		F_name = f_name;
	}
	public Set<FlowProcess> getFlowProcess() {
		return flowProcess;
	}
	public void setFlowProcess(Set<FlowProcess> flowProcess) {
		this.flowProcess = flowProcess;
	}
	
	
	
	

}
