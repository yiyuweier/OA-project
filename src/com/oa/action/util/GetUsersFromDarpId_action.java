package com.oa.action.util;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.oa.bean.Department;
import com.oa.bean.Role;
import com.oa.bean.User;
import com.oa.service.impl.DepartmentService;
import com.oa.service.impl.RoleService;

@Controller
@Scope("prototype")
/**
 * 根据部门异步获取该部门的人员
 * @author xg_liu
 *
 */
public class GetUsersFromDarpId_action {
	private Long id;
	
	@Resource
	DepartmentService deparmentService;
	
	@Resource
	RoleService roleService;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String FromDarpId(){
		System.out.println(id);
		Department department = deparmentService.findById(id);
    	Set<User> users = department.getUsers();
    	List<String> result = new ArrayList<String>();
    	if(users.size()!=0){
        	for(User user : users){
        		result.add(user.getId()+":"+user.getU_name());
        	}
    	}
    	System.out.println(result.toString());
    	HttpServletResponse response = ServletActionContext.getResponse(); 
        response.setCharacterEncoding("utf-8"); 
        response.setContentType("html/text"); 
        PrintWriter out = null; 
        
        try{ 
            out = response.getWriter(); 
            out.print(result.toString()); 
            out.flush(); 
            out.close(); 
        }catch(Exception e){ 
            e.printStackTrace(); 
        } 
		return "success";
	}
	
	public String FromRoleId(){
		Role role = roleService.findById(id);
		Set<User> users = role.getUsers();
		List<String> result = new ArrayList<String>();
    	if(users.size()!=0){
        	for(User user : users){
        		result.add(user.getId()+":"+user.getU_name());
        	}
    	}
    	System.out.println(result.toString());
    	HttpServletResponse response = ServletActionContext.getResponse(); 
        response.setCharacterEncoding("utf-8"); 
        response.setContentType("html/text"); 
        PrintWriter out = null; 
        
        try{ 
            out = response.getWriter(); 
            out.print(result.toString()); 
            out.flush(); 
            out.close(); 
        }catch(Exception e){ 
            e.printStackTrace(); 
        } 
		return "success";
	}

}
