package com.oa.bean;

import java.util.HashSet;
import java.util.Set;


import org.springframework.stereotype.Component;

/**
 * ¸ÚÎ»
 * @author Administrator
 *
 */
@Component
public class Role {
	
	private Long id;
	private String R_name;
	private String R_description;
	private Set<User> users = new HashSet<User>();
	private Set<Privilege> privileges = new HashSet<Privilege>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getR_name() {
		return R_name;
	}
	public void setR_name(String r_name) {
		R_name = r_name;
	}
	public String getR_description() {
		return R_description;
	}
	public void setR_description(String r_description) {
		R_description = r_description;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public Set<Privilege> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}
	
	

}
