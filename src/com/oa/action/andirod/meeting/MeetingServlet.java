package com.oa.action.andirod.meeting;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.oa.action.andirod.servlet.OaServlet;
import com.oa.bean.MeetingRoom;
import com.oa.bean.MeetingRoomBak;
import com.oa.bean.Privilege;
import com.oa.bean.User;
import com.oa.service.impl.MeetingInfoService;
import com.oa.service.impl.MeetingRoomService;
import com.oa.service.impl.PrivilegeService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class MeetingServlet extends OaServlet {
	private long id;
	private String name;
    private Integer people;
    private Integer state;
    private String roomAddress;
    
    private int rows; 
    private int page;
    private int total;
    
    private String privilegeId;//该权限的id
    
  
    @Resource
    private MeetingRoom meetingRoom;
    @Resource
	PrivilegeService privilegeService;
    

    
     public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	public Integer getPeople() {
		return people;
	}
	public void setPeople(Integer people) {
		this.people = people;
	}
	public Integer getState() {
		return state;
	}
	
	public void setState(Integer state) {
		this.state = state;
	}
	
	
	
	public String getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
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
	public String getRoomAddress() {
		roomAddress=roomAddress.replaceAll("\\s*","");
		return roomAddress;
	}
	public void setRoomAddress(String roomAddress) {	
		roomAddress=roomAddress.replaceAll("\\s*","");
		this.roomAddress = roomAddress;
	}



	@Resource
	MeetingRoomService meetingroomservice;
	@Resource
	MeetingInfoService meetinginfoservice;
	
	
	@Resource
	MeetingRoomBak meetingRoomBak;
	
	Date date=new Date();
	Timestamp ts = new Timestamp(date.getTime());
	
	
	List<MeetingRoom> meetingroomlist;
	//Date date=new Date();
	public static int j=1;
	//List<MeetingRoom> meetingroomlist;
	List<MeetingRoomBak> meetingroombaklist;
	//List meetingroomlist1= meetingroomservice.findAll();
	  public String list(){
		  System.out.println("page:"+page+"rows :"+rows);	
		  List meetingroomlist1= meetingroomservice.findAll();
	    	 JSONArray userJsonArray = new JSONArray();
	 		JSONObject userJsonObject = new JSONObject();
	 		//System.out.println("page:"+page+"rows :"+rows);		
	 		 meetingroomlist= meetingroomservice.findAllByPage(page,rows);
	 		System.out.println("meetingroomlist:  "+meetingroomlist.size());
	 		for(MeetingRoom meetingroom : meetingroomlist){
	 			userJsonObject.put("id", meetingroom.getId());
	 			userJsonObject.put("name", meetingroom.getName());
	 			userJsonObject.put("people", meetingroom.getPeople());
	 			userJsonObject.put("roomAddress", meetingroom.getRoomAddress());
	 			if(meetingroom.getState()==1)
	 			    userJsonObject.put("state", "可用");
	 			else 
	 				userJsonObject.put("state", "不可用");
	 			
	 			userJsonArray.add(userJsonObject);
	 		 } 
	 		//userJsonObject.put("total", 100);
	 		//userJsonArray.add(userJsonObject);
	 			System.out.println("{\"total\":"+meetingroomlist1.size()+",\"rows\":"+userJsonArray.toString()+"}");
	 			outprint("{\"total\":"+meetingroomlist1.size()+",\"rows\":"+userJsonArray.toString()+"}");
		   	
	 		return null;
	     }
	  
	  public String HistoryMeetingRoom(){	 
		  JSONArray userJsonArray = new JSONArray();
	 		JSONObject userJsonObject = new JSONObject();	
		  List<MeetingRoomBak> meetingroombaklist1=meetingroomservice.findRoomBak();	  
		  meetingroombaklist =meetingroomservice.findRoomBakByPage(page,rows);
			 for(MeetingRoomBak meetingroombak : meetingroombaklist){		
		 			userJsonObject.put("id", meetingroombak.getId());
		 			userJsonObject.put("name",meetingroombak.getName());
		 			userJsonObject.put("count",meetingroombak.getPeople());
		 			userJsonObject.put("address",meetingroombak.getRoomAddress());
		 			userJsonObject.put("createTime",meetingroombak.getRoomCreateTime()==null?null:meetingroombak.getRoomCreateTime().toString());
		 			userJsonArray.add(userJsonObject);
		 		 } 		
			 System.out.println(userJsonArray.toString());
	 			outprint("{\"total\":"+meetingroombaklist1.size()+",\"rows\":"+userJsonArray.toString()+"}");
			 
			 return null;
	  }
	
	
	public String addmeetingroom() throws Exception{
		System.out.println(name+"  "+people+"  "+state);
		meetingRoomBak.setName(name);
		meetingRoom.setName(name);
		meetingRoomBak.setPeople(people);
		meetingRoom.setPeople(people);
		meetingRoomBak.setState(state);
		meetingRoom.setState(state);
		meetingRoomBak.setRoomAddress(roomAddress);
		meetingRoom.setRoomAddress(roomAddress);
		meetingRoomBak.setRoomCreateTime(ts);
		meetingroomservice.addmeetingroom(meetingRoom); 
		meetingroomservice.addmeetingroombak(meetingRoomBak);
			//meetingroomlist = meetingroomservice.findAll();
			//ActionContext.getContext().getApplication().put("meetingroomlist", meetingroomlist);
			outprint("{\"success\":true}");	
			return null;
	}
	
	public String back(){
	return "F_add";
	}
	
	public String ApplyUpdateMeetingroom(){
		meetingRoom=meetingroomservice.findById(id);
    	ActionContext.getContext().getSession().put("meetingRoom",meetingRoom);
		return "updateMeetingroom";
	}
	public String update(){
		//int a=Integer.parseInt(request.getParameter("state")); 
		System.out.println(id+"  "+name+"   "+people+" "+state);
		meetingRoom.setId(id);
		meetingRoom.setName(name);
		meetingRoom.setPeople(people);
		meetingRoom.setState(state);
		meetingRoom.setRoomAddress(roomAddress);
		meetingRoomBak.setId(id);
		meetingRoomBak.setName(name);
		meetingRoomBak.setPeople(people);	
		meetingRoomBak.setState(state);
		meetingRoomBak.setRoomAddress(roomAddress);
		meetingRoomBak.setRoomCreateTime(ts);	
		meetingroomservice.updatemeetingroombak(meetingRoomBak);
		meetingroomservice.updatemeetingroom(meetingRoom);	
			outprint("{\"success\":true}");	
			
		return null;
	}
	
	public String MeetInfoUI(){
	  	List<Privilege> privilegeList = privilegeService.findChildPrivileges(Long.parseLong(privilegeId));
			ActionContext.getContext().getSession().put("privilegeList", privilegeList);
			return "MeetInfoUI";
	  }
	
	public String backupdate(){
		return "F_update";
		}
	
	public String delete(){
		 meetingroomservice.deletemeetingroom(id);
		 meetingroomlist = meetingroomservice.findAllByPage(page,rows);
			ActionContext.getContext().getApplication().put("meetingroomlist", meetingroomlist);
			outprint("{\"success\":true}");
		return null;
		
	}
	
	  //手机端
	  //**************************************************************//
	    public void JudgeState(){
	    	int k=0;
	    	int j=0;
	    	//System.out.println("page:"+page+"rows :"+rows);	
			 // List<MeetingRoom> meetingroomlist1= meetingroomservice.findAll();
		    	 JSONArray userJsonArray = new JSONArray();
		 		JSONObject userJsonObject = new JSONObject();
		 		while(j<2){
		 			if(k==0){
		 			userJsonObject.put("name", "会议室管理");	
		 			userJsonObject.put("judge", "1");
		 			k++;
		 			}
		 			else {
		 				userJsonObject.put("name", "会议室预定");	
		 			userJsonObject.put("judge", "0");
		 			}
		 			
		 			
		 			userJsonArray.add(userJsonObject);
		 			j++;
		 			//break;
		 		 } 
		 		
		 			outprint("{\"rows\":"+userJsonArray.toString()+"}");		   	
		 	
		 			
	    	
	    }
	    
	    public void PhoneList(){
	    	
			  List<MeetingRoom> meetingroomlist1= meetingroomservice.findAll();
		    	 JSONArray userJsonArray = new JSONArray();
		 		JSONObject userJsonObject = new JSONObject();
		 		//System.out.println("page:"+page+"rows :"+rows);		
		 		// meetingroomlist= meetingroomservice.findAllByPage(page,rows);
		 		//System.out.println("meetingroomlist:  "+meetingroomlist.size());
		 		for(MeetingRoom meetingroom : meetingroomlist1){
		 			userJsonObject.put("id", meetingroom.getId());
		 			userJsonObject.put("name", meetingroom.getName());
		 			userJsonObject.put("people", meetingroom.getPeople());
		 			userJsonObject.put("roomAddress", meetingroom.getRoomAddress());
		 			if(meetingroom.getState()==1)
		 			    userJsonObject.put("state", "可用");
		 			else 
		 				userJsonObject.put("state", "不可用");
		 			
	
		 			
		 			userJsonArray.add(userJsonObject);
		 		 } 
		 		//userJsonObject.put("total", 100);
		 		//userJsonArray.add(userJsonObject);
		 			System.out.println("{\"rows\":"+userJsonArray.toString()+"}");
		 			outprint("{\"rows\":"+userJsonArray.toString()+"}");
			   	
		 		//return null;
	    	
	    	
	    	
	    }
	    
	    public String PhoneHistoryMeetingRoom(){	 
			  JSONArray userJsonArray = new JSONArray();
		 		JSONObject userJsonObject = new JSONObject();	
			  List<MeetingRoomBak> meetingroombaklist1=meetingroomservice.findRoomBak();	  
			 // meetingroombaklist =meetingroomservice.findRoomBakByPage(page,rows);
				 for(MeetingRoomBak meetingroombak : meetingroombaklist1){		
			 			userJsonObject.put("id", meetingroombak.getId());
			 			userJsonObject.put("name",meetingroombak.getName());
			 			userJsonObject.put("count",meetingroombak.getPeople());
			 			userJsonObject.put("address",meetingroombak.getRoomAddress());
			 			userJsonObject.put("createTime",meetingroombak.getRoomCreateTime()==null?null:meetingroombak.getRoomCreateTime().toString());
			 			userJsonArray.add(userJsonObject);
			 		 } 		
				 System.out.println(userJsonArray.toString());
		 			outprint("{\"total\":"+meetingroombaklist1.size()+",\"rows\":"+userJsonArray.toString()+"}");
				 
				 return null;
		  }
	    
	    
	  
	  
	  
	  //****************************************************************//
	
}
