package edu.fanshawe.pawsomecare.repository;

import edu.fanshawe.pawsomecare.model.Appointment;
import edu.fanshawe.pawsomecare.model.Staff;
import edu.fanshawe.pawsomecare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByUserEquals(User user);
//
//    Optional<Appointment> findByIdAndStaff(Integer petId, Staff staff);

    Optional<Appointment> findByIdAndUser(Integer apptId, User user);

}