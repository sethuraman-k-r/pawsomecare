package edu.fanshawe.pawsomecare.model;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
@Data
public class User implements Serializable, UserDetails {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String address;

	@Column(name = "annual_income")
	private double annualIncome;

	private String contact;

	@Column(name = "created_at")
	private Timestamp createdAt;

	private Timestamp dob;

	@Column(unique = true)
	private String email;

	private String firstname;

	@Column(name = "is_active")
	private Boolean isActive;

	private String lastname;

	@JsonIgnore
	private String password;

	@Enumerated(EnumType.ORDINAL)
	private UserRole role;

	@Column(name = "updated_on")
	private Timestamp updatedOn;

	private String username;

	// bi-directional many-to-one association to AdoptionForm
	@OneToMany(mappedBy = "approvedBy", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	@ToString.Exclude
	private List<AdoptionForm> approverForms;

	// bi-directional many-to-one association to AdoptionForm
	@OneToMany(mappedBy = "ownerId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	@ToString.Exclude
	private List<AdoptionForm> petOwnerForms;

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

	public User() {
	}

	public AdoptionForm addApproverForms(AdoptionForm approverForm) {
		getApproverForms().add(approverForm);
		approverForm.setApprovedBy(this);
		return approverForm;
	}

	public AdoptionForm removeApproverForms(AdoptionForm approverForm) {
		getApproverForms().remove(approverForm);
		approverForm.setApprovedBy(null);
		return approverForm;
	}

	public AdoptionForm addPetOwnerForms(AdoptionForm petOwnerForm) {
		getPetOwnerForms().add(petOwnerForm);
		petOwnerForm.setOwnerId(this);

		return petOwnerForm;
	}

	public AdoptionForm removePetOwnerForms(AdoptionForm petOwnerForm) {
		getPetOwnerForms().remove(petOwnerForm);
		petOwnerForm.setOwnerId(null);

		return petOwnerForm;
	}

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
		SimpleGrantedAuthority role = new SimpleGrantedAuthority("ROLE_" + getRole().name());
		roles.add(role);
		return roles;
	}

}