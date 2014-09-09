package com.oa.action.andirod.login;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.ServletActionContext;
import org.jbpm.api.task.Task;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.oa.action.andirod.servlet.OaServlet;
import com.oa.bean.Andirod;
import com.oa.bean.Privilege;
import com.oa.bean.Role;
import com.oa.bean.User;
import com.oa.comet4j.Comet4j;
import com.oa.dao.RoleDao;
import com.oa.jbpm.operation.BaseJbpmOperation;
import com.oa.service.impl.PrivilegeService;
import com.oa.service.impl.UserService;
import com.opensymphony.xwork2.ActionContext;


@Controller
@Scope("prototype")
/**
 * ��½
 * Login����½���������ݣ�loginId���û���½������password����½���룩
 * ChongePasswd���������룬�������ݣ�userid��Ҫ�޸�������û���id����password���û����µ����룩
 * @author xg_liu
 *
 */
public class LoginServlet extends OaServlet{
	private String loginId;
	private String password;
	private String isloginfromandroid = "0";
	private String userid;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getIsloginfromandroid() {
		return isloginfromandroid;
	}

	public void setIsloginfromandroid(String isloginfromandroid) {
		this.isloginfromandroid = isloginfromandroid;
	}


	@Resource
	UserService userservice;
	@Resource
	PrivilegeService privilegeservice;
	@Resource
	BaseJbpmOperation basejbpmoperation;
	@Resource
	RoleDao roledao;
	
	JSONArray taskNumberJsonArray = new JSONArray();
	JSONArray allUserListJsonArray = new JSONArray();
	
	JSONArray privilegelistJsonArray = new JSONArray();
	JSONObject privilegelistJsonObject = new JSONObject();
	public String Login() throws IOException{//�û��ĵ�½
		
		int messagenumber = 0;
		
		System.out.println("loginId="+loginId);
		System.out.println("password="+password);
		System.out.println("isloginfromandroid="+isloginfromandroid);
		
		User user = userservice.login(loginId);//�����û��ĵ�¼���ҵ����û�����Ϣ
		
		System.out.println("USERNAME="+user.getU_name());
		
//		if(UpdateConfigFile.Flag=="1"){
//			outprint("{\"Updateflag\":"+UpdateConfigFile.Flag+",\"Vision\":"+UpdateConfigFile.Vision+",\"path\":"+UpdateConfigFile.Path+",\"Decriobe\":"+UpdateConfigFile.Decriobe+"}");
//			return null;
//		}else{

		if(user!=null){//�ж��û��Ƿ�Ϊ�գ���Ϊ����֤��û�и��û�����������֤�û��������Ƿ���ȷ
//			if(user.isIslogin()){
//				outprint("{\"success\":false,\"errorMsg\":\"���û��Ѿ���¼�������Ǳ��˵�¼������ϵ����Ա��\"}");
//			}else 
			if(user.getPassword().equals(DigestUtils.md5Hex(password))){//���û������������ܣ������ݿ����Ѽ��ܵ�����Ƚϣ���ȷ�Ļ���ȡ���е�Ȩ���б�
				
//				user.setIslogin(true);
				
				List<Privilege> privilegeToplist = privilegeservice.findTopPrivileges();//��ȡ���е�Ȩ���б�
				ActionContext.getContext().getApplication().put("Topprivilegelist", privilegeToplist);//�����е�Ȩ���б����application�У��Ա����Ժ�ʹ��
				
				List<Task> tasklist = basejbpmoperation.getTask(user.getId().toString());//�����û���id����ȡ���û������е�˽�˵Ĵ�������
				List<Task> groupsTaskList = basejbpmoperation.getTaskService().findGroupTasks(user.getId().toString());//��ȡ���û���������Ĵ�������ֻҪ��������һ���˴����˸����������������˾�û���˸�����
				tasklist.addAll(groupsTaskList);//����������ӵ����û��Ĵ��������б���
				
				
				
				if(tasklist.size()==0){//ǰ̨����Ϣ��������
					ActionContext.getContext().getSession().put("tasknumber", 0);
				}else{
					ActionContext.getContext().getSession().put("tasknumber", tasklist.size());
				}
				
				if(!Comet4j.isFlag()){
					Comet4j.init();//���Ϊ�����ʼ��comet4j��ע��start
				}
				
				System.out.println("------------------------------------------------");
				ActionContext.getContext().getSession().put("user", user);
				if(isloginfromandroid.equals("1")){
					Andirod.setIslognfromandird(true);
					System.out.println("���ֻ��˵�½��");
					Set<Role> rolelist = user.getRoles();
					for(Role role : rolelist){
						Long roleid = role.getId();
						Set<Privilege> privilegeset = roledao.findById(roleid).getPrivileges();
						for(Privilege privilege : privilegeset){
							privilegelistJsonObject.put("privilegeid", privilege.getId());
							privilegelistJsonObject.put("privilegename", privilege.getP_name());
							privilegelistJsonObject.put("parentprivilegeid", privilege.getParent()==null?0:privilege.getParent().getId());
							privilegelistJsonArray.add(privilegelistJsonObject);
						}
					}
					
//					System.out.println(privilegelistJsonArray.toString());
				}else{
					System.out.println("�ӵ��Զ˵�¼");

				}
				
				System.out.println("------------------------------------------------");
				
				//���е��û��б��û��б�ĸ�ʽΪ��1:��������Ա�����û�id:�û�����
				List<User> userlist = userservice.findAll();
				ActionContext.getContext().getSession().put("alluserlist", userlist);
				
//				String[] usernametmp = new String[userlist.size()];
//				for(User usertmp : userlist){
//					usernametmp[Integer.parseInt(usertmp.getId().toString())] = usertmp.getU_name();
//				}
//				ActionContext.getContext().getApplication().put("allusernametmp", usernametmp);
				
				JSONObject allUserListJsonObject  = new JSONObject();
				for(User users : userlist){//�����е��û�ת��Ϊjson�ĸ�ʽ
					allUserListJsonObject.put("username", users.getId()+":"+users.getU_name());
					allUserListJsonArray.add(allUserListJsonObject);
				}
				
				for(Task task : tasklist){//�ҳ����е�task������������֪ͨ����ĸ�����
					System.out.println(task.getName()+"-----"+task.getExecutionId());
					System.out.println("*****************************************************************");
					System.out.println(task.getExecutionId()==null?false:task.getExecutionId().toString().contains("Message."));
					if(task.getExecutionId()==null?false:task.getExecutionId().toString().contains("Message.")){
						messagenumber = messagenumber + 1;
					}
				}
				
				System.out.println("{\"success\":true,\"data\":{\"url\":\"index.jsp\"},\"userid\":"+user.getId()+",\"allUserList\":"+ allUserListJsonArray.toString() +",\"privilegelsit\":"+privilegelistJsonArray.toString()+",\"tasknumber\":"+tasklist.size()+",\"messagenumber\":"+messagenumber+",\"Updateflag\":"+UpdateConfigFile.Flag+",\"Vision\":"+UpdateConfigFile.Vision+",\"path\":"+UpdateConfigFile.Path.toString()+",\"Decriobe\":"+UpdateConfigFile.Decriobe.toString()+"}");
//				outprint("{\"success\":true,\"data\":{\"url\":\"index.jsp\"},\"userid\":"+user.getId()+",\"allUserList\":"+ allUserListJsonArray.toString() +",\"privilegelsit\":" + privilegelistJsonArray.toString() + ",\"tasknumber\":"+tasklist.size()+",\"messagenumber\":"+messagenumber+",\"Updateflag\":"+UpdateConfigFile.Flag+",\"Vision\":"+UpdateConfigFile.Vision+",\"path\":"+UpdateConfigFile.Path+",\"Decriobe\":"+UpdateConfigFile.Decriobe+"}");
				
				outprint("{\"success\":true,\"data\":{\"url\":\"index.jsp\"},\"userid\":"+user.getId()+",\"allUserList\":"+ allUserListJsonArray.toString() +",\"privilegelsit\":" + privilegelistJsonArray.toString() + ",\"tasknumber\":"+tasklist.size()+",\"messagenumber\":"+messagenumber+",\"Updateflag\":"+UpdateConfigFile.Flag+",\"Vision\":\""+UpdateConfigFile.Vision.toString()+"\",\"path\":\""+UpdateConfigFile.Path.toString()+"\",\"Decriobe\":\""+UpdateConfigFile.Decriobe.toString()+"\"}");
			}else{
				outprint("{\"success\":false,\"errorMsg\":\"�û��������\"}");
			}
		}else{//�û�userΪ�գ�û�и��û�
			outprint("{\"success\":false,\"errorMsg\":\"���û������ڣ��û�������\"}");
		}
//		}
		return null;
	}
	
	public void ChangePasswd(){
		User user = userservice.findById(Long.parseLong(userid));//���ݸ��û�id�ҵ����û�
		String mD5Digest = DigestUtils.md5Hex(password);//�����û����µ�����
		user.setPassword(mD5Digest);//�����û����������ó��µ�����
		userservice.save(user);//�����µ�����
		outprint("{\"success\":true}");
	}
	public String LoginOut(){
		ActionContext.getContext().getSession().remove("user");
		return "login";
	}
	
	
}
