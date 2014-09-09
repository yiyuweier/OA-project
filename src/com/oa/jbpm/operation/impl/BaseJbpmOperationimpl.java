package com.oa.jbpm.operation.impl;

import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.IdentityService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.history.HistoryActivityInstance;
import org.jbpm.api.history.HistoryActivityInstanceQuery;
import org.jbpm.api.task.Task;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oa.jbpm.operation.BaseJbpmOperation;

@Repository
@Transactional
public class BaseJbpmOperationimpl implements BaseJbpmOperation{

	@Resource
	protected ProcessEngine processEngine;
	@Resource
	protected RepositoryService repositoryService;
	@Resource
	protected ExecutionService executionService;
	@Resource
	protected TaskService taskService;
	@Resource
	protected HistoryService historyService;
	@Resource
	protected IdentityService identityService;
	
	
	
	
	public ProcessEngine getProcessEngine() {
		return processEngine;
	}


	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}


	public RepositoryService getRepositoryService() {
		return repositoryService;
	}


	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}


	public ExecutionService getExecutionService() {
		return executionService;
	}


	public void setExecutionService(ExecutionService executionService) {
		this.executionService = executionService;
	}


	public TaskService getTaskService() {
		return taskService;
	}


	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}


	public HistoryService getHistoryService() {
		return historyService;
	}


	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}


	public IdentityService getIdentityService() {
		return identityService;
	}


	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}


	public void deploy(String jpdl_xml_name) {//根据xml来部署流程
		repositoryService.createDeployment().addResourceFromClasspath(jpdl_xml_name).deploy();
	}

	
	public ProcessInstance createInstance(String InstanceKeyName,
			Map<String, Object> map) {//根据流程的名称来实例化流程，map为要传入该流程的参数列表
		return executionService.startProcessInstanceByKey(InstanceKeyName,map);
	}

	
	public List<Task> getTask(String assignee) {//根据taskid来获取任务
		return taskService.findPersonalTasks(assignee);
	}
	
	public Task getDocumentTask(String assignee){
		return taskService.findPersonalTasks(assignee).get(0);
	}

	
	public String getCurrectActivity(String currentInstanceId) {//获取当前流程实例currentInstanceId正在执行的部分名称
		return executionService.createProcessInstanceQuery().processInstanceId(currentInstanceId).uniqueResult().findActiveActivityNames().toString();
	}

	
	public void completeTask(String taskId) {//根据taskid来完成结束该taskid对应的任务
		taskService.completeTask(taskId);
	}

	
	public void setInstanceVariable(String executeId, Map<String, Object> map) {//给指定的流程实例executeId设置变量map
		executionService.setVariables(executeId, map);
		
	}


	public String deploy(ZipInputStream zin) {//根据流程打包的zip文件部署流程
		return repositoryService.createDeployment().addResourcesFromZipInputStream(zin).deploy();
	}
	
//	public void setInstanceVarialbe(String executeId,Map<String,Object> map){
//		executionService.setVariables(executeId, map);
//	}
	
	public String getInsatanceVariable(String executionId,String variableName){//获取流程实例为executionId的变量为variableName的值
		return (String) executionService.getVariable(executionId, variableName);
	}
	
	public String getTaskVariable(String taskId,String variableName){//根据流程任务taskid来获取变量的值
		return (String) taskService.getVariable(taskId, variableName);
	}
	
	public List<HistoryActivityInstance> getHistoryActivityInstanceList(String instanceId){
		return historyService.createHistoryActivityInstanceQuery()
				.processInstanceId(instanceId)
				.orderAsc(HistoryActivityInstanceQuery.PROPERTY_STARTTIME)
				.list();
	}


	
}
