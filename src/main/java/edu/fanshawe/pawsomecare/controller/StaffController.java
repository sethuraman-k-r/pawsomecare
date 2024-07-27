package edu.fanshawe.pawsomecare.controller;

import edu.fanshawe.pawsomecare.model.*;
import edu.fanshawe.pawsomecare.model.request.*;
import edu.fanshawe.pawsomecare.services.*;
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
    private final VaccineService vaccineService;
    private final MedicineService medicineService;
    private final GroomingService groomingService;

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

    @PutMapping("pet/finish/appointment")
    public ResponseEntity<Boolean> doFinishAppointment(@RequestBody @Validated FinishAppointment finishAppointment) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            appointmentService.finishAppointment(finishAppointment, user);
            return ResponseEntity.ok().body(true);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().header("error", ex.getLocalizedMessage()).build();
        }
    }

    @GetMapping("/pet/vaccine")
    public ResponseEntity<List<Vaccine>> addNewVaccine() {
        return ResponseEntity.ok().body(vaccineService.getVaccineList());
    }

    @GetMapping("/pet/medicine")
    public ResponseEntity<List<Medicine>> addNewMedicine() {
        return ResponseEntity.ok().body(medicineService.getMedicines());
    }

    @GetMapping("/pet/groom")
    public ResponseEntity<List<Grooming>> addNewGroomingService() {
        return ResponseEntity.ok().body(groomingService.getGroomingList());
    }

}
