package edu.fanshawe.pawsomecare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the vaccine database table.
 * 
 */
@Entity
@NamedQuery(name="Vaccine.findAll", query="SELECT v FROM Vaccine v")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Vaccine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private double amount;

	@Column(name="created_on")
	private Timestamp createdOn;

	@Column(length = 2000)
	private String description;

	@Column(name="is_ins_allowed")
	private Boolean isInsAllowed;

	private String name;

	//bi-directional many-to-one association to Appointment
	@OneToMany(mappedBy="vaccine")
	@ToString.Exclude
	@JsonIgnore
	private List<AppointmentDetails> appointmentDetails;

	public AppointmentDetails addAppointmentDetails(AppointmentDetails appointmentDetails) {
		getAppointmentDetails().add(appointmentDetails);
		appointmentDetails.setVaccine(this);

		return appointmentDetails;
	}

	public AppointmentDetails removeAppointmentDetails(AppointmentDetails appointmentDetails) {
		getAppointmentDetails().remove(appointmentDetails);
		appointmentDetails.setVaccine(null);

		return appointmentDetails;
	}

}