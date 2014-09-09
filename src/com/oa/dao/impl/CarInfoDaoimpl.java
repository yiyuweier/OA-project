package com.oa.dao.impl;


import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oa.bean.CarInfo;
import com.oa.bean.MeetingInfo;
import com.oa.dao.CarInfoDao;
@Repository
@Transactional
public class CarInfoDaoimpl extends BaseDaoimpl<CarInfo> implements CarInfoDao{
	@Resource
	private SessionFactory sessionfactory;

	public List<CarInfo> findCarByUserId(long userId) {
		return (List) sessionfactory.getCurrentSession().createQuery("from CarInfo m WHERE m.userId=:userId").setParameter("userId",userId).list();
	}
	
	public List findCarInfoByCarId(long carId) {
		String hql="from CarInfo m where m.car.id=:carId";
		List<CarInfo> carinfolist1=null;
		carinfolist1=(List) sessionfactory.getCurrentSession().createQuery(hql).setParameter("carId",carId).list();
		//System.out.println("cccccccccccccc"+meetinginfolist.size());
		 return carinfolist1;
	}

	@Override
	public List<CarInfo> findCarByUserIdByPage(Long id, int page, int rows) {
		return (List) sessionfactory.getCurrentSession().createQuery("from CarInfo m WHERE m.userId=:userId").setParameter("userId",id).setFirstResult((page-1)*rows).setMaxResults(rows).list();
	}

}
