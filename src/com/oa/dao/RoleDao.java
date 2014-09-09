package com.oa.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oa.bean.Role;
import com.oa.bean.User;

@Repository
public interface RoleDao extends BaseDao<Role>{

	List<Role> findByRowAndPage(String rows, String page);

}
