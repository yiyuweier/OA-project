package com.oa.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.oa.bean.DocInfo;
@Repository
public interface DocInfoDao extends BaseDao<DocInfo>{
   public List<DocInfo> findByUserId(Long userId);
   
   //按降序
   public List<DocInfo> findAll1();
   //查询重名个数
   public int findByName(String name);
   //按文件名查询
   public List<DocInfo> findByName1(String name);
   
   public List<DocInfo> findAllByPage1(int page,int rows);
   
   public List<DocInfo> findByUserIdByPage(Long id,int page,int rows);
 
}
