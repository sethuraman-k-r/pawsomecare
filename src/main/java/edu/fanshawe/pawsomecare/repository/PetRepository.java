package edu.fanshawe.pawsomecare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.fanshawe.pawsomecare.model.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {
	
	List<Pet> findByIsAdopted(Boolean isAdopted);

	Optional<Pet> findByIdAndIsAdopted(Integer id, Boolean isAdopted);
	
}
