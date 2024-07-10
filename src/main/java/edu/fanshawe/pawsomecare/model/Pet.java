package edu.fanshawe.pawsomecare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the pet database table.
 * 
 */
@Entity
@NamedQuery(name="Pet.findAll", query="SELECT p FROM Pet p")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Pet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private Integer age;

	@Column(name="created_at")
	private Timestamp createdAt;

	private Timestamp dob;

	private Gender gender;

	@Column(name="is_adopted")
	private Boolean isAdopted;

	@Column(name="is_licensed")
	private Boolean isLicensed;

	@Column(name="pet_name")
	private String petName;

	@Column(name="updated_on")
	private Timestamp updatedOn;

	private double weight;

	//bi-directional many-to-one association to AdoptionForm
	@OneToMany(mappedBy="pet")
	@ToString.Exclude
	@JsonIgnore
	private List<AdoptionForm> adoptionForms;

	//bi-directional many-to-one association to Appointment
	@OneToMany(mappedBy="pet")
	@ToString.Exclude
	@JsonIgnore
	private List<Appointment> appointments;

	//bi-directional many-to-one association to LicenseForm
	@OneToMany(mappedBy="pet")
	@ToString.Exclude
	@JsonIgnore
	private List<LicenseForm> licenseForms;

	//bi-directional many-to-one association to Category
	@ManyToOne
	@ToString.Exclude
	@JsonIgnore
	private PetCategory petCategory;

	//bi-directional many-to-one association to LicenseForm
	@ManyToOne
	@JoinColumn(name="license_id")
	@JsonIgnore
	private LicenseForm licenseForm;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="owner_id")
	@JsonIgnore
	private User user;

	public AdoptionForm addAdoptionForm(AdoptionForm adoptionForm) {
		getAdoptionForms().add(adoptionForm);
		adoptionForm.setPet(this);

		return adoptionForm;
	}

	public AdoptionForm removeAdoptionForm(AdoptionForm adoptionForm) {
		getAdoptionForms().remove(adoptionForm);
		adoptionForm.setPet(null);

		return adoptionForm;
	}

	public Appointment addAppointment(Appointment appointment) {
		getAppointments().add(appointment);
		appointment.setPet(this);

		return appointment;
	}

	public Appointment removeAppointment(Appointment appointment) {
		getAppointments().remove(appointment);
		appointment.setPet(null);

		return appointment;
	}

	public LicenseForm addLicenseForm(LicenseForm licenseForm) {
		getLicenseForms().add(licenseForm);
		licenseForm.setPet(this);

		return licenseForm;
	}

	public LicenseForm removeLicenseForm(LicenseForm licenseForm) {
		getLicenseForms().remove(licenseForm);
		licenseForm.setPet(null);

		return licenseForm;
	}

}