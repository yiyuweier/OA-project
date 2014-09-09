package com.oa.dao;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Repository;



import com.oa.bean.MeetingInfo;
import com.oa.bean.MeetingRoom;

@Repository
public  interface MeetingInfoDao extends BaseDao<MeetingInfo> {
	public List findMeetingInfoByMeetingRoomId(long meetingRoomId);
	
	
	public List findByUserId(Long userId);
	
	
	public List findAllByPage(int page,int rows);
	//public List findAlldistinct();
	//public List selectybytime(Timestamp starttime,Timestamp endtime);
	
	 public List findByUserIdByPage(Long id,int page,int rows);
}
