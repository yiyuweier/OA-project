package com.oa.action.andirod.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.jbpm.api.task.Task;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.oa.action.andirod.servlet.OaServlet;
import com.oa.action.util.ComparatorTask;
import com.oa.bean.MyFlow;
import com.oa.bean.User;
import com.oa.jbpm.operation.BaseJbpmOperation;
import com.oa.service.impl.UserService;

@Controller
@Scope("prototype")
/**
 * 获取待办任务
 * Dbrw：手机端获取待办任务，传入数据：userid
 * AllDbrw：电脑端获取代办任务，传入数据：userid
 */
public class DashboardDbrwServlet extends OaServlet{

	private String userid;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	@Resource
	BaseJbpmOperation basejbpmoperation;
	@Resource
	UserService userService;

	
	JSONArray taskJsonArray = new JSONArray();
	JSONArray allUserListJsonArray = new JSONArray();
	public String Dbrw() throws IOException{//手机端
		
		//**********************获取待办任务******************************************
		List<Task> tasks = basejbpmoperation.getTask(userid);
		List<Task> groupsTaskList = basejbpmoperation.getTaskService().findGroupTasks(userid);
		tasks.addAll(groupsTaskList);
		//***********************************************************************
		JSONObject jsonTaskObject = new JSONObject();
		Collections.sort(tasks, new ComparatorTask<Task>());
		for(Task task : tasks){
			jsonTaskObject.put("TaskName", task.getName().toString());
			jsonTaskObject.put("TaskCreateTime", task.getCreateTime().toString());
			String executionid = task.getExecutionId()==null?(""):(task.getExecutionId());
			jsonTaskObject.put("url", "JbpmServlet_JbpmGetDetailTask?executionId="+executionid+"&taskId="+task.getId()+"&userid="+userid);//Jbpm_getDetailTask?executionId=%{task.getExecutionId()}&taskId=%{task.getId()}&userid=%{#session.user.getId()}
			jsonTaskObject.put("TaskExecutionId", task.getExecutionId()==null?"Document":task.getExecutionId());
			taskJsonArray.add(jsonTaskObject);
		}
		System.out.println("{\"total\":"+tasks.size()+",\"rows\":"+taskJsonArray.toString()+"}");
		outprint("{\"total\":"+tasks.size()+",\"rows\":"+taskJsonArray.toString()+"}");
		return null;
	}
	
	public String AllDbrw() throws IOException{//电脑端
				
		List<Task> tasks = basejbpmoperation.getTask(userid);
		List<Task> groupsTaskList = basejbpmoperation.getTaskService().findGroupTasks(userid);
		tasks.addAll(groupsTaskList);
		
		JSONObject jsonTaskObject = new JSONObject();
		Collections.sort(tasks, new ComparatorTask<Task>());
		
		List<Task> result = new ArrayList<Task>();
		for(int i=0;i<(tasks.size()>8?8:tasks.size());i++){
			if(tasks.get(i)!=null){
				result.add(tasks.get(i));
			}
		}
		
		
		for(Task task : result){
//			String taskname = basejbpmoperation.getTaskVariable(task.getExecutionId(), "D_name");
//			if(taskname==null||taskname==""){
				jsonTaskObject.put("TaskName", task.getName().toString());
//			}else{
//				jsonTaskObject.put("TaskName", taskname);
//			}
			
			jsonTaskObject.put("TaskCreateTime", task.getCreateTime().toString());
			String executionid = task.getExecutionId()==null?(""):(task.getExecutionId());
			jsonTaskObject.put("url", "Jbpm_getDetailTask?executionId="+executionid+"&taskId="+task.getId()+"&userid="+userid);//Jbpm_getDetailTask?executionId=%{task.getExecutionId()}&taskId=%{task.getId()}&userid=%{#session.user.getId()}
			jsonTaskObject.put("TaskExecutionId", task.getExecutionId());		
			taskJsonArray.add(jsonTaskObject);
		}
		System.out.println("{\"total\":"+tasks.size()+",\"rows\":"+taskJsonArray.toString()+"}");
		outprint("{\"total\":"+tasks.size()+",\"rows\":"+taskJsonArray.toString()+"}");
		return null;
	}
}
