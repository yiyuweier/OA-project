package com.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa.bean.DocInfo;
import com.oa.bean.DocPrivateInfo;
import com.oa.dao.DocInfoDao;
import com.oa.dao.DocPrivateInfoDao;

@Service
@Transactional
public class DocPrivateInfoService {
	
	@Resource
	DocPrivateInfoDao docPrivateInfoDao;
      
       public void addDocPrivateInfo(DocPrivateInfo docPrivateInfo){ 
    	   docPrivateInfoDao.save(docPrivateInfo);  
       }

	public List<DocPrivateInfo> findAll() {
		
		return docPrivateInfoDao.findAll();
	}

	public void deletePrivateFile(Long id) {
		docPrivateInfoDao.delete(id);
		
	}

	public List<DocPrivateInfo> findByUserId(Long id) {
		// TODO Auto-generated method stub
		return docPrivateInfoDao.findByUserId(id);
	}
  public List<DocPrivateInfo> findByUserIdByPage(Long id,int page,int rows){
	  
	  return docPrivateInfoDao.findByUserIdByPage(id,page,rows);
	  
  }
	public int findByName(String imageFileName) {
		
		return docPrivateInfoDao.findByName(imageFileName);
	}
	
	 public boolean findByName1(String name){
  	   if(docPrivateInfoDao.findByName(name)>0){
  		   return true;
  	   }
  	   else 
  		   return false;
     }   
	 
	//按文件名查询，查出相似的
     public List<DocPrivateInfo> findByName2(String name){   
  	   //System.out.println("Size:  "+docInfoDao.findByName1(name).size());
  	   return docPrivateInfoDao.findByName1(name);
     }
      
}
