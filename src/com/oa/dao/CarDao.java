package com.oa.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oa.bean.Car;
import com.oa.bean.CarBak;
@Repository
public interface CarDao extends BaseDao<Car>{
	public int findCarInfoByCarId(long carId);
	
	//public List<Car> findAllByPage(int page,int rows);
	public List<CarBak> findAllCarBakByPage(int page,int rows);
}
