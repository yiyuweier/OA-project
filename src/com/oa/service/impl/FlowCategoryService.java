package com.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa.bean.FlowCategory;
import com.oa.dao.FlowCategoryDao;

@Service
@Transactional
public class FlowCategoryService {
	
	@Resource
	FlowCategoryDao flowcategorydao;
	
	public List<FlowCategory> findAll(){
		return flowcategorydao.findAll();
		
	}
	public FlowCategory getFlowProcessByProcessId(Long id){
		try {
			return flowcategorydao.getFlowProcessByProcessId(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
