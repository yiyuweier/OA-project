package com.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa.bean.Privilege;
import com.oa.dao.PrivilegeDao;

@Service
@Transactional
public class PrivilegeService {
	
	@Resource
	PrivilegeDao privilegedao;
	
	public List<Privilege> findAll(){
		return privilegedao.findAll();
	}

	public List<Privilege> findByIds(Long[] privilegeIdlist) {
		
		return privilegedao.findByIds(privilegeIdlist);
	}

	public List<Privilege> findTopPrivileges() {
		return privilegedao.findTopPrivileges();
		
	}

	public List<Privilege> findChildPrivileges(Long parentId) {
		return privilegedao.findChildPrivilege(parentId);
	}

}
