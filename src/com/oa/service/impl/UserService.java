package com.oa.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.oa.bean.User;
import com.oa.dao.UserDao;

@Service
@Transactional
public class UserService {
	
	@Resource
	UserDao userdao;
	
	public User login(String username){//根据用户的登陆名称（唯一的）找到该用户的所有信息
		System.out.println("username="+username);
		return userdao.findByName(username);
		
	}
	public List<User> findAll(){
		return userdao.findAll();
	}
	
	public List<User> findByRowAndPage(String rows,String page){
		return userdao.findByRowAndPage(rows,page);
	}
	
	public void delete(long id){
		userdao.delete(id);
	}
	
	public void save(User user){
		userdao.save(user);
	}
	
	public User findById(long id){
		return userdao.findById(id);
	}
	
	public void update(User user){
		userdao.update(user);
	}
	
	public List<User> findByDepartmentName(String DepartmentName){
		return userdao.findByDepartmentName(DepartmentName);
	}
	
	public List<User> findByRealName(String realname){
		
		return userdao.findByRealName(realname);
	}
	
	public List<User> findByRealNameByPage(String realname,int page,int rows){
		
		return userdao.findByRealNameByPage(realname,page,rows);
	}
	
	public List<User> findAllByPage(int page,int rows){
		
		return userdao.findAllByPage(page, rows);
	}


}
