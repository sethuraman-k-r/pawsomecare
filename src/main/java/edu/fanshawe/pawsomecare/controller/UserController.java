package edu.fanshawe.pawsomecare.controller;

import edu.fanshawe.pawsomecare.model.User;
import edu.fanshawe.pawsomecare.model.request.AdoptionRequest;
import edu.fanshawe.pawsomecare.services.PetService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RolesAllowed("USER")
@AllArgsConstructor
public class UserController {

    private final PetService petService;

    @RequestMapping("adopt/new")
    public ResponseEntity<Boolean> requestPetAdoption(@RequestBody AdoptionRequest petAdoption) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(petService.requestForPetAdoption(petAdoption, user));
    }

}
