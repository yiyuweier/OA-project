package com.oa.dao;



import java.util.List;

import org.springframework.stereotype.Repository;


import com.oa.bean.Department;

@Repository
public interface DepartmentDao extends BaseDao<Department>{

	List<Department> findToplist();

	List<Department> findChildlist(Long parentId);

	

}
