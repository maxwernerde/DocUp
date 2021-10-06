package com.bosch.onsite.clinic.model;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class Appointment {

	private String subject;
	private List<Person> members;
	private LocalTime startTime;
	private LocalTime endTime;

	public Appointment(String subject, LocalTime time, List<Person> members) {
		this.subject = subject;
		this.startTime = time;
		this.endTime = time.plusHours(1);
		this.members = members;
	}

	public String getSubject() {
		return subject;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public List<Person> getMembers() {
		return members;
	}

	@Override
	public int hashCode() {
		return Objects.hash(endTime, members, startTime, subject);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Appointment other = (Appointment) obj;
		return Objects.equals(endTime, other.endTime) && Objects.equals(members, other.members)
				&& Objects.equals(startTime, other.startTime) && Objects.equals(subject, other.subject);
	}

}
