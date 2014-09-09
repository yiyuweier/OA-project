package com.oa.bean;

// Generated by Hibernate Tools
import java.util.HashSet;
import java.util.Set;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;

import org.springframework.stereotype.Component;

/**
 * MeetingRoom .
 * 
 * @author Lingo
 */
//@Entity
//@Table(name = "MEETING_ROOM")
@Component
public class MeetingRoom implements java.io.Serializable {
    private static final long serialVersionUID = 0L;

    /** null. */
    private Long id;

    /** null. */
    private String name;

    /** null. */
    private Integer people;
    
    //״̬
    private Integer state;
    
    private String roomAddress;

    /** . */
    private Set<MeetingInfo> meetingInfos = new HashSet<MeetingInfo>(0);

    public MeetingRoom() {
    }

    public MeetingRoom(String name, Integer people,
            Set<MeetingInfo> meetingInfos,Integer state,String roomAddress) {
        this.name = name;
        this.people = people;
        this.meetingInfos = meetingInfos;
        this.state=state;
        this.roomAddress=roomAddress;
    }

    public MeetingRoom(Long id,String tempName, Integer people2) {
    	this.id=id;
    	this.name = tempName;
        this.people = people2;
       
	}

	/** @return null. */
    //@Id
   // @GeneratedValue
    //@Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    /**
     * @param id
     *            null.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return null. */
   // @Column(name = "NAME", length = 200)
    public String getName() {
        return this.name;
    }

    /**
     * @param name
     *            null.
     */
    public void setName(String name) {
        this.name = name;
    }

    /** @return null. */
    //@Column(name = "PEOPLE")
    public Integer getPeople() {
        return this.people;
    }

    /**
     * @param people
     *            null.
     */
    public void setPeople(Integer people) {
        this.people = people;
    }
 
    /** @return . */
   // @OneToMany(fetch = FetchType.LAZY, mappedBy = "meetingRoom")
    public Set<MeetingInfo> getMeetingInfos() {
        return this.meetingInfos;
    }

    /**
     * @param meetingInfos
     *            .
     */
    public void setMeetingInfos(Set<MeetingInfo> meetingInfos) {
        this.meetingInfos = meetingInfos;
    }

	public String getRoomAddress() {
		return roomAddress;
	}

	public void setRoomAddress(String roomAddress) {
		this.roomAddress = roomAddress;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	
    
    
}
