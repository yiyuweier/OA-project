package com.oa.action.andirod.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.jbpm.api.task.Task;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.oa.action.andirod.servlet.OaServlet;
import com.oa.action.util.ComparatorTask;
import com.oa.bean.Document;
import com.oa.bean.FlowCategory;
import com.oa.bean.FlowProcess;
import com.oa.bean.Message;
import com.oa.bean.MyFlow;
import com.oa.bean.User;
import com.oa.bean.Vacate;
import com.oa.dao.AnnouncementsDao;
import com.oa.dao.DocumentDao;
import com.oa.dao.FlowCategoryDao;
import com.oa.dao.MessageDao;
import com.oa.dao.VacateDao;
import com.oa.jbpm.operation.BaseJbpmOperation;
import com.oa.service.impl.UserService;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
/**
 * 获取常用流程
 * toComputer：电脑端获取常用流程，传入数据：无
 * toAndirod：手机端获取常用流程，传入数据：无
 * @author xg_liu
 *
 */
public class DashboardFlowServlet extends OaServlet{

	@Resource
	FlowCategoryDao flowcategorydao;
	
	JSONArray flowJsonArray = new JSONArray();
	
	public String toComputer() throws IOException{
		
		List<FlowCategory> result = flowcategorydao.findAll();
		JSONObject jsonFlowObject = new JSONObject();
		for(FlowCategory flowcategory : result){
			for(FlowProcess flowprocess : flowcategory.getFlowProcess()){
				jsonFlowObject.put("F_name", flowprocess.getF_name().toString());
				jsonFlowObject.put("url", "workplace_"+flowprocess.getF_key()+"?flowProcessId="+flowprocess.getF_id());
				flowJsonArray.add(jsonFlowObject);
			}
		}
		System.out.println("{\"total\":"+result.size() +",\"rows\":"+flowJsonArray.toString()+"}");
		outprint("{\"total\":"+result.size() +",\"rows\":"+flowJsonArray.toString()+"}");
		return null;
	}
	
public String toAndirod() throws IOException{
		
		List<FlowCategory> result = flowcategorydao.findAll();
		JSONObject jsonFlowObject = new JSONObject();
		for(FlowCategory flowcategory : result){
			for(FlowProcess flowprocess : flowcategory.getFlowProcess()){
				jsonFlowObject.put("F_name", flowprocess.getF_name().toString());
				jsonFlowObject.put("type", flowprocess.getF_key());
				jsonFlowObject.put("url", "workplaceStartProcessInstanceServlet_"+flowprocess.getF_key());
				flowJsonArray.add(jsonFlowObject);
			}
		}
		System.out.println("{\"total\":"+result.size() +",\"rows\":"+flowJsonArray.toString()+"}");
		outprint("{\"total\":"+result.size() +",\"rows\":"+flowJsonArray.toString()+"}");
		return null;
	}
}
