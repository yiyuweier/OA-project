package com.oa.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oa.bean.Document;
import com.oa.bean.Vacate;

@Repository
public interface VacateDao extends BaseDao<Vacate> {
	public Vacate getVacateByExecutionid(String executionid);
	
	public List<Vacate> getVacateByInitiator(String initiator);
	
	public List<Vacate> getVacateByInitiatorAndVacateName(String initiator, String D_name, String date);

}
