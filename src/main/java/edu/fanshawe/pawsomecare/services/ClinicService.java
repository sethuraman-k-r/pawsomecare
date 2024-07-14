package edu.fanshawe.pawsomecare.services;

import edu.fanshawe.pawsomecare.model.Clinic;
import edu.fanshawe.pawsomecare.model.Medicine;
import edu.fanshawe.pawsomecare.model.request.ClinicRequest;
import edu.fanshawe.pawsomecare.model.request.MedicineRequest;
import edu.fanshawe.pawsomecare.repository.ClinicRepository;
import edu.fanshawe.pawsomecare.repository.MedicineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ClinicService {

    private final ClinicRepository clinicRepository;

    public List<Clinic> getClinic() {
        List<Clinic> clinics = clinicRepository.findAll();
        clinics.sort(Comparator.comparing(o -> o.getId()));
        return clinics;
    }

    public Clinic addNewClinic(ClinicRequest clinicRequest) {
        Clinic clinic = new Clinic();
        clinic.setDescription(clinicRequest.getDescription());
        clinic.setName(clinicRequest.getName());
        clinic.setSpecialities(clinicRequest.getSpecialities());
        clinic.setAddress(clinicRequest.getAddress());
        return clinicRepository.save(clinic);
    }

    public Clinic updateClinic(ClinicRequest clinicRequest) {
        Clinic clinic = clinicRepository.findById(clinicRequest.getId()).orElse(new Clinic());
        clinic.setDescription(clinicRequest.getDescription());
        clinic.setName(clinicRequest.getName());
        clinic.setSpecialities(clinicRequest.getSpecialities());
        clinic.setAddress(clinicRequest.getAddress());
        return clinicRepository.save(clinic);
    }
}
