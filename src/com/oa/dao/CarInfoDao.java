package com.oa.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oa.bean.CarInfo;
import com.oa.dao.impl.BaseDaoimpl;

@Repository
public interface CarInfoDao extends BaseDao<CarInfo>{
	public List findCarByUserId(long userId);
	
	public List findCarInfoByCarId(long carId);
	
	public List<CarInfo> findCarByUserIdByPage(Long id,int page,int rows);
}
