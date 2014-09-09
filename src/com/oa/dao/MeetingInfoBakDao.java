package com.oa.dao;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Repository;



import com.oa.bean.MeetingInfo;
import com.oa.bean.MeetingInfoBak;
import com.oa.bean.MeetingRoom;

@Repository
public  interface MeetingInfoBakDao extends BaseDao<MeetingInfoBak> {
	public int findMeetingInfoByMeetingRoomId(long meetingRoomId);
	
	//public List selectybytime(Timestamp starttime,Timestamp endtime);
	public List findallbakByPage(int page,int rows);
}
