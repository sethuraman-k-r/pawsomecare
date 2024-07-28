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
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class IndexController {

    @GetMapping("/pawsomecare/")
    public String home() {
        return "index";
    }

}
