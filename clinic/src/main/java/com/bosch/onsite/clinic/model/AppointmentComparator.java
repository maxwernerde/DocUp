package com.bosch.onsite.clinic.model;

import java.util.Comparator;

//TODO: add tests to this class
public class AppointmentComparator implements Comparator<Appointment>{

	@Override
	public int compare(Appointment appointment1, Appointment appointment2) {
		if(appointment1.getStartTime().isBefore(appointment2.getStartTime())) {
			return -1;
		} else if(appointment1.getStartTime().isAfter(appointment2.getStartTime())){
			return 1;				
		} else {
			return 0;
		}
	}
}
