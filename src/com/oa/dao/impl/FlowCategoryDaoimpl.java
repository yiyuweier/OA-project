package com.oa.dao.impl;



import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oa.bean.FlowCategory;
import com.oa.bean.FlowProcess;
import com.oa.dao.FlowCategoryDao;

@Repository
@Transactional
public class FlowCategoryDaoimpl extends BaseDaoimpl<FlowCategory> implements FlowCategoryDao {
	
	@Resource
	private SessionFactory sessionfactory;
	
	public FlowCategory getFlowProcessByProcessId(Long id) throws Exception{
		System.out.println("shujuku----"+id);
		return (FlowCategory) sessionfactory.getCurrentSession().createQuery(//
				"from FlowCategory f  inner join fetch f.flowProcess e where e.id=:id")//
				.setParameter("id", id).uniqueResult();
	}

}
