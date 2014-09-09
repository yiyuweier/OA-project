package com.oa.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



import com.oa.bean.MeetingInfo;
import com.oa.bean.MeetingRoom;
import com.oa.bean.User;
import com.oa.dao.MeetingInfoDao;
import com.oa.dao.MeetingRoomDao;

@Repository
@Transactional
public class MeetingInfoDaoimpl extends BaseDaoimpl<MeetingInfo> implements MeetingInfoDao{

	@Resource
	private SessionFactory sessionfactory;
	
	public List findMeetingInfoByMeetingRoomId(long meetingRoomId2) {
		String hql="from MeetingInfo m where m.meetingRoom.id=:meetingRoomId2";
		List<MeetingInfo> meetinginfolist=null;
		meetinginfolist=(List) sessionfactory.getCurrentSession().createQuery(hql).setParameter("meetingRoomId2", meetingRoomId2).list();
		System.out.println("cccccccccccccc"+meetinginfolist.size());
		 return meetinginfolist;
	}


	public List findByUserId(Long userId) {
		return (List) sessionfactory.getCurrentSession().createQuery("from MeetingInfo m WHERE m.userId=:userId").setParameter("userId",userId).list();
		
	}


	@Override
	public List findAllByPage(int page, int rows) {
		String hql="from MeetingInfo m";
		 List<MeetingInfo> list=(List)sessionfactory.getCurrentSession().createQuery(hql).setFirstResult((page-1)*rows).setMaxResults(rows).list();

		        return list;
	}


	@Override
	public List findByUserIdByPage(Long id,int page, int rows) {
	 
		return (List) sessionfactory.getCurrentSession().createQuery("from MeetingInfo m WHERE m.userId=:userId").setParameter("userId",id).setFirstResult((page-1)*rows).setMaxResults(rows).list();
	}

}
