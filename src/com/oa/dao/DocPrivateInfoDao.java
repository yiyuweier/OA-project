package com.oa.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.oa.bean.DocInfo;
import com.oa.bean.DocPrivateInfo;
@Repository
public interface DocPrivateInfoDao extends BaseDao<DocPrivateInfo>{
   public List<DocPrivateInfo> findByUserId(Long userId);

public int findByName(String imageFileName);
   
//按文件名查询
public List<DocPrivateInfo> findByName1(String name);

public List<DocPrivateInfo> findByUserIdByPage(Long id,int page,int rows);
   //按降序
  // public List<DocInfo> findAll1();
   
  // public int findByName(String name);
   
  // public void SavePrivateFile(DocInfo docInfo);
}
