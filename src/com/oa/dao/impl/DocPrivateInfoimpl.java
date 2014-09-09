package com.oa.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oa.bean.DocInfo;
import com.oa.bean.DocPrivateInfo;
import com.oa.dao.BaseDao;
import com.oa.dao.DocInfoDao;
import com.oa.dao.DocPrivateInfoDao;
@Repository
@Transactional
public class DocPrivateInfoimpl extends BaseDaoimpl<DocPrivateInfo> implements DocPrivateInfoDao{
	@Resource
	private SessionFactory sessionfactory;
	@SuppressWarnings("unchecked")
	public List<DocPrivateInfo> findByUserId(Long userId) {
		
		 return sessionfactory.getCurrentSession().createQuery("from DocPrivateInfo privatedoc WHERE privatedoc.userId=:userId order by privatedoc.createTime desc").setParameter("userId", userId).list();
	}
	@Override
	public int findByName(String imageFileName) {
		imageFileName=imageFileName.substring(0,imageFileName.length()-4);
		System.out.println(imageFileName);
			List list=sessionfactory.getCurrentSession().createQuery("from DocPrivateInfo privatedoc where privatedoc.name like :name").setParameter("name",imageFileName+"%").list();
			return list.size();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<DocPrivateInfo> findByName1(String name) {
		return sessionfactory.getCurrentSession().createQuery("from DocPrivateInfo Privatedoc WHERE Privatedoc.name like :name order by Privatedoc.createTime desc").setParameter("name","%"+name+"%").list();		
	}
	@Override
	public List<DocPrivateInfo> findByUserIdByPage(Long id, int page, int rows) {
		return sessionfactory.getCurrentSession().createQuery("from DocPrivateInfo privatedoc WHERE privatedoc.userId=:userId order by privatedoc.createTime desc").setParameter("userId", id).setFirstResult((page-1)*rows).setMaxResults(rows).list();
	}
	
}
