package com.oa.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.oa.bean.Document;
import com.oa.bean.Message;

@Repository
public interface MessageDao extends BaseDao<Message> {
	
	Message findByExecution_id(String execution_id,String username);
	
	List<Message> findBySendtoName(String sendto);
	
	public List<Message> getMessageByInitiatorAndUniteName(String initiator);
	
	public List<Message> getMessageByInitiatorAndMessageName(String Initiator,String documentname);
}
