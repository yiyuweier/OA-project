package com.oa.action.andirod.system;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.oa.action.andirod.servlet.OaServlet;
import com.oa.bean.FlowCategory;
import com.oa.bean.FlowProcess;
import com.oa.bean.Privilege;
import com.oa.bean.Role;
import com.oa.dao.FlowCategoryDao;
import com.oa.dao.FlowProcessDao;
import com.oa.service.impl.PrivilegeService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 流程管理
 * list：获取已经存在的流程的列表，传入数据：无
 * delete：删除流程（此处的删除只是将该流程不在界面中显示，其实还存在，并没有从数据库中删除），传入数据：流程的id（该id其实并不是真实的流程分类的id，而是在表flowprocess中的id，该表记录所有已经部署的流程）
 * addCategory：添加流程分类，传入数据：Category_name：流程分类的名字
 * FlowManageUI：通过action跳转到流程管理页面，传入数据：无
 * @author xg_liu
 *
 */
public class FlowdeployServlet extends OaServlet{
	
	private Long id;
	private String Category_name;
	
	private String privilegeId;//该权限的id
	
	private String rows;
	private String page;
	
	
	@Resource
	FlowProcessDao flowprocessdao;
	@Resource
	FlowCategoryDao flowcategorydao;
	@Resource
	PrivilegeService privilegeService;
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

	public String getCategory_name() {
		return Category_name;
	}

	public void setCategory_name(String category_name) {
		Category_name = category_name;
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

	public String list(){//获取已经存在的流程的列表
		List<FlowProcess> resulttotle = flowprocessdao.findAll();
		List<FlowProcess> result = flowprocessdao.findByRowAndPage(rows, page);
		JSONArray flowProcessJsonArray = new JSONArray();
		JSONObject flowProcessJsonObject = new JSONObject();
		for(FlowProcess flowprocess : result){
			flowProcessJsonObject.put("id", flowprocess.getF_id());
			flowProcessJsonObject.put("F_name", flowprocess.getF_name());
			flowProcessJsonObject.put("flowcategory", flowprocess.getFlowcategory().getF_name());
			flowProcessJsonObject.put("F_desc", flowprocess.getF_desc());
			flowProcessJsonArray.add(flowProcessJsonObject);
		}
		System.out.println(flowProcessJsonArray.toString());
		outprint(flowProcessJsonArray.toString());
		return null;
	}
	
	public String delete(){//删除流程（此处的删除只是将该流程不在界面中显示，其实还存在，并没有从数据库中删除）
		flowprocessdao.delete(id);
		outprint("{\"success\":true}");
		return null;
	}
	
	public String addCategoryUI(){
		return "addCategoryUI";
	}
	
	public String addCategory(){//添加流程分类
		FlowCategory category = new FlowCategory();
		category.setF_name(Category_name);
		flowcategorydao.save(category);
		outprint("{\"success\":true}");
		return null;
	}
	
	public String FlowManageUI(){//通过action跳转到流程管理页面
		List<FlowCategory> result = flowcategorydao.findAll();
		ActionContext.getContext().getSession().put("Categorylist", result);//获取所有的流程分类，在部署流程时显示所有的流程分类
		
		List<Privilege> privilegeList = privilegeService.findChildPrivileges(Long.parseLong(privilegeId));
		ActionContext.getContext().getSession().put("privilegeList", privilegeList);//获取该用户在流程管理下的子权限
		return "FlowManageUI";
	}
	
	public String getFlowCategory(){
		JSONArray flowcategoryJsonArray = new JSONArray();
		JSONObject flowcategoryJsonObject = new JSONObject();
		List<FlowCategory> result = flowcategorydao.findAll();
		for(FlowCategory flowcategory : result){
			flowcategoryJsonObject.put("id", flowcategory.getId());
			flowcategoryJsonObject.put("text", flowcategory.getF_name());
			flowcategoryJsonArray.add(flowcategoryJsonObject);
		}
		
		outprint(flowcategoryJsonArray.toString());
		return null;
	}

}
