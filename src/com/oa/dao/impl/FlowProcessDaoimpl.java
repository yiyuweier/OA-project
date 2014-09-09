package com.oa.dao.impl;



import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oa.bean.FlowProcess;
import com.oa.dao.FlowProcessDao;

@Repository
@Transactional
public class FlowProcessDaoimpl extends BaseDaoimpl<FlowProcess> implements FlowProcessDao {
	
	@Resource
	SessionFactory sessionfactory;
	
	public FlowProcess getF_keyByF_id(String F_id){
		return (FlowProcess) sessionfactory.getCurrentSession().createQuery(//
				"from FlowProcess f where f.F_id=:F_id")//
				.setParameter("F_id", F_id)//
				.uniqueResult();
	}

	@Override
	public List<FlowProcess> findByRowAndPage(String rows, String page) {
		int rowsize = (Integer.parseInt(page)-1)*Integer.parseInt(rows);
		int row = Integer.parseInt(rows);
		return sessionfactory.getCurrentSession().createQuery(//
				"from FlowProcess")//
				.setFirstResult(rowsize)//
				.setMaxResults(row)//
				.list();
	}


}
