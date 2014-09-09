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
 * 登陆
 * Login：登陆，传入数据：loginId（用户登陆名），password（登陆密码）
 * ChongePasswd：更改密码，传入数据：userid（要修改密码的用户的id），password（用户的新的密码）
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
	public String Login() throws IOException{//用户的登陆
		
		int messagenumber = 0;
		
		System.out.println("loginId="+loginId);
		System.out.println("password="+password);
		System.out.println("isloginfromandroid="+isloginfromandroid);
		
		User user = userservice.login(loginId);//根据用户的登录名找到该用户的信息
		
		System.out.println("USERNAME="+user.getU_name());
		
//		if(UpdateConfigFile.Flag=="1"){
//			outprint("{\"Updateflag\":"+UpdateConfigFile.Flag+",\"Vision\":"+UpdateConfigFile.Vision+",\"path\":"+UpdateConfigFile.Path+",\"Decriobe\":"+UpdateConfigFile.Decriobe+"}");
//			return null;
//		}else{

		if(user!=null){//判断用户是否为空，若为空则证明没有该用户，否则则验证用户的密码是否正确
//			if(user.isIslogin()){
//				outprint("{\"success\":false,\"errorMsg\":\"该用户已经登录，若不是本人登录，请联系管理员！\"}");
//			}else 
			if(user.getPassword().equals(DigestUtils.md5Hex(password))){//将用户输入的密码加密，和数据空中已加密的密码比较，正确的话获取所有的权限列表
				
//				user.setIslogin(true);
				
				List<Privilege> privilegeToplist = privilegeservice.findTopPrivileges();//获取所有的权限列表
				ActionContext.getContext().getApplication().put("Topprivilegelist", privilegeToplist);//将所有的权限列表放入application中，以便在以后使用
				
				List<Task> tasklist = basejbpmoperation.getTask(user.getId().toString());//根据用户的id来获取该用户的所有的私人的待办任务
				List<Task> groupsTaskList = basejbpmoperation.getTaskService().findGroupTasks(user.getId().toString());//获取该用户的所在组的待办任务（只要该组中有一个人处理了该任务，则该组的其他人就没有了该任务）
				tasklist.addAll(groupsTaskList);//将组任务添加到该用户的待办任务列表中
				
				
				
				if(tasklist.size()==0){//前台“消息”的数量
					ActionContext.getContext().getSession().put("tasknumber", 0);
				}else{
					ActionContext.getContext().getSession().put("tasknumber", tasklist.size());
				}
				
				if(!Comet4j.isFlag()){
					Comet4j.init();//如果为空则初始化comet4j，注册start
				}
				
				System.out.println("------------------------------------------------");
				ActionContext.getContext().getSession().put("user", user);
				if(isloginfromandroid.equals("1")){
					Andirod.setIslognfromandird(true);
					System.out.println("从手机端登陆！");
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
					System.out.println("从电脑端登录");

				}
				
				System.out.println("------------------------------------------------");
				
				//所有的用户列表，用户列表的格式为：1:超级管理员，（用户id:用户名）
				List<User> userlist = userservice.findAll();
				ActionContext.getContext().getSession().put("alluserlist", userlist);
				
//				String[] usernametmp = new String[userlist.size()];
//				for(User usertmp : userlist){
//					usernametmp[Integer.parseInt(usertmp.getId().toString())] = usertmp.getU_name();
//				}
//				ActionContext.getContext().getApplication().put("allusernametmp", usernametmp);
				
				JSONObject allUserListJsonObject  = new JSONObject();
				for(User users : userlist){//将所有的用户转化为json的格式
					allUserListJsonObject.put("username", users.getId()+":"+users.getU_name());
					allUserListJsonArray.add(allUserListJsonObject);
				}
				
				for(Task task : tasklist){//找出所有的task（待办任务中通知公告的个数）
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
				outprint("{\"success\":false,\"errorMsg\":\"用户密码错误\"}");
			}
		}else{//用户user为空，没有该用户
			outprint("{\"success\":false,\"errorMsg\":\"该用户不存在，用户名错误！\"}");
		}
//		}
		return null;
	}
	
	public void ChangePasswd(){
		User user = userservice.findById(Long.parseLong(userid));//根据该用户id找到该用户
		String mD5Digest = DigestUtils.md5Hex(password);//加密用户的新的密码
		user.setPassword(mD5Digest);//将该用户的密码设置成新的密码
		userservice.save(user);//保存新的密码
		outprint("{\"success\":true}");
	}
	public String LoginOut(){
		ActionContext.getContext().getSession().remove("user");
		return "login";
	}
	
	
}
