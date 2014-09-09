package com.oa.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oa.bean.User;

/**
 * usedao接口继承basedao接口
 * @author Administrator
 *
 */
@Repository
public interface UserDao extends BaseDao<User>{
	public User findByName(String name);
	public List<User> findByDepartmentName(String DepartmentName);
	public List findByRealName(String realname);
	public List<User> findByRowAndPage(String rows, String page);
	
	public List<User> findByRealNameByPage(String realname,int page,int rows);
	
}
