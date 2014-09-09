package com.oa.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oa.bean.FlowProcess;

@Repository
public interface FlowProcessDao extends BaseDao<FlowProcess> {
	public FlowProcess getF_keyByF_id(String F_id);

	public List<FlowProcess> findByRowAndPage(String rows, String page);
}
