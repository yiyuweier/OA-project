package com.oa.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa.bean.Car;
import com.oa.bean.CarInfo;
import com.oa.bean.CarInfoBak;
import com.oa.bean.MeetingInfo;
import com.oa.bean.MeetingRoom;

import com.oa.dao.CarDao;
import com.oa.dao.CarInfoBakDao;
import com.oa.dao.CarInfoDao;
import com.opensymphony.xwork2.ActionContext;
@Service
@Transactional
public class CarInfoService {
	@Resource
    CarInfoDao carinfodao;
	@Resource
	CarDao cardao;
	@Resource
    CarInfoBakDao carinfobakdao;
		public void addCarinfo(CarInfo carInfo){
			carinfodao.save(carInfo);
		    }
		
		public void addCarinfobak(CarInfoBak carInfoBak){
			carinfobakdao.save(carInfoBak);		
		}
		
		public void updateCarInfo(CarInfo carInfo){
			 
			carinfodao.update(carInfo);
		}
		
		public List<CarInfo> findAll(){			
			return carinfodao.findAll();	
		}
		public List<CarInfo> findAllByPage(int page,int rows){
			
			return carinfodao.findAllByPage(page, rows);
		}
		
		public List<CarInfoBak> findAllCarInfobak(){			
			return carinfobakdao.findAll();	
		}
		
		public List<CarInfoBak> findAllCarInfobakByPage(int page,int rows){
			return carinfobakdao.findAllCarInfobakByPage(page, rows);
			
		}
		
		public List<CarInfo> findCarByUserIdByPage(Long id,int page,int rows){
			
			return carinfodao.findCarByUserIdByPage(id,page,rows);
		}
	public void deleteCarInfo(long id){
		
		carinfodao.delete(id);
	}
	
	public List<CarInfo> findCarByUserId(Long userId){
		return carinfodao.findCarByUserId(userId);	
	}
	
	
	public List delete_same(List list,long carId){
		List l=findCarInfoByCarId(carId);
			for(int z=0;z<l.size();z++){
				list.remove(l.get(z));			
			}
		return list;
	}
	
	
	public List delete_same_one(List list,long carId){
		List l=findCarInfoByCarId(carId);
		if(l.size()>1){
			for(int z=1;z<l.size();z++){
				list.remove(l.get(z));			
			}
			}
		return list;
	}
	
	
	public List findCarInfoByCarId(long carId){	
		return carinfodao.findCarInfoByCarId(carId);	
	}
	
	Date date=new Date();
	Timestamp ts = new Timestamp(date.getTime());
	@SuppressWarnings("null")
	public List selectbytime(Date carStartTime,Date carEndTime) throws IOException{
		//System.out.println("QQQQQQQQQQ");
		List <CarInfo> allinfos;
		List <Car> allcars;
		List <Car> notbookcars = new ArrayList<Car>();
		allinfos=carinfodao.findAll();
		allcars=cardao.findAll();
		
		//System.out.println(allinfos.size()+"        "+allcars.size());
		if(allinfos.size()==0){	
			for(int m=0;m<allcars.size();){
				if(allcars.get(m).getCarStatus()==0)
					allcars.remove(m);
				else 
					m++;		
			}
			notbookcars=allcars;
			ActionContext.getContext().getApplication().put("notbookcars",notbookcars);
			return notbookcars;
		}
		
	    Long[] a=null;
	    a=new Long[allinfos.size()];
		Long[] b=null;
		b=new Long[allcars.size()];
		Long[] c=new Long[allcars.size()];
		int  d[] = null;
		int j=0;
		int n=0;
		int k=0;
		int infolength=allinfos.size();
		//System.out.println(allinfos.size());
		
		int i=0;
		for(i=0;i<infolength;i++){
			a[i]=allinfos.get(i).getCar().getId();	
			}
		for(i=0;i<allinfos.size();){		
			if(!((allinfos.get(i).getCarStartTime().after(carEndTime))||(allinfos.get(i).getCarEndTime().before(carStartTime)))){		
				//d[k]=i;
				//k++;
				allinfos=delete_same(allinfos,allinfos.get(i).getCar().getId());
				i=0;
				//allinfos.remove(i);		
			}
			else i++;
		}
		//????删除重复
		for(i=0;i<allinfos.size();){
			int h=allinfos.size();
			allinfos=delete_same_one(allinfos,allinfos.get(i).getCar().getId());
			if(allinfos.size()<h){
				i=i;
			}
			else i++;
		}
	//所有会议室的id 存入b[k];
		for(k=0;k<allcars.size();k++){	
			b[k]=allcars.get(k).getId();	
			//System.out.println("aaaaaaaaaa  "+b[k]);
		}
		
		int m=0;
		k=0;
		for(k=0;k<allcars.size();k++){	
			for(m=0;m<infolength;m++){
				//System.out.println("a[m]:  "+a[1]);
				if(b[k]==a[m]){
					//System.out.println("cccccccc  "+b[k]+"  "+a[m]);
					break;
				}			
				else if(m==(infolength-1)){
					//System.out.println("b[k]:  "+b[k]);
			c[n]=b[k];
			n++;
		}
				//else 
				//System.out.println("NNNNNN  "+b[k]+"  "+a[m]);
			}		
		}
		
		//MeetingRoom meetingRoom;
		Car car;
		for(j=0;j<n;j++){
			car=cardao.findById(c[j]);
			if(car.getCarStatus()==1){
				notbookcars.add(car);		
			}	
		}
		
		Car car1;
		//List<MeetingRoom> temp = null;
		//???? 为啥set方法不行
 		for(int q=0;q<allinfos.size();q++){
 			if(allinfos.get(q).getCar().getCarStatus()==1){		
 			Long id=allinfos.get(q).getCar().getId();
 			String tempName=allinfos.get(q).getCar().getCarName();
 			String code=allinfos.get(q).getCar().getCode();
 			Integer carPeople=allinfos.get(q).getCar().getCarPeople();
 			car1=new Car(id,tempName,code,carPeople);
 			notbookcars.add(car1);
 			}
 		}	
 		
		
		ActionContext.getContext().getApplication().put("notbookcars",notbookcars);
		
		j=0;
		/*for(j=0;j<allinfos.size();){
			if(starttime.after(allinfos.get(j).getEndTime())){
				allinfos.remove(j);
			}
			else j++;		
		}*/
		return allinfos;
	}



	
	
	
	
}
