package com.oa.action.andirod.flow;

import java.util.List;

import javax.annotation.Resource;

import org.jbpm.api.task.Task;
import org.springframework.stereotype.Repository;

import com.oa.bean.Message;
import com.oa.bean.User;
import com.oa.dao.MessageDao;
import com.oa.dao.UserDao;
import com.oa.jbpm.operation.BaseJbpmOperation;
import com.opensymphony.xwork2.ActionContext;

/**
 * 获取该用户userid的收件箱中所有的消息（已读的和未读的）
 * @author Administrator
 *
 */

@Repository
public class msg_info_listReceivedServlet {
	
	private String userid;//当前登录用户的id
	
	@Resource
	MessageDao messagedao;
	@Resource
	UserDao userdao;
	@Resource
	BaseJbpmOperation basejbpmoperation;
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String execute(){
//		List<Message> messagelist = messagedao.findAll();
		User user = (User) ActionContext.getContext().getSession().get("user");
		String username = user.getU_name();
		System.out.println(user.getU_name());
		
		
		List<Task> tasklist = basejbpmoperation.getTask(user.getU_name());
		for(Task task : tasklist){
			String executionid = null;
			String exevutionid = task.getExecutionId();
			String test_executionid = exevutionid;
			int firstindex = exevutionid.indexOf(".");
			System.out.println(firstindex);
			int index = exevutionid.indexOf(".", firstindex+1);
			System.out.println(index);
			if(index==-1){
				executionid = test_executionid;
			}else{
				executionid = exevutionid.substring(0,index);
			}
			
			System.out.println("收件箱中获得的task的id为="+executionid+"----------------------------");
			Message message = messagedao.findByExecution_id(executionid,username);
			System.out.println("taskid="+task.getId()+"--------------------------------------");
			if(message!=null){
				message.setTask_id(task.getId());
				messagedao.save(message);
			}
			
		}
		List<Task> toAllTasklist = basejbpmoperation.getTask("所有人");
		for(Task task : toAllTasklist){
			String exevutionid = task.getExecutionId();
			System.out.println("收件箱中获得的task的id为="+exevutionid+"----------------------------");
			Message message = messagedao.findByExecution_id(exevutionid,username);
			System.out.println("taskid="+task.getId()+"--------------------------------------");
			message.setTask_id(task.getId());
			messagedao.save(message);
		}
		
		List<Message> messagelist = messagedao.findBySendtoName(user.getU_name());//获取当前用户的消息列表
		List<Message> toAllList = messagedao.findBySendtoName("所有人");
		messagelist.addAll(toAllList);
		for(Message message : messagelist){
			System.out.println("消息的名称为："+message.getM_name());
		}
		ActionContext.getContext().getSession().put("messagelist", messagelist);
		return "success";
	}

}
