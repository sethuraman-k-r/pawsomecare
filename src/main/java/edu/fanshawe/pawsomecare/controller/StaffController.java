package edu.fanshawe.pawsomecare.controller;

import edu.fanshawe.pawsomecare.model.*;
import edu.fanshawe.pawsomecare.model.request.AdoptionRequest;
import edu.fanshawe.pawsomecare.model.request.ApplyLicenseRequest;
import edu.fanshawe.pawsomecare.model.request.AppointmentRequest;
import edu.fanshawe.pawsomecare.model.request.UpdatePetRequest;
import edu.fanshawe.pawsomecare.services.AppointmentService;
import edu.fanshawe.pawsomecare.services.ClinicService;
import edu.fanshawe.pawsomecare.services.PetService;
import edu.fanshawe.pawsomecare.services.UserDetailsService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff")
@RolesAllowed({"VETERINARIAN", "GROOMING"})
@AllArgsConstructor
public class StaffController {

    private final AppointmentService appointmentService;

    @GetMapping("pet/appointments")
    public ResponseEntity<List<Appointment>> getAppointments() throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            List<Appointment> appointments = appointmentService.getStaffPetAppointments(user);
            return ResponseEntity.ok().body(appointments);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("pet/book/appointment")
    public ResponseEntity<Appointment> doUpdateAppointment(@RequestBody @Validated AppointmentRequest appointmentRequest) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            Appointment appointment = appointmentService.bookAppointment(appointmentRequest, user);
            return ResponseEntity.ok().body(appointment);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().header("error", ex.getLocalizedMessage()).build();
        }
    }

}
