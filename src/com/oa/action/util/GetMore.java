package com.oa.action.util;

import java.util.List;

import javax.annotation.Resource;

import org.jbpm.api.task.Task;

import com.oa.bean.Announcements;
import com.oa.bean.FlowCategory;
import com.oa.bean.User;
import com.oa.dao.AnnouncementsDao;
import com.oa.dao.FlowCategoryDao;
import com.oa.jbpm.operation.BaseJbpmOperation;
import com.opensymphony.xwork2.ActionContext;

public class GetMore {
	
	@Resource
	BaseJbpmOperation basejbpmoperation;
	@Resource
	FlowCategoryDao flowcategorydao;
	@Resource
	AnnouncementsDao announcementdao;
	
	public String dbrw(){//��ȡ���еĴ�������
		User user = (User)ActionContext.getContext().getSession().get("user");
		String username = user.getU_name();
		System.out.println(user.getU_name());
		List<Task> tasks = basejbpmoperation.getTask(username);
		List<Task> groupsTaskList = basejbpmoperation.getTaskService().findGroupTasks(user.getId().toString());
		tasks.addAll(groupsTaskList);
		ActionContext.getContext().getSession().put("taskslist", tasks);
		return "successfordbrw";
	}
	
	public String myflow(){//��ȡ���е��ҵ�����
		return "successformyflow";
	}
	
	public String flow(){//��ȡ���еĳ�������
		List<FlowCategory> result = flowcategorydao.findAll();
		ActionContext.getContext().getSession().put("Categorylist", result);
		return "successforflow";
	}
	
	public String message(){//��ȡ���е�֪ͨ����
		List<Announcements> announcementlist = announcementdao.findAll();
		ActionContext.getContext().getSession().put("announcementlist", announcementlist);
		return "successformessage";
	}

}
