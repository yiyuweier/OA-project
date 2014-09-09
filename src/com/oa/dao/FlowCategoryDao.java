package com.oa.dao;

import org.springframework.stereotype.Repository;

import com.oa.bean.FlowCategory;
import com.oa.bean.FlowProcess;

@Repository
public interface FlowCategoryDao extends BaseDao<FlowCategory>{
	public FlowCategory getFlowProcessByProcessId(Long id) throws Exception;
}
