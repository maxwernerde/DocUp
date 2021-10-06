package com.bosch.onsite.clinic.controller;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.stereotype.Component;

import com.bosch.onsite.clinic.model.Appointment;
import com.bosch.onsite.clinic.model.AppointmentComparator;
import com.bosch.onsite.clinic.model.Doctor;
import com.bosch.onsite.clinic.model.Patient;
import com.bosch.onsite.clinic.model.Person;

//TODO: make it a REST Controller providing the API as REST-API
@Component
public class ClinicAppointmentController {

	private Map<Person, SortedSet<Appointment>> personToAppointment = new HashMap<>();

	/**
	 * Creates a one hour appointment between patient and doctor with the given subject and start time
	 * @param doctor the doctor necessary for this appointment
	 * @param patient the patient necessary for this appointment
	 * @param subject the subject of this appointment
	 * @param startTime the start time, end time is automatically one hour later than the start time
	 * @return the appointment or null if patient or doctor are not available for the given time.
	 */
	public Appointment createAppointment(Doctor doctor, Patient patient, String subject, LocalTime startTime) {
		Objects.requireNonNull(doctor, "a doctor mus be provided to create an appointment");
		Objects.requireNonNull(patient, "a patient must be provided to create an appointment");
		Objects.requireNonNull(startTime, "start time must be provided to create an appointment");
		if (isFree(patient, startTime) && isFree(doctor, startTime)) {
			Appointment appointment = new Appointment(subject, startTime, List.of(doctor, patient));
			storeAppointment(doctor, appointment);
			storeAppointment(patient, appointment);
			return appointment;
		} else {
			return null;
		}
	}
	
	/**
	 * Returns all appointments for a given person.
	 * @param person the person for which to retrieve all the appointments for
	 * @return all appointments sorted by start time
	 */
	public SortedSet<Appointment> getAppointments(Person person) {
		return personToAppointment.get(person);
	}

	private boolean isFree(Person person, LocalTime time) {
		SortedSet<Appointment> appointments = personToAppointment.get(person);
		if(appointments == null) {
			return true;
		}
		Iterator<Appointment> iterator = appointments.iterator();
		while(iterator.hasNext()) {
			Appointment appointment = iterator.next();
			if(appointment.getStartTime().isBefore(time.plusHours(1)) && appointment.getEndTime().isAfter(time)) {
				return false;
			}
		}
		return true;
	}

	private void storeAppointment(Person person, Appointment appointment) {
		if (personToAppointment.containsKey(person)) {
			SortedSet<Appointment> appointments = personToAppointment.get(person);
			appointments.add(appointment);
		} else {
			SortedSet<Appointment> appointments = new TreeSet<>(new AppointmentComparator());
			appointments.add(appointment);
			personToAppointment.put(person, appointments);
		}
	}

}
