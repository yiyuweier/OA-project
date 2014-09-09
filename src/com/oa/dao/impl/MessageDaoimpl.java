package com.oa.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.oa.bean.Document;
import com.oa.bean.Message;
import com.oa.dao.MessageDao;

@Repository
public class MessageDaoimpl extends BaseDaoimpl<Message> implements MessageDao {

	
	public Message findByExecution_id(String execution_id,String username) {
		
		return (Message) getSession().createQuery(//
				"from Message m where m.execution_id=:execution_id and m.sendto=:username")//
				.setParameter("execution_id", execution_id)//
				.setParameter("username", username)//
				.uniqueResult();
	}

	
	public List<Message> findBySendtoName(String sendto) {
		return getSession().createQuery(//
				"from Message m where m.sendto=:sendto")
				.setParameter("sendto", sendto)
				.list();
	}
	
	public List<Message> getMessageByInitiatorAndUniteName(String initiator){
		return getSession().createQuery(//
				"from Message m where m.initiator=:initiator group by m.M_name order by m.M_date asc")//
				.setParameter("initiator", initiator)//
				.list();
	}
	
	public List<Message> getMessageByInitiatorAndMessageName(String Initiator,String documentname){
		return getSession().createQuery(//
				"from Message d where d.initiator=:Initiator and d.M_name=:documentname")//
				.setParameter("Initiator", Initiator)//
				.setParameter("documentname", documentname)
				.list();
	}
	

	

}
