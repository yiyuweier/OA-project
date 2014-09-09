package com.oa.dao.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



import com.oa.bean.MeetingInfo;
import com.oa.bean.MeetingInfoBak;
import com.oa.bean.MeetingRoom;
import com.oa.bean.MeetingRoomBak;
import com.oa.dao.MeetingInfoBakDao;
import com.oa.dao.MeetingInfoDao;
import com.oa.dao.MeetingRoomDao;

@Repository
@Transactional
public class MeetingInfoBakDaoimpl extends BaseDaoimpl<MeetingInfoBak> implements MeetingInfoBakDao{

	@Resource
	private SessionFactory sessionfactory;
	
	public int findMeetingInfoByMeetingRoomId(long meetingRoomId2) {
		String hql="from MeetingInfo m where m.meetingRoomId=:meetRoomId2";
		List meetinginfolist=null;
		meetinginfolist=(List) sessionfactory.getCurrentSession().createQuery(hql).setParameter("meetingRoomId2", meetingRoomId2).list();
		System.out.println("cccccccccccccc"+meetinginfolist.size());
		 return meetinginfolist.size();
	}

	@Override
	public List findallbakByPage(int page, int rows) {
		String hql="from MeetingInfoBak m";
		List<MeetingRoomBak> list=(List)sessionfactory.getCurrentSession().createQuery(hql).setFirstResult((page-1)*rows).setMaxResults(rows).list();
		return list;
	}


	//public List selectybytime(Timestamp starttime, Timestamp endtime) {
	//	List list;
		//if(starttime.after(endtime)){
			
			
			//
			
	//	}
		//Declare @Tdate nchar(10);
		//String hql="from MeetingInfo m where m.startTime between(:starttime and '"2013-04-21 02:20:20)";
		//System.out.println(starttime+"qqqqqqqqqq"+endtime);
		//m.starttime<:endtime or m.endtime<:starttime"2013/04/21  02:20:20
		///return sessionfactory.getCurrentSession().createQuery(hql).list();
		//return null;.setParameter("endtime", endtime).setParameter("starttime", starttime)
	//}

}
