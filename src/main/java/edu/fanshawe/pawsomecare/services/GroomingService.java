package edu.fanshawe.pawsomecare.services;

import edu.fanshawe.pawsomecare.model.Grooming;
import edu.fanshawe.pawsomecare.model.request.GroomingRequest;
import edu.fanshawe.pawsomecare.repository.GroomingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class GroomingService {

    private final GroomingRepository groomingRepository;

    public List<Grooming> getGroomingList() {
        List<Grooming> groomings = groomingRepository.findAll();
        groomings.sort(Comparator.comparing(o -> o.getId()));
        return groomings;
    }

    public Grooming addNewGroomingService(GroomingRequest groomingRequest) {
        Grooming grooming = new Grooming();
        grooming.setName(groomingRequest.getName());
        grooming.setCost(groomingRequest.getCost());
        grooming.setDescription(groomingRequest.getDescription());
        grooming.setIsInsAllowed(groomingRequest.getIsInsAllowed());
        grooming.setTimeRequire(groomingRequest.getTimeRequire());
        return groomingRepository.save(grooming);
    }

    public Grooming updateGroomingService(GroomingRequest groomingRequest) {
        Grooming grooming = groomingRepository.findById(groomingRequest.getId()).orElse(new Grooming());
        grooming.setName(groomingRequest.getName());
        grooming.setCost(groomingRequest.getCost());
        grooming.setDescription(groomingRequest.getDescription());
        grooming.setIsInsAllowed(groomingRequest.getIsInsAllowed());
        grooming.setTimeRequire(groomingRequest.getTimeRequire());
        return groomingRepository.save(grooming);
    }
}
