package com.oa.action.andirod.feedback;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import com.oa.action.andirod.servlet.OaServlet;
import com.oa.bean.FeedBackInfo;
import com.oa.dao.FeedBackDao;

public class FeedBackServlet extends OaServlet{
	
	@Resource
	FeedBackDao feedbackdao;
	
	public String execute(){
		
		String feedbackinfo = (String) getRequest().getParameter("feedbackinfo");
		String userid = (String) getRequest().getParameter("userid");
		
		System.out.println("feedbackinfo="+feedbackinfo);
		System.out.println("userid="+userid);
		
		FeedBackInfo feedbackObject = new FeedBackInfo();
		feedbackObject.setFeedbackinfo(feedbackinfo);
		feedbackObject.setUserid(userid);
		
		SimpleDateFormat myfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		feedbackObject.setDate(myfmt.format(date).toString());
		
		feedbackdao.save(feedbackObject);
		
		outprint("{\"success\":true}");
		
		return null;
		
	}
	
	
	
}
