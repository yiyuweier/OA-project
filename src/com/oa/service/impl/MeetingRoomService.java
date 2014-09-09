package com.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.oa.bean.MeetingRoom;
import com.oa.bean.MeetingRoomBak;
import com.oa.bean.User;
import com.oa.dao.MeetingRoomBakDao;
import com.oa.dao.MeetingRoomDao;
import com.oa.dao.UserDao;

@Service
@Transactional
public class MeetingRoomService {
	@Resource
	MeetingRoomDao meetingroomdao;
	@Resource
	MeetingRoomBakDao meetingroombakdao;
	 public void addmeetingroom(MeetingRoom meetingRoom){
		 meetingroomdao.save(meetingRoom);	
		 
         }
	 
	 public void addmeetingroombak(MeetingRoomBak meetingRoomBak){
		
		 meetingroombakdao.save(meetingRoomBak);
		 
         }
	 
	// public void addmeetingroom(MeetingRoom meetingRoom){
	//	 meetingroomdao.save(meetingRoom);
     //    }
	 
	 
     public void updatemeetingroom(MeetingRoom meetingRoom){
    	 
    	 meetingroomdao.update(meetingRoom);
    	 
     }
     
public void updatemeetingroombak(MeetingRoomBak meetingRoomBak){
    	 
    	 meetingroombakdao.update(meetingRoomBak);
    	 
     }
	 
public void deletemeetingroom(long id){
    	 
    	 meetingroomdao.delete(id);
    	 
     }

	public List<MeetingRoom> findAllByPage(int page,int rows){
		return meetingroomdao.findAllByPage(page,rows);	
	}
	
	public List<MeetingRoomBak> findRoomBakByPage(int page,int rows){
		return meetingroombakdao.findRoomBakByPage(page, rows);
		
		
	}
	public List<MeetingRoom> findAll(){
		
		return meetingroomdao.findAll();
	}
	
	public List<MeetingRoomBak> findRoomBak(){
		return meetingroombakdao.findAll();
	
	}

	public MeetingRoom findById(long id){
		return meetingroomdao.findById(id);
	}
	
	public MeetingRoomBak findBakById(long id){
		return meetingroombakdao.findById(id);
	}
	
	
	public void update(long id1){
		meetingroomdao.update(id1);
	}
	
	
	
	
	
}
