package edu.fanshawe.pawsomecare.services;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.fanshawe.pawsomecare.model.PetCategory;
import edu.fanshawe.pawsomecare.repository.CategoryRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PetCategoryService {

	private final CategoryRepository categoryRepository;

	public List<PetCategory> getPetCategoryList() {
		List<PetCategory> categories = categoryRepository.findAll();
		categories.sort(Comparator.comparing(o -> o.getId()));
		return categories;
	}

	public PetCategory addNewPetCategory(String name) {
		Optional<PetCategory> categoryOpt = categoryRepository.findByName(name.toLowerCase());
		if (categoryOpt.isEmpty()) {
			PetCategory petCategory = new PetCategory();
			petCategory.setIsActive(true);
			petCategory.setName(name.toLowerCase());
			return categoryRepository.save(petCategory);
		}
		return categoryOpt.get();
	}

	public PetCategory updatePetCategory(String name, Boolean isActive) {
		Optional<PetCategory> categoryOpt = categoryRepository.findByName(name.toLowerCase());
		if (categoryOpt.isPresent()) {
			PetCategory petCategory = categoryOpt.get();
			petCategory.setIsActive(isActive);
			return categoryRepository.save(petCategory);
		}
		return null;
	}

	public PetCategory findPetCategory(Integer id) {
		return categoryRepository.findById(id).orElse(null);
	}
}
