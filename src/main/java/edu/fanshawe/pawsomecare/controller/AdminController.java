package edu.fanshawe.pawsomecare.controller;

import java.util.List;

import edu.fanshawe.pawsomecare.model.Grooming;
import edu.fanshawe.pawsomecare.model.PetCategory;
import edu.fanshawe.pawsomecare.model.Vaccine;
import edu.fanshawe.pawsomecare.model.request.AddUnAdoptedPetRequest;
import edu.fanshawe.pawsomecare.model.request.GroomingRequest;
import edu.fanshawe.pawsomecare.model.request.VaccineRequest;
import edu.fanshawe.pawsomecare.services.GroomingService;
import edu.fanshawe.pawsomecare.services.VaccineService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import edu.fanshawe.pawsomecare.model.Pet;
import edu.fanshawe.pawsomecare.services.PetCategoryService;
import edu.fanshawe.pawsomecare.services.PetService;
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

    @GetMapping("/pet/category")
    public ResponseEntity<List<PetCategory>> getPetCategory() {
        return ResponseEntity.ok().body(petCategoryService.getPetCategoryList());
    }

    @PostMapping("/pet/category")
    public ResponseEntity<PetCategory> addPetCategory(@RequestParam("name") String name) {
        return ResponseEntity.ok().body(petCategoryService.addNewPetCategory(name));
    }

    @PutMapping("/pet/category")
    public ResponseEntity<PetCategory> updatePetCategory(@RequestParam("name") String name,
                                                         @RequestParam(required = false, name = "active", defaultValue = "true") Boolean isActive) {
        return ResponseEntity.ok().body(petCategoryService.updatePetCategory(name, isActive));
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
    public ResponseEntity<Vaccine> addNewGroomingService(@RequestBody @Validated VaccineRequest vaccineRequest) {
        Vaccine vaccine = vaccineService.addNewVaccine(vaccineRequest);
        return ResponseEntity.ok().body(vaccine);
    }

    @PutMapping("/pet/vaccine")
    public ResponseEntity<Vaccine> updateGroomingService(@RequestBody @Validated VaccineRequest vaccineRequest) {
        Vaccine vaccine = vaccineService.updateVaccine(vaccineRequest);
        return ResponseEntity.ok().body(vaccine);
    }

}
