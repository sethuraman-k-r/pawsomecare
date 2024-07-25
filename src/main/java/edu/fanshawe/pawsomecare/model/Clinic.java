package edu.fanshawe.pawsomecare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the clinic database table.
 * 
 */
@Entity
@NamedQuery(name="Clinic.findAll", query="SELECT c FROM Clinic c")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Clinic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 2000)
	private String address;

	@Column(length = 2000)
	private String description;

	private String name;

	@Column(length = 2000)
	private String specialities;

	//bi-directional many-to-one association to Appointment
	@OneToMany(mappedBy="clinic")
	@ToString.Exclude
	@JsonIgnore
	private List<Appointment> appointments;

	//bi-directional many-to-many association to Staff
	@ManyToMany(mappedBy="clinics")
	@ToString.Exclude
	@JsonIgnore
	private List<Staff> staffs;

	public Appointment addAppointment(Appointment appointment) {
		getAppointments().add(appointment);
		appointment.setClinic(this);

		return appointment;
	}

	public Appointment removeAppointment(Appointment appointment) {
		getAppointments().remove(appointment);
		appointment.setClinic(null);

		return appointment;
	}

}