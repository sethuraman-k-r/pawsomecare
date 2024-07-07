package edu.fanshawe.pawsomecare.services;

import edu.fanshawe.pawsomecare.model.request.AdoptionRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdoptionService {

    private final PetService petService;

    public Boolean addNewRequestForPetAdoption(AdoptionRequest adoptionRequest) {
        return false;
    }

}
