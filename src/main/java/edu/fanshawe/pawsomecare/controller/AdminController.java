package edu.fanshawe.pawsomecare.controller;

import java.util.List;

import edu.fanshawe.pawsomecare.model.PetCategory;
import edu.fanshawe.pawsomecare.model.request.AddUnAdoptedPetRequest;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/category")
    public ResponseEntity<List<PetCategory>> getPetCategory() {
        return ResponseEntity.ok().body(petCategoryService.getPetCategoryList());
    }

    @PostMapping("/category")
    public ResponseEntity<PetCategory> addPetCategory(@RequestParam("name") String name) {
        return ResponseEntity.ok().body(petCategoryService.addNewPetCategory(name));
    }

    @PutMapping("/category")
    public ResponseEntity<PetCategory> updatePetCategory(@RequestParam("name") String name,
                                                         @RequestParam(required = false, name = "active", defaultValue = "true") Boolean isActive) {
        return ResponseEntity.ok().body(petCategoryService.updatePetCategory(name, isActive));
    }

    @PostMapping("/pet")
    public ResponseEntity<Pet> addNewUnadoptedPet(@RequestBody AddUnAdoptedPetRequest petRequest) {
        PetCategory petCategory = petCategoryService.findPetCategory(petRequest.getCategory());
        return ResponseEntity.ok().body(petService.addUnadoptedPet(petRequest, petCategory));
    }

}
