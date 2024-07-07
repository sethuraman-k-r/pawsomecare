package edu.fanshawe.pawsomecare.controller;

import edu.fanshawe.pawsomecare.model.Pet;
import edu.fanshawe.pawsomecare.services.PetService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/common")
@RolesAllowed({"USER", "SPCA", "ASA"})
@AllArgsConstructor
public class StandardController {

    private final PetService petService;

    @GetMapping("/pet")
    public ResponseEntity<List<Pet>> getUnAdoptedPets(@RequestParam("isAdopt") Boolean isAdopt) {
        return ResponseEntity.ok().body(petService.getUnadoptedPet(isAdopt));
    }

}
