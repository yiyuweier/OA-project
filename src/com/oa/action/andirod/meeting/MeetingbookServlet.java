package com.oa.action.andirod.meeting;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.oa.action.andirod.servlet.OaServlet;
import com.oa.bean.MeetingInfo;
import com.oa.bean.MeetingInfoBak;
import com.oa.bean.MeetingRoom;
import com.oa.bean.MeetingRoomBak;
import com.oa.bean.Privilege;
import com.oa.bean.User;
import com.oa.service.impl.MeetingInfoService;
import com.oa.service.impl.MeetingRoomService;
import com.oa.service.impl.PrivilegeService;
import com.oa.service.impl.UserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
@Controller
@Scope("prototype")
public class MeetingbookServlet extends OaServlet{
	private Long id;

    /** null. */

    MeetingRoom meetingRoom;
    
    
    MeetingRoomBak meetingRoomBak;
    
	private Long meetingRoomId; 
	private int rows; 
    private int page;
    
    private String privilegeId;
	
	User user=(User) ActionContext.getContext().getSession().get("user");
	//user=(User) ActionContext.getContext().getSession().get(user.getName());
	public Long getMeetingRoomId() {
		return meetingRoomId;
	}

	
	public void setMeetingRoomId(Long meetingRoomId) {
		this.meetingRoomId = meetingRoomId;
	}



	public int getRows() {
		return rows;
	}


	public void setRows(int rows) {
		this.rows = rows;
	}


	public int getPage() {
		return page;
	}


	public void setPage(int page) {
		this.page = page;
	}



	public String getPrivilegeId() {
		return privilegeId;
	}


	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}



	/** null. */
    private String name;

    /** null. */
    private Timestamp startTime;
    
	/** null. */
    private Timestamp  endTime;

    /** null. */
    private Long userId;
    
    
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		name=name.replaceAll("\\s*","");
		return name;
	}

	public void setName(String name) {
		name=name.replaceAll("\\s*","");
		this.name = name;
	}


	public Timestamp getStartTime() {
		return startTime;
	}


	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}




	public Timestamp getEndTime() {
		return endTime;
	}


	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

    @Resource
	MeetingRoomService meetingroomservice;
	@Resource
	MeetingInfoService meetinginfoservice;
	@Resource
	UserService userService;
	@Resource
	PrivilegeService privilegeService;
	
    List<MeetingRoom> meetingroomlist;
	
	List<MeetingInfo> meetinginfolist;
	
	List<MeetingInfoBak> meetinginfobaklist;
	
	
	public String MeetingBook(){
		List<Privilege> privilegeList = privilegeService.findChildPrivileges(Long.parseLong(privilegeId));
		ActionContext.getContext().getSession().put("privilegeList", privilegeList);
		
		return "MeetingBook";
	}
   
	//罗列所有info信息
    public String book(){
   	 meetinginfolist = meetinginfoservice.findAll();
   	/* for(int i=0;i<meetinginfolist.size();){
   		if(new Date().after(meetinginfolist.get(i).getEndTime())){		
   			meetinginfolist.remove(i);
   			//meetinginfoservice.deletemeetinginfo(meetinginfolist.get(i).getId());
   		} 		   
   		else i++;
   	 }
   	meetinginfolist = meetinginfoservice.findAll();
   */
   	
   	    JSONArray userJsonArray = new JSONArray();
		JSONObject userJsonObject = new JSONObject();
		
		List<MeetingInfo> meetinginfolist1=meetinginfoservice.findAllByPage(page,rows);
		
		for(MeetingInfo meetingInfo:meetinginfolist1){
			Long a=meetingInfo.getUserId();
			User user=userService.findById(a);
			userJsonObject.put("id", meetingInfo.getId());
			userJsonObject.put("meetingName", meetingInfo.getName());
			userJsonObject.put("starttime", meetingInfo.getStartTime().toString());
			userJsonObject.put("endtime", meetingInfo.getEndTime().toString());
			userJsonObject.put("roomName", meetingInfo.getMeetingRoom().getName());
			userJsonObject.put("bookGuest", user.getU_name());
			userJsonObject.put("booktime", meetingInfo.getInfoCreateTime().toString());
			userJsonArray.add(userJsonObject);
			
			
		}
		 //System.out.println(userJsonArray.toString());
			outprint("{\"total\":"+meetinginfolist.size()+",\"rows\":"+userJsonArray.toString()+"}");
		 
		 return null;
 
    } 
    
    public String HistoryBookMeetingRoom(){
    	 
    	JSONArray userJsonArray = new JSONArray();
		JSONObject userJsonObject = new JSONObject();
    	List<MeetingInfoBak> meetinginfobaklist1=meetinginfoservice.findallbak();
    	
    	meetinginfobaklist=meetinginfoservice.findallbakByPage(page,rows);
    	for(MeetingInfoBak meetingInfoBak:meetinginfobaklist){
    		Long a=meetingInfoBak.getUserId();   		   
    		User user=userService.findById(a);
    		
    		userJsonObject.put("id", meetingInfo.getId());
			userJsonObject.put("meetingName", meetingInfoBak.getName());
			userJsonObject.put("starttime", meetingInfoBak.getStartTime().toString());
			userJsonObject.put("endtime", meetingInfoBak.getEndTime().toString());
			userJsonObject.put("roomName", meetingInfoBak.getMeetingRoomBak().getName());
			userJsonObject.put("bookGuest",user.getU_name());
			userJsonObject.put("booktime", meetingInfoBak.getInfoCreateTime().toString());
			userJsonArray.add(userJsonObject);
  	
    	}
    	//System.out.println(userJsonArray.toString());
		outprint("{\"total\":"+meetinginfobaklist1.size()+",\"rows\":"+userJsonArray.toString()+"}");
    	return null;
    }
    
    
    
    public String createbook(){
   	 meetingroomlist= meetingroomservice.findAll();
			ActionContext.getContext().getApplication().put("meetingRooms", meetingroomlist);
   	 return "NewBook";
    }
    
    @Resource
 	MeetingInfo meetingInfo;
    @Resource
 	MeetingInfoBak meetingInfoBak;
    
    Date date=new Date();
	Timestamp ts = new Timestamp(date.getTime());
    
    
    
    @SuppressWarnings("unchecked")
	public int judgeTime(){
    List<MeetingInfo> list1;
    
    System.out.println(meetingRoomId);
    //ts
    if(new Date().after(endTime)){
    	//this.addFieldError("time_error_add", "该时间已过期，请重新选择！");
    	return 0; 	
    }
    	list1=meetinginfoservice.findMeetingInfoByMeetingRoomId(meetingRoomId);
    int i=0;
    	for(i=0;i<list1.size();i++){
    		if(!((list1.get(i).getStartTime().after(endTime))||(list1.get(i).getEndTime().before(startTime)))){					
    			// this.addFieldError("time_error_add", "预定时间有冲突，请另行选择！");
    			return 0;
			}    		
    	}   	    	
    	return 1;
    }
    
     public String addroominfo() throws Exception{ 	 
 		meetingRoom=meetingroomservice.findById(meetingRoomId);
 		meetingInfo.setName(name);
 		meetingInfo.setStartTime(startTime);
 		meetingInfo.setEndTime(endTime);
 		meetingInfo.setMeetingRoom(meetingRoom);
 		meetingInfo.setUserId(user.getId());
 		meetingInfo.setInfoCreateTime(ts);
 		meetingRoom.getMeetingInfos().add(meetingInfo);
 		meetinginfoservice.addmeetinginfo(meetingInfo); 
 		meetinginfolist = meetinginfoservice.findAll();
 	   	 ActionContext.getContext().getApplication().put("meetinginfolist", meetinginfolist);
 	   	 
 	//向备份表插入数据   	 
 	   	meetingRoomBak=meetingroomservice.findBakById(meetingRoomId);
 	   	meetingInfoBak.setName(name);
 	    meetingInfoBak.setStartTime(startTime);
 	    meetingInfoBak.setEndTime(endTime);
 	    meetingInfoBak.setMeetingRoomBak(meetingRoomBak);	    
 	    meetingInfoBak.setUserId(user.getId()); 
 	    meetingInfoBak.setInfoCreateTime(new Date());
 		meetingRoomBak.getMeetingInfoBaks().add(meetingInfoBak);//?????????
 		meetinginfoservice.addmeetinginfobak(meetingInfoBak);
 		outprint("{\"success\":true}");
 			return null;
 	}
     
 	public String backinfo(){
 		return "F_addinfo";
 		}
 	
 	public String select(){
 		return "select";
 		}
 	
 	
 	public String userdetails(){
 		
 		JSONArray userJsonArray = new JSONArray();
		JSONObject userJsonObject = new JSONObject();
 		List mylist=meetinginfoservice.findByUserId(user.getId());
 		
 		List<MeetingInfo> mylist1=meetinginfoservice.findByUserIdByPage(user.getId(),page,rows);
    	for(MeetingInfo meetingInfo:mylist1){
    		Long a=meetingInfo.getUserId();   		   
    		User user=userService.findById(a);
    		
    		userJsonObject.put("id", meetingInfo.getId());
			userJsonObject.put("meetingName", meetingInfo.getName());
			userJsonObject.put("starttime", meetingInfo.getStartTime().toString());
			userJsonObject.put("endtime", meetingInfo.getEndTime().toString());
			userJsonObject.put("roomName", meetingInfo.getMeetingRoom().getName());
			userJsonObject.put("bookGuest",user.getU_name());
			userJsonObject.put("booktime", meetingInfo.getInfoCreateTime().toString());
			userJsonArray.add(userJsonObject);
  	
    	}
    	//System.out.println(userJsonArray.toString());
		outprint("{\"total\":"+mylist.size()+",\"rows\":"+userJsonArray.toString()+"}");
    	
 		//ActionContext.getContext().getApplication().put("mylist",mylist);
 		return null;
 		
 	}
 	

	public String selectbytime() throws IOException{
 		System.out.println(startTime+"ffffffffff"+endTime);	
 		JSONArray userJsonArray = new JSONArray();
		JSONObject userJsonObject = new JSONObject();

 		meetinginfoservice.selectbytime(startTime, endTime);	
        List<MeetingRoom> notbookrooms=(List)ActionContext.getContext().getApplication().get("notbookrooms");
 		for(MeetingRoom meetingRoom :notbookrooms){
 			userJsonObject.put("id", meetingRoom.getId());
			userJsonObject.put("name", meetingRoom.getName());
			userJsonObject.put("address", meetingRoom.getRoomAddress());
			userJsonObject.put("people", meetingRoom.getPeople());
			userJsonArray.add(userJsonObject);
 		
 		}	
 		outprint("{\"total\":"+notbookrooms.size()+",\"rows\":"+userJsonArray.toString()+"}");
 		return null;		
 	}
 		
	
	@SuppressWarnings("unchecked")
	public String selectbytime1() throws IOException{
 		System.out.println(startTime+"ffffffffff"+endTime);	
 		
 		meetinginfoservice.selectbytime(startTime, endTime);
 
 		
 		List<MeetingRoom> notbookrooms=(List)ActionContext.getContext().getApplication().get("notbookrooms");
 		
 		JSONArray json = JSONArray.fromObject(notbookrooms);
 		System.out.println(json.toString());
 		
 		outprint(json.toString());
 		
 		
 		return null;		
 	}
	
	
	
 	//public String delete(){
 		//meetinginfoservice.deletemeetinginfo(id);
 		//return userdetails();
 	//}
 	
 	public String delete1(){
 		meetinginfoservice.deletemeetinginfo(id);
 		outprint("{\"success\":true}");
 		return null;
 	}
 	
 	 //手机端
	  //**************************************************************//
	    public void JudgeStateBook(){
	    	List<MeetingInfo> meetinginfolist = meetinginfoservice.findAll();
			 // List<MeetingRoom> meetingroomlist1= meetingroomservice.findAll();
		    	 JSONArray userJsonArray = new JSONArray();
		 		JSONObject userJsonObject = new JSONObject();
		 		for(MeetingInfo meetingInfo : meetinginfolist){
		 			
		 			userJsonObject.put("name", "会议室预定");
		 		
		 			userJsonObject.put("judge", "0");
		 				 			
		 			userJsonArray.add(userJsonObject);
		 			break;
		 		 } 
		 		
		 			outprint("{\"rows\":"+userJsonArray.toString()+"}");		   	
	    	
	    }
	
	    public String Phonebook(){
	    	List<MeetingInfo> meetinginfolist2 = meetinginfoservice.findAll();
	      
	      	    JSONArray userJsonArray = new JSONArray();
	   		JSONObject userJsonObject = new JSONObject();
	   		
	   		//List<MeetingInfo> meetinginfolist1=meetinginfoservice.findAllByPage(page,rows);
	   	
	   		for(MeetingInfo meetingInfo:meetinginfolist2){
	   			Long a=meetingInfo.getUserId();
	   			User user=userService.findById(a);
	   			userJsonObject.put("id", meetingInfo.getId());
	   			userJsonObject.put("meetingName", meetingInfo.getName());
	   			userJsonObject.put("starttime", meetingInfo.getStartTime().toString());
	   			userJsonObject.put("endtime", meetingInfo.getEndTime().toString());
	   			userJsonObject.put("roomName", meetingInfo.getMeetingRoom().getName());
	   			userJsonObject.put("bookGuest", user.getU_name());
	   			userJsonObject.put("booktime", meetingInfo.getInfoCreateTime().toString());
	   			userJsonArray.add(userJsonObject);
	   			
	   			
	   		}
	   		
	   			outprint("{\"rows\":"+userJsonArray.toString()+"}");
	   		 
	   		 return null;
	    
	       } 
	    
	    
	    public String phoneuserdetails(){
	 		
	 		JSONArray userJsonArray = new JSONArray();
			JSONObject userJsonObject = new JSONObject();
	 		List<MeetingInfo> mylist=meetinginfoservice.findByUserId(userId);//user.getId()
	 		
	 		//List<MeetingInfo> mylist1=meetinginfoservice.findByUserIdByPage(user.getId(),page,rows);
	    	for(MeetingInfo meetingInfo:mylist){
	    		Long a=meetingInfo.getUserId();   		   
	    		User user=userService.findById(userId);  //a 		
	    		userJsonObject.put("id", meetingInfo.getId());
				userJsonObject.put("meetingName", meetingInfo.getName());
				userJsonObject.put("starttime", meetingInfo.getStartTime().toString());
				userJsonObject.put("endtime", meetingInfo.getEndTime().toString());
				userJsonObject.put("roomName", meetingInfo.getMeetingRoom().getName());
				userJsonObject.put("bookGuest",user.getU_name());
				userJsonObject.put("booktime", meetingInfo.getInfoCreateTime().toString());
				userJsonArray.add(userJsonObject);
	  	
	    	}
	    	//System.out.println(userJsonArray.toString());
			outprint("{\"total\":"+mylist.size()+",\"rows\":"+userJsonArray.toString()+"}");
	    	
	 		//ActionContext.getContext().getApplication().put("mylist",mylist);
	 		return null;
	 		
	 	}
	    
	    
	    public String PhoneAddroominfo() throws Exception{ 	 
	 		meetingRoom=meetingroomservice.findById(meetingRoomId);
	 		meetingInfo.setName(name);
	 		meetingInfo.setStartTime(startTime);
	 		meetingInfo.setEndTime(endTime);
	 		meetingInfo.setMeetingRoom(meetingRoom);
	 		meetingInfo.setUserId(userId);//user.getId()
	 		meetingInfo.setInfoCreateTime(ts);
	 		meetingRoom.getMeetingInfos().add(meetingInfo);
	 		meetinginfoservice.addmeetinginfo(meetingInfo); 
	 		meetinginfolist = meetinginfoservice.findAll();
	 	   	 ActionContext.getContext().getApplication().put("meetinginfolist", meetinginfolist);
	 	   	 
	 	//向备份表插入数据   	 
	 	   	meetingRoomBak=meetingroomservice.findBakById(meetingRoomId);
	 	   	meetingInfoBak.setName(name);
	 	    meetingInfoBak.setStartTime(startTime);
	 	    meetingInfoBak.setEndTime(endTime);
	 	    meetingInfoBak.setMeetingRoomBak(meetingRoomBak);	    
	 	    meetingInfoBak.setUserId(userId); //user.getId()
	 	    meetingInfoBak.setInfoCreateTime(new Date());
	 		meetingRoomBak.getMeetingInfoBaks().add(meetingInfoBak);//?????????
	 		meetinginfoservice.addmeetinginfobak(meetingInfoBak);
	 		outprint("{\"success\":true}");
	 			return null;
	 	}
	    
	    
	   
	
	//************************************************************************************//
 
}
