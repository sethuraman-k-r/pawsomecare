package edu.fanshawe.pawsomecare.model;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name = "users")
@Data
public class User implements Serializable, UserDetails {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String address;

	private double annualIncome;

	private String contact;

	private Timestamp createdAt;

	private Timestamp dob;

	@Column(unique = true)
	private String email;

	private String firstname;

	private Boolean isActive;

	private String lastname;

	@JsonIgnore
	private String password;

	private Timestamp updatedOn;

	private String username;

	@ManyToMany
	@JoinTable(
			name = "user_auth_role",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> authRoles;

	// bi-directional many-to-one association to Appointment
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Appointment> appointments;

	// bi-directional many-to-one association to LicenseForm
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	@ToString.Exclude
	private List<LicenseForm> licenseForms;

	// bi-directional many-to-one association to Pet
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<Pet> pets;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "staff_id", referencedColumnName = "id")
	private Staff staff;

	public Appointment addAppointment(Appointment appointment) {
		getAppointments().add(appointment);
		appointment.setUser(this);

		return appointment;
	}

	public Appointment removeAppointment(Appointment appointment) {
		getAppointments().remove(appointment);
		appointment.setUser(null);

		return appointment;
	}

	public LicenseForm addLicenseForm(LicenseForm licenseForm) {
		getLicenseForms().add(licenseForm);
		licenseForm.setUser(this);

		return licenseForm;
	}

	public LicenseForm removeLicenseForm(LicenseForm licenseForm) {
		getLicenseForms().remove(licenseForm);
		licenseForm.setUser(null);

		return licenseForm;
	}

	public Pet addPet(Pet pet) {
		getPets().add(pet);
		pet.setUser(this);

		return pet;
	}

	public Pet removePet(Pet pet) {
		getPets().remove(pet);
		pet.setUser(null);

		return pet;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		ArrayList<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		roles.addAll(getAuthRoles().stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRoleType())).collect(Collectors.toUnmodifiableList()));
		return roles;
	}

}