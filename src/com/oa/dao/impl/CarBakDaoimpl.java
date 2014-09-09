package com.oa.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oa.bean.Car;
import com.oa.bean.CarBak;
import com.oa.dao.CarBakDao;
import com.oa.dao.CarDao;
@Repository
@Transactional
public class CarBakDaoimpl extends BaseDaoimpl<CarBak> implements CarBakDao{
	@Resource
	private SessionFactory sessionfactory;
		public int findCarInfoByCarId(long CarId) {
		System.out.println("进入查询界面");
		String hql="from oa_carinfo car_name m where m.carid=:carid";
		List carinfolist=null;
		carinfolist=(List) sessionfactory.getCurrentSession().createQuery(hql).setParameter("carid", CarId).list();
		System.out.println("carID.size"+carinfolist.size());
		return carinfolist.size();
	}
}
