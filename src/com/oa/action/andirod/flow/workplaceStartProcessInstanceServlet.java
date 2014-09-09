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
	
	//********�����صı���**********************
	private String reason;//�������
	private String selecttype;//��ٵ�����
	private String begintime;//��ٵĿ�ʼʱ��
	private String time;//��ٵ�����
	private String sendto;//���͸���������
	//***************************************
	
	//*******֪ͨ������ر���********************
	private File image; //�ϴ����ļ�
    private String imageFileName; //�ļ�����
    private String imageContentType; //�ļ�����
	
	
	private String messagename;//��Ϣ���ƣ����⣩
	private String messagecontent;//��Ϣ����
	private String userid;
	//**************************************
	
	//********�ļ���ǩ��ر���********************
	private String D_name;//��ǩDocument����
	private String D_content;//��ǩDocument����
	private String D_type;//��ǩ������
	private String D_sendto;//�����ǩ����Ա
	//**************************************
	private Long D_taskid;//����ûͨ��ʱ��taskid���޸Ĳ����·��͵�taskid
	
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

	public String askForLeave() throws IOException{//������̵�ʵ����
		
		String reason = (String) getRequest().getParameter("reason");
		String selecttype = (String) getRequest().getParameter("selecttype");//selecttypeΪ���͵ı�ţ�Ϊ����
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
		
		Vacate vacate = new Vacate();//��ٵ�java bean
		User beginuser = userService.findById(Long.parseLong(userid));//�����û�id��ȡ����ǰ�û����������̵��ˣ���ǰ�û���id="+Long.parseLong(userid)+"��ǰ�û�������="+beginuser.getU_name());
		User processuser = userService.findById(Long.parseLong(processuserid));//��ȡ����������̵��ˣ����͸������˵�id="+Long.parseLong(sendto)+"���͸������˵�����="+processuser.getU_name());

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("reason", reason);
		map.put("type", selecttype);
		map.put("begintime", begintime);
		map.put("time", time);
		map.put("process_user", processuserid);
		map.put("begin_user", userid);
		
		ProcessInstance processInstance = basejbpmoperation.createInstance("AskForLeave", map);//��������ʵ����mapΪ�������ʵ�����ݵ�����
		String executionid = processInstance.getId();
		
		List<Task> tasklist = basejbpmoperation.getTask(userid);//��������ʵ�����ȡ��ǰ�û�����������ǡ���д�����������ɸ����񣨸����̵����Ϊ��д�������Ϣ��Ŵ�������ʵ���ģ�
		for(Task task : tasklist){
			if(task.getActivityName().equals("��д�����")){
				basejbpmoperation.completeTask(task.getId());
			}
		}
		
		//���û��������Ϣ�������ݿ���
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
		//����������Ϣ
		Comet4j comet4j = new Comet4j();
		comet4j.sendtoAll(processuser.getU_name());
		//******************************************************************************************
		
		outprint("{\"success\":true,\"data\":{\"url\":\"/jsp/flow/AskForLeave.jsp\"}}");
		return null;
	}
	
	public String Message() throws IOException{//֪ͨ�������̵�ʵ����
		
		//----------------------------�ϴ�����-------------------------------------------
//		String realpath = ServletActionContext.getServletContext().getRealPath("/file");//��ȡ��ʵ��·��
//        //D:\apache-tomcat-6.0.18\webapps\struts2_upload\images
//        System.out.println("realpath: "+realpath);
//        System.out.println("imagefilename"+imageFileName);
//        if (image != null) {
//            File savefile = new File(new File(realpath), imageFileName);
//            if (!savefile.getParentFile().exists())
//                savefile.getParentFile().mkdirs();
//            FileUtils.copyFile(image, savefile);
////            ActionContext.getContext().put("message", "�ļ��ϴ��ɹ�");
//            System.out.println("�ļ��ϴ��ɹ�");
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
		User beginuser = userService.findById(Long.parseLong(userid));//�����û�id��ȡ����ǰ�û����������̵��ˣ���ǰ�û���id="+Long.parseLong(userid)+"��ǰ�û�������="+beginuser.getU_name())
		String beginusername = beginuser.getU_name();//��ȡ�����˵����֣���ǰ�û���id="+Long.parseLong(userid)+"��ǰ�û�������="+beginusername);
		
		String[] participantsArray;//��������֪ͨ����Ҫ�������˵��б�
		String comet4jto = null;//����Ҫ������Ϣ���˵��б���ʽΪ������1������2������3��
		
		String[] participantsArrayId;//----��������֪ͨ����Ҫ�������˵�id���б�
		String[] participantsArrayName;
		String[] participantsArrayTemp;//---��������֪ͨ����Ҫ�������˵�id:username��ֵ
		String receivePeople = null;//ָ��Ҫ���͸��������ض���ĳЩ�˻���������
		
		if(D_sendto.equals("<����˴���ӷ��Ͷ���>")||D_sendto==null){//��������ϢҪ�������е���
			receivePeople = "������";
			List<User> userlist = userService.findAll();//��ȡ���е���
			participantsArrayId = new String[userlist.size()];//��ʼ��
			participantsArrayName = new String[userlist.size()];//��ʼ��
			int i = 0;
			for(User user : userlist){
				participantsArrayId[i] = user.getId().toString();//-----
				participantsArrayName[i] = user.getU_name();
				comet4jto = comet4jto + "," + user.getId().toString();//-------
				i = i+1;
			}
		}else{//����ָ������D_sengto��D_sendto�ĸ�ʽΪ������1������2������3��
			receivePeople = D_sendto;
			comet4jto = D_sendto;
			participantsArray = D_sendto.split(",");//��ȡ���е�Ҫ�����˵�����
			
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
		ProcessInstance processInstance = basejbpmoperation.createInstance("Message", map);//��������ʵ��
		
		Announcements announcement = new Announcements();//֪ͨ�����java bean
		announcement.setA_name(messagename+"("+receivePeople+")");
		announcement.setA_content(messagecontent);
		SimpleDateFormat myfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//��ʽ��ʱ��ĸ�ʽ
		Date date = new Date();
		announcement.setA_time(myfmt.format(date).toString());
//		announcement.setA_FJUrl(realpath);
		announcementdao.save(announcement);
		
		//******************************************************************************************
		//����������Ϣ
		Comet4j comet4j = new Comet4j();
		comet4j.sendtoAll(comet4jto);
		//******************************************************************************************
		
		String execution_id = processInstance.getId();
		List<Task> tasklist = basejbpmoperation.getTask(userid);//��ȡ��ǰ�û��������б�
		for(Task task : tasklist){//��������ʵ�����ȡ��ǰ�û�����������ǡ���д��Ϣ������ɸ����񣨸����̵����Ϊ��д����Ϣ��Ϣ��Ŵ�������ʵ���ģ�
			if(task.getActivityName().equals("��д��Ϣ")){
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
	
	public String Document() throws IOException{//��������
		
		String D_sendto = (String) getRequest().getParameter("D_sendto");
		String D_name = (String) getRequest().getParameter("D_name");//selecttypeΪ���͵ı�ţ�Ϊ����
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
		String[] participantsArray = D_sendto.split(",");//��ȡ���е�������,�����е������˷���list��
		
		System.out.println("���͵�="+participantsArray[0]);
		
		String[] participantsArrayTemp = new String[participantsArray.length];//-----
		
		for(String participant : participantsArray){
			
//			participants.add(participant);
			
			participantsArrayTemp = participant.split(":");//-----
			System.out.println("�û��ı��"+participantsArrayTemp[0]);
			participants.add(participantsArrayTemp[0]);
		}
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("D_name", D_name);
		map.put("D_content", D_content);
		map.put("participants", participants);
//		User user = (User) ActionContext.getContext().getSession().get("user");//��ȡ��ǰ�û�
//		String beginuser = user.getId().toString();
		map.put("begin_user", userid);
		
		//Ϊ�����а칫�һ���ָ�������ˣ�Ϊ�칫�ҵ������ˣ����Ƕ����Կ��������������һ���˽����˸�������ô�����˾�û�и������ˣ�
		List<User> userlist = userService.findByDepartmentName("�칫��");
		IdentityService identityService = basejbpmoperation.getIdentityService();
		if(identityService.findGroupById("officeGroups") == null){
			String officeGroups = identityService.createGroup("officeGroups");//������
		}
		for(User u : userlist){
			System.out.println("userid1="+u.getId().toString());
			if(identityService.findUserById(u.getId().toString())==null){
				identityService.createUser(u.getId().toString(), u.getId().toString(),"");//�����û�
				identityService.createMembership(u.getId().toString(), "officeGroups");//�����û���ӵ�����
			}
//			identityService.createUser(u.getId().toString(), u.getU_name(),"");//�����û�
//			identityService.createUser(u.getId().toString(), u.getId().toString(),"");//�����û�
//			identityService.createMembership(u.getId().toString(), "officeGroups");//�����û���ӵ�����
		}
		
		if(D_type.equals("2")){//��������
			ProcessInstance processInstance = basejbpmoperation.createInstance("Document", map);
			String processInstanceId = processInstance.getId();
			ActionContext.getContext().getSession().put("Document_processInstanceId", processInstanceId);
			
			//******************************************************************************************
			//����������Ϣ
			System.out.println("����������Ϣ�������͸�"+D_sendto);
			Comet4j comet4j = new Comet4j();
			comet4j.sendtoAll(D_sendto);
			//******************************************************************************************
			
		}else if(D_type.equals("1")){//��������
			//String SerialPeople = participantsArray[0];//��ȡ��һ����������
			String SerialPeople = participants.get(0);//��ȡ��һ����������----
			
			Document document = new Document();//�ļ��������java bean�����ļ����������Ϣ�������ݿ�
			Date time = new Date();
			SimpleDateFormat myfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			document.setD_name(D_name);
			document.setD_content(allparticipants);
			document.setD_date(myfmt.format(time).toString());
			document.setInitiator(userid);
			document.setType(1);
			document.setIspass(3);//�����ݿ�����ĵ�
			documentdao.save(document);
			
			map.put("SerialPeople", SerialPeople);
			ProcessInstance processInstance = basejbpmoperation.createInstance("DocumentSerial", map);
			String processInstanceId = processInstance.getId();
			ActionContext.getContext().getSession().put("Document_processInstanceId", processInstanceId);
			
			//******************************************************************************************
			//����������Ϣ
			Comet4j comet4j = new Comet4j();
			comet4j.sendtoAll(D_sendto);
			//******************************************************************************************
		}
		
		outprint("{\"success\":true,\"data\":{\"url\":\"/jsp/flow/Document.jsp\"}}");
		return null;
	}
	
	public String DocumentForeditor() throws IOException{//����û��ͨ��ʱ���������·����µ����̣���ʱҪ�Ƚ��������֮ǰ������ɾ��
		
		String D_sendto = (String) getRequest().getParameter("D_sendto");
		String D_name = (String) getRequest().getParameter("D_name");//selecttypeΪ���͵ı�ţ�Ϊ����
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
		String[] participantsArray = D_sendto.split(",");//��ȡ���е�������,�����е������˷���list��
		
		System.out.println("���͵�="+participantsArray[0]);
		
		String[] participantsArrayTemp = new String[participantsArray.length];//-----
		
		for(String participant : participantsArray){
			
//			participants.add(participant);
			
			participantsArrayTemp = participant.split(":");//-----
			System.out.println("�û��ı��"+participantsArrayTemp[0]);
			participants.add(participantsArrayTemp[0]);
		}
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("D_name", D_name);
		map.put("D_content", D_content);
		map.put("participants", participants);
		
		User user = (User) ActionContext.getContext().getSession().get("user");//��ȡ��ǰ�û�
		System.out.println("session�е�user="+user.getU_name()+"-------------------------------------------------");
//		String beginuser = user.getId().toString();
		map.put("begin_user", userid);
		
		//Ϊ�����а칫�һ���ָ�������ˣ�Ϊ�칫�ҵ������ˣ����Ƕ����Կ��������������һ���˽����˸�������ô�����˾�û�и������ˣ�
		List<User> userlist = userService.findByDepartmentName("�칫��");
		IdentityService identityService = basejbpmoperation.getIdentityService();
		if(identityService.findGroupById("officeGroups") == null){
			String groups = identityService.createGroup("officeGroups");//������
			for(User u : userlist){
				identityService.createUser(u.getId().toString(), u.getU_name(),"");//�����û�
				identityService.createMembership(u.getId().toString(), groups);//�����û���ӵ�����
			}
		}
		
		if(D_type.equals("2")){//��������
			ProcessInstance processInstance = basejbpmoperation.createInstance("Document", map);
			String processInstanceId = processInstance.getId();
			ActionContext.getContext().getSession().put("Document_processInstanceId", processInstanceId);
			
			//******************************************************************************************
			//����������Ϣ
			Comet4j comet4j = new Comet4j();
			comet4j.sendtoAll(D_sendto);
			//******************************************************************************************
			
		}else if(D_type.equals("1")){//��������
			//String SerialPeople = participantsArray[0];//��ȡ��һ����������
			String SerialPeople = participants.get(0);//��ȡ��һ����������----
			
			Document document = new Document();//�ļ��������java bean�����ļ����������Ϣ�������ݿ�
			Date time = new Date();
			SimpleDateFormat myfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			document.setD_name(D_name);
			document.setD_content(allparticipants);
			document.setD_date(myfmt.format(time).toString());
			document.setInitiator(userid);
			document.setType(1);
			document.setIspass(3);//�����ݿ�����ĵ�
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
