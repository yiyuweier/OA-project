package com.oa.bean;

import java.util.HashSet;
import java.util.Set;

public class Privilege {
	private Long id;
	private String P_name;//权限名
	private String P_url;//权限对应的url
	private String P_image;//该权限所对应的图标
	
	private Set<Role> roles = new HashSet<Role>(); //权限与岗位的多对多关系
	
	private Privilege parent;//与上级权限的关系
	private Set<Privilege> children = new HashSet<Privilege>();//与下级权限的关系
	
	
	public Privilege() {
	}
	public Privilege(String p_name, String p_url, Privilege parent, String p_image) {
		super();
		P_name = p_name;
		P_url = p_url;
		this.parent = parent;
		this.P_image = p_image;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getP_name() {
		return P_name;
	}
	public void setP_name(String p_name) {
		P_name = p_name;
	}
	public String getP_url() {
		return P_url;
	}
	public void setP_url(String p_url) {
		P_url = p_url;
	}
	
	
	public String getP_image() {
		return P_image;
	}
	public void setP_image(String p_image) {
		P_image = p_image;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public Privilege getParent() {
		return parent;
	}
	public void setParent(Privilege parent) {
		this.parent = parent;
	}
	public Set<Privilege> getChildren() {
		return children;
	}
	public void setChildren(Set<Privilege> children) {
		this.children = children;
	}
	
	

}
