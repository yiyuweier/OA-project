package com.oa.bean;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Component
public class CarInfoBak {
	private Long id;

    /** null. */
    private CarBak carBak;
    
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
	

	public CarBak getCarBak() {
		return carBak;
	}
	public void setCarBak(CarBak carBak) {
		this.carBak = carBak;
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
