package com.oa.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oa.bean.MeetingRoom;
import com.oa.bean.MeetingRoomBak;
import com.oa.bean.User;
import com.oa.dao.MeetingRoomBakDao;
import com.oa.dao.MeetingRoomDao;
import com.oa.dao.UserDao;
@Repository
@Transactional
public class MeetingRoomBakDaoimpl extends BaseDaoimpl<MeetingRoomBak> implements MeetingRoomBakDao{

	
	@Resource
	private SessionFactory sessionfactory;
	public void update(long id1) {
		String hql="update MeetingRoom m set m.id=m.id-1 where m.id>:id1";
		sessionfactory.getCurrentSession().createQuery(hql).setParameter("id1", id1).executeUpdate();		
	}
	
	public List findRoomBakByPage(int page, int rows) {
		String hql="from MeetingRoomBak m";
		 List<MeetingRoomBak> list=(List)sessionfactory.getCurrentSession().createQuery(hql).setFirstResult((page-1)*rows).setMaxResults(rows).list();
		return list;
	}

}
