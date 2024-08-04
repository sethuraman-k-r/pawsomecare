package edu.fanshawe.pawsomecare.repository;

import edu.fanshawe.pawsomecare.model.AppointmentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentDetailsRepository extends JpaRepository<AppointmentDetails, Integer> {
}