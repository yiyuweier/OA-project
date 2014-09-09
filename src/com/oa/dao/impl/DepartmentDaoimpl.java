package com.oa.dao.impl;



import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.oa.bean.Department;
import com.oa.dao.DepartmentDao;

@Repository
@Transactional
public class DepartmentDaoimpl extends BaseDaoimpl<Department> implements DepartmentDao{
	
	@Resource
	private SessionFactory sessionfactory;

	public List<Department> findChildlist(Long parentId) {
		System.out.println(parentId);
		return sessionfactory.getCurrentSession().createQuery(//
				"from Department d where d.parent.id=:parentId")//
				.setParameter("parentId", parentId)//
				.list();
	}

	public List<Department> findToplist() {
		return sessionfactory.getCurrentSession().createQuery(//
				"from Department d where d.parent is null")//
				.list();
	}

	

}
