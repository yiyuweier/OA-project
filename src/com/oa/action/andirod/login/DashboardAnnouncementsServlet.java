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
import com.oa.action.util.ComparatorAnnouncements;
import com.oa.action.util.ComparatorTask;
import com.oa.bean.Announcements;
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
 * 获取通知公告
 * toAndirod：手机端，传入数据：无
 * toComputer：电脑端，传入数据：无
 * @author xg_liu
 *
 */
public class DashboardAnnouncementsServlet extends OaServlet{
	private String rows;
	private String page;
	
	

	
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
	AnnouncementsDao announcementdao;
	
	JSONArray announcementJsonArray = new JSONArray();
	
	
	public String toAndirod() throws IOException{
		
		List<Announcements> announcementlist = announcementdao.findAll();
		JSONObject announcementjsonObject = new JSONObject();
		Collections.sort(announcementlist, new ComparatorAnnouncements<Announcements>());
		
//		List<Announcements> result = new ArrayList<Announcements>();
//		for(int i=0;i<(announcementlist.size()>10?10:announcementlist.size());i++){
//			if(announcementlist.get(i)!=null){
//				result.add(announcementlist.get(i));
//			}
//		}
		
		for(Announcements announcements : announcementlist){
			announcementjsonObject.put("A_name", announcements.getA_name().toString());
			announcementjsonObject.put("A_time", announcements.getA_time().toString());
			announcementjsonObject.put("A_content", announcements.getA_content().toString());
			announcementjsonObject.put("A_FJName", announcements.getA_FJName()==null?"":announcements.getA_FJName().toString());
			announcementjsonObject.put("A_FJUrl", announcements.getA_FJUrl());
			announcementJsonArray.add(announcementjsonObject);
		}
		outprint("{\"total\":"+announcementlist.size() +",\"rows\":"+announcementJsonArray.toString()+"}");
		return null;
	}
	public String toComputer() throws IOException{
		List<Announcements> announcementlist = announcementdao.findByRowAndPage("8", "1");
		Collections.sort(announcementlist,new ComparatorAnnouncements<Announcements>());
		JSONObject announcementjsonObject = new JSONObject();
		for(Announcements announcements : announcementlist){
			announcementjsonObject.put("A_name", announcements.getA_name().toString());
			announcementjsonObject.put("A_time", announcements.getA_time().toString());
			announcementjsonObject.put("url", "GetDetailMessage?A_id="+announcements.getId().toString());
			announcementJsonArray.add(announcementjsonObject);
		}
		outprint("{\"total\":"+announcementlist.size() +",\"rows\":"+announcementJsonArray.toString()+"}");
		return null;
	}
	
	public String toComputerAllAnnouncements() throws IOException{
		List<Announcements> announcementlist1 = announcementdao.findAll();
		List<Announcements> announcementlist = announcementdao.findByRowAndPage(rows, page);
		Collections.sort(announcementlist,new ComparatorAnnouncements<Announcements>());
		JSONObject announcementjsonObject = new JSONObject();
		for(Announcements announcements : announcementlist){
			announcementjsonObject.put("A_name", announcements.getA_name().toString());
			announcementjsonObject.put("A_time", announcements.getA_time().toString());
			announcementjsonObject.put("url", "GetDetailMessage?A_id="+announcements.getId().toString());
			announcementJsonArray.add(announcementjsonObject);
		}
		outprint("{\"total\":"+announcementlist1.size() +",\"rows\":"+announcementJsonArray.toString()+"}");
		
		return null;
		
	}
}
