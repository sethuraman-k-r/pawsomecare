package edu.fanshawe.pawsomecare.repository;

import edu.fanshawe.pawsomecare.model.AdoptionForm;
import edu.fanshawe.pawsomecare.model.Grooming;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroomingRepository extends JpaRepository<Grooming, Integer> {
}