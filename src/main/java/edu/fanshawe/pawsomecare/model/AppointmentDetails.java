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

	@ManyToOne
	@JoinColumn(name="veterinarian_id")
	private Staff veterinaryStaff;

	@ManyToOne
	@JoinColumn(name="grooming_id")
	private Staff groomingStaff;

	@OneToOne
	@JoinColumn(name="feedback")
	private Feedback feedback;

}