package com.oa.action.andirod.flow;



import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;
import org.jbpm.api.IdentityService;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.oa.action.andirod.servlet.OaServlet;
import com.oa.bean.Announcements;
import com.oa.bean.User;
import com.oa.bean.Vacate;
import com.oa.bean.Message;
import com.oa.bean.Document;
import com.oa.comet4j.Comet4j;
import com.oa.dao.AnnouncementsDao;
import com.oa.dao.DocumentDao;
import com.oa.dao.MessageDao;
import com.oa.dao.VacateDao;
import com.oa.jbpm.operation.BaseJbpmOperation;
import com.oa.service.impl.UserService;
import com.opensymphony.xwork2.ActionContext;


@Controller
@Scope("prototype")
public class workplaceStartProcessInstanceServlet extends OaServlet{
	
	//********请假相关的变量**********************
	private String reason;//请假理由
	private String selecttype;//请假的类型
	private String begintime;//请假的开始时间
	private String time;//请假的天数
	private String sendto;//发送给的审批人
	//***************************************
	
	//*******通知公告相关变量********************
	private File image; //上传的文件
    private String imageFileName; //文件名称
    private String imageContentType; //文件类型
	
	
	private String messagename;//消息名称（标题）
	private String messagecontent;//消息内容
	private String userid;
	//**************************************
	
	//********文件会签相关变量********************
	private String D_name;//会签Document标题
	private String D_content;//会签Document内容
	private String D_type;//会签的类型
	private String D_sendto;//参与会签的人员
	//**************************************
	private Long D_taskid;//审批没通过时的taskid，修改并重新发送的taskid
	
	@Resource
	BaseJbpmOperation basejbpmoperation;
	@Resource
	UserService userService;
	@Resource
	VacateDao vacatedao;
	@Resource
	MessageDao messagedao;
	@Resource
	AnnouncementsDao announcementdao;
	@Resource
	DocumentDao documentdao;
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	
	
	public String getSelecttype() {
		return selecttype;
	}

	public void setSelecttype(String selecttype) {
		this.selecttype = selecttype;
	}
	
	public String getBegintime() {
		return begintime;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSendto() {
		return sendto;
	}

	public void setSendto(String sendto) {
		this.sendto = sendto;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getMessagename() {
		return messagename;
	}

	public void setMessagename(String messagename) {
		this.messagename = messagename;
	}

	public String getMessagecontent() {
		return messagecontent;
	}

	public void setMessagecontent(String messagecontent) {
		this.messagecontent = messagecontent;
	}

	public String getD_name() {
		return D_name;
	}

	public void setD_name(String d_name) {
		D_name = d_name;
	}

	public String getD_content() {
		return D_content;
	}

	public void setD_content(String d_content) {
		D_content = d_content;
	}

	public String getD_type() {
		return D_type;
	}

	public void setD_type(String d_type) {
		D_type = d_type;
	}

	public String getD_sendto() {
		return D_sendto;
	}

	public void setD_sendto(String d_sendto) {
		D_sendto = d_sendto;
	}

	public Long getD_taskid() {
		return D_taskid;
	}

	public void setD_taskid(Long d_taskid) {
		D_taskid = d_taskid;
	}
	
	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}

	public String askForLeave() throws IOException{//请假流程的实例化
		
		String reason = (String) getRequest().getParameter("reason");
		String selecttype = (String) getRequest().getParameter("selecttype");//selecttype为类型的编号，为数字
		String begintime = (String) getRequest().getParameter("begintime");
		String time = (String) getRequest().getParameter("time");
		String D_sendto = (String) getRequest().getParameter("D_sendto");
		
		System.out.println("reason="+reason);
		System.out.println("selecttype="+selecttype);
		System.out.println("begintime="+begintime);
		System.out.println("time="+time);
		System.out.println("D_sendto="+D_sendto);
		
		
		String[] D_sendtoArray = D_sendto.split(":");
		String processuserid = D_sendtoArray[0];
		
		String userid = (String) getRequest().getParameter("userid");
		
		Vacate vacate = new Vacate();//请假的java bean
		User beginuser = userService.findById(Long.parseLong(userid));//根据用户id获取到当前用户（发起流程的人）当前用户的id="+Long.parseLong(userid)+"当前用户的名字="+beginuser.getU_name());
		User processuser = userService.findById(Long.parseLong(processuserid));//获取审批请假流程的人，发送给审批人的id="+Long.parseLong(sendto)+"发送给审批人的名字="+processuser.getU_name());

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("reason", reason);
		map.put("type", selecttype);
		map.put("begintime", begintime);
		map.put("time", time);
		map.put("process_user", processuserid);
		map.put("begin_user", userid);
		
		ProcessInstance processInstance = basejbpmoperation.createInstance("AskForLeave", map);//创建流程实例，map为向该流程实例传递的数据
		String executionid = processInstance.getId();
		
		List<Task> tasklist = basejbpmoperation.getTask(userid);//启动流程实例后获取当前用户的任务，如果是“填写请假条”则完成该任务（该流程的设计为填写完请假信息后才创建流程实例的）
		for(Task task : tasklist){
			if(task.getActivityName().equals("填写请假条")){
				basejbpmoperation.completeTask(task.getId());
			}
		}
		
		//将用户的请假信息存入数据库中
		vacate.setAskForLeave_reason(reason);
		vacate.setAskForLeave_type(selecttype);
		vacate.setBegintime(begintime);
		vacate.setTime(time);
		vacate.setInitiator(userid);
//		vacate.setSendto(processuser.getU_name());
		vacate.setSendto(processuserid);
		vacate.setExecutionid(executionid);
		SimpleDateFormat myfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		vacate.setDate(myfmt.format(date).toString());
		vacate.setIspass(4);
		vacatedao.save(vacate);
		
		//******************************************************************************************
		//发送推送消息
		Comet4j comet4j = new Comet4j();
		comet4j.sendtoAll(processuser.getU_name());
		//******************************************************************************************
		
		outprint("{\"success\":true,\"data\":{\"url\":\"/jsp/flow/AskForLeave.jsp\"}}");
		return null;
	}
	
	public String Message() throws IOException{//通知公告流程的实例化
		
		//----------------------------上传附件-------------------------------------------
//		String realpath = ServletActionContext.getServletContext().getRealPath("/file");//获取真实的路径
//        //D:\apache-tomcat-6.0.18\webapps\struts2_upload\images
//        System.out.println("realpath: "+realpath);
//        System.out.println("imagefilename"+imageFileName);
//        if (image != null) {
//            File savefile = new File(new File(realpath), imageFileName);
//            if (!savefile.getParentFile().exists())
//                savefile.getParentFile().mkdirs();
//            FileUtils.copyFile(image, savefile);
////            ActionContext.getContext().put("message", "文件上传成功");
//            System.out.println("文件上传成功");
//        }
		//-----------------------------------------------------------------------------
		
		
		
		
		String messagename = (String) getRequest().getParameter("messagename");
		String messagecontent = (String) getRequest().getParameter("messagecontent");
		String D_sendto = (String) getRequest().getParameter("D_sendto");
		String userid = (String) getRequest().getParameter("userid");
		
//		System.out.println("messagename="+messagename);
//		System.out.println("messagecontent="+messagecontent);
//		System.out.println("D_sendto"+D_sendto);
//		System.out.println("userid="+userid);
		User beginuser = userService.findById(Long.parseLong(userid));//根据用户id获取到当前用户（发起流程的人）当前用户的id="+Long.parseLong(userid)+"当前用户的名字="+beginuser.getU_name())
		String beginusername = beginuser.getU_name();//获取发起人的名字，当前用户的id="+Long.parseLong(userid)+"当前用户的名字="+beginusername);
		
		String[] participantsArray;//用来保存通知公告要发给的人的列表
		String comet4jto = null;//保存要推送消息的人的列表（格式为：名字1，名字2，名字3）
		
		String[] participantsArrayId;//----用来保存通知公告要发给的人的id的列表
		String[] participantsArrayName;
		String[] participantsArrayTemp;//---用来保存通知公告要发给的人的id:username的值
		String receivePeople = null;//指定要发送给的人是特定的某些人还是所有人
		
		if(D_sendto.equals("<点击此处添加发送对象>")||D_sendto==null){//表明该消息要发给所有的人
			receivePeople = "所有人";
			List<User> userlist = userService.findAll();//获取所有的人
			participantsArrayId = new String[userlist.size()];//初始化
			participantsArrayName = new String[userlist.size()];//初始化
			int i = 0;
			for(User user : userlist){
				participantsArrayId[i] = user.getId().toString();//-----
				participantsArrayName[i] = user.getU_name();
				comet4jto = comet4jto + "," + user.getId().toString();//-------
				i = i+1;
			}
		}else{//发给指定的人D_sengto，D_sendto的格式为（名字1，名字2，名字3）
			receivePeople = D_sendto;
			comet4jto = D_sendto;
			participantsArray = D_sendto.split(",");//获取所有的要发送人的姓名
			
			participantsArrayId = new String[participantsArray.length];//---
			participantsArrayName = new String[participantsArray.length];
			int i = 0;//----
			for(String userString : participantsArray){//----
				participantsArrayTemp = userString.split(":");//---
				participantsArrayId[i] = participantsArrayTemp[0];//-----
				participantsArrayName[i] = participantsArrayTemp[1];
				i = i+1;
			}//-----
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("messagename", messagename+"("+receivePeople+")");
		map.put("message", messagecontent);
		map.put("viewUsers",participantsArrayId);
		map.put("begin_user", userid);
		map.put("viewUserssize", participantsArrayId.length);
		ProcessInstance processInstance = basejbpmoperation.createInstance("Message", map);//创建流程实例
		
		Announcements announcement = new Announcements();//通知公告的java bean
		announcement.setA_name(messagename+"("+receivePeople+")");
		announcement.setA_content(messagecontent);
		SimpleDateFormat myfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//格式化时间的格式
		Date date = new Date();
		announcement.setA_time(myfmt.format(date).toString());
//		announcement.setA_FJUrl(realpath);
		announcementdao.save(announcement);
		
		//******************************************************************************************
		//发送推送消息
		Comet4j comet4j = new Comet4j();
		comet4j.sendtoAll(comet4jto);
		//******************************************************************************************
		
		String execution_id = processInstance.getId();
		List<Task> tasklist = basejbpmoperation.getTask(userid);//获取当前用户的任务列表
		for(Task task : tasklist){//启动流程实例后获取当前用户的任务，如果是“填写消息”则完成该任务（该流程的设计为填写完消息信息后才创建流程实例的）
			if(task.getActivityName().equals("填写消息")){
				basejbpmoperation.completeTask(task.getId());
			}
		}
		
		for(String username : participantsArrayId){
			Message message = new Message();
			message.setM_name(messagename);
			message.setM_content(messagecontent);
			message.setInitiator(userid);
			message.setSendto(username);
			message.setSendtoName(userService.findById(Long.parseLong(username)).getU_name());
			Date date1 = new Date();
			message.setM_date(myfmt.format(date1).toString());
			message.setState(false);
			message.setExecution_id(execution_id);
//			message.setM_FJUrl(realpath);
			messagedao.save(message);
		}
			
		ActionContext.getContext().getSession().put("execution_id", execution_id);
		
		outprint("{\"success\":true,\"data\":{\"url\":\"/jsp/flow/Message.jsp\"}}");
		return null;
		
	}
	
	public String Document() throws IOException{//公文审批
		
		String D_sendto = (String) getRequest().getParameter("D_sendto");
		String D_name = (String) getRequest().getParameter("D_name");//selecttype为类型的编号，为数字
		String D_content = (String) getRequest().getParameter("D_content");
		String D_type = (String) getRequest().getParameter("D_type");
		String userid = (String) getRequest().getParameter("userid");
		
		System.out.println("D_sendto="+D_sendto);
		System.out.println("D_name="+D_name);
		System.out.println("D_content="+D_content);
		System.out.println("D_type="+D_type);
		System.out.println("userid="+userid);
		
		String allparticipants = D_sendto;
		
		List<String> participants = new ArrayList<String>();
		String[] participantsArray = D_sendto.split(",");//获取所有的审批人,将所有的审批人放入list中
		
		System.out.println("发送到="+participantsArray[0]);
		
		String[] participantsArrayTemp = new String[participantsArray.length];//-----
		
		for(String participant : participantsArray){
			
//			participants.add(participant);
			
			participantsArrayTemp = participant.split(":");//-----
			System.out.println("用户的编号"+participantsArrayTemp[0]);
			participants.add(participantsArrayTemp[0]);
		}
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("D_name", D_name);
		map.put("D_content", D_content);
		map.put("participants", participants);
//		User user = (User) ActionContext.getContext().getSession().get("user");//获取当前用户
//		String beginuser = user.getId().toString();
		map.put("begin_user", userid);
		
		//为流程中办公室环节指定班里人（为办公室的所有人，他们都可以看到该任务，如果有一个人接受了该任务，那么其他人就没有该任务了）
		List<User> userlist = userService.findByDepartmentName("办公室");
		IdentityService identityService = basejbpmoperation.getIdentityService();
		if(identityService.findGroupById("officeGroups") == null){
			String officeGroups = identityService.createGroup("officeGroups");//创建组
		}
		for(User u : userlist){
			System.out.println("userid1="+u.getId().toString());
			if(identityService.findUserById(u.getId().toString())==null){
				identityService.createUser(u.getId().toString(), u.getId().toString(),"");//创建用户
				identityService.createMembership(u.getId().toString(), "officeGroups");//将该用户添加到组里
			}
//			identityService.createUser(u.getId().toString(), u.getU_name(),"");//创建用户
//			identityService.createUser(u.getId().toString(), u.getId().toString(),"");//创建用户
//			identityService.createMembership(u.getId().toString(), "officeGroups");//将该用户添加到组里
		}
		
		if(D_type.equals("2")){//并行审批
			ProcessInstance processInstance = basejbpmoperation.createInstance("Document", map);
			String processInstanceId = processInstance.getId();
			ActionContext.getContext().getSession().put("Document_processInstanceId", processInstanceId);
			
			//******************************************************************************************
			//发送推送消息
			System.out.println("发送推送消息！，发送给"+D_sendto);
			Comet4j comet4j = new Comet4j();
			comet4j.sendtoAll(D_sendto);
			//******************************************************************************************
			
		}else if(D_type.equals("1")){//串行审批
			//String SerialPeople = participantsArray[0];//获取第一个审批的人
			String SerialPeople = participants.get(0);//获取第一个审批的人----
			
			Document document = new Document();//文件审批相关java bean，将文件审批相关信息存入数据库
			Date time = new Date();
			SimpleDateFormat myfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			document.setD_name(D_name);
			document.setD_content(allparticipants);
			document.setD_date(myfmt.format(time).toString());
			document.setInitiator(userid);
			document.setType(1);
			document.setIspass(3);//见数据库相关文档
			documentdao.save(document);
			
			map.put("SerialPeople", SerialPeople);
			ProcessInstance processInstance = basejbpmoperation.createInstance("DocumentSerial", map);
			String processInstanceId = processInstance.getId();
			ActionContext.getContext().getSession().put("Document_processInstanceId", processInstanceId);
			
			//******************************************************************************************
			//发送推送消息
			Comet4j comet4j = new Comet4j();
			comet4j.sendtoAll(D_sendto);
			//******************************************************************************************
		}
		
		outprint("{\"success\":true,\"data\":{\"url\":\"/jsp/flow/Document.jsp\"}}");
		return null;
	}
	
	public String DocumentForeditor() throws IOException{//审批没有通过时，可以重新发起新的流程，此时要先将这个流程之前的数据删除
		
		String D_sendto = (String) getRequest().getParameter("D_sendto");
		String D_name = (String) getRequest().getParameter("D_name");//selecttype为类型的编号，为数字
		String D_content = (String) getRequest().getParameter("D_content");
		String D_type = (String) getRequest().getParameter("D_type");
		String userid = (String) getRequest().getParameter("userid");
		String D_taskid = (String) getRequest().getParameter("D_taskid");
		
		System.out.println("D_sendto="+D_sendto);
		System.out.println("D_name="+D_name);
		System.out.println("D_content="+D_content);
		System.out.println("D_type="+D_type);
		System.out.println("userid="+userid);
		
		System.out.println("D_taskid="+D_taskid+"--------------------------------------------------");
		
		basejbpmoperation.getTaskService().completeTask(D_taskid, "to end1");
		
		String allparticipants = D_sendto;
		
		List<String> participants = new ArrayList<String>();
		String[] participantsArray = D_sendto.split(",");//获取所有的审批人,将所有的审批人放入list中
		
		System.out.println("发送到="+participantsArray[0]);
		
		String[] participantsArrayTemp = new String[participantsArray.length];//-----
		
		for(String participant : participantsArray){
			
//			participants.add(participant);
			
			participantsArrayTemp = participant.split(":");//-----
			System.out.println("用户的编号"+participantsArrayTemp[0]);
			participants.add(participantsArrayTemp[0]);
		}
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("D_name", D_name);
		map.put("D_content", D_content);
		map.put("participants", participants);
		
		User user = (User) ActionContext.getContext().getSession().get("user");//获取当前用户
		System.out.println("session中的user="+user.getU_name()+"-------------------------------------------------");
//		String beginuser = user.getId().toString();
		map.put("begin_user", userid);
		
		//为流程中办公室环节指定班里人（为办公室的所有人，他们都可以看到该任务，如果有一个人接受了该任务，那么其他人就没有该任务了）
		List<User> userlist = userService.findByDepartmentName("办公室");
		IdentityService identityService = basejbpmoperation.getIdentityService();
		if(identityService.findGroupById("officeGroups") == null){
			String groups = identityService.createGroup("officeGroups");//创建组
			for(User u : userlist){
				identityService.createUser(u.getId().toString(), u.getU_name(),"");//创建用户
				identityService.createMembership(u.getId().toString(), groups);//将该用户添加到组里
			}
		}
		
		if(D_type.equals("2")){//并行审批
			ProcessInstance processInstance = basejbpmoperation.createInstance("Document", map);
			String processInstanceId = processInstance.getId();
			ActionContext.getContext().getSession().put("Document_processInstanceId", processInstanceId);
			
			//******************************************************************************************
			//发送推送消息
			Comet4j comet4j = new Comet4j();
			comet4j.sendtoAll(D_sendto);
			//******************************************************************************************
			
		}else if(D_type.equals("1")){//串行审批
			//String SerialPeople = participantsArray[0];//获取第一个审批的人
			String SerialPeople = participants.get(0);//获取第一个审批的人----
			
			Document document = new Document();//文件审批相关java bean，将文件审批相关信息存入数据库
			Date time = new Date();
			SimpleDateFormat myfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			document.setD_name(D_name);
			document.setD_content(allparticipants);
			document.setD_date(myfmt.format(time).toString());
			document.setInitiator(userid);
			document.setType(1);
			document.setIspass(3);//见数据库相关文档
			documentdao.save(document);
			
			map.put("SerialPeople", SerialPeople);
			ProcessInstance processInstance = basejbpmoperation.createInstance("DocumentSerial", map);
			String processInstanceId = processInstance.getId();
			ActionContext.getContext().getSession().put("Document_processInstanceId", processInstanceId);
			
			Comet4j comet4j = new Comet4j();
			comet4j.sendtoAll(D_sendto);
		}
		
		outprint("{\"success\":true}");
		return null;
	}

}
