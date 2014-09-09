package com.oa.bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.type.SerializableType;
import org.springframework.stereotype.Component;

import com.oa.service.impl.UserService;





@Component
public class User{
	
	@Resource
	UserService userService;

	private long id;
	
	private Department department;//用户所属的部门
	
	private Set<Role> roles = new HashSet<Role>();//用户所在的岗位
	
	private String loginname;//登陆名
	private String password;//登陆密码
	private String U_name;//真实姓名
	private String U_gender;//性别
	private String U_phoneNumber;//电话号码
	private String U_email;//电子邮件
	private String U_description;//备注
	
	private String face_url;
	private boolean islogin;
	
	public Long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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
	public String getFace_url() {
		return face_url;
	}
	public void setFace_url(String face_url) {
		this.face_url = face_url;
	}
	public boolean isIslogin() {
		return islogin;
	}
	public void setIslogin(boolean islogin) {
		this.islogin = islogin;
	}
	/**
	 * 判断用户是否有权限
	 * @param P_name
	 * @return
	 */
	public boolean hasPrivilegeByName(String P_name){
		if(isAdmin()){
			return true;
		}
		for(Role role : roles){
			for(Privilege privilege : role.getPrivileges()){
				if(privilege.getP_name().equals(P_name)){
					return true;
				}
			}
        }
		return false;
	}
	
	
	public boolean isAdmin() {
		return "admin".equals(loginname);
	}
	
	
}
