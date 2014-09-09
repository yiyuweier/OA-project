package com.oa.dao;



import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oa.bean.Document;

@Repository
@Transactional
public interface DocumentDao extends BaseDao<Document>{

	public Document getDocumentByTaskId(String taskid,String username);
	
	public Document getDocumentByBeginuserNameAndIspass(String BeginuserName,String taskid);
	
	public List<Document> getDocumentByInitiatorAndUniteName(String initiator);
	
	public List<Document> getDocumentByInitiatorAndDocumentName(String Initiator,String documentname);
}
