package edu.fanshawe.pawsomecare.controller;

import java.util.List;
import java.util.Map;

import edu.fanshawe.pawsomecare.model.*;
import edu.fanshawe.pawsomecare.model.request.*;
import edu.fanshawe.pawsomecare.repository.UserRepository;
import edu.fanshawe.pawsomecare.services.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/admin")
@RolesAllowed("ADMIN")
@AllArgsConstructor
public class AdminController {

    private final PetCategoryService petCategoryService;
    private final PetService petService;
    private final GroomingService groomingService;
    private final VaccineService vaccineService;
    private final MedicineService medicineService;
    private final ClinicService clinicService;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    @GetMapping("/pet/category")
    public ResponseEntity<List<PetCategory>> getPetCategory() {
        return ResponseEntity.ok().body(petCategoryService.getPetCategoryList());
    }

    @PostMapping("/pet/category")
    public ResponseEntity<PetCategory> addPetCategory(@RequestParam("name") String name, @RequestParam("cost") double cost) {
        return ResponseEntity.ok().body(petCategoryService.addNewPetCategory(name, cost));
    }

    @PutMapping("/pet/category")
    public ResponseEntity<PetCategory> updatePetCategory(@RequestParam("name") String name,
                                                         @RequestParam(required = false, name = "active", defaultValue = "true") Boolean isActive,
                                                         @RequestParam("cost") double cost) {
        return ResponseEntity.ok().body(petCategoryService.updatePetCategory(name, isActive, cost));
    }

    @PostMapping("/pet/new/unadopt")
    public ResponseEntity<Pet> addNewUnadoptedPet(@RequestBody AddUnAdoptedPetRequest petRequest) {
        PetCategory petCategory = petCategoryService.findPetCategory(petRequest.getCategory());
        return ResponseEntity.ok().body(petService.addUnadoptedPet(petRequest, petCategory));
    }

    @GetMapping("/pet/groom")
    public ResponseEntity<List<Grooming>> addNewGroomingService() {
        return ResponseEntity.ok().body(groomingService.getGroomingList());
    }

    @PostMapping("/pet/groom")
    public ResponseEntity<Grooming> addNewGroomingService(@RequestBody @Validated GroomingRequest groomingRequest) {
        Grooming grooming = groomingService.addNewGroomingService(groomingRequest);
        return ResponseEntity.ok().body(grooming);
    }

    @PutMapping("/pet/groom")
    public ResponseEntity<Grooming> updateGroomingService(@RequestBody @Validated GroomingRequest groomingRequest) {
        Grooming grooming = groomingService.updateGroomingService(groomingRequest);
        return ResponseEntity.ok().body(grooming);
    }

    @GetMapping("/pet/vaccine")
    public ResponseEntity<List<Vaccine>> addNewVaccine() {
        return ResponseEntity.ok().body(vaccineService.getVaccineList());
    }

    @PostMapping("/pet/vaccine")
    public ResponseEntity<Vaccine> addNewVaccineService(@RequestBody @Validated VaccineRequest vaccineRequest) {
        Vaccine vaccine = vaccineService.addNewVaccine(vaccineRequest);
        return ResponseEntity.ok().body(vaccine);
    }

    @PutMapping("/pet/vaccine")
    public ResponseEntity<Vaccine> updateVaccineService(@RequestBody @Validated VaccineRequest vaccineRequest) {
        Vaccine vaccine = vaccineService.updateVaccine(vaccineRequest);
        return ResponseEntity.ok().body(vaccine);
    }

    @GetMapping("/pet/medicine")
    public ResponseEntity<List<Medicine>> addNewMedicine() {
        return ResponseEntity.ok().body(medicineService.getMedicines());
    }

    @PostMapping("/pet/medicine")
    public ResponseEntity<Medicine> addNewMedicineService(@RequestBody @Validated MedicineRequest medicineRequest) {
        return ResponseEntity.ok().body(medicineService.addNewMedicine(medicineRequest));
    }

    @PutMapping("/pet/medicine")
    public ResponseEntity<Medicine> updateGroomingService(@RequestBody @Validated MedicineRequest medicineRequest) {
        return ResponseEntity.ok().body(medicineService.updateMedicine(medicineRequest));
    }

    @GetMapping("/pet/clinic")
    public ResponseEntity<List<Clinic>> addNewClinic() {
        return ResponseEntity.ok().body(clinicService.getClinic());
    }

    @PostMapping("/pet/clinic")
    public ResponseEntity<Clinic> addNewClinicService(@RequestBody @Validated ClinicRequest clinicRequest) {
        return ResponseEntity.ok().body(clinicService.addNewClinic(clinicRequest));
    }

    @PutMapping("/pet/clinic")
    public ResponseEntity<Clinic> updateClinicService(@RequestBody @Validated ClinicRequest clinicRequest) {
        return ResponseEntity.ok().body(clinicService.updateClinic(clinicRequest));
    }

    @PostMapping("/pet/staff")
    public ResponseEntity<User> addNewClinicStaff(@RequestBody @Validated AddStaffRequest staffRequest) {
        return ResponseEntity.ok().body(userDetailsService.addNewClinicalStaff(staffRequest));
    }

    @GetMapping("/pet/staff")
    public ResponseEntity<List<User>> getClinicStaff() {
        return ResponseEntity.ok().body(userDetailsService.getClinicStaffs(true));
    }

    @GetMapping("/pet/license")
    public ResponseEntity<List<LicenseForm>> getAppliedLicense() {
        return ResponseEntity.ok().body(petService.getAppliedPetLicense());
    }

    @PutMapping("/pet/license/{licenseId}")
    public ResponseEntity<Boolean> addNewClinicService(@PathVariable("licenseId") Integer licenseId) {
        return ResponseEntity.ok().body(petService.approvePetLicense(licenseId));
    }

}
