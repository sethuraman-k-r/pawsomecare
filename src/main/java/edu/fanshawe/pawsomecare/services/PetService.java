package edu.fanshawe.pawsomecare.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import edu.fanshawe.pawsomecare.model.*;
import edu.fanshawe.pawsomecare.model.request.AddUnAdoptedPetRequest;
import edu.fanshawe.pawsomecare.model.request.AdoptionRequest;
import edu.fanshawe.pawsomecare.model.request.ApplyLicenseRequest;
import edu.fanshawe.pawsomecare.model.request.UpdatePetRequest;
import edu.fanshawe.pawsomecare.repository.AdoptionRepository;
import edu.fanshawe.pawsomecare.repository.LicenseFormRepository;
import edu.fanshawe.pawsomecare.repository.UserRepository;
import org.springframework.stereotype.Service;

import edu.fanshawe.pawsomecare.repository.PetRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PetService {

	private final PetRepository petRepository;
	private final AdoptionRepository adoptionRepository;
	private final UserRepository userRepository;
	private final LicenseFormRepository licenseFormRepository;

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

	public Pet adoptNewPet(AdoptionRequest adoptionRequest, User user) throws Exception {
		Optional<Pet> oPet = petRepository.findById(adoptionRequest.getPetId());
		if(oPet.isPresent()) {
			Pet pet = oPet.get();
			pet.setUser(user);
			pet.setUpdatedOn(Timestamp.from(Instant.now()));
			pet.setIsAdopted(true);
			petRepository.save(pet);

			user.addPet(pet);
			user.setUpdatedOn(Timestamp.from(Instant.now()));
			userRepository.save(user);

			return pet;
		}
		throw new Exception("Pet not available");
	}

	public List<Pet> getUserPets(User user) throws Exception {
		return petRepository.findByUser(user).stream()
				.map(p -> {
					List<LicenseForm> forms = p.getLicenseForms();
					p.setLicenseForms(forms.stream().filter(l -> l.getUser().getId() == user.getId()).collect(Collectors.toUnmodifiableList()));
					return p;
				})
				.collect(Collectors.toUnmodifiableList());
	}

	public Pet updatePet(UpdatePetRequest petRequest, User user) throws Exception {
		Optional<Pet> oPet =  petRepository.findByUserAndId(user, petRequest.getPetId());
		if(oPet.isPresent()) {
			Pet pet = oPet.get();
			if(petRequest.getPetName() != null) {
				pet.setPetName(petRequest.getPetName());
			}
			if(petRequest.getPetWeight() != null) {
				pet.setWeight(petRequest.getPetWeight());
			}
			return petRepository.save(pet);
		}
		throw  new Exception("Invalid user pet");
	}

	public Pet applyForPetLicense(ApplyLicenseRequest licenseRequest, User user) throws Exception {
		Optional<Pet> oPet =  petRepository.findByUserAndId(user, licenseRequest.getPetId());
		if(oPet.isPresent()) {
			Pet pet = oPet.get();
			LicenseForm prevLicenseForm = pet.getLicenseForm();
			if(prevLicenseForm == null || (prevLicenseForm != null && !prevLicenseForm.getIsActive())) {
				LicenseForm licenseForm = new LicenseForm();
				licenseForm.setLicenseNo(UUID.randomUUID().toString());
				licenseForm.setAmount(licenseRequest.getCost());
				licenseForm.setDescription(licenseRequest.getDescription());
				licenseForm.setIsActive(true);
				licenseForm.setUser(user);
				licenseForm.setPet(pet);
				licenseFormRepository.save(licenseForm);

				pet.setLicenseForm(licenseForm);
				petRepository.save(pet);
				return pet;
			}
		}
		throw new Exception("Invalid user pet");
	}

	public List<LicenseForm> getAppliedPetLicense() {
		List<LicenseForm> licenseForms = licenseFormRepository.findByIsActiveEquals(true);
		return licenseForms.stream().filter(l -> !l.getPet().getIsLicensed()).map(l -> {
			User user = new User();
			user.setId(l.getUser().getId());
			user.setUsername(l.getUser().getUsername());
			user.setAuthRoles(l.getUser().getAuthRoles());
			Pet pet = new Pet();
			pet.setId(l.getPet().getId());
			pet.setPetName(l.getPet().getPetName());
			pet.setPetCategory(l.getPet().getPetCategory());
			pet.setWeight(l.getPet().getWeight());
			pet.setDob(l.getPet().getDob());
			pet.setGender(l.getPet().getGender());
			l.setAnimal(pet);
			l.setOwner(user);
			return l;
		}).collect(Collectors.toUnmodifiableList());
	}

	public boolean approvePetLicense(Integer licenseId) {
		Optional<LicenseForm> oLicenseForm = licenseFormRepository.findById(licenseId);
		if(oLicenseForm.isPresent()) {
			LicenseForm licenseForm = oLicenseForm.get();
			licenseForm.setIssuedAt(Timestamp.from(Instant.now()));
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.YEAR, 2);
			licenseForm.setExpiresOn(Timestamp.from(calendar.toInstant()));
			licenseFormRepository.save(licenseForm);

			Pet pet = licenseForm.getPet();
			pet.setIsLicensed(true);
			pet.setLicenseForm(licenseForm);
			petRepository.save(pet);
			return true;
		}
		return false;
	}

}
