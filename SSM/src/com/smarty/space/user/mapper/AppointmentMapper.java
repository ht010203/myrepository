package com.smarty.space.user.mapper;

import org.apache.ibatis.annotations.Param;

import com.smarty.space.user.model.Appointment;

public interface AppointmentMapper {

	
	public int insertAppointment(Appointment appointment) throws Exception ;
	
	public Appointment queryByKeyWithBook(@Param("bookid") Long Bookid,@Param("studentid") Long studentid)throws Exception ;
}
