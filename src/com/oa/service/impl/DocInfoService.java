package com.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa.bean.DocInfo;
import com.oa.bean.DocPrivateInfo;
import com.oa.dao.DocInfoDao;

@Service
@Transactional
public class DocInfoService {
	
	@Resource
	DocInfoDao docInfoDao;
       public void addDocInfo(DocInfo docInfo){ 
    	   docInfoDao.save(docInfo);  
       }
       
      
       public List<DocInfo> findAll(){
    	   
    	  return docInfoDao.findAll1();
       }
       
       public List<DocInfo> findAllByPage1(int page,int rows){
    	   
     	  return docInfoDao.findAllByPage1(page,rows);
        }
       
       public List<DocInfo> findByUserIdByPage(Long id,int page,int rows){
    	   
    	   return docInfoDao.findByUserIdByPage(id,page,rows);
    	   
       }
       public void deleteFile(long id){
    	   
    	   docInfoDao.delete(id);
       }
       
       public List<DocInfo> findByUserId(Long userId){
    	   
    	   return docInfoDao.findByUserId(userId);
       }
       
       public int findByName(String name){
    	   
    	   return docInfoDao.findByName(name);
       }
       //是否有文件同名的
       public boolean findByName1(String name){
    	   if(docInfoDao.findByName(name)>0){
    		   return true;
    	   }
    	   else 
    		   return false;
       }
       //按文件名查询，查出相似的
       public List<DocInfo> findByName2(String name){   
    	   System.out.println("Size:  "+docInfoDao.findByName1(name).size());
    	   return docInfoDao.findByName1(name);
       }
}
