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
 * ���̹���
 * list����ȡ�Ѿ����ڵ����̵��б��������ݣ���
 * delete��ɾ�����̣��˴���ɾ��ֻ�ǽ������̲��ڽ�������ʾ����ʵ�����ڣ���û�д����ݿ���ɾ�������������ݣ����̵�id����id��ʵ��������ʵ�����̷����id�������ڱ�flowprocess�е�id���ñ��¼�����Ѿ���������̣�
 * addCategory��������̷��࣬�������ݣ�Category_name�����̷��������
 * FlowManageUI��ͨ��action��ת�����̹���ҳ�棬�������ݣ���
 * @author xg_liu
 *
 */
public class FlowdeployServlet extends OaServlet{
	
	private Long id;
	private String Category_name;
	
	private String privilegeId;//��Ȩ�޵�id
	
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

	public String list(){//��ȡ�Ѿ����ڵ����̵��б�
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
	
	public String delete(){//ɾ�����̣��˴���ɾ��ֻ�ǽ������̲��ڽ�������ʾ����ʵ�����ڣ���û�д����ݿ���ɾ����
		flowprocessdao.delete(id);
		outprint("{\"success\":true}");
		return null;
	}
	
	public String addCategoryUI(){
		return "addCategoryUI";
	}
	
	public String addCategory(){//������̷���
		FlowCategory category = new FlowCategory();
		category.setF_name(Category_name);
		flowcategorydao.save(category);
		outprint("{\"success\":true}");
		return null;
	}
	
	public String FlowManageUI(){//ͨ��action��ת�����̹���ҳ��
		List<FlowCategory> result = flowcategorydao.findAll();
		ActionContext.getContext().getSession().put("Categorylist", result);//��ȡ���е����̷��࣬�ڲ�������ʱ��ʾ���е����̷���
		
		List<Privilege> privilegeList = privilegeService.findChildPrivileges(Long.parseLong(privilegeId));
		ActionContext.getContext().getSession().put("privilegeList", privilegeList);//��ȡ���û������̹����µ���Ȩ��
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
