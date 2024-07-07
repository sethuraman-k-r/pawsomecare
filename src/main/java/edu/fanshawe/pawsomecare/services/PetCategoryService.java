package edu.fanshawe.pawsomecare.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import edu.fanshawe.pawsomecare.model.Gender;
import edu.fanshawe.pawsomecare.model.Pet;
import edu.fanshawe.pawsomecare.repository.PetRepository;
import org.springframework.stereotype.Service;

import edu.fanshawe.pawsomecare.model.Category;
import edu.fanshawe.pawsomecare.repository.CategoryRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PetCategoryService {

	private final CategoryRepository categoryRepository;

	public List<Category> getPetCategoryList() {
		return categoryRepository.findAll();
	}

	public Category addNewPetCategory(String name) {
		Optional<Category> categoryOpt = categoryRepository.findByName(name.toLowerCase());
		if (categoryOpt.isEmpty()) {
			Category category = new Category();
			category.setIsActive(true);
			category.setName(name.toLowerCase());
			return categoryRepository.save(category);
		}
		return categoryOpt.get();
	}

	public Category updatePetCategory(String name, Boolean isActive) {
		Optional<Category> categoryOpt = categoryRepository.findByName(name.toLowerCase());
		if (categoryOpt.isPresent()) {
			Category category = categoryOpt.get();
			category.setIsActive(isActive);
			return categoryRepository.save(category);
		}
		return null;
	}

	public Category findPetCategory(Integer id) {
		return categoryRepository.findById(id).orElse(null);
	}
}
