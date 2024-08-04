package edu.fanshawe.pawsomecare.repository;

import edu.fanshawe.pawsomecare.model.Appointment;
import edu.fanshawe.pawsomecare.model.Staff;
import edu.fanshawe.pawsomecare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByUserEquals(User user);

    @Query("select a from Appointment a where a.id = ?1 AND (a.appointmentDetails.veterinaryStaff = ?2 OR a.appointmentDetails.groomingStaff = ?2)")
    Optional<Appointment> findByIdAndStaff(Integer apptId, Staff staff);

    Optional<Appointment> findByIdAndUser(Integer apptId, User user);

}