package com.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa.bean.CarBak;
import com.oa.bean.CarInfo;
import com.oa.bean.Car;
import com.oa.dao.CarBakDao;
import com.oa.dao.CarDao;
import com.oa.dao.CarInfoDao;

@Service
@Transactional
public class CarService {
	@Resource
	CarDao cardao;
	@Resource
	CarBakDao carbakdao;
	
	
	public void addcar(Car car){	
		cardao.save(car);		
	}
	public void addcarbak(CarBak carbak){
		
		carbakdao.save(carbak);
	}

	public void updateCarbak(CarBak carbak){
		
		carbakdao.update(carbak);	
	}
	public List<Car> findAll(){		
		return cardao.findAll();
	}
	public List<Car> findAllByPage(int page,int rows){	
		return cardao.findAllByPage(page,rows);	
	}
	public List<CarBak> findAllCarBakByPage(int page,int rows){
		
		return cardao.findAllCarBakByPage(page,rows);	
	}
	public List<CarBak> findAllCarbak(){
		
		return carbakdao.findAll();
		
	}
	public void updateCar(Car car){
		cardao.update(car);
			
	}
	
	public void deleteCar(Long id){
		cardao.delete(id);
	}
	public Car findById(Long id){	
		return cardao.findById(id);
	}
	
	public CarBak findCarBakById(Long id){	
		return carbakdao.findById(id);
	}
}
