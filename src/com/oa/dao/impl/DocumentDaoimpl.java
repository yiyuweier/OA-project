package com.oa.dao.impl;



import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.oa.bean.Department;
import com.oa.bean.Document;
import com.oa.dao.DepartmentDao;
import com.oa.dao.DocumentDao;

@Repository
@Transactional
public class DocumentDaoimpl extends BaseDaoimpl<Document> implements DocumentDao{
	
	@Resource
	private SessionFactory sessionfactory;
	
	/**
	 * 通过任务id和审批人的姓名来获取公文审批相关信息
	 */
	public Document getDocumentByTaskId(String taskid,String username) {
		return (Document) getSession().createQuery("from Document d where d.D_taskid=:taskid and d.sendto=:username")//
				.setParameter("taskid", taskid)//
				.setParameter("username", username)//
				.uniqueResult();
	}

	/**
	 * 通过发起人的姓名和流程实例id 来查询那些没有通过的审批信息
	 */
	public Document getDocumentByBeginuserNameAndIspass(String BeginuserName,String taskid) {
		return (Document) getSession().createQuery("from Document d where d.initiator=:BeginuserName and d.ispass=:ispass and d.D_executionid=:taskid")//
				.setParameter("BeginuserName", BeginuserName)//
				.setParameter("ispass", 0)//
				.setParameter("taskid", taskid)//
				.uniqueResult();
	}
	
	/**
	 * 获取摸一个发起人所发起的公文审批的信息（由于一个公文审批的每一个审批人在数据库中均对应一条记录，即一条公文审批记录在数据库会有对应审批人个数条记录，而我们查询出来的只需一条，所以要group by d.D_name）
	 */
	public List<Document> getDocumentByInitiatorAndUniteName(String initiator){
		return getSession().createQuery(//
				"from Document d where d.initiator=:initiator group by d.D_name order by d.D_date asc")//
				.setParameter("initiator", initiator)//
				.list();
	}
	
	/**
	 * 获取某一发起人发起的公文审批的详细信息（根据发起人和审批文件名称）
	 */
	public List<Document> getDocumentByInitiatorAndDocumentName(String Initiator,String documentname){
		return getSession().createQuery(//
				"from Document d where d.initiator=:Initiator and d.D_name=:documentname order by d.ispass")//
				.setParameter("Initiator", Initiator)//
				.setParameter("documentname", documentname)//
				.list();
	}


	

	

}
