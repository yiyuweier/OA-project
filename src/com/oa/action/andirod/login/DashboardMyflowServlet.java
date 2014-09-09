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
import com.oa.bean.Document;
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
 * 获取我的流程
 * toCompute：电脑端，传入数据：userid
 * toAndirod：手机端，传入数据：userid
 * @author xg_liu
 *
 */
public class DashboardMyflowServlet extends OaServlet{

	private String userid;
	
	private String rows;
	private String page;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
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
	BaseJbpmOperation basejbpmoperation;
	@Resource
	FlowCategoryDao flowcategorydao;
	@Resource
	AnnouncementsDao announcementdao;
	@Resource
	DocumentDao documentdao;
	@Resource
	VacateDao vacatedao;
	@Resource
	MessageDao messagedao;
	@Resource
	UserService userService;
	
	JSONArray myflowJsonArray = new JSONArray();
	
	
	public String toCompute() throws IOException{
		
		User user = userService.findById(Long.parseLong(userid));
		String username = user.getU_name();
		
		//*************************本人发起的请假流程************************
		List<Vacate> vacatelist = vacatedao.getVacateByInitiator(userid);//获取发起人为username的请假信息
		//*************************************************************
		
		//*************************本人发起的通知流程************************
		List<Message> messagelist = messagedao.getMessageByInitiatorAndUniteName(userid);//获取发起人为username的通知流程信息
		//*************************************************************
		
		//*************************本人发起的审批流程************************
		List<Document> documentlist = documentdao.getDocumentByInitiatorAndUniteName(userid);
		//*************************************************************
		
		//*************************************************************
		List<MyFlow> myflowlist = new ArrayList<MyFlow>();
		
		for(Vacate vacate : vacatelist){
			MyFlow myflow = new MyFlow();
			int key = Integer.parseInt(vacate.getAskForLeave_type());
			String name = null;
			switch (key) {
			case 1:
				name = "婚假";
				break;
			case 2:
				name = "产假";
				break;
			case 3:
				name = "病假";
				break;
			case 4:
				name = "倒休";
				break;
			default:
				break;
			}
			myflow.setM_name(name);
			myflow.setM_date(vacate.getDate());
			myflow.setM_type("AskForLeave");
			myflowlist.add(myflow);
		}
		for(Message message : messagelist){
			MyFlow myflow = new MyFlow();
			myflow.setM_name(message.getM_name());
			myflow.setM_date(message.getM_date());
			myflow.setM_type("Message");
			myflowlist.add(myflow);
		}
		for(Document document : documentlist){
			MyFlow myflow = new MyFlow();
			myflow.setM_name(document.getD_name());
			myflow.setM_date(document.getD_date());
			myflow.setM_type("Document");
			myflowlist.add(myflow);
		}
		//*************************************************************
		
		//********************对list中的数据按照date来排序（从大到小）************
		Collections.sort(myflowlist);
		ActionContext.getContext().getSession().put("mydocumentlist", myflowlist);
		ActionContext.getContext().getSession().put("mydocumentlistsize", myflowlist.size());
		//*************************************************************
		
		JSONObject jsonMyflowObject = new JSONObject();
		
//		int rowsize = (Integer.parseInt(page)-1)*Integer.parseInt(rows);
//		int row = Integer.parseInt(rows);
//		System.out.println("从第几个开始="+rowsize);
//		System.out.println("每页几行="+row);
		List<MyFlow> result = new ArrayList<MyFlow>();
		for(int i=0;i<(myflowlist.size()>8?8:myflowlist.size());i++){
			if(myflowlist.get(i)!=null){
				result.add(myflowlist.get(i));
			}
		}
		
		for(MyFlow myflow : result){
			jsonMyflowObject.put("M_name", myflow.getM_name().toString());
			jsonMyflowObject.put("M_date", myflow.getM_date().toString());
			jsonMyflowObject.put("url", "getMyFlowDetail?D_name="+myflow.getM_name().toString()+"&username="+userid+"&flowtype="+myflow.getM_type().toString()+"&date="+myflow.getM_date().toString());
			myflowJsonArray.add(jsonMyflowObject);
		}
		outprint("{\"total\":"+myflowlist.size()+",\"rows\":"+myflowJsonArray.toString()+"}");
		return null;
	}
	
	public String toComputeAllRunFlow() throws IOException{
		
		User user = userService.findById(Long.parseLong(userid));
		String username = user.getU_name();
		
		//*************************本人发起的请假流程************************
		List<Vacate> vacatelist = vacatedao.getVacateByInitiator(userid);//获取发起人为username的请假信息
		//*************************************************************
		
		//*************************本人发起的通知流程************************
		List<Message> messagelist = messagedao.getMessageByInitiatorAndUniteName(userid);//获取发起人为username的通知流程信息
		//*************************************************************
		
		//*************************本人发起的审批流程************************
		List<Document> documentlist = documentdao.getDocumentByInitiatorAndUniteName(userid);
		//*************************************************************
		
		//*************************************************************
		List<MyFlow> myflowlist = new ArrayList<MyFlow>();
		
		for(Vacate vacate : vacatelist){
			MyFlow myflow = new MyFlow();
			int key = Integer.parseInt(vacate.getAskForLeave_type());
			String name = null;
			switch (key) {
			case 1:
				name = "婚假";
				break;
			case 2:
				name = "产假";
				break;
			case 3:
				name = "病假";
				break;
			case 4:
				name = "倒休";
				break;
			default:
				break;
			}
			myflow.setM_name(name);
			myflow.setM_date(vacate.getDate());
			myflow.setM_type("AskForLeave");
			myflowlist.add(myflow);
		}
		for(Message message : messagelist){
			MyFlow myflow = new MyFlow();
			myflow.setM_name(message.getM_name());
			myflow.setM_date(message.getM_date());
			myflow.setM_type("Message");
			myflowlist.add(myflow);
		}
		for(Document document : documentlist){
			MyFlow myflow = new MyFlow();
			myflow.setM_name(document.getD_name());
			myflow.setM_date(document.getD_date());
			myflow.setM_type("Document");
			myflowlist.add(myflow);
		}
		//*************************************************************
		
		//********************对list中的数据按照date来排序（从大到小）************
		Collections.sort(myflowlist);
		ActionContext.getContext().getSession().put("mydocumentlist", myflowlist);
		ActionContext.getContext().getSession().put("mydocumentlistsize", myflowlist.size());
		//*************************************************************
		
		JSONObject jsonMyflowObject = new JSONObject();
		
		int rowsize = (Integer.parseInt(page)-1)*Integer.parseInt(rows);
		int row = Integer.parseInt(rows);
		System.out.println("从第几个开始="+rowsize);
		System.out.println("每页几行="+row);
		List<MyFlow> result = new ArrayList<MyFlow>();
		for(int i=rowsize;i<((rowsize+row)>myflowlist.size()?myflowlist.size():(rowsize+row));i++){
			if(myflowlist.get(i)!=null){
				result.add(myflowlist.get(i));
			}
		}
		
		for(MyFlow myflow : result){
			jsonMyflowObject.put("M_name", myflow.getM_name().toString());
			jsonMyflowObject.put("M_date", myflow.getM_date().toString());
			jsonMyflowObject.put("url", "getMyFlowDetail?D_name="+myflow.getM_name().toString()+"&username="+userid+"&flowtype="+myflow.getM_type().toString());
			myflowJsonArray.add(jsonMyflowObject);
		}
		outprint("{\"total\":"+myflowlist.size()+",\"rows\":"+myflowJsonArray.toString()+"}");
		return null;
	}
	
public String toAndirod() throws IOException{
		
//		User user = userService.findById(Long.parseLong(userid));
//		String username = user.getU_name();
//		String userid = (String) getRequest().getParameter("userid");
		System.out.println("useid="+userid);
		
		//*************************本人发起的请假流程************************
		List<Vacate> vacatelist = vacatedao.getVacateByInitiator(userid);
		//*************************************************************
		
		//*************************本人发起的通知流程************************
		List<Message> messagelist = messagedao.getMessageByInitiatorAndUniteName(userid);
		//*************************************************************
		
		//*************************本人发起的审批流程************************
		List<Document> documentlist = documentdao.getDocumentByInitiatorAndUniteName(userid);
		//*************************************************************
		
		//*************************************************************
		List<MyFlow> myflowlist = new ArrayList<MyFlow>();
		
		for(Vacate vacate : vacatelist){
			MyFlow myflow = new MyFlow();
			int key = Integer.parseInt(vacate.getAskForLeave_type());
			String name = null;
			switch (key) {
			case 1:
				name = "婚假";
				break;
			case 2:
				name = "产假";
				break;
			case 3:
				name = "病假";
				break;
			case 4:
				name = "倒休";
				break;
			default:
				break;
			}
			myflow.setM_name(name);
			myflow.setM_date(vacate.getDate());
			myflow.setM_type("AskForLeave");
			myflowlist.add(myflow);
		}
		for(Message message : messagelist){
			MyFlow myflow = new MyFlow();
			myflow.setM_name(message.getM_name());
			myflow.setM_date(message.getM_date());
			myflow.setM_type("Message");
			myflowlist.add(myflow);
		}
		for(Document document : documentlist){
			MyFlow myflow = new MyFlow();
			myflow.setM_name(document.getD_name());
			myflow.setM_date(document.getD_date());
			myflow.setM_type("Document");
			myflowlist.add(myflow);
		}
		//*************************************************************
		
		//********************对list中的数据按照date来排序（从大到小）************
		Collections.sort(myflowlist);
		ActionContext.getContext().getSession().put("mydocumentlist", myflowlist);
		ActionContext.getContext().getSession().put("mydocumentlistsize", myflowlist.size());
		//*************************************************************
		
		JSONObject jsonMyflowObject = new JSONObject();
		for(MyFlow myflow : myflowlist){
			jsonMyflowObject.put("M_name", myflow.getM_name().toString());
			jsonMyflowObject.put("M_date", myflow.getM_date().toString());
//			jsonMyflowObject.put("M_type", myflow.getM_type().toString());
			jsonMyflowObject.put("url", "getMyFlowDetailServlet?D_name="+myflow.getM_name().toString()+"&username="+userid+"&flowtype="+myflow.getM_type().toString()+"&date="+myflow.getM_date().toString());
			myflowJsonArray.add(jsonMyflowObject);
		}
		outprint("{\"total\":"+myflowlist.size()+",\"rows\":"+myflowJsonArray.toString()+"}");
		return null;
	}
}
