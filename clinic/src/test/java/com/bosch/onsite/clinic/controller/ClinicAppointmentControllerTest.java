package com.bosch.onsite.clinic.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.SortedSet;

import org.junit.jupiter.api.Test;

import com.bosch.onsite.clinic.model.Appointment;
import com.bosch.onsite.clinic.model.Doctor;
import com.bosch.onsite.clinic.model.Patient;

public class ClinicAppointmentControllerTest {
	
	private ClinicAppointmentController clinicAppointmentController = new ClinicAppointmentController();

	@Test
	public void testCreateAppointmentReturnsAppointment() {
		//given
		Doctor doctor = new Doctor("Dr. Jekyll");
		Patient patient = new Patient("Max Stahl");
		LocalTime time = LocalTime.parse("10:30:00");
		
		//when
		Appointment appointment = clinicAppointmentController.createAppointment(doctor, patient, "checking", time);
		
		//then
		assertNotNull(appointment);
		assertEquals("checking", appointment.getSubject());
		assertEquals(time, appointment.getStartTime());
		assertTrue(appointment.getMembers().contains(doctor));
		assertTrue(appointment.getMembers().contains(patient));
	}
	
	@Test
	public void testCreateAppointmentReturnsNullIfDoctorOrPatientIsNotAvailable() {
		//given
		Doctor doctor1 = new Doctor("Dr. Jekyll");
		Patient patient = new Patient("Max Stahl");
		LocalTime time = LocalTime.parse("10:30:00");
		clinicAppointmentController.createAppointment(doctor1, patient, "checking", time);
		
		Doctor doctor2 = new Doctor("Dr. Foo");
		//when
		Appointment appointment2 = clinicAppointmentController.createAppointment(doctor2, patient, "another meeting", time);
		
		//then
		assertNull(appointment2);
	}
	
	@Test
	public void testCreateAppointmentReturnsAppointmentIfDoctorOrPatientHasAMeetingRightBeforeAnotherMeeting() {
		//given
		Doctor doctor1 = new Doctor("Dr. Jekyll");
		Patient patient = new Patient("Max Stahl");
		LocalTime time1 = LocalTime.parse("10:30:00");
		clinicAppointmentController.createAppointment(doctor1, patient, "checking", time1);
		
		Doctor doctor2 = new Doctor("Dr. Foo");
		LocalTime time2 = LocalTime.parse("11:30:00");
		//when
		Appointment appointment2 = clinicAppointmentController.createAppointment(doctor2, patient, "another meeting", time2);
		
		//then
		assertNotNull(appointment2);
		assertEquals("another meeting", appointment2.getSubject());
		assertEquals(time2, appointment2.getStartTime());
		assertTrue(appointment2.getMembers().contains(doctor2));
		assertTrue(appointment2.getMembers().contains(patient));
	}
	
	
	@Test
	public void testgetAppointmentsReturnsAllAppointmentsStoredForAPerson() throws ParseException {
		//given
		Doctor doctor = new Doctor("Dr. Jekyll");
		Patient patient = new Patient("Max Stahl");
		LocalTime time = LocalTime.parse("10:30:00");
		Appointment expectedAppointment = clinicAppointmentController.createAppointment(doctor, patient, "checking", time);
		
		//when
		SortedSet<Appointment> appointments = clinicAppointmentController.getAppointments(patient);
		
		//then
		assertNotNull(appointments);
		assertEquals(1, appointments.size());
		assertEquals(expectedAppointment, appointments.first());
	}
	
	@Test
	public void testgetAppointmentsReturnsNullIfNoPersonNotAvailable() throws ParseException {
		//given
		Doctor doctor = new Doctor("Dr. Jekyll");
		Patient patient = new Patient("Max Stahl");
		LocalTime time = LocalTime.parse("10:30:00");
		clinicAppointmentController.createAppointment(doctor, patient, "checking", time);
		
		//when
		SortedSet<Appointment> appointments = clinicAppointmentController.getAppointments(new Patient("Oskar"));
		
		//then
		assertNull(appointments);
	}
}
