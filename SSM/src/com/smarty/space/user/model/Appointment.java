package com.smarty.space.user.model;

import java.util.Date;

public class Appointment {
	
	private Long bookid ;
	
	private Long studentid ;
	
	private Date appointtime ;
	
	public void setAppointtime(Date appointtime) {
		this.appointtime = appointtime;
	}
	
	public void setBookid(Long bookid) {
		this.bookid = bookid;
	}
	
	public void setStudentid(Long studentid) {
		this.studentid = studentid;
	}
	
	public Date getAppointtime() {
		return appointtime;
	}
	
	public Long getBookid() {
		return bookid;
	}
	
	public Long getStudentid() {
		return studentid;
	}

	@Override
	public String toString() {
		return "Appointment [bookid=" + bookid + ", studentid=" + studentid
				+ ", appointtime=" + appointtime + "]";
	}
	
	

}
