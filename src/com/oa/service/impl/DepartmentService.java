package com.oa.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa.bean.Department;
import com.oa.dao.DepartmentDao;

@Service
@Transactional
public class DepartmentService {
	@Resource
	DepartmentDao departmentdao;
	
	public List<Department> findAll(){
		return departmentdao.findAll();
		
	}
	
	public List<Department> findTopAndChild(){
		List<Department> DepartmentList = new ArrayList<Department>();
		List<Department> TopList = new ArrayList<Department>();
		TopList = departmentdao.findToplist();
		for(Department department : TopList){
			List<Department> ChildList = new ArrayList<Department>();
			ChildList = departmentdao.findChildlist(department.getId());
			DepartmentList.add(department);
			DepartmentList.addAll(ChildList);
		}
		return DepartmentList;
	}
	
	public void delete(Long id){
		departmentdao.delete(id);
	}
	
	public void save(Department department){
		departmentdao.save(department);
	}
	
	public Department findById(Long id){
		if(id == null){
			return null;
		}else{
			return departmentdao.findById(id);
		}
			
		
	}
	
	public void update(Department department){
		departmentdao.update(department);
	}

	/**
	 * 获取最上级的部门的列表
	 * @return
	 */
	public List<Department> findToplist() {
		return departmentdao.findToplist();
	}

	/**
	 * 获取子部门的列表
	 * @param parentId 
	 * @return
	 */
	public List<Department> findChildlist(Long parentId) {
		return departmentdao.findChildlist(parentId);
	}

}
