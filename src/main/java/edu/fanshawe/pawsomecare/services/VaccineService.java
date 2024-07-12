package edu.fanshawe.pawsomecare.services;

import edu.fanshawe.pawsomecare.model.Grooming;
import edu.fanshawe.pawsomecare.model.Vaccine;
import edu.fanshawe.pawsomecare.model.request.GroomingRequest;
import edu.fanshawe.pawsomecare.model.request.VaccineRequest;
import edu.fanshawe.pawsomecare.repository.GroomingRepository;
import edu.fanshawe.pawsomecare.repository.VaccineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class VaccineService {

    private final VaccineRepository vaccineRepository;

    public List<Vaccine> getVaccineList() {
        List<Vaccine> vaccines = vaccineRepository.findAll();
        vaccines.sort(Comparator.comparing(o -> o.getId()));
        return vaccines;
    }

    public Vaccine addNewVaccine(VaccineRequest vaccineRequest) {
        Vaccine vaccine = new Vaccine();
        vaccine.setName(vaccineRequest.getName());
        vaccine.setAmount(vaccineRequest.getAmount());
        vaccine.setDescription(vaccineRequest.getDescription());
        vaccine.setIsInsAllowed(vaccineRequest.getIsInsAllowed());
        vaccine.setCreatedOn(Timestamp.from(new Date().toInstant()));
        return vaccineRepository.save(vaccine);
    }

    public Vaccine updateVaccine(VaccineRequest vaccineRequest) {
        Vaccine vaccine = vaccineRepository.findById(vaccineRequest.getId()).orElse(new Vaccine());
        vaccine.setName(vaccineRequest.getName());
        vaccine.setAmount(vaccineRequest.getAmount());
        vaccine.setDescription(vaccineRequest.getDescription());
        vaccine.setIsInsAllowed(vaccineRequest.getIsInsAllowed());
        return vaccineRepository.save(vaccine);
    }
}
