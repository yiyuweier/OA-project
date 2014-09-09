package com.oa.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.oa.bean.Announcements;
import com.oa.dao.AnnouncementsDao;


@Repository
public class AnnouncementsDaoimpl extends BaseDaoimpl<Announcements> implements AnnouncementsDao{

	@Resource
	private SessionFactory sessionfactory;
	@Override
	public List<Announcements> findByRowAndPage(String rows, String page) {
		int rowsize = (Integer.parseInt(page)-1)*Integer.parseInt(rows);
		int row = Integer.parseInt(rows);
		return sessionfactory.getCurrentSession().createQuery(//
				"from Announcements")//
				.setFirstResult(rowsize)//
				.setMaxResults(row)//
				.list();
	}
	

}
