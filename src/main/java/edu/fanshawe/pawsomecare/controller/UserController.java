package edu.fanshawe.pawsomecare.controller;

import edu.fanshawe.pawsomecare.model.Pet;
import edu.fanshawe.pawsomecare.model.User;
import edu.fanshawe.pawsomecare.model.request.AdoptionRequest;
import edu.fanshawe.pawsomecare.model.request.ApplyLicenseRequest;
import edu.fanshawe.pawsomecare.model.request.UpdatePetRequest;
import edu.fanshawe.pawsomecare.services.PetService;
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

}
