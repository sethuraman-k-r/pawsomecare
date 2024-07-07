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

	public Pet addUnadoptedPet(AddUnAdoptedPetRequest petRequest, Category petCategory) {
		Pet pet = new Pet();
		pet.setPetName(petRequest.getName());
		pet.setGender(petRequest.getGender());
		pet.setDob(Timestamp.from(petRequest.getDob().toInstant()));
		pet.setCreatedAt(Timestamp.from(Instant.now()));
		pet.setCategory(petCategory);
		pet.setIsAdopted(false);
		pet.setIsLicensed(false);
		pet.setWeight(petRequest.getWeight());
		return petRepository.save(pet);
	}

	public Boolean requestForPetAdoption(AdoptionRequest petAdoption, User user) {
		Optional<Pet> petOptional = petRepository.findByIdAndIsAdopted(petAdoption.getPetId(), false);
		if(petOptional.isEmpty()) {
			return false;
		}
		AdoptionForm adoptionForm = new AdoptionForm();
		Pet pet = petOptional.get();
		if(pet.getUser() != null) {
			return false;
		}
		adoptionForm.setPet(pet);
		adoptionForm.setCreatedAt(Timestamp.from(Instant.now()));
		adoptionForm.setDescription(petAdoption.getDescription());
		adoptionForm.setOwnerId(user);
		adoptionRepository.save(adoptionForm);
		return true;
	}

	public Boolean approvePetAdoption(Integer adoptId, User user) {
		Optional<AdoptionForm> optionalAdoptionForm = adoptionRepository.findById(adoptId);
		if(optionalAdoptionForm.isEmpty()) {
			return false;
		}
		AdoptionForm adoptionForm = optionalAdoptionForm.get();
		Pet pet = adoptionForm.getPet();
		pet.setUser(adoptionForm.getOwnerId());
		pet.setIsAdopted(true);
		pet.setUpdatedOn(Timestamp.from(Instant.now()));
		adoptionForm.setApprovedBy(user);
		adoptionForm.setUpdatedOn(Timestamp.from(Instant.now()));
		adoptionForm.setPet(pet);
		adoptionRepository.save(adoptionForm);
		return true;
	}

}
