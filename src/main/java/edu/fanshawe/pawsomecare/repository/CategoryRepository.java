package edu.fanshawe.pawsomecare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.fanshawe.pawsomecare.model.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	Optional<Category> findByName(String name);
	
}
