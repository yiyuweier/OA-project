package com.oa.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyFlow implements Comparable<MyFlow>{
	
	private String M_name;
	private String M_date;
	private String M_type;
	public String getM_name() {
		return M_name;
	}
	public void setM_name(String m_name) {
		M_name = m_name;
	}
	public String getM_date() {
		return M_date;
	}
	public void setM_date(String m_date) {
		M_date = m_date;
	}
	public String getM_type() {
		return M_type;
	}
	public void setM_type(String m_type) {
		M_type = m_type;
	}
	
	public int compareTo(MyFlow o) {
//		System.out.println("***************************"+this.getM_name());
		Date date1 = new Date();
		Date date2 = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date1 = sdf.parse(this.getM_date());
			date2 = sdf.parse(o.getM_date());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int i = date2.compareTo(date1);
		if(i>0){
			return 1;
		}else if(i<0){
			return 0;
		}
		return -1;
	}
	
	

}
