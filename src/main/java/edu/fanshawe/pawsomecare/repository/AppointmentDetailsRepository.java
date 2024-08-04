package edu.fanshawe.pawsomecare.repository;

import edu.fanshawe.pawsomecare.model.AppointmentDetails;
import edu.fanshawe.pawsomecare.model.AppointmentStatus;
import edu.fanshawe.pawsomecare.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointmentDetailsRepository extends JpaRepository<AppointmentDetails, Integer> {

        @Query("select a from AppointmentDetails a where (a.veterinaryStaff = ?1 AND a.veterinarianStatus = ?2) OR (a.groomingStaff = ?1 AND a.groomingStatus = ?2)")
        List<AppointmentDetails> findByStaffEquals(Staff staff1, AppointmentStatus status);

}