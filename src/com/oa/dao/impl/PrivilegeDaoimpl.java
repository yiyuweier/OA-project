package com.oa.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oa.bean.Privilege;
import com.oa.dao.PrivilegeDao;

@Repository
@Transactional
public class PrivilegeDaoimpl extends BaseDaoimpl<Privilege> implements PrivilegeDao {

	public List<Privilege> findTopPrivileges() {
		return getSession().createQuery(//
				"from Privilege p where p.parent is null")//
				.list();
	}

	
	public List<Privilege> findChildPrivilege(Long parentId) {
		return getSession().createQuery(//
				"from Privilege p where p.parent.id=:parentId")//
				.setParameter("parentId", parentId)//
				.list();
	}

	

}
