package com.oa.action.andirod.system;


import java.util.HashSet;
import java.util.List;
import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.oa.action.andirod.servlet.OaServlet;
import com.oa.bean.Department;
import com.oa.bean.Privilege;
import com.oa.bean.Role;
import com.oa.bean.User;
import com.oa.service.impl.DepartmentService;
import com.oa.service.impl.PrivilegeService;
import com.oa.service.impl.RoleService;
import com.oa.service.impl.UserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sun.mail.smtp.DigestMD5;


/**
 * �̳�ModelDriven�ӿڣ����Խ������Զ���װ��Model��
 * �������н����id loginname���Զ���װ��user�У�����һЩ���ǻ������Ե����Բ����Զ���װ�����籾��user�е�department����roles�Ȳ����Զ�
 * ��װ��Ҫ�ֶ���װ��set��
 * @author xg_liu
 *
 */
@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class UserServlet extends OaServlet implements ModelDriven<User>{
	
	private Long id;
	private String loginname;//��½��
	private String password;//��½����
	private String U_name;//��ʵ����
	private String U_gender;//�Ա�
	private String U_phoneNumber;//�绰����
	private String U_email;//�����ʼ�
	private String U_description;//��ע
	
	private String rows;
	private String page;
	private String name1;
	
	private String privilegeId;//��Ȩ�޵�id
	
	/**
	 * �������Զ���װ��user��
	 */
	@Resource
	private User model;
	public User getModel() {
		return model;
	}
	
	
	public String getName1() {
		return name1;
	}


	public void setName1(String name1) {
		this.name1 = name1;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getU_name() {
		return U_name;
	}
	public void setU_name(String u_name) {
		U_name = u_name;
	}
	public String getU_gender() {
		return U_gender;
	}
	public void setU_gender(String u_gender) {
		U_gender = u_gender;
	}
	public String getU_phoneNumber() {
		return U_phoneNumber;
	}
	public void setU_phoneNumber(String u_phoneNumber) {
		U_phoneNumber = u_phoneNumber;
	}
	public String getU_email() {
		return U_email;
	}
	public void setU_email(String u_email) {
		U_email = u_email;
	}
	public String getU_description() {
		return U_description;
	}
	public void setU_description(String u_description) {
		U_description = u_description;
	}
	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
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



	private Long departmentId;
	private Long[] roleIdList;
	
	
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long[] getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(Long[] roleIdList) {
		this.roleIdList = roleIdList;
	}


	@Resource
	UserService userservice;
	@Resource
	DepartmentService departmentservice;
	@Resource
	RoleService roleservice;
	@Resource
	PrivilegeService privilegeService;
	
	public String list(){//��ȡ�����û����б�
		System.out.println("����Ϊ="+rows+"ҳ��Ϊ="+page);
		//*********��ȡ�û���Ϣ*********************
		JSONArray userJsonArray = new JSONArray();
		JSONObject userJsonObject = new JSONObject();
		List<User> resulttotle = userservice.findAll();
		List<User> result = userservice.findByRowAndPage(rows, page);
		for(User user : result){
			String roleString = "";
			userJsonObject.put("userid", user.getId());
			userJsonObject.put("loginname", user.getLoginname());
			userJsonObject.put("U_name", user.getU_name());
			userJsonObject.put("department", user.getDepartment()==null?null:user.getDepartment().getD_name());
			userJsonObject.put("departmentId", user.getDepartment()==null?null:user.getDepartment().getId());
			userJsonObject.put("U_email", user.getU_email());
			userJsonObject.put("U_phoneNumber", user.getU_phoneNumber());
			userJsonObject.put("U_gender", user.getU_gender());
			for(Role role : user.getRoles()){
				roleString = roleString+role.getR_name();
				userJsonObject.put("roleIdList", role.getId());
			}
			userJsonObject.put("gangwei", roleString);
			userJsonObject.put("U_description", user.getU_description());
			userJsonArray.add(userJsonObject);
//			i = i+1;
		}
		System.out.println(userJsonArray.toString());
		outprint("{\"total\":"+resulttotle.size() +",\"rows\":"+userJsonArray.toString()+"}");
		//**************************************
		return null;
	}
	public String add(){//����µ��û�
		System.out.println("departmentId==="+departmentId);
		model.setDepartment(departmentservice.findById(departmentId));
		String mD5Digest = DigestUtils.md5Hex("1234");//���ó�ʼ����Ϊ1234
		model.setPassword(mD5Digest);
		List<Role> roles = roleservice.findByIds(roleIdList);
		model.setRoles(new HashSet<Role>(roles));
		//user.setR_description(U_description);
		userservice.save(model);
		outprint("{\"success\":true}");
		return null;
	}
	public String delete(){//ɾ���û�
		System.out.println(model.getId());
		userservice.delete(model.getId());
		outprint("{\"success\":true}");
		return null;
	}
	public String edit(){//�༭�û�
		User user = userservice.findById(model.getId());
		user.setLoginname(model.getLoginname());
		user.setU_name(model.getU_name());
		user.setU_gender(model.getU_gender());
		user.setU_phoneNumber(model.getU_phoneNumber());
		user.setU_email(model.getU_email());
		user.setU_description(model.getU_description());
		
		user.setDepartment(departmentservice.findById(departmentId));
		List<Role> rolelist = roleservice.findByIds(roleIdList);
		user.setRoles(new HashSet<Role>(rolelist));
		userservice.update(user);
		outprint("{\"success\":true}");
		return null;
	}
	
	
	
	public String userManageUI(){//ͨ��action��ת���û�����ҳ��
//		List<Department> department_result = departmentservice.findAll();
		List<Department> department_result = departmentservice.findTopAndChild();
		ActionContext.getContext().put("departmentlist", department_result);
		List<Role> role_result = roleservice.findAll();
		ActionContext.getContext().put("rolelist", role_result);
		
		List<Privilege> privilegeList = privilegeService.findChildPrivileges(Long.parseLong(privilegeId));
		ActionContext.getContext().getSession().put("privilegeList", privilegeList);
		System.out.println("execution at addUI");
		return "UserManage";
	}
	public String editUI(){
//		List<Department> department_result = departmentservice.findAll();
		List<Department> department_result = departmentservice.findTopAndChild();
		ActionContext.getContext().put("departmentlist", department_result);
		List<Role> role_result = roleservice.findAll();
		ActionContext.getContext().put("rolelist", role_result);
		User user = userservice.findById(model.getId());
		if(user.getDepartment()!=null){
			departmentId = user.getDepartment().getId();
		}
		if(user.getRoles()!=null){
			roleIdList = new Long[user.getRoles().size()];
			int index = 0;
			for(Role role:user.getRoles()){
				roleIdList[index++] = role.getId();
			}
		}
		
		
		ActionContext.getContext().getValueStack().push(user);
		return "editUI";
	}
	
	public String initPassword(){//��ʼ���û�������
		User user = userservice.findById(model.getId());
		String mD5Digest = DigestUtils.md5Hex("1234");
		user.setPassword(mD5Digest);
		userservice.update(user);
		outprint("{\"success\":true}");
		return null;
	}
	
	public String testlist(){
		JSONArray userJsonArray = new JSONArray();
		JSONObject userJsonObject = new JSONObject();
		List<User> result = userservice.findAll();
		List<User> result1 = userservice.findAllByPage(Integer.parseInt(page),Integer.parseInt(rows));
		for(User user : result1){
			String roleString = "";
			userJsonObject.put("userid", user.getId());
			userJsonObject.put("email", user.getU_email());
			userJsonObject.put("name", user.getU_name());
			userJsonObject.put("sex", user.getU_gender());
			userJsonObject.put("department", user.getDepartment()==null?null:user.getDepartment().getD_name());
			for(Role role : user.getRoles()){
				roleString = roleString+role.getR_name();
			}
			userJsonObject.put("gangwei", roleString);
			userJsonObject.put("phone", user.getU_phoneNumber());
			userJsonArray.add(userJsonObject);
//			i = i+1;
		}
		System.out.println(userJsonArray.toString());
		outprint("{\"total\":"+result.size()+",\"rows\":"+userJsonArray.toString()+"}");
		//**************************************
		return null;
	}
	
	public String findByName(){
		JSONArray userJsonArray = new JSONArray();
		JSONObject userJsonObject = new JSONObject();
		//System.out.println("1111111111111"+name1);
		List<User> result=userservice.findByRealName(name1);
		List<User> result1=userservice.findByRealNameByPage(name1,Integer.parseInt(page),Integer.parseInt(rows));
		
		for(User user : result1){
			String roleString = "";
			userJsonObject.put("userid", user.getId());
			userJsonObject.put("email", user.getU_email());
			userJsonObject.put("name", user.getU_name());
			userJsonObject.put("sex", user.getU_gender());
			userJsonObject.put("department", user.getDepartment()==null?null:user.getDepartment().getD_name());
			for(Role role : user.getRoles()){
				roleString = roleString+role.getR_name();
			}
			userJsonObject.put("gangwei", roleString);
			userJsonObject.put("phone", user.getU_phoneNumber());
			userJsonArray.add(userJsonObject);
//			i = i+1;
		}
		System.out.println(userJsonArray.toString());
		outprint("{\"total\":"+result.size()+",\"rows\":"+userJsonArray.toString()+"}");
		
		
		
		
		return null;
	}
	
	public String getDepartmentInfo(){//��ȡ�����б�
			
		JSONArray departmentJsonArray = new JSONArray();
		JSONObject departmentJsonObject = new JSONObject();
		List<Department> department_result = departmentservice.findTopAndChild();
		for(Department department : department_result){
			departmentJsonObject.put("id", department.getId());
			departmentJsonObject.put("text", department.getD_name());
			departmentJsonArray.add(departmentJsonObject);
		}
		
		outprint(departmentJsonArray.toString());
		return null;
	}
	
	public String getRoleInfo(){
		JSONArray roleJsonArray = new JSONArray();
		JSONObject roleJsonObject = new JSONObject();
		List<Role> role_result = roleservice.findAll();
		for(Role role : role_result){
			roleJsonObject.put("id", role.getId());
			roleJsonObject.put("text", role.getR_name());
			roleJsonArray.add(roleJsonObject);
		}
		
		outprint(roleJsonArray.toString());
		return null;
	}
	
	
	//�ֻ���************************************************************************//
		public String phonefindByName(){
			System.out.println(name1);
			JSONArray userJsonArray = new JSONArray();
			JSONObject userJsonObject = new JSONObject();
			List<User> result = userservice.findByRealName(name1);
			//List<User> result1 = userservice.findByRealNameByPage(name1,Integer.parseInt(page),Integer.parseInt(rows));
			for(User user : result){
				String roleString = "";
				userJsonObject.put("userid", user.getId());
				userJsonObject.put("email", user.getU_email());
				userJsonObject.put("name", user.getU_name());
				userJsonObject.put("sex", user.getU_gender());
				userJsonObject.put("department", user.getDepartment()==null?null:user.getDepartment().getD_name());
				for(Role role : user.getRoles()){
					roleString = roleString+role.getR_name();
				}
				userJsonObject.put("gangwei", roleString);
				userJsonObject.put("phone", user.getU_phoneNumber());
				userJsonArray.add(userJsonObject);
//				i = i+1;
			}
			System.out.println(userJsonArray.toString());
			outprint("{\"total\":"+result.size()+",\"rows\":"+userJsonArray.toString()+"}");
			//**************************************
			return null;
		}
		
		
		public String testlistPhone(){
			JSONArray userJsonArray = new JSONArray();
			JSONObject userJsonObject = new JSONObject();
			List<User> result = userservice.findAll();
			//List<User> result1 = userservice.findAllByPage(Integer.parseInt(page),Integer.parseInt(rows));
			for(User user : result){
				String roleString = "";
				userJsonObject.put("userid", user.getId());
				userJsonObject.put("email", user.getU_email());
				userJsonObject.put("name", user.getU_name());
				userJsonObject.put("sex", user.getU_gender());
				userJsonObject.put("department", user.getDepartment()==null?null:user.getDepartment().getD_name());
				for(Role role : user.getRoles()){
					roleString = roleString+role.getR_name();
				}
				userJsonObject.put("gangwei", roleString);
				userJsonObject.put("phone", user.getU_phoneNumber());
				userJsonArray.add(userJsonObject);
//				i = i+1;
			}
			System.out.println(userJsonArray.toString());
			outprint("{\"total\":"+result.size()+",\"rows\":"+userJsonArray.toString()+"}");
			//**************************************
			return null;
		}
		
		
		
		
	//**************************************************************************//	
	

}
