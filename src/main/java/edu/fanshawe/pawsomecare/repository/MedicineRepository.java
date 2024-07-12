package edu.fanshawe.pawsomecare.repository;

import edu.fanshawe.pawsomecare.model.Medicine;
import edu.fanshawe.pawsomecare.model.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Integer> {
}