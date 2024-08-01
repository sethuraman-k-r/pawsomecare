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

	@Column(name="created_at")
	private Timestamp createdAt;

	@Column(length = 2000)
	private String description;

	private Integer rate;

	private String title;

	//bi-directional many-to-one association to Appointment
	@OneToOne(mappedBy="feedback")
	@ToString.Exclude
	private Appointment appointment;

}