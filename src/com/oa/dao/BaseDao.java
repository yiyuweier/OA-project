package com.oa.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * ������Dao�ӿڣ�ʵ�ֻ���������ɾ���ģ���
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
