package com.oa.action.andirod.car;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.oa.action.andirod.servlet.OaServlet;
import com.oa.bean.CarBak;
import com.oa.bean.CarInfo;
import com.oa.bean.Car;
import com.oa.bean.CarInfoBak;
import com.oa.bean.MeetingInfo;
import com.oa.bean.MeetingRoom;
import com.oa.bean.Privilege;
import com.oa.bean.User;
import com.oa.dao.CarInfoDao;
import com.oa.service.impl.CarService;
import com.oa.service.impl.CarInfoService;
import com.oa.service.impl.PrivilegeService;
import com.oa.service.impl.UserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class CarbookServlet extends OaServlet{

	@Resource
	CarInfoService carInfoService;	
	List<CarInfo> carinfolist;
	List<CarInfo> userCarList;
	@Resource
	CarInfo carInfo;
	@Resource
	CarInfoBak carInfoBak;
	@Resource
	CarBak carbak;
	@Resource
	UserService userService;
	@Resource
	PrivilegeService privilegeService;
	
	private Long id;

    /** null. */
    private Car car;
    
	/** null. */
    private String carUsage;

   
	/** null. */
    private Timestamp carStartTime;

    /** null. */
    private Timestamp carEndTime;

    private Timestamp carInfoCreateTime;
    /** null. */
    private Long userId;
    
    private Long carId;
    
    private String privilegeId;
    
    private int rows; 
	 private int page;
    User user=(User) ActionContext.getContext().getSession().get("user");
     
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}


	
	public Long getCarId() {
		return carId;
	}

	public void setCarId(Long carId) {
		this.carId = carId;
	}

	public String getCarUsage() {
		return carUsage;
	}
	public void setCarUsage(String carUsage) {
		this.carUsage = carUsage;
	}
	
	public Timestamp getCarStartTime() {
		return carStartTime;
	}
	public void setCarStartTime(Timestamp carStartTime) {
		this.carStartTime = carStartTime;
	}
	public Timestamp getCarEndTime() {
		return carEndTime;
	}
	public void setCarEndTime(Timestamp carEndTime) {
		this.carEndTime = carEndTime;
	}
	public Timestamp getCarInfoCreateTime() {
		return carInfoCreateTime;
	}
	public void setCarInfoCreateTime(Timestamp carInfoCreateTime) {
		this.carInfoCreateTime = carInfoCreateTime;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	public String getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}


	@Resource
	CarService carservice;	
	List<Car> carlist;
	
	Date date=new Date();
	Timestamp ts = new Timestamp(date.getTime());
	
	public String list(){
		JSONArray userJsonArray = new JSONArray();
		JSONObject userJsonObject = new JSONObject();
		carinfolist= carInfoService.findAll();
		/* for(int i=0;i<carinfolist.size();){
		   		if(new Date().after(carinfolist.get(i).getCarEndTime())){		
		   			carinfolist.remove(i);
		   			//carInfoService.deleteCarInfo(carinfolist.get(i).getId());
		   		} 		   
		   		else i++;
		   	 }
		 
		 System.out.println(carinfolist.size());*/
	     List<CarInfo> carinfolist1=carInfoService.findAllByPage(page,rows);
		 
    	for(CarInfo carInfo :carinfolist1){
    		
    		Long a=carInfo.getUserId();
			User user=userService.findById(a);
			
    		userJsonObject.put("id", carInfo.getId());
    		userJsonObject.put("carUsage", carInfo.getCarUsage());
    		userJsonObject.put("starttime", carInfo.getCarStartTime().toString());
    		userJsonObject.put("endtime", carInfo.getCarEndTime().toString());
    		userJsonObject.put("carName", carInfo.getCar().getCarName());
    		userJsonObject.put("code", carInfo.getCar().getCode());
    		userJsonObject.put("bookGuest", user.getU_name());
    		userJsonObject.put("booktime", carInfo.getCarInfoCreateTime().toString());
    		
    		userJsonArray.add(userJsonObject);		
    	}
    	outprint("{\"total\":"+carinfolist.size()+",\"rows\":"+userJsonArray.toString()+"}");
		return null;		
	}
	
	public String Applyaddcarinfo(){
		carlist= carservice.findAll();
    	ActionContext.getContext().getApplication().put("carlist",carlist);	
		return "Applyaddcarinfo";
	}
	
	 
    @SuppressWarnings("unchecked")
	public int judgeTime(){
    List<CarInfo> list1;
    
   // System.out.println(meetingRoomId);
    //ts
    if(new Date().after(carEndTime)){
    	//this.addFieldError("time_error_add", "该时间已过期，请重新选择！");
    	return 0; 	
    }
    	list1=carInfoService.findCarInfoByCarId(carId);
    int i=0;
    	for(i=0;i<list1.size();i++){
    		if(!((list1.get(i).getCarStartTime().after(carEndTime))||(list1.get(i).getCarEndTime().before(carStartTime)))){					
    			// this.addFieldError("time_error_add", "预定时间有冲突，请另行选择！");
    			return 0;
			}    		
    	}   	    	
    	return 1;
    }
	
	public String addcarinfo(){
		
		
		
		//System.out.println("id: "+id);
		car=carservice.findById(carId);
		carInfo.setCarUsage(carUsage);
		carInfo.setCarStartTime(carStartTime);
		carInfo.setCarEndTime(carEndTime);
		carInfo.setCarInfoCreateTime(ts);
		carInfo.setCar(car);
		carInfo.setUserId(user.getId());
		car.getCarInfos().add(carInfo);
		carInfoService.addCarinfo(carInfo);
		
		carbak=carservice.findCarBakById(carId);
		carInfoBak.setCarUsage(carUsage);
		carInfoBak.setCarStartTime(carStartTime);
		carInfoBak.setCarEndTime(carEndTime);
		carInfoBak.setCarInfoCreateTime(ts);
		carInfoBak.setCarBak(carbak);
		carInfoBak.setUserId(user.getId());
		carbak.getCarInfoBaks().add(carInfoBak);
		carInfoService.addCarinfobak(carInfoBak);
		
		outprint("{\"success\":true}");	
	return null;			
	}
	
	public String deleteCarInfo(){
		if(id!=null){
		carInfoService.deleteCarInfo(id);
		outprint("{\"success\":true}");	
		}
		return null;	
	}
	
   public String deleteUserCarInfo(){
	   if(id!=null){
	   carInfoService.deleteCarInfo(id);
	   }
	   return userCarDetails();
	}
	
	public String userCarDetails(){
		userCarList=carInfoService.findCarByUserId(user.getId());
		JSONArray userJsonArray = new JSONArray();
		JSONObject userJsonObject = new JSONObject();
		
		List<CarInfo> userCarList1=carInfoService.findCarByUserIdByPage(user.getId(),page,rows);
             
		for(CarInfo carInfo :userCarList1){
    		
    		Long a=carInfo.getUserId();
			User user=userService.findById(a);
			
    		userJsonObject.put("id", carInfo.getId());
    		userJsonObject.put("carUsage", carInfo.getCarUsage());
    		userJsonObject.put("starttime", carInfo.getCarStartTime().toString());
    		userJsonObject.put("endtime", carInfo.getCarEndTime().toString());
    		userJsonObject.put("carName", carInfo.getCar().getCarName());
    		userJsonObject.put("code", carInfo.getCar().getCode());
    		userJsonObject.put("bookGuest", user.getU_name());
    		userJsonObject.put("booktime", carInfo.getCarInfoCreateTime().toString());
    		
    		userJsonArray.add(userJsonObject);		
    	}
            outprint("{\"total\":"+userCarList.size()+",\"rows\":"+userJsonArray.toString()+"}");
		return null;		
	}
	
	
	public String selectbytime() throws IOException{
 		System.out.println(carStartTime+"ffffffffff"+carEndTime);	
 		JSONArray userJsonArray = new JSONArray();
		JSONObject userJsonObject = new JSONObject();

		carInfoService.selectbytime(carStartTime, carEndTime);	
        List<Car> notbookcars=(List)ActionContext.getContext().getApplication().get("notbookcars");
 		for(Car car :notbookcars){
 			userJsonObject.put("id", car.getId());
			userJsonObject.put("carName", car.getCarName());
			userJsonObject.put("code", car.getCode());
			userJsonObject.put("carPeople", car.getCarPeople());
			userJsonArray.add(userJsonObject);
 		
 		}	
 		System.out.println("{\"total\":"+notbookcars.size()+",\"rows\":"+userJsonArray.toString()+"}");
		 outprint("{\"total\":"+notbookcars.size()+",\"rows\":"+userJsonArray.toString()+"}");
 		return null;		
 	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public String selectbytime1() throws IOException{
 		System.out.println(carStartTime+"gggggggggg"+carEndTime);	
 		carInfoService.selectbytime(carStartTime, carEndTime);
 		
 		
 		List<Car> notbookcars=(List<Car>)ActionContext.getContext().getApplication().get("notbookcars");		
 		JSONArray json = JSONArray.fromObject(notbookcars);
 		//System.out.println(json.toString());
 		
 		outprint(json.toString());
 	
 		
 		return null;		
 	}
	
	 public String CarBookUI(){
	    	List<Privilege> privilegeList = privilegeService.findChildPrivileges(Long.parseLong(privilegeId));
			ActionContext.getContext().getSession().put("privilegeList", privilegeList);
			return "CarBookUI";
	    }
	
	public String HistoryBookInfo(){
		JSONArray userJsonArray = new JSONArray();
		JSONObject userJsonObject = new JSONObject();
		List<CarInfoBak> carinfobaklist=carInfoService.findAllCarInfobak();
		List<CarInfoBak> carinfobaklist1=carInfoService.findAllCarInfobakByPage(page,rows);
    	for(CarInfoBak carInfoBak :carinfobaklist1){
    		
    		Long a=carInfoBak.getUserId();
			User user=userService.findById(a);
			
    		userJsonObject.put("id", carInfoBak.getId());
    		userJsonObject.put("carUsage", carInfoBak.getCarUsage());
    		userJsonObject.put("starttime", carInfoBak.getCarStartTime().toString());
    		userJsonObject.put("endtime", carInfoBak.getCarEndTime().toString());
    		userJsonObject.put("carName",carInfoBak.getCarBak().getCarName());
    		userJsonObject.put("code", carInfoBak.getCarBak().getCode());
    		userJsonObject.put("bookGuest", user.getU_name());
    		userJsonObject.put("booktime", carInfoBak.getCarInfoCreateTime().toString());
    		
    		userJsonArray.add(userJsonObject);		
    	}
    	outprint("{\"total\":"+carinfobaklist.size()+",\"rows\":"+userJsonArray.toString()+"}");
		return null;	
		
		
		
	
	}
	
	//手机端
		//********************************************************************************//
		public String PhoneCarBooklist(){
			JSONArray userJsonArray = new JSONArray();
			JSONObject userJsonObject = new JSONObject();
			carinfolist= carInfoService.findAll();
			
		    // List<CarInfo> carinfolist1=carInfoService.findAllByPage(page,rows);
			 
	    	for(CarInfo carInfo :carinfolist){
	    		
	    		Long a=carInfo.getUserId();
				User user=userService.findById(a);
				
	    		userJsonObject.put("id", carInfo.getId());
	    		userJsonObject.put("carUsage", carInfo.getCarUsage());
	    		userJsonObject.put("starttime", carInfo.getCarStartTime().toString());
	    		userJsonObject.put("endtime", carInfo.getCarEndTime().toString());
	    		userJsonObject.put("carName", carInfo.getCar().getCarName());
	    		userJsonObject.put("code", carInfo.getCar().getCode());
	    		userJsonObject.put("bookGuest", user.getU_name());
	    		userJsonObject.put("booktime", carInfo.getCarInfoCreateTime().toString());
	    		
	    		userJsonArray.add(userJsonObject);		
	    	}
	    	outprint("{\"total\":"+carinfolist.size()+",\"rows\":"+userJsonArray.toString()+"}");
			return null;		
		}
		
		
		public String PhoneHistoryBookInfo(){
			JSONArray userJsonArray = new JSONArray();
			JSONObject userJsonObject = new JSONObject();
			List<CarInfoBak> carinfobaklist=carInfoService.findAllCarInfobak();
			//List<CarInfoBak> carinfobaklist1=carInfoService.findAllCarInfobakByPage(page,rows);
	    	for(CarInfoBak carInfoBak :carinfobaklist){
	    		
	    		Long a=carInfoBak.getUserId();
				User user=userService.findById(a);
				
	    		userJsonObject.put("id", carInfoBak.getId());
	    		userJsonObject.put("carUsage", carInfoBak.getCarUsage());
	    		userJsonObject.put("starttime", carInfoBak.getCarStartTime().toString());
	    		userJsonObject.put("endtime", carInfoBak.getCarEndTime().toString());
	    		userJsonObject.put("carName",carInfoBak.getCarBak().getCarName());
	    		userJsonObject.put("code", carInfoBak.getCarBak().getCode());
	    		userJsonObject.put("bookGuest", user.getU_name());
	    		userJsonObject.put("booktime", carInfoBak.getCarInfoCreateTime().toString());
	    		
	    		userJsonArray.add(userJsonObject);		
	    	}
	    	outprint("{\"total\":"+carinfobaklist.size()+",\"rows\":"+userJsonArray.toString()+"}");
			return null;	
			
			
			
		
		}
		
		public String PhoneuserCarDetails(){
			userCarList=carInfoService.findCarByUserId(userId);//user.getId()
			JSONArray userJsonArray = new JSONArray();
			JSONObject userJsonObject = new JSONObject();
			
			//List<CarInfo> userCarList1=carInfoService.findCarByUserIdByPage(user.getId(),page,rows);
	             
			for(CarInfo carInfo :userCarList){
	    		
	    		Long a=carInfo.getUserId();
				User user=userService.findById(a);
				
	    		userJsonObject.put("id", carInfo.getId());
	    		userJsonObject.put("carUsage", carInfo.getCarUsage());
	    		userJsonObject.put("starttime", carInfo.getCarStartTime().toString());
	    		userJsonObject.put("endtime", carInfo.getCarEndTime().toString());
	    		userJsonObject.put("carName", carInfo.getCar().getCarName());
	    		userJsonObject.put("code", carInfo.getCar().getCode());
	    		userJsonObject.put("bookGuest", user.getU_name());
	    		userJsonObject.put("booktime", carInfo.getCarInfoCreateTime().toString());
	    		
	    		userJsonArray.add(userJsonObject);		
	    	}
	            outprint("{\"total\":"+userCarList.size()+",\"rows\":"+userJsonArray.toString()+"}");
			return null;		
		}
		
		
	public String PhoneAddcarinfo(){
			
			
			
			//System.out.println("id: "+id);
			car=carservice.findById(carId);
			carInfo.setCarUsage(carUsage);
			carInfo.setCarStartTime(carStartTime);
			carInfo.setCarEndTime(carEndTime);
			carInfo.setCarInfoCreateTime(ts);
			carInfo.setCar(car);
			carInfo.setUserId(userId);//user.getId()
			car.getCarInfos().add(carInfo);
			carInfoService.addCarinfo(carInfo);
			
			carbak=carservice.findCarBakById(carId);
			carInfoBak.setCarUsage(carUsage);
			carInfoBak.setCarStartTime(carStartTime);
			carInfoBak.setCarEndTime(carEndTime);
			carInfoBak.setCarInfoCreateTime(ts);
			carInfoBak.setCarBak(carbak);
			carInfoBak.setUserId(userId);//user.getId()
			carbak.getCarInfoBaks().add(carInfoBak);
			carInfoService.addCarinfobak(carInfoBak);
			
			outprint("{\"success\":true}");	
		return null;			
		}
		
		//**********************************************************************************//
		
	
}
