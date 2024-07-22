package edu.fanshawe.pawsomecare.repository;

import edu.fanshawe.pawsomecare.model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Integer> {

    List<Clinic> findByIdIn(List<Integer> clinicIds);

}