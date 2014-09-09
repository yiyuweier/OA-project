package com.oa.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oa.bean.CarInfo;
import com.oa.bean.CarInfoBak;
import com.oa.dao.impl.BaseDaoimpl;

@Repository
public interface CarInfoBakDao extends BaseDao<CarInfoBak>{
	public List findCarByUserId(long userId);
	
	public List findCarInfoByCarId(long carId);
	
	public List<CarInfoBak> findAllCarInfobakByPage(int page,int rows);
}
