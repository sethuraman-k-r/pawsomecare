package edu.fanshawe.pawsomecare.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the feedback database table.
 * 
 */
@Entity
@NamedQuery(name="Feedback.findAll", query="SELECT f FROM Feedback f")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Feedback implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private Integer appointment;

	@Column(name="created_at")
	private Timestamp createdAt;

	private String description;

	private Integer rate;

	private String title;

	//bi-directional many-to-one association to Appointment
	@OneToMany(mappedBy="feedbackBean")
	@ToString.Exclude
	private List<Appointment> appointments;

	public Appointment addAppointment(Appointment appointment) {
		getAppointments().add(appointment);
		appointment.setFeedbackBean(this);

		return appointment;
	}

	public Appointment removeAppointment(Appointment appointment) {
		getAppointments().remove(appointment);
		appointment.setFeedbackBean(null);

		return appointment;
	}

}