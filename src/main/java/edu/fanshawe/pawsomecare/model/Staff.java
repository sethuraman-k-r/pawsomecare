package edu.fanshawe.pawsomecare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the Staff database table.
 * 
 */
@Entity
@Getter
@Setter
@Table(name = "staffs")
@ToString
@RequiredArgsConstructor
public class Staff implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(unique = true)
	private String email;

	private String firstname;

	@Column(name = "is_active")
	private Boolean isActive;

	private String lastname;

	@JsonIgnore
	private String password;

	@Column(name = "updated_on")
	private Timestamp updatedOn;

	private String username;

	private double consultFee;

	private String address;

	private double annualIncome;

	private String contact;

	@Column(name = "created_at")
	private Timestamp createdAt;

	private Timestamp dob;

	@ManyToMany
	@JoinTable(
			name = "user_auth_role",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> authRoles;

	//bi-directional many-to-one association to Appointment
	@OneToMany(mappedBy="staff")
	@ToString.Exclude
	private List<Appointment> appointments;

	//bi-directional many-to-many association to Clinic
	@ManyToMany
	@JoinTable(
		name="staff_clinic"
		, joinColumns={
			@JoinColumn(name="staff_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="clinic_id")
			}
		)
	@ToString.Exclude
	private List<Clinic> clinics;

	public Appointment addAppointment(Appointment appointment) {
		getAppointments().add(appointment);
		appointment.setStaff(this);

		return appointment;
	}

	public Appointment removeAppointment(Appointment appointment) {
		getAppointments().remove(appointment);
		appointment.setStaff(null);

		return appointment;
	}

}