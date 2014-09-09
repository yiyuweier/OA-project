package com.oa.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oa.bean.Privilege;

@Repository
public interface PrivilegeDao extends BaseDao<Privilege> {

	List<Privilege> findTopPrivileges();

	List<Privilege> findChildPrivilege(Long parentId);

}
