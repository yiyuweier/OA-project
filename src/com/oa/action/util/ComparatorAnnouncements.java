package com.oa.action.util;

import java.util.Comparator;

import com.oa.bean.Announcements;
public class ComparatorAnnouncements<Announcements> implements Comparator<Announcements>{

	@Override
	public int compare(Announcements o1, Announcements o2) {
		com.oa.bean.Announcements announcement1 = (com.oa.bean.Announcements) o1;
		com.oa.bean.Announcements announcement2 = (com.oa.bean.Announcements) o2;
		int flag = announcement2.getA_time().compareTo(announcement1.getA_time());
		return flag;
	}

}
