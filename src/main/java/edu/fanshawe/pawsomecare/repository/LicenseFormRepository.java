package edu.fanshawe.pawsomecare.repository;

import edu.fanshawe.pawsomecare.model.LicenseForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LicenseFormRepository extends JpaRepository<LicenseForm, Integer> {

    List<LicenseForm> findByIsActiveEquals(boolean v);

}