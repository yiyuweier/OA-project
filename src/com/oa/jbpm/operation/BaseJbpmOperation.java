package com.oa.jbpm.operation;

import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.IdentityService;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.history.HistoryActivityInstance;
import org.jbpm.api.task.Task;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseJbpmOperation {
	
	
	public RepositoryService getRepositoryService();


	public void setRepositoryService(RepositoryService repositoryService);


	public ExecutionService getExecutionService() ;


	public void setExecutionService(ExecutionService executionService) ;


	public TaskService getTaskService();


	public void setTaskService(TaskService taskService); 
	
	public HistoryService getHistoryService();

	public void setHistoryService(HistoryService historyService);
	
	public IdentityService getIdentityService();
	
	public void setIdentityService(IdentityService identityService);
	/**
	 * 部署流程
	 * @param jpdl_xml_name 流程配置文件的名字
	 */
	void deploy(String jpdl_xml_name);
	
	/**
	 * 通过zip发布流程
	 */
	String deploy(ZipInputStream zin);
	/**
	 * 创建流程实例
	 * @param InstanceKeyName 实例流程的key的值
	 */
	ProcessInstance createInstance(String InstanceKeyName,Map<String,Object> map); 
	
	/**
	 * 获取相应人员的任务
	 * @param assignee assignee的值
	 * @return 相应人现在所对应的任务
	 */
	List<Task> getTask(String assignee); 
	
	/**
	 * 获取会签的任务
	 * @param assignee
	 * @return
	 */
	Task getDocumentTask(String assignee);
	
	/**
	 * 查询流程实例当前所在节点
	 * @param currentInstanceId 当前流程实例的ID
	 * @return 当前所在节点的名字
	 */
	String getCurrectActivity(String currentInstanceId);
	
	/**
	 * 完成任务
	 * @param taskId 任务ID
	 */
	void completeTask(String taskId);
	
	/**
	 * 设置流程实例变量
	 * @param executeId 流程实例ID
	 * @param map 变量的map
	 */
	void setInstanceVariable(String executeId, Map<String,Object> map);
	
	String getInsatanceVariable(String executionId,String variableName);
	
	public String getTaskVariable(String taskId,String variableName);
	
	/**
	 * 根据instanceid来获取当前流程的历史过程。
	 * @param instanceId
	 * @return
	 */
	public List<HistoryActivityInstance> getHistoryActivityInstanceList(String instanceId);
	
	
}
