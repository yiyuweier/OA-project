package com.oa.action.andirod.system;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.oa.action.andirod.servlet.OaServlet;
import com.oa.bean.Department;
import com.oa.bean.Privilege;
import com.oa.service.impl.DepartmentService;
import com.oa.service.impl.PrivilegeService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
/**
 * 部门管理
 * list：获取部门的列表，传入数据：无
 * add：添加部门，传入数据：D_name（部门名称），D_description（部门描述）
 * delete：删除部门，传入数据：departmentid（部门编号）
 * edit：编辑部门，传入数据：departmentid（部门编号）
 * DepartmentManageUI：通过action跳转到部门管理页面
 * @author xg_liu
 *
 */
public class DepartmentServlet extends OaServlet{
	private long id;
	
	private String departmentid;
	private String D_name;
	private String D_description;
	
	private String parentId;
	
	private String privilegeId;//该权限的id
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
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
	
	
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}



	@Resource
	DepartmentService departmentservice;
	
	@Resource
	Department department;
	
	@Resource
	PrivilegeService privilegeService;
	
	public String list(){//获取部门的列表
		List<Department> result = null;
		JSONArray departmentlistJsonArray = new JSONArray();
		JSONObject departmentlistJsonObject = new JSONObject();
		if(parentId == null||parentId.equals("")){
			result = departmentservice.findToplist();
		}else{
			result = departmentservice.findChildlist(Long.parseLong(parentId));
		}
		for(Department department : result){
			departmentlistJsonObject.put("D_id",department.getId());
			departmentlistJsonObject.put("D_name",department.getD_name());
			departmentlistJsonObject.put("D_description", department.getD_description());
			departmentlistJsonObject.put("parentDepartmentName", department.getParent()==null?null:department.getParent().getD_name());
			departmentlistJsonObject.put("parentId", department.getParent()==null?null:department.getParent().getId());
			departmentlistJsonArray.add(departmentlistJsonObject);
			for(Department children : department.getChildrens()){
				departmentlistJsonObject.put("D_id",children.getId());
				departmentlistJsonObject.put("D_name",children.getD_name());
				departmentlistJsonObject.put("D_description", children.getD_description());
				departmentlistJsonObject.put("parentDepartmentName", children.getParent()==null?null:children.getParent().getD_name());
				departmentlistJsonObject.put("parentId", children.getParent()==null?null:children.getParent().getId());
				departmentlistJsonArray.add(departmentlistJsonObject);
			}
		}
		outprint("{\"total\":"+result.size() +",\"rows\":"+departmentlistJsonArray.toString()+"}");
		return null;
	}
	public String add(){//添加部门
		System.out.println("add department!");
		department.setD_name(D_name);
		department.setD_description(D_description);
		System.out.println(parentId+"123");
		if(parentId.equals("0")){
//			department.setParent(null);
			departmentservice.save(department);
			outprint("{\"success\":true}");
			return null;
		}else{
			Department parent_department = departmentservice.findById(Long.parseLong(parentId));
			department.setParent(parent_department);
			departmentservice.save(department);
			outprint("{\"success\":true}");
			return null;
		}
		
	}
	public String delete(){//删除部门
		System.out.println(getId());
		departmentservice.delete(Long.parseLong(departmentid));
		outprint("{\"success\":true}");
		return null;
	}
	public String edit(){//编辑部门
		department = departmentservice.findById(Long.parseLong(departmentid));
//		department.setId(id);
		department.setD_name(D_name);
		department.setD_description(D_description);
		department.setParent(departmentservice.findById(Long.parseLong(parentId)));
		departmentservice.update(department);
		outprint("{\"success\":true}");
		return null;
	}
	public String DepartmentManageUI(){
//		List<Department> Deaprtment_result = departmentservice.findAll();
		List<Department> Deaprtment_result = departmentservice.findTopAndChild();
		ActionContext.getContext().put("departmentlist", Deaprtment_result);
		ActionContext.getContext().put("parentId", parentId);//获取在添加和编辑页面中上级部门的列表
		
		List<Privilege> privilegeList = privilegeService.findChildPrivileges(Long.parseLong(privilegeId));//获取该权限的下级权限
		ActionContext.getContext().getSession().put("privilegeList", privilegeList);
		return "DepartmentManage";
	}
	public String editUI(){
//		List<Department> result = departmentservice.findAll();
		List<Department> result = departmentservice.findTopAndChild();
		ActionContext.getContext().put("departmentlist", result);
		department = departmentservice.findById(id);
		if(department.getParent()!=null){
//			parentId = department.getParent().getId();
		}
		ActionContext.getContext().getValueStack().push(department);
//		ActionContext.getContext().put("id", role.getId());
//		ActionContext.getContext().put("R_name", role.getR_name());
//		ActionContext.getContext().put("R_description", role.getR_description());
		return "editUI";
	}

}
