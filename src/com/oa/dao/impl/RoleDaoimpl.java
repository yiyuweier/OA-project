package com.oa.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.oa.bean.Role;
import com.oa.bean.User;
import com.oa.dao.RoleDao;

@Repository
public class RoleDaoimpl extends BaseDaoimpl<Role> implements RoleDao{

	@Resource
	private SessionFactory sessionfactory;
	@Override
	public List<Role> findByRowAndPage(String rows, String page) {
		int rowsize = (Integer.parseInt(page)-1)*Integer.parseInt(rows);
		int row = Integer.parseInt(rows);
		return sessionfactory.getCurrentSession().createQuery(//
				"from Role")//
				.setFirstResult(rowsize)//
				.setMaxResults(row)//
				.list();
	}

}
