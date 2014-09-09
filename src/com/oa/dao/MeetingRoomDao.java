package com.oa.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oa.bean.MeetingRoom;
@Repository
public interface MeetingRoomDao extends BaseDao<MeetingRoom> {
       public void update(long id1);
      // public void savebak(MeetingRoom meetingRoom);
       public List findAllByPage(int page,int rows);
       
}
