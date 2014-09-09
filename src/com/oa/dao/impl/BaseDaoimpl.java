package com.oa.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oa.dao.BaseDao;


@Repository
@Transactional
@Scope()
public abstract class BaseDaoimpl<T> implements BaseDao<T>{
	
	
	private Class<T> clazz;

	public BaseDaoimpl() {
		// 使用反射技术得到T的真实类型
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass(); // 获取当前new的对象的 泛型的父类 类型
		this.clazz = (Class<T>) pt.getActualTypeArguments()[0]; // 获取第一个类型参数的真实类型
	}
	/**
	 * 回去sessionfactoy
	 * 用protect 因为这样能让子类调用
	 * @return
	 */
	
	@Resource
	private SessionFactory sessionFactory;
	
	protected Session getSession(){
		if(sessionFactory==null){
			System.out.println("sessionFactory is null");
		}else{
			System.out.println("here");
		}
		return sessionFactory.getCurrentSession();
	}

	
	public void save(T t) {
		getSession().save(t);
		
	}

	
	public void update(T t) {
		getSession().update(t);
	}

	public void delete(Long id) {
		getSession().delete(findById(id));
	}

	public T findById(Long id) {
		return (T) getSession().get(clazz, id);
		
	}

	public List<T> findAll() {
		return getSession().createQuery(//
				"from "+clazz.getSimpleName())//
				.list();
	}

	public List<T> findByIds(Long[] ids) {
		if(ids == null || ids.length==0){
			return Collections.EMPTY_LIST;
		}else{
			return getSession().createQuery(//
					"from "+clazz.getSimpleName()+" where id in (:ids)")//
					.setParameterList("ids", ids)//
					.list();
	
		}
	}
	
	public List<T> findAllByPage(int page,int rows){
		return getSession().createQuery(//
				"from "+clazz.getSimpleName())//
				.setFirstResult((page-1)*rows).setMaxResults(rows).list();
				
	}

}
