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
 * ��λ����
 * list����ȡ���и�λ���б��������ݣ���
 * add������µĸ�λ���������ݣ�R_name����λ���ƣ���R_description����λ��������
 * delete��ɾ����λ���������ݣ�id����λ�ı�ţ�
 * edit���༭��λ���������ݣ�id����λ�ı�ţ���R_name����λ���ƣ���R_description����λ��������
 * roleManageUI��ͨ��action��ת����λ�����ҳ�棬�������ݣ�privilegeId����Ȩ�޵�id��
 * privilegeUI��ͨ��action��ת��Ȩ�޹���ҳ�棬�������ݣ�id���ø�λ��id��
 * privilege���޸ĸø�λ��Ȩ�ޣ��������ݣ�Roleid����λ��id����privilegeIdlist���ø�λ��ӵ�е�Ȩ�޵����飩
 * @author xg_liu
 *
 */
@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class RoleServlet extends OaServlet{
	
	private String id;
	private String Roleid;//��λ��id
	
	private String R_name;//��λ������
	private String R_description;//��λ������
	
	private String privilegeId;//��Ȩ�޵�id
	
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
	
	
	private Long[] privilegeIdlist;//�ø�λ��ӵ�е�Ȩ�޵�����
	
	
	
	public Long[] getPrivilegeIdlist() {
		return privilegeIdlist;
	}
	public void setPrivilegeIdlist(Long[] privilegeIdlist) {
		this.privilegeIdlist = privilegeIdlist;
	}
	
	
	public String list(){//��ȡ���и�λ���б�
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
	public String add(){//����µĸ�λ
		role.setR_name(R_name);
		role.setR_description(R_description);
		roleservice.save(role);
		outprint("{\"success\":true}");
		return null;
	}
	public String delete(){//ɾ����λ
		System.out.println(getId());
		roleservice.delete(Long.parseLong(id));
		outprint("{\"success\":true}");
		return null;
	}
	public String edit(){//�༭��λ
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
	public String roleManageUI(){//ͨ��action��ת����λ�����ҳ��
		Long roleid = (long) 0.0;
		List<Privilege> privilegeList = privilegeservice.findChildPrivileges(Long.parseLong(privilegeId));
		ActionContext.getContext().getSession().put("privilegeList", privilegeList);
		
		return "roleManage";
	}
	
	public String privilegeUI(){//ͨ��action��ת��Ȩ�޹���ҳ��
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
	public String privilege(){//�޸ĸø�λ��Ȩ��
		System.out.println("�޸�Ȩ�޵ĸ�λ��idΪ��"+Roleid+"----------");
		role = roleservice.findById(Long.parseLong(Roleid));
		List<Privilege> privileges = privilegeservice.findByIds(privilegeIdlist);
		role.setPrivileges(new HashSet<Privilege>(privileges));
		roleservice.update(role);
		outprint("{\"success\":true,\"data\":{\"url\":\"PostionManage.jsp\",\"name\":\"��λ����\"}}");
		return null;
	}

}
