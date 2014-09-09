package com.oa.action.flow;

import javax.annotation.Resource;

import com.oa.bean.Announcements;
import com.oa.dao.AnnouncementsDao;
import com.opensymphony.xwork2.ActionContext;

public class GetDetailMessage_action {
	private String A_id;

	public String getA_id() {
		return A_id;
	}

	public void setA_id(String a_id) {
		A_id = a_id;
	}
	
	@Resource
	AnnouncementsDao announcementdao;
	
	public String execute(){
		Announcements announcement = announcementdao.findById(Long.parseLong(A_id));
		ActionContext.getContext().getSession().put("A_name", announcement.getA_name());
		ActionContext.getContext().getSession().put("A_content", announcement.getA_content());
		ActionContext.getContext().getSession().put("A_time", announcement.getA_time());
		ActionContext.getContext().getSession().put("A_FJUrl", announcement.getA_FJUrl());
		ActionContext.getContext().getSession().put("A_FJName", announcement.getA_FJName());
		return "success";
	}

}
