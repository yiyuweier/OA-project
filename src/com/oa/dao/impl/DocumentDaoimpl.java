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
	 * ͨ������id�������˵���������ȡ�������������Ϣ
	 */
	public Document getDocumentByTaskId(String taskid,String username) {
		return (Document) getSession().createQuery("from Document d where d.D_taskid=:taskid and d.sendto=:username")//
				.setParameter("taskid", taskid)//
				.setParameter("username", username)//
				.uniqueResult();
	}

	/**
	 * ͨ�������˵�����������ʵ��id ����ѯ��Щû��ͨ����������Ϣ
	 */
	public Document getDocumentByBeginuserNameAndIspass(String BeginuserName,String taskid) {
		return (Document) getSession().createQuery("from Document d where d.initiator=:BeginuserName and d.ispass=:ispass and d.D_executionid=:taskid")//
				.setParameter("BeginuserName", BeginuserName)//
				.setParameter("ispass", 0)//
				.setParameter("taskid", taskid)//
				.uniqueResult();
	}
	
	/**
	 * ��ȡ��һ��������������Ĺ�����������Ϣ������һ������������ÿһ�������������ݿ��о���Ӧһ����¼����һ������������¼�����ݿ���ж�Ӧ�����˸�������¼�������ǲ�ѯ������ֻ��һ��������Ҫgroup by d.D_name��
	 */
	public List<Document> getDocumentByInitiatorAndUniteName(String initiator){
		return getSession().createQuery(//
				"from Document d where d.initiator=:initiator group by d.D_name order by d.D_date asc")//
				.setParameter("initiator", initiator)//
				.list();
	}
	
	/**
	 * ��ȡĳһ�����˷���Ĺ�����������ϸ��Ϣ�����ݷ����˺������ļ����ƣ�
	 */
	public List<Document> getDocumentByInitiatorAndDocumentName(String Initiator,String documentname){
		return getSession().createQuery(//
				"from Document d where d.initiator=:Initiator and d.D_name=:documentname order by d.ispass")//
				.setParameter("Initiator", Initiator)//
				.setParameter("documentname", documentname)//
				.list();
	}


	

	

}
