package edu.fanshawe.pawsomecare.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PetCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="is_active")
	private Boolean isActive;

	@Column(unique = true)
	private String name;

	//bi-directional many-to-one association to Pet
	@OneToMany(mappedBy= "petCategory")
	@ToString.Exclude
	@JsonIgnore
	private List<Pet> pets;

	public Pet addPet(Pet pet) {
		getPets().add(pet);
		pet.setPetCategory(this);

		return pet;
	}

	public Pet removePet(Pet pet) {
		getPets().remove(pet);
		pet.setPetCategory(null);

		return pet;
	}

}