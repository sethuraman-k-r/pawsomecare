package edu.fanshawe.pawsomecare.controller;

import edu.fanshawe.pawsomecare.model.*;
import edu.fanshawe.pawsomecare.model.request.AdoptionRequest;
import edu.fanshawe.pawsomecare.model.request.ApplyLicenseRequest;
import edu.fanshawe.pawsomecare.model.request.AppointmentRequest;
import edu.fanshawe.pawsomecare.model.request.UpdatePetRequest;
import edu.fanshawe.pawsomecare.services.*;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RolesAllowed("CLIENT")
@AllArgsConstructor
public class UserController {

    private final PetService petService;
    private final ClinicService clinicService;
    private final UserDetailsService userDetailsService;
    private final AppointmentService appointmentService;
    private final GroomingService groomingService;

    @PostMapping("adopt/new")
    public ResponseEntity<Pet> requestPetAdoption(@RequestBody AdoptionRequest petAdoption) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            Pet pet = petService.adoptNewPet(petAdoption, user);
            return ResponseEntity.ok().body(pet);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("pets")
    public ResponseEntity<List<Pet>> getClientPets() throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            List<Pet> pets = petService.getUserPets(user);
            return ResponseEntity.ok().body(pets);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("pet")
    public ResponseEntity<Pet> updatePet(@RequestBody @Validated UpdatePetRequest updatePetRequest) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            Pet pet = petService.updatePet(updatePetRequest, user);
            return ResponseEntity.ok().body(pet);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("pet/license")
    public ResponseEntity<Pet> applyPetLicense(@RequestBody @Validated ApplyLicenseRequest licenseRequest) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            Pet pet = petService.applyForPetLicense(licenseRequest, user);
            return ResponseEntity.ok().body(pet);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("pet/clinic")
    public ResponseEntity<List<Clinic>> getPetClinics() throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            List<Clinic> clinics = clinicService.getClinic();
            return ResponseEntity.ok().body(clinics);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("pet/staff")
    public ResponseEntity<List<User>> getPetStaff(@RequestParam("clinic") Integer clinicId) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            List<User> users = userDetailsService.getClinicStaffs(true);
            return ResponseEntity.ok().body(users);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("pet/grooming")
    public ResponseEntity<List<Grooming>> getPetGrooming() throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            List<Grooming> groomings = groomingService.getGroomingList();
            return ResponseEntity.ok().body(groomings);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("pet/service")
    public ResponseEntity<List<Service>> getPetService() throws Exception {
        try {
            List<Service> services = petService.getPetServices();
            return ResponseEntity.ok().body(services);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("pet/appointments")
    public ResponseEntity<List<Appointment>> getAppointments() throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            List<Appointment> appointments = appointmentService.getPetAppointments(user);
            return ResponseEntity.ok().body(appointments);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("pet/book/appointment")
    public ResponseEntity<Boolean> doBookPetAppointment(@RequestBody @Validated AppointmentRequest appointmentRequest) throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            appointmentService.bookAppointment(appointmentRequest, user);
            return ResponseEntity.ok().body(true);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().header("error", ex.getLocalizedMessage()).build();
        }
    }

}
