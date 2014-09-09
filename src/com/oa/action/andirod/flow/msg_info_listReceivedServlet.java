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
 * ��ȡ���û�userid���ռ��������е���Ϣ���Ѷ��ĺ�δ���ģ�
 * @author Administrator
 *
 */

@Repository
public class msg_info_listReceivedServlet {
	
	private String userid;//��ǰ��¼�û���id
	
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
			
			System.out.println("�ռ����л�õ�task��idΪ="+executionid+"----------------------------");
			Message message = messagedao.findByExecution_id(executionid,username);
			System.out.println("taskid="+task.getId()+"--------------------------------------");
			if(message!=null){
				message.setTask_id(task.getId());
				messagedao.save(message);
			}
			
		}
		List<Task> toAllTasklist = basejbpmoperation.getTask("������");
		for(Task task : toAllTasklist){
			String exevutionid = task.getExecutionId();
			System.out.println("�ռ����л�õ�task��idΪ="+exevutionid+"----------------------------");
			Message message = messagedao.findByExecution_id(exevutionid,username);
			System.out.println("taskid="+task.getId()+"--------------------------------------");
			message.setTask_id(task.getId());
			messagedao.save(message);
		}
		
		List<Message> messagelist = messagedao.findBySendtoName(user.getU_name());//��ȡ��ǰ�û�����Ϣ�б�
		List<Message> toAllList = messagedao.findBySendtoName("������");
		messagelist.addAll(toAllList);
		for(Message message : messagelist){
			System.out.println("��Ϣ������Ϊ��"+message.getM_name());
		}
		ActionContext.getContext().getSession().put("messagelist", messagelist);
		return "success";
	}

}
