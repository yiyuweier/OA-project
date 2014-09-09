package com.oa.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 基本的Dao接口，实现基本的增，删，改，查
 * @author Administrator
 *
 */
@Repository
public interface BaseDao<T> {
	
	void save(T t);
	
	void update(T t);
	
	void delete(Long id);
	
	T findById(Long id);
	
	List<T> findAll();
	
	List<T> findByIds(Long[] ids);
	
	List<T> findAllByPage(int page,int rows);

}
