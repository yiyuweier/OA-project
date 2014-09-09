package com.oa.dao;

import org.springframework.stereotype.Repository;

import com.oa.bean.Car;
import com.oa.bean.CarBak;
@Repository
public interface CarBakDao extends BaseDao<CarBak>{
	public int findCarInfoByCarId(long carId);
	
}
