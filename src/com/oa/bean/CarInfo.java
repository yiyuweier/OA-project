package com.oa.bean;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Component
public class CarInfo {
	private Long id;

    /** null. */
    private Car car;
    
	/** null. */
    private String carUsage;

   
	/** null. */
    private Timestamp carStartTime;

    /** null. */
    private Timestamp carEndTime;

    private Date carInfoCreateTime;
    /** null. */
    private Long userId;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}

	public String getCarUsage() {
		return carUsage;
	}
	public void setCarUsage(String carUsage) {
		this.carUsage = carUsage;
	}
	public Timestamp getCarStartTime() {
		return carStartTime;
	}
	public void setCarStartTime(Timestamp carStartTime) {
		this.carStartTime = carStartTime;
	}
	public Timestamp getCarEndTime() {
		return carEndTime;
	}
	public void setCarEndTime(Timestamp carEndTime) {
		this.carEndTime = carEndTime;
	}

	public Date getCarInfoCreateTime() {
		return carInfoCreateTime;
	}
	public void setCarInfoCreateTime(Date carInfoCreateTime) {
		this.carInfoCreateTime = carInfoCreateTime;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
}
