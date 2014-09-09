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
	 * ��������
	 * @param jpdl_xml_name ���������ļ�������
	 */
	void deploy(String jpdl_xml_name);
	
	/**
	 * ͨ��zip��������
	 */
	String deploy(ZipInputStream zin);
	/**
	 * ��������ʵ��
	 * @param InstanceKeyName ʵ�����̵�key��ֵ
	 */
	ProcessInstance createInstance(String InstanceKeyName,Map<String,Object> map); 
	
	/**
	 * ��ȡ��Ӧ��Ա������
	 * @param assignee assignee��ֵ
	 * @return ��Ӧ����������Ӧ������
	 */
	List<Task> getTask(String assignee); 
	
	/**
	 * ��ȡ��ǩ������
	 * @param assignee
	 * @return
	 */
	Task getDocumentTask(String assignee);
	
	/**
	 * ��ѯ����ʵ����ǰ���ڽڵ�
	 * @param currentInstanceId ��ǰ����ʵ����ID
	 * @return ��ǰ���ڽڵ������
	 */
	String getCurrectActivity(String currentInstanceId);
	
	/**
	 * �������
	 * @param taskId ����ID
	 */
	void completeTask(String taskId);
	
	/**
	 * ��������ʵ������
	 * @param executeId ����ʵ��ID
	 * @param map ������map
	 */
	void setInstanceVariable(String executeId, Map<String,Object> map);
	
	String getInsatanceVariable(String executionId,String variableName);
	
	public String getTaskVariable(String taskId,String variableName);
	
	/**
	 * ����instanceid����ȡ��ǰ���̵���ʷ���̡�
	 * @param instanceId
	 * @return
	 */
	public List<HistoryActivityInstance> getHistoryActivityInstanceList(String instanceId);
	
	
}
