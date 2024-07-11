package edu.fanshawe.pawsomecare.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import edu.fanshawe.pawsomecare.model.*;
import edu.fanshawe.pawsomecare.model.request.AddUnAdoptedPetRequest;
import edu.fanshawe.pawsomecare.model.request.AdoptionRequest;
import edu.fanshawe.pawsomecare.repository.AdoptionRepository;
import org.springframework.stereotype.Service;

import edu.fanshawe.pawsomecare.repository.PetRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PetService {

	private final PetRepository petRepository;
	private final AdoptionRepository adoptionRepository;

	public List<Pet> getUnadoptedPet(boolean isAdopt) {
		return petRepository.findByIsAdopted(isAdopt);
	}

	public Pet addUnadoptedPet(AddUnAdoptedPetRequest petRequest, PetCategory petCategory) {
		Pet pet = new Pet();
		pet.setPetName(petRequest.getName());
		pet.setGender(petRequest.getGender());
		pet.setDob(Timestamp.from(petRequest.getDob().toInstant()));
		pet.setCreatedAt(Timestamp.from(Instant.now()));
		pet.setPetCategory(petCategory);
		pet.setIsAdopted(false);
		pet.setIsLicensed(false);
		pet.setWeight(petRequest.getWeight());
		return petRepository.save(pet);
	}

}
