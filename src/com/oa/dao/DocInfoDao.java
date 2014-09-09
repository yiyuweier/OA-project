package com.oa.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.oa.bean.DocInfo;
@Repository
public interface DocInfoDao extends BaseDao<DocInfo>{
   public List<DocInfo> findByUserId(Long userId);
   
   //������
   public List<DocInfo> findAll1();
   //��ѯ��������
   public int findByName(String name);
   //���ļ�����ѯ
   public List<DocInfo> findByName1(String name);
   
   public List<DocInfo> findAllByPage1(int page,int rows);
   
   public List<DocInfo> findByUserIdByPage(Long id,int page,int rows);
 
}
