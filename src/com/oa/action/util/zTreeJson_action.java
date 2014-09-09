package com.oa.action.util;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;




import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.oa.bean.Department;
import com.oa.bean.User;
import com.oa.service.impl.DepartmentService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
/**
 * 异步获取部门
 * @author xg_liu
 *
 */
public class zTreeJson_action extends ActionSupport{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Long id;
	
	
	 @Resource
     DepartmentService deparmentService;
	 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String execute(){
//		System.out.println("hello!");
//        System.out.println(id);
//        System.out.println("first");
        JSONArray jsonArray = new JSONArray(); 
        JSONArray jsonArrayflag = new JSONArray();
        JSONObject jsonobject = new JSONObject();
        
        if(id==-1){
        	List<Department> result = deparmentService.findToplist();
//        	if(result.isEmpty()){
//        		System.out.println("result is null!");
//        	}else{
//        		System.out.println("result is not null!");
//        	}
        	//jsonArray = JSONArray.fromObject(result);
            for(Department department : result){
//            	System.out.println("here-----------------------execute for");
            	List<Department> islast = deparmentService.findChildlist(department.getId());
            	jsonobject.put("id", department.getId());
            	jsonobject.put("pId", "0");
            	jsonobject.put("name", department.getD_name());
            	if(islast.size()!=0){
            		jsonobject.put("click", "showusers('"+department.getId()+"');");//click是传到前段的javascript函数
            		jsonobject.put("isParent", true);
            	}else{
            		jsonobject.put("click", "showusers('"+department.getId()+"','"+"department"+"');");
            		jsonobject.put("isParent", false);
            	}
            	jsonArray.add(jsonobject);
            }
            
//            System.out.println("id is null");
    
        }else{
        	List<Department> result = deparmentService.findChildlist(id);
            if(result.size()!=0){
	            for(Department department : result){
	            	List<Department> islast = deparmentService.findChildlist(department.getId());
	            	jsonobject = new JSONObject(); 
	            	jsonobject.put("id", department.getId());
	            	jsonobject.put("pId", "0");
	            	jsonobject.put("name", department.getD_name());
	            	if(islast.size()!=0){
	            		jsonobject.put("click", "showusers('"+department.getId()+"');");
	            		jsonobject.put("isParent", true);
	            	}else{
	            		jsonobject.put("click", "showusers('"+department.getId()+"','"+"department"+"');");
	            		jsonobject.put("isParent", false);
	            	}
	            	jsonArray.add(jsonobject);
	            }
//            }else{
//            	jsonobject = new JSONObject(); 
//            	jsonobject.put("id", "-2");
//            	jsonobject.put("pId", "0");
//            	jsonobject.put("name", "该部门没有子部门");
//            	jsonobject.put("isParent", false);
//            	jsonArray.add(jsonobject);
//            	jsonArrayflag.add(jsonobject);
            }
//            System.out.println("id is not null");
        }
        
//        System.out.println(jsonArray.toString());
//        System.out.println("[{'id':'-2','pId':'0','name':'该部门没有子部门','isParent':false}]");
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
