package com.oa.action.jbpm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.validator.util.GetClassLoader;
import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.task.TaskImpl;

import com.oa.bean.Announcements;
import com.oa.bean.Document;
import com.oa.bean.Message;
import com.oa.bean.User;
import com.oa.bean.Vacate;
import com.oa.comet4j.Comet4j;
import com.oa.dao.AnnouncementsDao;
import com.oa.dao.DocumentDao;
import com.oa.dao.MessageDao;
import com.oa.dao.UserDao;
import com.oa.dao.VacateDao;
import com.oa.jbpm.operation.BaseJbpmOperation;
import com.oa.service.impl.UserService;
import com.opensymphony.xwork2.ActionContext;

public class Jbpm_action {
	
	private String executionId;
	private String taskId;//��Ӧ������id
	
	private String execution_id;
	
	private boolean state;//������Ϣ�Ƿ����Ѷ�
	
	private String userid;
	
	private String ISPASS;//�Ƿ�ͨ����ˣ����У����У�
	
	private String suggestion;//��˵����
	
	private String D_sendto;//���ͨ���󣬰칫��Ҫ���ļ����͸�����
	
	
	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	

	public String getExecution_id() {
		return execution_id;
	}

	public void setExecution_id(String execution_id) {
		this.execution_id = execution_id;
	}


	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
	
	
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getISPASS() {
		return ISPASS;
	}

	public void setISPASS(String iSPASS) {
		ISPASS = iSPASS;
	}
	
	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
	
	public String getD_sendto() {
		return D_sendto;
	}

	public void setD_sendto(String d_sendto) {
		D_sendto = d_sendto;
	}







	@Resource
	BaseJbpmOperation basejbpmoperation;
	@Resource
	MessageDao messagedao;
	@Resource
	UserService userService;
	@Resource
	DocumentDao documentdao;
	@Resource
	VacateDao vacatedao;
	@Resource
	AnnouncementsDao announcementdao;
	
	public String getAllTaskByUserName(){
		
		User user = (User)ActionContext.getContext().getSession().get("user");
		List<Task> tasks = basejbpmoperation.getTask(user.getId().toString());
		List<Task> groupsTaskList = basejbpmoperation.getTaskService().findGroupTasks(user.getId().toString());
		tasks.addAll(groupsTaskList);

		ActionContext.getContext().getSession().put("taskslist", tasks);
		return "checkalltasks";
	}
	
	/**
	 * ����ʱͨ��jbpm�õ�����Ϣ����ϸ��Ϣ
	 * @return
	 */
	public String getDetailTask(){
		String activityName = basejbpmoperation.getTaskService().getTask(taskId).getActivityName();//��ȡ��ǰ����ִ�е����������
		String type = null;//������Ǹ������������ʲô�������or֪ͨ����or��������
		User user = userService.findById(Long.parseLong(userid));
		String username = user.getU_name();
		System.out.println("�鿴��ϸ���ݵ���Ϊ="+username);
		System.out.println("��ȡ��ϸ������Ϣ��executionId="+executionId);
		String message_execution_id = executionId;
		if(executionId!=null&&!executionId.equals("")){
			type = executionId.substring(0,executionId.indexOf("."));
			System.out.println(type);
		}else{
			type = "other";
		}
		if(type.equals("Message")){
			if(state==false){//δ��
				String MessageName = basejbpmoperation.getInsatanceVariable(executionId, "messagename");//��ȡ������ʵ���ı���
				String Message = basejbpmoperation.getInsatanceVariable(executionId, "message");
				String M_FJUrl = basejbpmoperation.getInsatanceVariable(executionId, "M_FJUrl");
				String M_FJName = basejbpmoperation.getInsatanceVariable(executionId, "M_FJName");
				
				ActionContext.getContext().getSession().put("MessageName", MessageName);
				ActionContext.getContext().getSession().put("Message", Message);
				ActionContext.getContext().getSession().put("taskid", taskId);
				ActionContext.getContext().getSession().put("executionId", executionId);
				ActionContext.getContext().getSession().put("state", state);
				ActionContext.getContext().getSession().put("M_FJUrl", M_FJUrl);
				ActionContext.getContext().getSession().put("M_FJName", M_FJName);
			}else{//�Ѷ����Ѷ���ʾ�������ѽ�����ˣ������̽����ˣ������ٻ�ȡ�������еı����ˣ�����ͨ��message���л�ȡ�ü�¼��
				Message message = messagedao.findByExecution_id(message_execution_id,userid);
				
				ActionContext.getContext().getSession().put("MessageName", message.getM_name());
				ActionContext.getContext().getSession().put("Message", message.getM_content());
				ActionContext.getContext().getSession().put("executionId", message.getExecution_id());
				ActionContext.getContext().getSession().put("state", state);
				ActionContext.getContext().getSession().put("M_FJUrl", message.getM_FJUrl());
				ActionContext.getContext().getSession().put("M_FJName", message.getM_FJName());
			}
			return "detailtask_forMessage";
		}else if(type.equals("AskForLeave")){
			String taskname = basejbpmoperation.getTaskService().getTask(taskId).getActivityName();
			System.out.println("��ǰ���������="+taskname);
			if(taskname.equals("���ûͨ��")){
				System.out.println(taskId);
				ActionContext.getContext().getSession().put("taskid", taskId);
				return "detailtask_forNotAskForLeave";
			}else if(taskname.equals("����")){
				System.out.println(taskId);
				ActionContext.getContext().getSession().put("taskid", taskId);
				return "cancel_AskForLeave";
			}else{
			//-----------------------------------------���---------------------------------------------------------
				String AskForLeave_type = basejbpmoperation.getInsatanceVariable(executionId, "type");
				String AskForLeave_reason = basejbpmoperation.getInsatanceVariable(executionId, "reason");
				String AskForLeave_begin_time = basejbpmoperation.getInsatanceVariable(executionId, "begintime");
				String AskForLeave_time = basejbpmoperation.getInsatanceVariable(executionId, "time");
				String beginUserName = basejbpmoperation.getInsatanceVariable(executionId, "begin_user");
				System.out.println("�������Ϊ="+AskForLeave_type);
				switch(Integer.parseInt(AskForLeave_type)){
				case 1:
					AskForLeave_type = "���";
					break;
				case 2:
					AskForLeave_type = "����";
					break;
				case 3:
					AskForLeave_type = "����";
					break;
				case 4:
					AskForLeave_type = "����";
					break;
				}
				ActionContext.getContext().getSession().put("AskForLeave_type", AskForLeave_type);
				ActionContext.getContext().getSession().put("AskForLeave_reason", AskForLeave_reason);
				ActionContext.getContext().getSession().put("AskForLeave_begin_time", AskForLeave_begin_time);
				ActionContext.getContext().getSession().put("AskForLeave_time", AskForLeave_time);
				ActionContext.getContext().getSession().put("beginUserName", beginUserName);
				ActionContext.getContext().getSession().put("taskid", taskId);
				ActionContext.getContext().getSession().put("executionId", executionId);
				return "detailtask_forAskForLeave";
			}
		}else if((type.equals("Document")||type.equals("DocumentSerial"))&&activityName.equals("����ûͨ��")){//�鿴�����
			Document document = documentdao.getDocumentByBeginuserNameAndIspass(userid,executionId);
			
			ActionContext.getContext().getSession().put("D_suggestion", document.getD_suggestion());
			ActionContext.getContext().getSession().put("ispass", document.isIspass());
			ActionContext.getContext().getSession().put("Document_name", document.getD_name());
			ActionContext.getContext().getSession().put("Document_content", document.getD_content());
			ActionContext.getContext().getSession().put("D_sendto", document.getSendto());
			ActionContext.getContext().getSession().put("D_type", document.getType());
			ActionContext.getContext().getSession().put("D_taskid", taskId);
			ActionContext.getContext().getSession().put("executionId", executionId);
			ActionContext.getContext().getSession().put("D_FJUrl", document.getD_FJUrl());
			ActionContext.getContext().getSession().put("D_FJName", document.getD_FJName());
			return "result_forDocument";
		}else{//�鿴������ϸ��Ϣ:�����ļ������ļ����ݵ�
			if(activityName.equals("�칫��")){
				String Document_name = basejbpmoperation.getTaskVariable(taskId, "D_name");
				String Document_content = basejbpmoperation.getTaskVariable(taskId, "D_content");
				String D_FJUrl = basejbpmoperation.getTaskVariable(taskId, "D_FJUrl");
				String D_FJName = basejbpmoperation.getTaskVariable(taskId, "D_FJName");
				System.out.println(Document_name+":"+Document_content);
				ActionContext.getContext().getSession().put("Document_name", Document_name);
				ActionContext.getContext().getSession().put("Document_content", Document_content);
				ActionContext.getContext().getSession().put("taskid", taskId);
				ActionContext.getContext().getSession().put("executionId", executionId);
				ActionContext.getContext().getSession().put("D_FJUrl", D_FJUrl);
				ActionContext.getContext().getSession().put("D_FJName", D_FJName);
				System.out.println("gothought_forDocument");
				return "gothought_forDocument";
			}else if(activityName.equals("�·��ļ�")){
				String Document_name = basejbpmoperation.getTaskVariable(taskId, "D_name");
				String Document_content = basejbpmoperation.getTaskVariable(taskId, "D_content");
				String D_FJUrl = basejbpmoperation.getTaskVariable(taskId, "D_FJUrl");
				String D_FJName = basejbpmoperation.getTaskVariable(taskId, "D_FJName");
				System.out.println(Document_name+":"+Document_content);
				ActionContext.getContext().getSession().put("Document_name", Document_name);
				ActionContext.getContext().getSession().put("Document_content", Document_content);
				ActionContext.getContext().getSession().put("taskid", taskId);
				ActionContext.getContext().getSession().put("executionId", executionId);
				ActionContext.getContext().getSession().put("D_FJUrl", D_FJUrl);
				ActionContext.getContext().getSession().put("D_FJName", D_FJName);
				System.out.println("issued_document");
				
				Document mydocument = documentdao.getDocumentByTaskId(taskId, userid);
				
				mydocument.setIspass(1);
				documentdao.save(mydocument);
				basejbpmoperation.completeTask(taskId);
				return "issued_document";
			}else{
				String Document_name = basejbpmoperation.getTaskVariable(taskId, "D_name");
				String Document_content = basejbpmoperation.getTaskVariable(taskId, "D_content");
				String D_FJUrl = basejbpmoperation.getTaskVariable(taskId, "D_FJUrl");
				String D_FJName = basejbpmoperation.getTaskVariable(taskId, "D_FJName");
				System.out.println(Document_name+":"+Document_content);
				ActionContext.getContext().getSession().put("Document_name", Document_name);
				ActionContext.getContext().getSession().put("Document_content", Document_content);
				ActionContext.getContext().getSession().put("taskid", taskId);
				ActionContext.getContext().getSession().put("executionId", executionId);
				ActionContext.getContext().getSession().put("D_FJUrl", D_FJUrl);
				ActionContext.getContext().getSession().put("D_FJName", D_FJName);
				return "detailtask_forDocument";
			}
			
		}
			//--------------------------------------------------------------------------------------------------------------------------
		
	}
	
	@SuppressWarnings("unused")
	public String Taskgothough(){
		
		String message_execution_id = executionId;
		String type = null;
		if(executionId!=null&&!executionId.equals("")){//����executionId���ж�����
			type = executionId.substring(0,executionId.indexOf("."));
		}else{
			type = "other";
		}
		System.out.println("������̵�����="+type);
		System.out.println("���̵�ǰtaskid="+taskId);
		if(type.equals("AskForLeave")){
			Vacate vacate = vacatedao.getVacateByExecutionid(executionId);
			if(ISPASS.equals("ͬ��")){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("content", "����");
				basejbpmoperation.setInstanceVariable(executionId, map);
				basejbpmoperation.completeTask(taskId);
				vacate.setIspass(1);
				vacatedao.save(vacate);
			}else{
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("content", "�鿴���");
				basejbpmoperation.setInstanceVariable(executionId, map);
				basejbpmoperation.completeTask(taskId);
				vacate.setIspass(0);
				vacatedao.save(vacate);
			}
			
			String initiator = vacate.getInitiator();
			
			//******************************************************************************************
			//����������Ϣ
			Comet4j comet4j = new Comet4j();
			comet4j.sendtoAll(initiator);
			//******************************************************************************************
			
		}else if(type.equals("Message")){
			User user = userService.findById(Long.parseLong(userid));
			String username = user.getU_name();
			System.out.println("�鿴��ϸ���ݵ���Ϊ="+username);
			System.out.println("��������ǵ�executionId="+executionId);
			
			String executionid = null;
			int firstindex = executionId.indexOf(".");
			System.out.println(firstindex);
			int index = executionId.indexOf(".", firstindex+1);
			if(index==-1){
				System.out.println(index);
				executionid = message_execution_id;
			}else{
				System.out.println(index);
				executionid = executionId.substring(0,index);
			}
			System.out.println("Message��execution_id="+executionid);
			System.out.println("������Ϣ����Ϊ="+username);
			Message message = messagedao.findByExecution_id(executionid,userid);
			message.setState(true);
//			taskId = message.getTask_id();
			basejbpmoperation.completeTask(taskId);
			messagedao.update(message);
		}else{
			TaskImpl subtask = (TaskImpl)basejbpmoperation.getTaskService().getTask(taskId);
			Task supertask = subtask.getSuperTask();
			System.out.println("executionId="+executionId);
			if(supertask!=null){//����
				System.out.println("supertaskid="+supertask.getId());
				User user = userService.findById(Long.parseLong(userid));
//				String username = user.getU_name();
				if(ISPASS!=null&&ISPASS.equals("��ͬ��")){
					
					Document document = documentdao.getDocumentByTaskId(subtask.getId(),userid);
		            document.setIspass(0);
//		            document.setType(1);
		            document.setD_suggestion(suggestion);
		            documentdao.save(document);
					
		            basejbpmoperation.getTaskService().completeTask(subtask.getId());  
		            basejbpmoperation.getTaskService().completeTask(supertask.getId(),"��ͬ��");
		            
		          //******************************************************************************************
		        	String initiator = document.getInitiator();
		    		//����������Ϣ
		    		Comet4j comet4j = new Comet4j();
		    		comet4j.sendtoAll(initiator);
		    		//******************************************************************************************
		            
		            
		            
//		            return "Notgothoughsuccessful";  
		        }else{  
//		        	Document document = documentdao.getDocumentByTaskId(subtask.getId(),username);
		        	
		        	Document document = documentdao.getDocumentByTaskId(subtask.getId(),userid);
		        	
			        document.setIspass(1);
			        documentdao.save(document);
					basejbpmoperation.getTaskService().completeTask(subtask.getId());  
			        if(basejbpmoperation.getTaskService().getSubTasks(supertask.getId()).size()==0){  
			        	basejbpmoperation.getTaskService().completeTask(supertask.getId(),"ͬ��");
			        
			        	
	//		            return "gothoughsuccessful";  
			        } 
		        }
				System.out.println("�Ƿ�ͨ��"+ISPASS);
//				return "gothoughsuccessful"; 
			}else if(supertask==null){//����
				System.out.println("���е��������"+taskId+"------------------------------------------------------------");
				
				
				Document document = new Document();
				String D_name = basejbpmoperation.getInsatanceVariable(executionId, "D_name");
				String D_content = basejbpmoperation.getInsatanceVariable(executionId, "D_content");
				String begin_user = basejbpmoperation.getInsatanceVariable(executionId, "begin_user");
				Date time = new Date();
				SimpleDateFormat myfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				document.setD_name(D_name);
				document.setD_content(D_content);
				document.setD_date(myfmt.format(time).toString());
				String username = userService.findById(Long.parseLong(userid)).getU_name();
				document.setSendto(userid);
				document.setSendtoName(username);
				document.setInitiator(begin_user);
				document.setD_taskid(taskId);
				document.setD_executionid(executionId);
				document.setType(1);
				System.out.println("*******************************************************************");
				System.out.println("D_name="+D_name);
				System.out.println("D_content="+D_content);
				System.out.println("begin_user="+begin_user);
				System.out.println("username="+username);
				System.out.println("*******************************************************************");
				
				
				
				if(ISPASS!=null&&ISPASS.equals("��ͬ��")){
					
					System.out.println("���������="+suggestion);
					
					document.setD_suggestion(suggestion);
					
					System.out.println("�����Ľ��Ϊ="+ISPASS);
					
					document.setIspass(0);
					
					documentdao.save(document);
					List<String> nulllist = new ArrayList<String>();
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("ispass", "��ͬ��");
					map.put("participants", nulllist);
					basejbpmoperation.setInstanceVariable(executionId, map);
					basejbpmoperation.completeTask(taskId);
					System.out.println("�������"+"���Ϊ="+ISPASS);
					
					//******************************************************************************************
		        	String initiator = document.getInitiator();
		    		//����������Ϣ
		    		Comet4j comet4j = new Comet4j();
		    		comet4j.sendtoAll(initiator);
		    		//******************************************************************************************
					
//					return "gothoughsuccessful"; 
				}else{
					document.setIspass(1);
					
					documentdao.save(document);
					
					System.out.println("�����Ľ��Ϊ="+ISPASS);
					List<String> participants = (List<String>) basejbpmoperation.getTaskService().getVariable(taskId, "participants");
					for(String participant : participants){
						System.out.println("������������Ա��="+participant);
					}
					System.out.println("executionId="+executionId);
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("ispass", "ͬ��");
					map.put("participants", participants);
					basejbpmoperation.setInstanceVariable(executionId, map);
					basejbpmoperation.completeTask(taskId);
					System.out.println("�������"+"���Ϊ="+ISPASS);
//					return "gothoughsuccessful"; 
				}
//				return "gothoughsuccessful"; 
			}
		}
		
		
		return "gothoughsuccessful";
	}
	
	public String issued(){//�·�����
		
		List<String> participants = new ArrayList<String>();
		System.out.println(D_sendto);
		String[] participantsArray;//��������֪ͨ����Ҫ�������˵��б�
		String[] participantsArrayId;//----��������֪ͨ����Ҫ�������˵�id���б�
		String[] participantsArrayTemp;//---��������֪ͨ����Ҫ�������˵�id:username��ֵ
		if(D_sendto.equals("<����˴���ӷ��Ͷ���>")){
			List<User> userlist = userService.findAll();//��ȡ���е���
			participantsArrayId = new String[userlist.size()];//��ʼ��
			int i = 0;
			for(User user : userlist){
				participantsArrayId[i] = user.getId().toString();//-----

				i = i+1;
			}
			
		}else{
			participantsArray = D_sendto.split(",");//��ȡ���е�Ҫ�����˵�����
			participantsArrayId = new String[participantsArray.length];//---
			int i = 0;//----
			for(String userString : participantsArray){//----
				participantsArrayTemp = userString.split(":");//---
				participantsArrayId[i] = participantsArrayTemp[0];//-----
			}//-----
		}
		
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("issued_participants", participantsArrayId);
		basejbpmoperation.setInstanceVariable(executionId, map);
		basejbpmoperation.completeTask(taskId);
		
		String D_name = basejbpmoperation.getInsatanceVariable(executionId, "D_name");
		String D_content = basejbpmoperation.getInsatanceVariable(executionId, "D_content");
		
		Announcements announcement = new Announcements();
		announcement.setA_name(D_name);
		announcement.setA_content(D_content);
		SimpleDateFormat myfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		announcement.setA_time(myfmt.format(date).toString());
		announcementdao.save(announcement);
		
		return "gothoughsuccessful";
	}
	
	public String viewSuggest(){
		
		User user = userService.findById(Long.parseLong(userid));
		String username = user.getU_name();
		Document document = documentdao.getDocumentByBeginuserNameAndIspass(userid,executionId);
		
		ActionContext.getContext().getSession().put("D_suggestion", document.getD_suggestion());
		ActionContext.getContext().getSession().put("ispass", document.isIspass());
		ActionContext.getContext().getSession().put("Document_name", document.getD_name());
		ActionContext.getContext().getSession().put("Document_content", document.getD_content());
		ActionContext.getContext().getSession().put("D_sendto", document.getSendtoName());
		ActionContext.getContext().getSession().put("D_type", document.getType());
		ActionContext.getContext().getSession().put("D_taskid", taskId);
		ActionContext.getContext().getSession().put("executionId", executionId);
		return "result_forsuggest";
	}
	

}
