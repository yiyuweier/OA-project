package com.oa.bean;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;



@Component
public class Department {
	private long id;//部门id
	private String D_name;//部门名称
	private String D_description;//部门描述
	private Set<User> users = new HashSet<User>();//部门所有职员
	private Department parent;//上级部门
	private Set<Department> childrens = new HashSet<Department>();//下级部门
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getD_name() {
		return D_name;
	}
	public void setD_name(String d_name) {
		D_name = d_name;
	}
	public String getD_description() {
		return D_description;
	}
	public void setD_description(String d_description) {
		D_description = d_description;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public Department getParent() {
		return parent;
	}
	public void setParent(Department parent) {
		this.parent = parent;
	}
	public Set<Department> getChildrens() {
		return childrens;
	}
	public void setChildrens(Set<Department> childrens) {
		this.childrens = childrens;
	}
	

}
