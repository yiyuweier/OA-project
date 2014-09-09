package com.oa.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oa.bean.Vacate;
import com.oa.dao.VacateDao;

@Repository
@Transactional
public class VacateDaoimpl extends BaseDaoimpl<Vacate> implements VacateDao {

	public Vacate getVacateByExecutionid(String executionid){
		return (Vacate) getSession().createQuery(//
				"from Vacate v where v.executionid=:executionid")//
				.setParameter("executionid", executionid)//
				.uniqueResult();
				
	}
	
	public List<Vacate> getVacateByInitiator(String initiator){
		return getSession().createQuery(//
				"from Vacate v where v.initiator=:initiator").setParameter("initiator", initiator)
				.list();
	}
	
	public List<Vacate> getVacateByInitiatorAndVacateName(String initiator, String D_name, String date){
		return getSession().createQuery(//
				"from Vacate d where d.initiator=:Initiator and d.date=:date and d.AskForLeave_type=:documentname order by d.ispass")//
				.setParameter("Initiator", initiator)//
				.setParameter("date", date)
				.setParameter("documentname", D_name)//
				.list();
	}
}
