package edu.fanshawe.pawsomecare.repository;

import edu.fanshawe.pawsomecare.model.AdoptionForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdoptionRepository extends JpaRepository<AdoptionForm, Integer> {
}