package com.oa.action.ajax;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.jbpm.api.task.Task;

import com.oa.bean.User;
import com.oa.jbpm.operation.BaseJbpmOperation;
import com.oa.service.impl.UserService;
import com.opensymphony.xwork2.ActionContext;

public class GetTaskNumber_action {
	
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

	public String execute(){
		User user = userService.findById(Long.parseLong(userid));
		List<Task> tasklist = basejbpmoperation.getTask(user.getU_name());
		List<Task> groupsTaskList = basejbpmoperation.getTaskService().findGroupTasks(userid);
		tasklist.addAll(groupsTaskList);
		HttpServletResponse response = ServletActionContext.getResponse(); 
        response.setCharacterEncoding("utf-8"); 
        response.setContentType("html/text"); 
        PrintWriter out = null; 
        String tasknumber;
        if(tasklist.size()==0){
			tasknumber = "0";
		}else{
			tasknumber = String.valueOf(tasklist.size());
		}
        try{ 
            out = response.getWriter(); 
            out.print(tasknumber); 
            System.out.println("用户"+user.getU_name()+"的任务数量为="+tasknumber);
            out.flush(); 
            out.close(); 
        }catch(Exception e){ 
            e.printStackTrace(); 
        } 
        return null;
	}

	
}
