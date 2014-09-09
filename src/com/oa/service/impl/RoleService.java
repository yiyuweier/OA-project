package com.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa.bean.Role;
import com.oa.bean.User;
import com.oa.dao.RoleDao;


@Service
@Transactional
public class RoleService {
	
	@Resource
	RoleDao roledao;
	
	public List<Role> findAll(){
		return roledao.findAll();
	}
	
	public List<Role> findByRowAndPage(String rows,String page){
		return roledao.findByRowAndPage(rows,page);
	}
	
	public void delete(long id){
		roledao.delete(id);
	}
	
	public void save(Role role){
		roledao.save(role);
	}
	
	public Role findById(long id){
		return roledao.findById(id);
	}
	
	public void update(Role role){
		roledao.update(role);
	}
	
	public List<Role> findByIds(Long[] ids){
		return roledao.findByIds(ids);
	}

}
