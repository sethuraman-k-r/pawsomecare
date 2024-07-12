package edu.fanshawe.pawsomecare.services;

import edu.fanshawe.pawsomecare.model.Medicine;
import edu.fanshawe.pawsomecare.model.Vaccine;
import edu.fanshawe.pawsomecare.model.request.MedicineRequest;
import edu.fanshawe.pawsomecare.model.request.VaccineRequest;
import edu.fanshawe.pawsomecare.repository.MedicineRepository;
import edu.fanshawe.pawsomecare.repository.VaccineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;

    public List<Medicine> getMedicines() {
        List<Medicine> medicines = medicineRepository.findAll();
        medicines.sort(Comparator.comparing(o -> o.getId()));
        return medicines;
    }

    public Medicine addNewMedicine(MedicineRequest medicineRequest) {
        Medicine medicine = new Medicine();
        medicine.setDescription(medicineRequest.getDescription());
        medicine.setName(medicineRequest.getName());
        medicine.setIsInsAllowed(medicineRequest.getIsInsAllowed());
        medicine.setCreatedOn(Timestamp.from(new Date().toInstant()));
        medicine.setExpiresAt(Timestamp.from(medicineRequest.getExpiresAt().toInstant()));
        medicine.setPerCost(medicineRequest.getCost());
        medicine.setCount(medicineRequest.getCount());
        return medicineRepository.save(medicine);
    }

    public Medicine updateMedicine(MedicineRequest medicineRequest) {
        Medicine medicine = medicineRepository.findById(medicineRequest.getId()).orElse(new Medicine());
        medicine.setDescription(medicineRequest.getDescription());
        medicine.setName(medicineRequest.getName());
        medicine.setIsInsAllowed(medicineRequest.getIsInsAllowed());
        medicine.setCreatedOn(Timestamp.from(new Date().toInstant()));
        medicine.setExpiresAt(Timestamp.from(medicineRequest.getExpiresAt().toInstant()));
        medicine.setPerCost(medicineRequest.getCost());
        medicine.setCount(medicineRequest.getCount());
        return medicineRepository.save(medicine);
    }
}
