package edu.fanshawe.pawsomecare.repository;

import java.util.Optional;

import edu.fanshawe.pawsomecare.model.PetCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<PetCategory, Integer> {

	Optional<PetCategory> findByName(String name);
	
}
