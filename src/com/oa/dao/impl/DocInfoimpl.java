package com.oa.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oa.bean.DocInfo;
import com.oa.dao.BaseDao;
import com.oa.dao.DocInfoDao;
@Repository
@Transactional
public class DocInfoimpl extends BaseDaoimpl<DocInfo> implements DocInfoDao{

	@Resource
	private SessionFactory sessionfactory;
	@SuppressWarnings("unchecked")
	public List<DocInfo> findByUserId(Long userId) {
		
		return sessionfactory.getCurrentSession().createQuery("from DocInfo doc WHERE doc.userId=:userId order by doc.createTime desc").setParameter("userId", userId).list();
	}

	public List<DocInfo> findAll1() {
		
		return sessionfactory.getCurrentSession().createQuery("from DocInfo doc order by doc.createTime desc").list();
	}

	 public List<DocInfo> findAllByPage1(int page,int rows){
		 
		 return sessionfactory.getCurrentSession().createQuery("from DocInfo doc order by doc.createTime desc").setFirstResult((page-1)*rows).setMaxResults(rows).list();
	 }

	public int findByName(String name) {
	name=name.substring(0,name.length()-4);
	System.out.println(name);
		List list=sessionfactory.getCurrentSession().createQuery("from DocInfo doc where doc.name like :name").setParameter("name",name+"%").list();
		return list.size();
	}

	@SuppressWarnings("unchecked")
	public List<DocInfo> findByName1(String name) {
		return sessionfactory.getCurrentSession().createQuery("from DocInfo doc WHERE doc.name like :name order by doc.createTime desc").setParameter("name","%"+name+"%").list();		
	}

	@Override
	public List<DocInfo> findByUserIdByPage(Long id, int page, int rows) {
		return sessionfactory.getCurrentSession().createQuery("from DocInfo doc WHERE doc.userId=:userId order by doc.createTime desc").setParameter("userId",id).setFirstResult((page-1)*rows).setMaxResults(rows).list();
	}

	
	
	
	
}
