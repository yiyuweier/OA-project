package com.oa.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oa.bean.User;
import com.oa.dao.UserDao;

@Repository
@Transactional
public class UserDaoimpl extends BaseDaoimpl<User> implements UserDao{

	@Resource
	private SessionFactory sessionfactory;
	public User findByName(String name) {
		System.out.println("name="+name);
//		User user = (User) sessionfactory.getCurrentSession().createQuery(//
//				"from User user WHERE user.loginname=:loginname")//
//				.setParameter("loginname", name)//
//				.uniqueResult();
//		System.out.println("username="+user.getU_name()+"-----------");
		return (User) sessionfactory.getCurrentSession().createQuery(//
				"from User user WHERE user.loginname=:loginname")//
				.setParameter("loginname", name)//
				.uniqueResult();
	}
	public List<User> findByDepartmentName(String DepartmentName){
		return sessionfactory.getCurrentSession().createQuery(//
				"from User user where user.department.D_name=:DepartmentName")//
				.setParameter("DepartmentName", DepartmentName)//
				.list();
	}
	public List<User> findByRealName(String realname) {
		// TODO Auto-generated method stub
		return sessionfactory.getCurrentSession().createQuery("from User user WHERE user.U_name like :realname").setParameter("realname", "%"+realname+"%").list();
	}
	
	public List<User> findByRowAndPage(String rows, String page) {
		int rowsize = (Integer.parseInt(page)-1)*Integer.parseInt(rows);
		int row = Integer.parseInt(rows);
		return sessionfactory.getCurrentSession().createQuery(//
				"from User")//
				.setFirstResult(rowsize)//
				.setMaxResults(row)//
				.list();
	}
	@Override
	public List<User> findByRealNameByPage(String realname, int page, int rows) {
		return sessionfactory.getCurrentSession().createQuery("from User user WHERE user.U_name like :realname").setParameter("realname", "%"+realname+"%").setFirstResult((page-1)*rows).setMaxResults(rows).list();
		
	}

}
