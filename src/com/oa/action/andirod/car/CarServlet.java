package com.oa.action.andirod.car;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


import com.oa.action.andirod.servlet.OaServlet;
import com.oa.bean.Car;
import com.oa.bean.CarBak;
import com.oa.bean.Privilege;
import com.oa.bean.User;
import com.oa.service.impl.CarInfoService;
import com.oa.service.impl.CarService;
import com.oa.service.impl.PrivilegeService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
@Controller
@Scope("prototype")
public class CarServlet extends OaServlet {
	/** null. */
	 private Long id;
	 private String carName;
	 private Integer carStatus;
	 private Integer carPeople;
	 private String code;
	 private String driverName;
	 private String driverPhone;
	 
	 private String privilegeId;//该权限的id
	 
	private int rows; 
	 private int page;
	 User user=(User) ActionContext.getContext().getSession().get("user"); 
	 
	 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCarName() {
		carName=carName.replaceAll("\\s*","");
		return carName;
	}

	public void setCarName(String carName) {
		carName=carName.replaceAll("\\s*","");
		this.carName = carName;
	}

	

	public Integer getCarStatus() {
		return carStatus;
	}

	public void setCarStatus(Integer carStatus) {
		this.carStatus = carStatus;
	}

	

	public Integer getCarPeople() {
		return carPeople;
	}

	public void setCarPeople(Integer carPeople) {
		this.carPeople = carPeople;
	}

	public String getDriverName() {
		driverName=driverName.replaceAll("\\s*","");
		return driverName;
	}

	public void setDriverName(String driverName) {
		driverName=driverName.replaceAll("\\s*","");
		this.driverName = driverName;
	}

	public String getDriverPhone() {
		driverPhone=driverPhone.replaceAll("\\s*","");
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		driverPhone=driverPhone.replaceAll("\\s*","");
		this.driverPhone = driverPhone;
	}

	public String getCode() {
		code=code.replaceAll("\\s*","");
		return code;
	}

	public void setCode(String code) {
		code=code.replaceAll("\\s*","");
		this.code = code;
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
	@Resource
	Car car;
	@Resource
	CarBak carbak;
	@Resource
	PrivilegeService privilegeService;
	
	Date date=new Date();
	Timestamp ts = new Timestamp(date.getTime());
	
    public String list(){
    	JSONArray userJsonArray = new JSONArray();
		JSONObject userJsonObject = new JSONObject();
   	 	carlist= carservice.findAll();
   	 	
   	 	List<Car> carlist1=carservice.findAllByPage(page,rows);
   	 	
   	 	for(Car car:carlist1){
   	 	userJsonObject.put("id", car.getId());
   	    userJsonObject.put("carName", car.getCarName());
   	    userJsonObject.put("carPeople", car.getCarPeople());
   	    userJsonObject.put("code", car.getCode());
   	    //userJsonObject.put("carStatus", car.getCarStatus());
   	    userJsonObject.put("driverName", car.getDriverName());
   	    userJsonObject.put("driverPhone", car.getDriverPhone());	
   	    
   	 if(car.getCarStatus()==1)
		userJsonObject.put("carStatus", "可用");
	 else 
		userJsonObject.put("carStatus", "不可用");
   	        
   	     userJsonArray.add(userJsonObject); 		
   	 	}
   	 	
   		outprint("{\"total\":"+carlist.size()+",\"rows\":"+userJsonArray.toString()+"}");
    	//ActionContext.getContext().getApplication().put("carlist", carlist);

   	
   	 return null;
   	 }
    
    public String addcar(){	
    	System.out.println("1111111111111111111");
    	car.setCarName(carName);
    	car.setCarPeople(carPeople);
    	car.setCarStatus(carStatus);
    	car.setCode(code);
    	car.setDriverName(driverName);
    	car.setDriverPhone(driverPhone);	
    	carservice.addcar(car);
    	
    	carbak.setCarName(carName);
    	carbak.setCarName(carName);
    	carbak.setCarPeople(carPeople);
    	carbak.setCarStatus(carStatus);
    	carbak.setCode(code);
    	carbak.setDriverName(driverName);
    	carbak.setDriverPhone(driverPhone);	
    	carbak.setCarCreateTime(ts);
    	carservice.addcarbak(carbak);
    	
    	outprint("{\"success\":true}");	
		return null;
    	//return list();
    }
    public String ApplyUpdateCar(){
    	car=carservice.findById(id);
    	ActionContext.getContext().getSession().put("car",car);
    	return "updatecar";  	
    }
    
    public String updateCar(){
    	if(id!=null){
    	car.setId(id);
    	car.setCarName(carName);
    	car.setCarPeople(carPeople);
    	car.setCarStatus(carStatus);   	
    	car.setCode(code);	
    	car.setDriverName(driverName);
    	car.setDriverPhone(driverPhone);	
    	carservice.updateCar(car);	
    	
    	carbak.setId(id);
    	carbak.setCarName(carName);
    	carbak.setCarPeople(carPeople);
    	carbak.setCarStatus(carStatus);   	
    	carbak.setCode(code);	
    	carbak.setDriverName(driverName);
    	carbak.setDriverPhone(driverPhone);	
    	carbak.setCarCreateTime(ts);
    	carservice.updateCarbak(carbak);	
    	
    	}
    	outprint("{\"success\":true}");	
		return null;
    }
    
    public String deleteCar(){	
    	System.out.println("id:  "+id);
    	if(id!=null){
    		//System.out.println("id22222222:  "+id);
    	carservice.deleteCar(id);
    	}
    	outprint("{\"success\":true}");	
    	return null;
    }
    
    
    public String HistoryCar(){
    	JSONArray userJsonArray = new JSONArray();
		JSONObject userJsonObject = new JSONObject();
    	List<CarBak> carbaklist=carservice.findAllCarbak();
    	List<CarBak> carbaklist1=carservice.findAllCarBakByPage(page, rows);
    	for(CarBak carBak:carbaklist1){
       	 	userJsonObject.put("id", carBak.getId());
       	    userJsonObject.put("carName", carBak.getCarName());
       	    userJsonObject.put("carPeople", carBak.getCarPeople());
       	    userJsonObject.put("code", carBak.getCode());
       	    userJsonObject.put("driverName", carBak.getDriverName());
       	    userJsonObject.put("driverPhone", carBak.getDriverPhone());	
       	    userJsonObject.put("carCreateTime", carBak.getCarCreateTime().toString());	
       	    
       	 if(carBak.getCarStatus()==1)
    		userJsonObject.put("carStatus", "可用");
    	 else 
    		userJsonObject.put("carStatus", "不可用");
       	        
       	     userJsonArray.add(userJsonObject); 		
       	 	}      	 	
       outprint("{\"total\":"+carbaklist.size()+",\"rows\":"+userJsonArray.toString()+"}");
    	
    	return null;
    }
    
    public String CarInfoUI(){
    	List<Privilege> privilegeList = privilegeService.findChildPrivileges(Long.parseLong(privilegeId));
		ActionContext.getContext().getSession().put("privilegeList", privilegeList);
		return "CarInfoUI";
    }
    
    public String toCarDriverList(){
   
    	System.out.println("toCarDriverList");
    	
    	return "todriver";
    }
    
    //手机端
    //*****************************************************************************************************//
    public void CarJudgeState(){
    	int k=0;
    	int j=0;
    	
	    	 JSONArray userJsonArray = new JSONArray();
	 		JSONObject userJsonObject = new JSONObject();
	 		while(j<2){
	 			if(k==0){
	 			userJsonObject.put("name", "车辆管理");	
	 			userJsonObject.put("judge", "1");
	 			k++;
	 			}
	 			else {
	 				userJsonObject.put("name", "车辆预定");	
	 			userJsonObject.put("judge", "0");
	 			}
	 			
	 			userJsonArray.add(userJsonObject);
	 		j++;
	 		 } 
	 		
	 			outprint("{\"rows\":"+userJsonArray.toString()+"}");		   	
	 	
	 			
    	
    }
    
    public String PhoneCarlist(){
    	JSONArray userJsonArray = new JSONArray();
		JSONObject userJsonObject = new JSONObject();
   	 	carlist= carservice.findAll();
   	 	
   	 	//List<Car> carlist1=carservice.findAllByPage(page,rows);
   	 	
   	 	for(Car car:carlist){
   	 	userJsonObject.put("id", car.getId());
   	    userJsonObject.put("carName", car.getCarName());
   	    userJsonObject.put("carPeople", car.getCarPeople());
   	    userJsonObject.put("code", car.getCode());
   	    //userJsonObject.put("carStatus", car.getCarStatus());
   	    userJsonObject.put("driverName", car.getDriverName());
   	    userJsonObject.put("driverPhone", car.getDriverPhone());	
   	    
   	 if(car.getCarStatus()==1)
		userJsonObject.put("carStatus", "可用");
	 else 
		userJsonObject.put("carStatus", "不可用");
   	        
   	     userJsonArray.add(userJsonObject); 		
   	 	}
   	 	
   		outprint("{\"total\":"+carlist.size()+",\"rows\":"+userJsonArray.toString()+"}");
    	

   	
   	 return null;
   	 }
    
    
    public String PhoneHistoryCar(){
    	JSONArray userJsonArray = new JSONArray();
		JSONObject userJsonObject = new JSONObject();
    	List<CarBak> carbaklist=carservice.findAllCarbak();
    	//List<CarBak> carbaklist1=carservice.findAllCarBakByPage(page, rows);
    	for(CarBak carBak:carbaklist){
       	 	userJsonObject.put("id", carBak.getId());
       	    userJsonObject.put("carName", carBak.getCarName());
       	    userJsonObject.put("carPeople", carBak.getCarPeople());
       	    userJsonObject.put("code", carBak.getCode());
       	    userJsonObject.put("driverName", carBak.getDriverName());
       	    userJsonObject.put("driverPhone", carBak.getDriverPhone());	
       	    userJsonObject.put("carCreateTime", carBak.getCarCreateTime().toString());	
       	    
       	 if(carBak.getCarStatus()==1)
    		userJsonObject.put("carStatus", "可用");
    	 else 
    		userJsonObject.put("carStatus", "不可用");
       	        
       	     userJsonArray.add(userJsonObject); 		
       	 	}      	 	
       outprint("{\"total\":"+carbaklist.size()+",\"rows\":"+userJsonArray.toString()+"}");
    	
    	return null;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    //****************************************************************************************************//
}
