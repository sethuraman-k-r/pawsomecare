package edu.fanshawe.pawsomecare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class AppointmentDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;

	private double amount;

	private double veterinarianCost;

	private double groomingCost;

	@Column(length = 3000)
	private String consultDetail;

	private AppointmentStatus veterinarianStatus;

	private AppointmentStatus groomingStatus;

	@Column(length = 3000)
	private String consultDetails;

	@Column(length = 3000)
	private String groomDetails;

	@ManyToOne
	@JoinColumn(name="veterinarian_id")
	private Staff veterinaryStaff;

	@ManyToOne
	@JoinColumn(name="grooming_id")
	private Staff groomingStaff;

	@OneToOne
	@JoinColumn(name="feedback")
	private Feedback feedback;

	@OneToOne(mappedBy="appointmentDetails")
	@ToString.Exclude
	@JsonIgnore
	private Appointment appointment;

	//bi-directional many-to-one association to Vaccine
	@ManyToOne
	@JoinColumn(name="vaccine")
	private Vaccine vaccine;

}