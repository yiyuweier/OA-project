package com.oa.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oa.bean.MeetingRoom;
import com.oa.bean.MeetingRoomBak;
@Repository
public interface MeetingRoomBakDao extends BaseDao<MeetingRoomBak> {
      // public void update(long id1);
	 public List findRoomBakByPage(int page,int rows);
}
