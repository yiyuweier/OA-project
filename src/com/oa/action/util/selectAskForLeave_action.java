package com.oa.action.util;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Repository;

import com.oa.bean.Department;
import com.oa.bean.User;
import com.oa.dao.UserDao;
import com.opensymphony.xwork2.ActionContext;
@Repository
public class selectAskForLeave_action {
	
	
	@Resource
	public UserDao userdao;
	
	public String execute(){
		System.out.println("hello!");
        System.out.println("first");
        JSONArray jsonArray = new JSONArray(); 
        JSONObject jsonobject = new JSONObject();
        List<User> result = userdao.findAll();
        for(User user : result){
        	System.out.println("usreid="+ user.getId());
        	jsonobject.put("id", user.getId());
        	jsonobject.put("U_name", user.getU_name());
        	jsonobject.put("D_name", user.getDepartment().getD_name());
        	jsonArray.add(jsonobject);
        
        }
        
        HttpServletResponse response = ServletActionContext.getResponse(); 
        response.setCharacterEncoding("utf-8"); 
        response.setContentType("html/text"); 
        PrintWriter out = null; 
        
        try{ 
            out = response.getWriter(); 
            out.print(jsonArray.toString()); 
            System.out.println(jsonArray.toString());
            out.flush(); 
            out.close(); 
        }catch(Exception e){ 
            e.printStackTrace(); 
        } 
        return "success";
        
	}

}
