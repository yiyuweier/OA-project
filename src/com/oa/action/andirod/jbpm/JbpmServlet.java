package com.oa.action.andirod.jbpm;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.validator.util.GetClassLoader;
import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.task.TaskImpl;

import com.oa.action.andirod.servlet.OaServlet;
import com.oa.action.util.ComparatorTask;
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

public class JbpmServlet extends OaServlet{
	
	private String executionId;
	private String taskId;//对应的任务id
	
	private String execution_id;
	
	private boolean state;//设置消息是否是已读
	
	private String userid;
	
	private String ISPASS;//是否通过审核（串行，并行）
	
	private String suggestion;//审核的意见
	
	private String D_sendto;//审核通过后，办公室要将文件发送给的人
	
	private String rows;
	private String page;
	
	
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
	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
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
	
	public String getAllTaskByUserName() throws IOException{//获取当前用户的所有的任务
		
		JSONArray alltaskJsonArray = new JSONArray();
		JSONObject alltaskJsonObject = new JSONObject();
		List<Task> result = new ArrayList<Task>();
		
		User user = (User)ActionContext.getContext().getSession().get("user");//获取当前用户的信息

		List<Task> tasks = basejbpmoperation.getTask(user.getId().toString());//获取当前用户的所有的任务
		List<Task> groupsTaskList = basejbpmoperation.getTaskService().findGroupTasks(user.getId().toString());//获取当前用户的组任务
		tasks.addAll(groupsTaskList);//将组任务添加到当前用户的任务列表中
		Collections.sort(tasks, new ComparatorTask<Task>());//对tasks集合进行排序，按照时间排序
		
		/*实现分页*/
		int rowsize = (Integer.parseInt(page)-1)*Integer.parseInt(rows);
		int row = Integer.parseInt(rows);
		for(int i=rowsize;i<((rowsize+row)>tasks.size()?tasks.size():row);i++){
			if(tasks.get(i)!=null){
				result.add(tasks.get(i));
			}
		}
		
		for(Task task : result){
			alltaskJsonObject.put("taskid", task.getId());
//			String taskname = basejbpmoperation.getTaskVariable(task.getId(), "messagename");
			String taskname = task.getName();
			if(taskname.equals("查看消息")){
				taskname = basejbpmoperation.getTaskVariable(task.getId(), "messagename");
			}
			if(taskname.equals("审批没通过")){
				taskname = basejbpmoperation.getTaskVariable(task.getId(), "D_name")+"--"+ taskname;
			}
			alltaskJsonObject.put("taskname", taskname);
			alltaskJsonObject.put("url", "Jbpm_getDetailTask?executionId="+(task.getExecutionId()==null?"":task.getExecutionId())+"&taskId="+task.getId()+"&userid="+user.getId());
			alltaskJsonObject.put("tasktime", task.getCreateTime().toString());
			alltaskJsonArray.add(alltaskJsonObject);
		}
		outprint("{\"success\":true,\"total\":"+tasks.size()+",\"rows\":"+alltaskJsonArray.toString()+"}");
		return null;
		
	}
	
	/**
	 * 审阅时通过jbpm得到的消息的详细信息
	 * @return
	 * @throws IOException 
	 */
	public String JbpmGetDetailTask() throws IOException{
		
		String activityName = basejbpmoperation.getTaskService().getTask(taskId).getActivityName();//获取当前流程编号为taskid的流程现在执行到哪一步了
		String type = null;
		User user = userService.findById(Long.parseLong(userid));//获取当前用户
		String username = user.getU_name();//获取当前用户的名字
		String message_execution_id = executionId;
		if(executionId!=null&&!executionId.equals("")){
			type = executionId.substring(0,executionId.indexOf("."));//获取当前流程的类型（是“通知公告”，“公文审批”，“请假流程”）
			System.out.println(type);
		}else{
			type = "other";
		}
		if(type.equals("Message")){//为通知公告流程
			JSONArray MessageJsonArray = null;
			if(state==false){
				String MessageName = basejbpmoperation.getInsatanceVariable(executionId, "messagename");
				String Message = basejbpmoperation.getInsatanceVariable(executionId, "message");
				String M_FJUrl = basejbpmoperation.getInsatanceVariable(executionId, "M_FJUrl");
				String M_FJName = basejbpmoperation.getInsatanceVariable(executionId, "M_FJName");
				MessageJsonArray = new JSONArray();
				JSONObject MessageJsonObject = new JSONObject();
				MessageJsonObject.put("MessageName", MessageName);
				MessageJsonObject.put("Message", Message);
				MessageJsonObject.put("taskid", taskId);
				MessageJsonObject.put("executionId", executionId);
				MessageJsonObject.put("state", state);
				MessageJsonObject.put("M_FJUrl", M_FJUrl);
				MessageJsonObject.put("M_FJName", M_FJName);
				MessageJsonArray.add(MessageJsonObject);
//				ActionContext.getContext().getSession().put("MessageName", MessageName);
//				ActionContext.getContext().getSession().put("Message", Message);
//				ActionContext.getContext().getSession().put("taskid", taskId);
//				ActionContext.getContext().getSession().put("executionId", executionId);
//				ActionContext.getContext().getSession().put("state", state);
			}else{
				Message message = messagedao.findByExecution_id(message_execution_id,userid);
				MessageJsonArray = new JSONArray();
				JSONObject MessageJsonObject = new JSONObject();
				MessageJsonObject.put("MessageName", message.getM_name());
				MessageJsonObject.put("Message", message.getM_content());
				MessageJsonObject.put("taskid", taskId);
				MessageJsonObject.put("executionId", executionId);
				MessageJsonObject.put("M_FJUrl", message.getM_FJUrl());
				MessageJsonObject.put("M_FJName", message.getM_FJName());
				MessageJsonObject.put("state", state);
				MessageJsonArray.add(MessageJsonObject);
			}
			outprint("{\"success\":true,\"data\":{\"url\":\"/jsp/jbpm/detailtask_forMessage.jsp\",\"message\":"+MessageJsonArray.toString()+"}}");
			return null;
		}else if(type.equals("AskForLeave")){//为请假的流程
			String taskname = basejbpmoperation.getTaskService().getTask(taskId).getActivityName();
			if(taskname.equals("请假没通过")){
				System.out.println(taskId);
//				ActionContext.getContext().getSession().put("taskid", taskId);
				JSONArray AskForLeaveJsonArray = new JSONArray();
				JSONObject AskForLeaveJsonArrayObject = new JSONObject();
				AskForLeaveJsonArrayObject.put("taskid", taskId);
				AskForLeaveJsonArray.add(AskForLeaveJsonArrayObject);
				outprint("{\"success\":true,\"data\":{\"url\":\"/jsp/jbpm//jsp/jbpm/detailtask_forNotAskForLeave.jsp\",\"content\":"+AskForLeaveJsonArray.toString()+"}}");
				return null;
			}else if(taskname.equals("销假")){
//				ActionContext.getContext().getSession().put("taskid", taskId);
				JSONArray AskForLeaveJsonArray = new JSONArray();
				JSONObject AskForLeaveJsonArrayObject = new JSONObject();
				AskForLeaveJsonArrayObject.put("taskid", taskId);
				AskForLeaveJsonArray.add(AskForLeaveJsonArrayObject);
				outprint("{\"success\":true,\"data\":{\"url\":\"/jsp/jbpm/cancel_AskForLeave.jsp\",\"content\":"+AskForLeaveJsonArray.toString()+"}}");
				return null;
			}else{
			//-----------------------------------------请假---------------------------------------------------------
				String AskForLeave_type = basejbpmoperation.getInsatanceVariable(executionId, "type");
				String AskForLeave_reason = basejbpmoperation.getInsatanceVariable(executionId, "reason");
				String AskForLeave_begin_time = basejbpmoperation.getInsatanceVariable(executionId, "begintime");
				String AskForLeave_time = basejbpmoperation.getInsatanceVariable(executionId, "time");
				String beginUserName = basejbpmoperation.getInsatanceVariable(executionId, "begin_user");
				
				String beginusername = userService.findById(Long.parseLong(beginUserName)).getU_name();
				switch(Integer.parseInt(AskForLeave_type)){
				case 1:
					AskForLeave_type = "婚假";
					break;
				case 2:
					AskForLeave_type = "产假";
					break;
				case 3:
					AskForLeave_type = "病假";
					break;
				case 4:
					AskForLeave_type = "倒休";
					break;
				}
//				ActionContext.getContext().getSession().put("AskForLeave_type", AskForLeave_type);
//				ActionContext.getContext().getSession().put("AskForLeave_reason", AskForLeave_reason);
//				ActionContext.getContext().getSession().put("AskForLeave_begin_time", AskForLeave_begin_time);
//				ActionContext.getContext().getSession().put("AskForLeave_time", AskForLeave_time);
//				ActionContext.getContext().getSession().put("beginUserName", beginUserName);
//				ActionContext.getContext().getSession().put("taskid", taskId);
//				ActionContext.getContext().getSession().put("executionId", executionId);
				JSONArray AskForLeaveJsonArray = new JSONArray();
				JSONObject AskForLeaveJsonArrayObject = new JSONObject();
				AskForLeaveJsonArrayObject.put("AskForLeave_type", AskForLeave_type);
				AskForLeaveJsonArrayObject.put("AskForLeave_reason", AskForLeave_reason);
				AskForLeaveJsonArrayObject.put("AskForLeave_begin_time", AskForLeave_begin_time);
				AskForLeaveJsonArrayObject.put("AskForLeave_time", AskForLeave_time);
				AskForLeaveJsonArrayObject.put("beginUserName", beginusername);
				AskForLeaveJsonArrayObject.put("taskid", taskId);
				AskForLeaveJsonArrayObject.put("executionId", executionId);
				AskForLeaveJsonArray.add(AskForLeaveJsonArrayObject);
				outprint("{\"success\":true,\"data\":{\"url\":\"/jsp/jbpm/detailtask_forAskForLeave.jsp\",\"content\":"+AskForLeaveJsonArray.toString()+"}}");
				return null;
			}
		}else if((type.equals("Document")||type.equals("DocumentSerial"))&&activityName.equals("审批没通过")){//查看审查结果
			Document document = documentdao.getDocumentByBeginuserNameAndIspass(userid,executionId);
			
			JSONArray DocumentJsonArray = new JSONArray();
			JSONObject DocumentJsonObject = new JSONObject();
			DocumentJsonObject.put("D_suggestion", document.getD_suggestion());
			DocumentJsonObject.put("ispass", document.isIspass());
			DocumentJsonObject.put("Document_name", document.getD_name());
			DocumentJsonObject.put("Document_content", document.getD_content());
			DocumentJsonObject.put("D_sendto", document.getSendto());
			DocumentJsonObject.put("D_type", document.getType());
			DocumentJsonObject.put("D_taskid", taskId);
			DocumentJsonObject.put("executionId", executionId);
			DocumentJsonArray.add(DocumentJsonObject);
			outprint("{\"success\":true,\"data\":{\"url\":\"/jsp/jbpm/result_forDocument.jsp\",\"content\":"+DocumentJsonArray.toString()+"}}");
//			ActionContext.getContext().getSession().put("D_suggestion", document.getD_suggestion());
//			ActionContext.getContext().getSession().put("ispass", document.isIspass());
//			ActionContext.getContext().getSession().put("Document_name", document.getD_name());
//			ActionContext.getContext().getSession().put("Document_content", document.getD_content());
//			ActionContext.getContext().getSession().put("D_sendto", document.getSendto());
//			ActionContext.getContext().getSession().put("D_type", document.getType());
//			ActionContext.getContext().getSession().put("D_taskid", taskId);
//			ActionContext.getContext().getSession().put("executionId", executionId);
			return null;
		}else{//查看审查的详细信息:审查的文件名，文件内容等
			if(activityName.equals("办公室")){
				String Document_name = basejbpmoperation.getTaskVariable(taskId, "D_name");
				String Document_content = basejbpmoperation.getTaskVariable(taskId, "D_content");
				JSONArray DocumentJsonArray = new JSONArray();
				JSONObject DocumentJsonObject = new JSONObject();
				DocumentJsonObject.put("Document_name", Document_name);
				DocumentJsonObject.put("Document_content", Document_content);
				DocumentJsonObject.put("taskid", taskId);
				DocumentJsonObject.put("executionId",  executionId);
				DocumentJsonArray.add(DocumentJsonObject);
//				ActionContext.getContext().getSession().put("Document_name", Document_name);
//				ActionContext.getContext().getSession().put("Document_content", Document_content);
//				ActionContext.getContext().getSession().put("taskid", taskId);
//				ActionContext.getContext().getSession().put("executionId", executionId);
				outprint("{\"success\":true,\"data\":{\"url\":\"/jsp/jbpm/result_forDocument.jsp\",\"content\":"+DocumentJsonArray.toString()+"}}");
				return null;
			}else if(activityName.equals("下发文件")){
				String Document_name = basejbpmoperation.getTaskVariable(taskId, "D_name");
				String Document_content = basejbpmoperation.getTaskVariable(taskId, "D_content");
				System.out.println(Document_name+":"+Document_content);
				ActionContext.getContext().getSession().put("Document_name", Document_name);
				ActionContext.getContext().getSession().put("Document_content", Document_content);
				ActionContext.getContext().getSession().put("taskid", taskId);
				ActionContext.getContext().getSession().put("executionId", executionId);
				System.out.println("issued_document");
				Document mydocument = documentdao.getDocumentByTaskId(taskId, username);
				mydocument.setIspass(1);
				documentdao.save(mydocument);
				basejbpmoperation.completeTask(taskId);
				return "issued_document";
			}else{
				String Document_name = basejbpmoperation.getTaskVariable(taskId, "D_name");
				String Document_content = basejbpmoperation.getTaskVariable(taskId, "D_content");
				System.out.println(Document_name+":"+Document_content);
				JSONArray DocumentJsonArray = new JSONArray();
				JSONObject DocumentJsonObject = new JSONObject();
				DocumentJsonObject.put("Document_name", Document_name);
				DocumentJsonObject.put("Document_content", Document_content);
				DocumentJsonObject.put("taskid", taskId);
				DocumentJsonObject.put("executionId",  executionId);
				DocumentJsonArray.add(DocumentJsonObject);
//				ActionContext.getContext().getSession().put("Document_name", Document_name);
//				ActionContext.getContext().getSession().put("Document_content", Document_content);
//				ActionContext.getContext().getSession().put("taskid", taskId);
//				ActionContext.getContext().getSession().put("executionId", executionId);
				outprint("{\"success\":true,\"data\":{\"url\":\"/jsp/jbpm/result_forDocument.jsp\",\"content\":"+DocumentJsonArray.toString()+"}}");
				return null;
			}
			
		}
			//--------------------------------------------------------------------------------------------------------------------------
		
	}
	
	@SuppressWarnings("unused")
	public String Taskgothough(){
		
		System.out.println("手机端发送过来的数executionId="+executionId);
		System.out.println("手机端收到的数据taskid="+taskId);
		System.out.println("手机端收到的数据userid="+userid);
		System.out.println("手机端收到的数据ISPASS="+ISPASS);
		String suggestion = (String) getRequest().getParameter("suggestion");
		System.out.println("手机端收到的数据suggestion="+suggestion);
		
//		User user = userService.findById(Long.parseLong(userid));
//		String username = user.getU_name();
//		System.out.println("查看详细内容的人为="+username);
//		System.out.println("完成流程是的executionId="+executionId);
		String message_execution_id = executionId;
		String type = null;
		if(executionId!=null&&!executionId.equals("")){
			type = executionId.substring(0,executionId.indexOf("."));
		}else{
			type = "other";
		}
		System.out.println("完成流程的类型="+type);
		System.out.println("流程当前taskid="+taskId);
		if(type.equals("AskForLeave")){
			Vacate vacate = vacatedao.getVacateByExecutionid(executionId);
			if(ISPASS.equals("同意")){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("content", "销假");
				basejbpmoperation.setInstanceVariable(executionId, map);
				basejbpmoperation.completeTask(taskId);
				vacate.setIspass(1);
				vacatedao.save(vacate);
				outprint("{\"success\":true}");
			}else{
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("content", "查看结果");
				basejbpmoperation.setInstanceVariable(executionId, map);
				basejbpmoperation.completeTask(taskId);
				vacate.setIspass(0);
				vacatedao.save(vacate);
				outprint("{\"success\":true}");
			}
			
			String initiator = vacate.getInitiator();
			
			//******************************************************************************************
			//发送推送消息
			Comet4j comet4j = new Comet4j();
			comet4j.sendtoAll(initiator);
			//******************************************************************************************
			
		}else if(type.equals("Message")){
			User user = userService.findById(Long.parseLong(userid));
			String username = user.getU_name();
			System.out.println("查看详细内容的人为="+username);
			System.out.println("完成流程是的executionId="+executionId);
			
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
			System.out.println("Message的execution_id="+executionid);
			System.out.println("处理消息的人为="+username);
			Message message = messagedao.findByExecution_id(executionid,userid);
			message.setState(true);
//			taskId = message.getTask_id();
			basejbpmoperation.completeTask(taskId);
			messagedao.update(message);
			outprint("{\"success\":true}");
		}else{
			TaskImpl subtask = (TaskImpl)basejbpmoperation.getTaskService().getTask(taskId);
			Task supertask = subtask.getSuperTask();
			System.out.println("executionId="+executionId);
			if(supertask!=null){//并行
				System.out.println("supertaskid="+supertask.getId());
				User user = userService.findById(Long.parseLong(userid));
				String username = user.getU_name();
				if(ISPASS!=null&&ISPASS.equals("不同意")){
					
					Document document = documentdao.getDocumentByTaskId(subtask.getId(),userid);
		            document.setIspass(0);
//		            document.setType(1);
		            document.setD_suggestion(suggestion);
		            documentdao.save(document);
					
		            basejbpmoperation.getTaskService().completeTask(subtask.getId());  
		            basejbpmoperation.getTaskService().completeTask(supertask.getId(),"不同意");
		            
		          //******************************************************************************************
		        	String initiator = document.getInitiator();
		    		//发送推送消息
		    		Comet4j comet4j = new Comet4j();
		    		comet4j.sendtoAll(initiator);
		    		//******************************************************************************************
		            
		            
		            outprint("{\"success\":true}");
//		            return "Notgothoughsuccessful";  
		        }else{  
		        	Document document = documentdao.getDocumentByTaskId(subtask.getId(),userid);
			        document.setIspass(1);
			        documentdao.save(document);
					basejbpmoperation.getTaskService().completeTask(subtask.getId());  
			        if(basejbpmoperation.getTaskService().getSubTasks(supertask.getId()).size()==0){  
			        	basejbpmoperation.getTaskService().completeTask(supertask.getId(),"同意");
			        
			        	outprint("{\"success\":true}");
	//		            return "gothoughsuccessful";  
			        } 
		        }
				System.out.println("是否通过"+ISPASS);
//				return "gothoughsuccessful"; 
			}else if(supertask==null){//串行
				System.out.println("串行的完成任务"+taskId+"------------------------------------------------------------");
				
				
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
				
				
				
				if(ISPASS!=null&&ISPASS.equals("不同意")){
					
					System.out.println("审批的意见="+suggestion);
					
					document.setD_suggestion(suggestion);
					
					System.out.println("审批的结果为="+ISPASS);
					
					document.setIspass(0);
					
					documentdao.save(document);
					List<String> nulllist = new ArrayList<String>();
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("ispass", "不同意");
					map.put("participants", nulllist);
					basejbpmoperation.setInstanceVariable(executionId, map);
					basejbpmoperation.completeTask(taskId);
					System.out.println("完成审批"+"结果为="+ISPASS);
					
					//******************************************************************************************
		        	String initiator = document.getInitiator();
		    		//发送推送消息
		    		Comet4j comet4j = new Comet4j();
		    		comet4j.sendtoAll(initiator);
		    		//******************************************************************************************
					
//					return "gothoughsuccessful"; 
				}else{
					document.setIspass(1);
					
					documentdao.save(document);
					
					System.out.println("审批的结果为="+ISPASS);
					List<String> participants = (List<String>) basejbpmoperation.getTaskService().getVariable(taskId, "participants");
					for(String participant : participants){
						System.out.println("参与审批的人员有="+participant);
					}
					System.out.println("executionId="+executionId);
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("ispass", "同意");
					map.put("participants", participants);
					basejbpmoperation.setInstanceVariable(executionId, map);
					basejbpmoperation.completeTask(taskId);
					System.out.println("完成审批"+"结果为="+ISPASS);
//					return "gothoughsuccessful"; 
				}
//				return "gothoughsuccessful"; 
			}
		}
		
		
		return null;
	}
	
	public String issued(){
		
		List<String> participants = new ArrayList<String>();
		System.out.println(D_sendto);
		if(D_sendto.equals("<点击此处添加发送对象>")){
			List<User> userlist = userService.findAll();
			for(User user : userlist){
				participants.add(user.getU_name());
			}
			
		}else{
			String[] participantsArray = D_sendto.split(",");
			for(String participant : participantsArray){
				System.out.println(participant+"*******************************************");
				participants.add(participant);
			}
		}
		
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("issued_participants", participants);
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
		
//		User user = userService.findById(Long.parseLong(userid));
//		String username = user.getU_name();
		Document document = documentdao.getDocumentByBeginuserNameAndIspass(userid,executionId);
		
		JSONArray SuggestJSonArray = new JSONArray();
		JSONObject SuggestJSonObject = new JSONObject();
		
		SuggestJSonObject.put("D_suggestion", document.getD_suggestion());
		SuggestJSonObject.put("D_sendto", document.getSendtoName());
		
		SuggestJSonArray.add(SuggestJSonObject);
//		ActionContext.getContext().getSession().put("D_suggestion", document.getD_suggestion());
//		ActionContext.getContext().getSession().put("ispass", document.isIspass());
//		ActionContext.getContext().getSession().put("Document_name", document.getD_name());
//		ActionContext.getContext().getSession().put("Document_content", document.getD_content());
//		ActionContext.getContext().getSession().put("D_sendto", document.getSendto());
//		ActionContext.getContext().getSession().put("D_type", document.getType());
//		ActionContext.getContext().getSession().put("D_taskid", taskId);
//		ActionContext.getContext().getSession().put("executionId", executionId);
		outprint("{\"rows\":"+SuggestJSonArray.toString()+"}");
		return null;
	}
	

}
