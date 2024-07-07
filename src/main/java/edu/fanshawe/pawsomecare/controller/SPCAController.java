package edu.fanshawe.pawsomecare.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import edu.fanshawe.pawsomecare.model.Gender;
import edu.fanshawe.pawsomecare.model.User;
import edu.fanshawe.pawsomecare.model.request.AddUnAdoptedPetRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import edu.fanshawe.pawsomecare.model.Category;
import edu.fanshawe.pawsomecare.model.Pet;
import edu.fanshawe.pawsomecare.services.PetCategoryService;
import edu.fanshawe.pawsomecare.services.PetService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/spca")
@RolesAllowed("SPCA")
@AllArgsConstructor
public class SPCAController {

    private final PetCategoryService petCategoryService;
    private final PetService petService;

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getPetCategory() {
        return ResponseEntity.ok().body(petCategoryService.getPetCategoryList());
    }

    @PostMapping("/category")
    public ResponseEntity<Category> addPetCategory(@RequestParam("name") String name) {
        return ResponseEntity.ok().body(petCategoryService.addNewPetCategory(name));
    }

    @PutMapping("/category")
    public ResponseEntity<Category> updatePetCategory(@RequestParam("name") String name,
                                                      @RequestParam(required = false, name = "active", defaultValue = "true") Boolean isActive) {
        return ResponseEntity.ok().body(petCategoryService.updatePetCategory(name, isActive));
    }

    @PostMapping("/pet")
    public ResponseEntity<Pet> addNewUnadoptedPet(@RequestBody AddUnAdoptedPetRequest petRequest) {
        Category petCategory = petCategoryService.findPetCategory(petRequest.getCategory());
        return ResponseEntity.ok().body(petService.addUnadoptedPet(petRequest, petCategory));
    }

    @PostMapping("/approve/pet/adopt")
    public ResponseEntity<Boolean> approvePetAdopt(@RequestParam("adoptId") Integer adoptId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(petService.approvePetAdoption(adoptId, user));
    }

}
