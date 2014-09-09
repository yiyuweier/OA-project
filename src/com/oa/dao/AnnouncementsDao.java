package com.oa.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oa.bean.Announcements;

@Repository
public interface AnnouncementsDao extends BaseDao<Announcements> {
	public List<Announcements> findByRowAndPage(String rows, String page);
}
