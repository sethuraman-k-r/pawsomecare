package edu.fanshawe.pawsomecare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the license_form database table.
 * 
 */
@Entity
@Table(name="license_form")
@NamedQuery(name="LicenseForm.findAll", query="SELECT l FROM LicenseForm l")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class LicenseForm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private double amount;

	@Column(name="expires_on")
	private Timestamp expiresOn;

	@Column(name="is_active")
	private Boolean isActive;

	@Column(name="issued_at")
	private Timestamp issuedAt;

	@Column(name="license_no", unique = true, nullable = false)
	private String licenseNo;

	//bi-directional many-to-one association to Pet
	@ManyToOne
	@JsonIgnore
	@ToString.Exclude
	private Pet pet;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="owner_id")
	@JsonIgnore
	@ToString.Exclude
	private User user;

	//bi-directional many-to-one association to Pet
	@OneToMany(mappedBy="licenseForm")
	@ToString.Exclude
	@JsonIgnore
	private List<Pet> pets;

	@Column(length = 2000)
	private String description;

	@Transient
	private User owner;

	@Transient
	private Pet animal;

	private LicenseStatus licenseStatus;

	public Pet addPet(Pet pet) {
		getPets().add(pet);
		pet.setLicenseForm(this);

		return pet;
	}

	public Pet removePet(Pet pet) {
		getPets().remove(pet);
		pet.setLicenseForm(null);

		return pet;
	}

}