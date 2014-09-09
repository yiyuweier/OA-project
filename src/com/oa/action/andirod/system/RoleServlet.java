package com.oa.action.andirod.system;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.oa.action.andirod.servlet.OaServlet;
import com.oa.bean.Privilege;
import com.oa.bean.Role;
import com.oa.bean.User;
import com.oa.service.impl.PrivilegeService;
import com.oa.service.impl.RoleService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;



/**
 * 岗位管理
 * list：获取所有岗位的列表，传入数据：无
 * add：添加新的岗位，传入数据：R_name（岗位名称），R_description（岗位的描述）
 * delete：删除岗位，传入数据：id（岗位的编号）
 * edit：编辑岗位，传入数据：id（岗位的编号），R_name（岗位名称），R_description（岗位的描述）
 * roleManageUI：通过action跳转到岗位管理的页面，传入数据：privilegeId（该权限的id）
 * privilegeUI：通过action跳转到权限管理页面，传入数据：id（该岗位的id）
 * privilege：修改该岗位的权限，传入数据：Roleid（岗位的id），privilegeIdlist（该岗位的拥有的权限的数组）
 * @author xg_liu
 *
 */
@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class RoleServlet extends OaServlet{
	
	private String id;
	private String Roleid;//岗位的id
	
	private String R_name;//岗位的名称
	private String R_description;//岗位的描述
	
	private String privilegeId;//该权限的id
	
	private String rows;
	private String page;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public String getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}
	public String getRoleid() {
		return Roleid;
	}
	public void setRoleid(String roleid) {
		Roleid = roleid;
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



	@Resource
	RoleService roleservice;
	
	@Resource
	PrivilegeService privilegeservice;
	
	@Resource
	Role role;
	
	
	private Long[] privilegeIdlist;//该岗位的拥有的权限的数组
	
	
	
	public Long[] getPrivilegeIdlist() {
		return privilegeIdlist;
	}
	public void setPrivilegeIdlist(Long[] privilegeIdlist) {
		this.privilegeIdlist = privilegeIdlist;
	}
	
	
	public String list(){//获取所有岗位的列表
		JSONArray roleJsonArray = new JSONArray();
		JSONObject roleJsonObject = new JSONObject();
		List<Role> resulttotle = roleservice.findAll();
		List<Role> result = roleservice.findByRowAndPage(rows, page);
		for(Role role : result){
			roleJsonObject.put("postionid", role.getId());
			roleJsonObject.put("R_name", role.getR_name());
			roleJsonObject.put("R_description", role.getR_description());
			roleJsonObject.put("url", "RoleServlet_privilegeUI?id="+role.getId().toString());
			roleJsonArray.add(roleJsonObject);
		}
		outprint("{\"total\":"+resulttotle.size() +",\"rows\":"+roleJsonArray.toString()+"}");
		return null;
	}
	public String add(){//添加新的岗位
		role.setR_name(R_name);
		role.setR_description(R_description);
		roleservice.save(role);
		outprint("{\"success\":true}");
		return null;
	}
	public String delete(){//删除岗位
		System.out.println(getId());
		roleservice.delete(Long.parseLong(id));
		outprint("{\"success\":true}");
		return null;
	}
	public String edit(){//编辑岗位
//		role.setId(id);
		Role role = roleservice.findById(Long.parseLong(id));
		role.setR_name(R_name);
		role.setR_description(R_description);
		roleservice.update(role);
		outprint("{\"success\":true}");
		return null;
	}
	public String addUI(){
		return "addUI";
	}
	public String editUI(){
		role = roleservice.findById(Long.parseLong(id));
		ActionContext.getContext().getValueStack().push(role);
//		ActionContext.getContext().put("id", role.getId());
//		ActionContext.getContext().put("R_name", role.getR_name());
//		ActionContext.getContext().put("R_description", role.getR_description());
		return "editUI";
	}
	public String roleManageUI(){//通过action跳转到岗位管理的页面
		Long roleid = (long) 0.0;
		List<Privilege> privilegeList = privilegeservice.findChildPrivileges(Long.parseLong(privilegeId));
		ActionContext.getContext().getSession().put("privilegeList", privilegeList);
		
		return "roleManage";
	}
	
	public String privilegeUI(){//通过action跳转到权限管理页面
		role = roleservice.findById(Long.parseLong(id));
		R_name = role.getR_name();
		if(role.getPrivileges()!=null){
			privilegeIdlist = new Long[role.getPrivileges().size()];
			int index = 0;
			for(Privilege privilege:role.getPrivileges()){
				privilegeIdlist[index++] = privilege.getId();
			}
		}
		
//		List<Privilege> privileges = privilegeservice.findAll();
//		ActionContext.getContext().getValueStack().push(role);
//		ActionContext.getContext().put("id", role.getId());
//		ActionContext.getContext().put("R_name", role.getR_name());
//		ActionContext.getContext().put("R_description", role.getR_description());
//		ActionContext.getContext().put("privileges", privileges);
		return "privilegeUI";
	}
	public String privilege(){//修改该岗位的权限
		System.out.println("修改权限的岗位的id为："+Roleid+"----------");
		role = roleservice.findById(Long.parseLong(Roleid));
		List<Privilege> privileges = privilegeservice.findByIds(privilegeIdlist);
		role.setPrivileges(new HashSet<Privilege>(privileges));
		roleservice.update(role);
		outprint("{\"success\":true,\"data\":{\"url\":\"PostionManage.jsp\",\"name\":\"岗位管理\"}}");
		return null;
	}

}
