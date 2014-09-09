package com.oa.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa.bean.MeetingInfo;
import com.oa.bean.MeetingInfoBak;
import com.oa.bean.MeetingRoom;
import com.oa.dao.MeetingInfoBakDao;
import com.oa.dao.MeetingInfoDao;
import com.oa.dao.MeetingRoomDao;
import com.opensymphony.xwork2.ActionContext;

@Service
@Transactional
public class MeetingInfoService {
	@Resource
	MeetingInfoDao meetinginfodao;
	@Resource
	MeetingRoomDao meetingroomdao;
	

	
	@Resource
	MeetingInfoBakDao meetinginfobakdao;
	
	 public void addmeetinginfo(MeetingInfo meetingInfo){
		 meetinginfodao.save(meetingInfo);
         }
	 
	 public void addmeetinginfobak(MeetingInfoBak meetingInfoBak){
		 meetinginfobakdao.save(meetingInfoBak);
         }
 
	 public List<MeetingInfoBak> findallbak(){
		 
		return meetinginfobakdao.findAll();
	 }
	 

		public List findallbakByPage(int page,int rows){
			
			return meetinginfobakdao.findallbakByPage(page,rows);
			 
		}
		
		public List findAllByPage(int page,int rows){
			
			return meetinginfodao.findAllByPage(page,rows);
		}
	 
	   public List findByUserIdByPage(Long id,int page,int rows){
		   
		   return meetinginfodao.findByUserIdByPage(id,page,rows);
	   }
	 
     public void updatemeetingroom(MeetingInfo meetingInfo){
    	 
    	 meetinginfodao.update(meetingInfo);
    	 
     }
	 
     
     
//public void deletemeetingroom(long id){
    	 
	//meetinginfodao.delete(id);
    	 
   //  }

public void deletemeetinginfo(long id){
	
	meetinginfodao.delete(id);
}



public MeetingInfo findById(long id){
	return meetinginfodao.findById(id);

}

	public List<MeetingInfo> findAll(){
		return meetinginfodao.findAll();
	
	}
	
	public List<MeetingInfo> findByUserId(long userId){
		return meetinginfodao.findByUserId(userId);
	
	}
	
	//public int findMeetingInfoByMeetingRoomId(long meetingRoomId){
              //  return meetinginfodao.findMeetingInfoByMeetingRoomId(meetingRoomId);
                		
	//}
	public List delete_same(List list,long meetingRoomId){
		List l=findMeetingInfoByMeetingRoomId(meetingRoomId);
			for(int z=0;z<l.size();z++){
				list.remove(l.get(z));			
			}
		return list;
	}
	
	public List delete_same_one(List list,long meetingRoomId){
		List l=findMeetingInfoByMeetingRoomId(meetingRoomId);
		if(l.size()>1){
			for(int z=1;z<l.size();z++){
				list.remove(l.get(z));			
			}
			}
		return list;
	}
	

	public List findMeetingInfoByMeetingRoomId(long meetingRoomId) {
		
		return meetinginfodao.findMeetingInfoByMeetingRoomId(meetingRoomId);
	}
	
	Date date=new Date();
	Timestamp ts = new Timestamp(date.getTime());
	@SuppressWarnings("null")
	public List selectbytime(Date starttime,Date endtime) throws IOException{
		List <MeetingInfo> allinfos;
		List <MeetingRoom> allrooms;
		List <MeetingRoom> notbookrooms = new ArrayList<MeetingRoom>();
		allinfos=meetinginfodao.findAll();
		allrooms=meetingroomdao.findAll();
		if(allinfos.size()==0){
			for(int m=0;m<allrooms.size();){
				if(allrooms.get(m).getState()==0)
					allrooms.remove(m);
				else 
					m++;		
			}
			notbookrooms=allrooms;
			ActionContext.getContext().getApplication().put("notbookrooms", notbookrooms);
			return notbookrooms;
			
		}
	    Long[] a=null;
	    a=new Long[allinfos.size()];
		Long[] b=null;
		b=new Long[allrooms.size()];
		Long[] c=new Long[allrooms.size()];
		int  d[] = null;
		int j=0;
		int n=0;
		int k=0;
		int infolength=allinfos.size();
		System.out.println(allinfos.size());
		
		int i=0;
		for(i=0;i<infolength;i++){
			a[i]=allinfos.get(i).getMeetingRoom().getId();	
			}
		for(i=0;i<allinfos.size();){		
			if(!((allinfos.get(i).getStartTime().after(endtime))||(allinfos.get(i).getEndTime().before(starttime)))){		
				//d[k]=i;
				//k++;
				allinfos=delete_same(allinfos,allinfos.get(i).getMeetingRoom().getId());
				i=0;
				//allinfos.remove(i);		
			}
			else i++;
		}
		//????删除重复
		for(i=0;i<allinfos.size();){
			int h=allinfos.size();
			allinfos=delete_same_one(allinfos,allinfos.get(i).getMeetingRoom().getId());
			if(allinfos.size()<h){
				i=i;
			}
			else i++;
		}
	//所有会议室的id 存入b[k];
		for(k=0;k<allrooms.size();k++){	
			b[k]=allrooms.get(k).getId();	
			//System.out.println("aaaaaaaaaa  "+b[k]);
		}
		
		int m=0;
		k=0;
		for(k=0;k<allrooms.size();k++){	
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
		
		MeetingRoom meetingRoom;
		for(j=0;j<n;j++){
			meetingRoom=meetingroomdao.findById(c[j]);
			if(meetingRoom.getState()==1){
			notbookrooms.add(meetingRoom);		
			}	
		}
		
		MeetingRoom meetingRoom1;
		//List<MeetingRoom> temp = null;
		//???? 为啥set方法不行
 		for(int q=0;q<allinfos.size();q++){
 			if(allinfos.get(q).getMeetingRoom().getState()==1){
			
 			Long id=allinfos.get(q).getMeetingRoom().getId();
 			String tempName=allinfos.get(q).getMeetingRoom().getName();
 			Integer people=allinfos.get(q).getMeetingRoom().getPeople();
 			//Integer state=allinfos.get(q).getMeetingRoom().getState();
 			//String roomAddress=allinfos.get(q).getMeetingRoom().getRoomAddress();
 			//Set<MeetingInfo> meetingInfos=allinfos.get(q).getMeetingRoom().getMeetingInfos();
 		//	System.out.println("tempName:  "+tempName);
 			//meetingRoom1=new MeetingRoom(tempName,people,aaa,state,roomAddress);
 			meetingRoom1=new MeetingRoom(id,tempName,people);
 			notbookrooms.add(meetingRoom1);
 			}
 		}	
 		
 		
 		//System.out.println(json.toString());
		//System.out.println("notbookrooms:  "+notbookrooms.size());
		ActionContext.getContext().getApplication().put("notbookrooms", notbookrooms);
		
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
