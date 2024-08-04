package edu.fanshawe.pawsomecare.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the appointment database table.
 * 
 */
@Entity
@NamedQuery(name="Appointment.findAll", query="SELECT a FROM Appointment a")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Appointment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;

	@Column(name="appt_time")
	private Timestamp apptTime;

	@Column(name="created_at")
	private Timestamp createdAt;

	@Column(name="next_visit_suggest")
	private Timestamp nextVisitSuggest;

	@Column(length = 3000)
	private String reason;

	private AppointmentStatus status;



	@Column(name="updated_on")
	private Timestamp updatedOn;

	//bi-directional many-to-one association to Clinic
	@ManyToOne
	@JoinColumn(name="clinic")
	private Clinic clinic;

	//bi-directional many-to-one association to Pet
	@ManyToOne
	private Pet pet;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="appt_by")
	private User user;

	@Transient
	public List<User> staffDetails;

	//bi-directional many-to-many association to Medicine
	@ManyToMany
	@JoinTable(
		name="appointment_medicine"
		, joinColumns={
			@JoinColumn(name="appointment_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="medicine_id")
			}
		)
	@ToString.Exclude
	private List<Medicine> medicines;

	@ManyToMany(mappedBy = "appointments")
	@ToString.Exclude
	private List<Service> services;

	//bi-directional many-to-many association to Grooming
	@ManyToMany(mappedBy="appointments")
	@ToString.Exclude
	private List<Grooming> groomings;

	@OneToOne
	@JoinColumn(name="appointmentDetails")
	private AppointmentDetails appointmentDetails;

	@Transient
	private boolean isGroom;

	@Transient
	private boolean isConsult;

}