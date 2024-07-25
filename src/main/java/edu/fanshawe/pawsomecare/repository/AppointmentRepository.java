package edu.fanshawe.pawsomecare.repository;

import edu.fanshawe.pawsomecare.model.Appointment;
import edu.fanshawe.pawsomecare.model.Staff;
import edu.fanshawe.pawsomecare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByUserEquals(User user);

    List<Appointment> findByStaffEquals(Staff staff);

}