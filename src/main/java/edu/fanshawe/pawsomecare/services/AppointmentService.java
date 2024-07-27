package edu.fanshawe.pawsomecare.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.fanshawe.pawsomecare.model.*;
import edu.fanshawe.pawsomecare.model.request.AppointmentRequest;
import edu.fanshawe.pawsomecare.model.request.FinishAppointment;
import edu.fanshawe.pawsomecare.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppointmentService {

    private final ClinicRepository clinicRepository;
    private final StaffRepository staffRepository;
    private final VaccineRepository vaccineRepository;
    private final PetRepository petRepository;
    private final AppointmentRepository appointmentRepository;
    private final ServiceRepository serviceRepository;
    private final GroomingRepository groomingRepository;
    private final MedicineRepository medicineRepository;
    private final ObjectMapper objectMapper;

    public Appointment bookAppointment(AppointmentRequest appointmentRequest, User user) throws Exception {
        Optional<Pet> oPet = petRepository.findById(appointmentRequest.getPetId());
        Optional<Clinic> oClinic = clinicRepository.findById(appointmentRequest.getClinicId());
        Optional<Staff> oStaff = staffRepository.findById(appointmentRequest.getStaffId());
        Optional<edu.fanshawe.pawsomecare.model.Service> oService = serviceRepository.findByServiceNameEquals(appointmentRequest.getService());
        if(oPet.isEmpty()) {
            throw new Exception("Pet details not found");
        }
        if(oClinic.isEmpty()) {
            throw new Exception("Clinic unavailable");
        }
        if(oStaff.isEmpty()) {
            throw new Exception("Staff details not found");
        }
        if(oService.isEmpty()) {
            throw new Exception("Service details not found");
        }
        List<Grooming> groomings = null;
        if(!appointmentRequest.getGrooms().isEmpty()) {
            groomings = groomingRepository.findAllById(appointmentRequest.getGrooms());
        }
        edu.fanshawe.pawsomecare.model.Service service = oService.get();
        Appointment appointment = new Appointment();
        appointment.setPet(oPet.get());
        appointment.setClinic(oClinic.get());
        appointment.setStaff(oStaff.get());
        appointment.setReason(appointmentRequest.getReason());
        appointment.setApptTime(Timestamp.from(appointmentRequest.getApptTime().toInstant()));
        appointment.setCreatedAt(Timestamp.from(Instant.now()));
        appointment.setService(oService.get());
        appointment.setUser(user);
        appointment.setIsConsult(service.getServiceName() == OfferService.CONSULTATION);
        appointment.setIsGrooming(service.getServiceName() == OfferService.GROOMING);
        appointment.setIsVaccine(service.getServiceName() == OfferService.VACCINATION);

        appointment.setStatus(AppointmentStatus.OPEN);
        appointmentRepository.save(appointment);

        if(groomings != null) {
            groomingRepository.saveAll(groomings.stream().map(grooming -> {
                grooming.getAppointments().add(appointment);
                return grooming;
            }).collect(Collectors.toUnmodifiableList()));
        }

        return appointment;
    }

    public Appointment finishAppointment(FinishAppointment finishAppointment, User user) throws Exception {
        Optional<Appointment> oAppt = appointmentRepository.findByIdAndStaff(finishAppointment.getAppointmentId(), user.getStaff());
        if(oAppt.isEmpty()) {
            throw new Exception("Appointment is not present");
        }
        Appointment appointment = oAppt.get();
        appointment.setConsultDetail(finishAppointment.getAnalysis());
        appointment.setNextVisitSuggest(Timestamp.from(finishAppointment.getNextTime().toInstant()));
        AtomicReference<Double> medicineCost = new AtomicReference<>(0.0d);
        Map<String, Object> presInfo = new HashMap<>();
        List<Map<String, Object>> presMeds = new ArrayList<>();
        if(finishAppointment.getMedicines() != null) {
            finishAppointment.getMedicines().stream().forEach(m -> {
                Map<String, Object> med = m;
                Integer medicineId = (Integer) med.get("id");
                Integer numbers = (Integer) med.get("nos");
                Optional<Medicine> oMedicine = medicineRepository.findById(medicineId);
                if(oMedicine.isPresent()) {
                    Medicine medicine = oMedicine.get();
                    medicine.setCount(medicine.getCount() - numbers);
                    medicineRepository.save(medicine);
                    medicineCost.updateAndGet(v -> v + (medicine.getPerCost() * numbers));

                    Map<String, Object> medInfo = new HashMap<>();
                    medInfo.put("medicine", medicine.getName());
                    medInfo.put("mrng", med.get("mrng"));
                    medInfo.put("noon", med.get("noon"));
                    medInfo.put("evng", med.get("evng"));
                    medInfo.put("ngt", med.get("night"));
                    medInfo.put("count", numbers);
                    medInfo.put("cost", (medicine.getPerCost() * numbers));
                    presMeds.add(medInfo);
                }
            });
        }


        Map<String, Object> vac = new HashMap<>();
        Double vaccineCost = 0.0d;
        if(finishAppointment.getVaccineId() != -1 && finishAppointment.getVaccineId() != null) {
            Optional<Vaccine> oVaccine = vaccineRepository.findById(finishAppointment.getVaccineId());
            if(oVaccine.isPresent()) {
                Vaccine vaccine = oVaccine.get();
                appointment.setVaccine(vaccine);
                vaccineCost += vaccine.getAmount();
                vac.put("name", vaccine.getName());
                vac.put("cost", vaccineCost);
            }
        }

        List<Map<String, Object>> grooms = new ArrayList<>();
        AtomicReference<Double> groomingCost = new AtomicReference<>(0.0d);
        if(appointment.getGroomings() != null) {
            appointment.getGroomings().forEach(g -> {
                Map<String, Object> groom = new HashMap<>();
                groomingCost.updateAndGet(v -> v + g.getCost());
                groom.put("name", g.getName());
                groom.put("cost", g.getCost());
                grooms.add(groom);
            });
        }

        presInfo.put("medicine", presMeds);
        presInfo.put("vaccine", Arrays.asList(vac));
        presInfo.put("grooming", grooms);
        presInfo.put("fee", appointment.getStaff().getConsultFee());

        appointment.setTabletPrescribed(objectMapper.writeValueAsString(presInfo));

        Double finalAmount = vaccineCost + groomingCost.get() + medicineCost.get() + appointment.getStaff().getConsultFee();
        appointment.setAmount(finalAmount);
        appointment.setUpdatedOn(Timestamp.from(Instant.now()));
        appointment.setStatus(AppointmentStatus.CLOSED);
        appointmentRepository.save(appointment);

        return appointment;
    }

    public List<Appointment> getPetAppointments(User user) {
        return appointmentRepository.findByUserEquals(user).stream().map(a -> {
            a.setStaffDetails(a.getStaff().getUser());
            return a;
        }).collect(Collectors.toUnmodifiableList());
    }

    public List<Appointment> getStaffPetAppointments(User user) {
        Staff staff = user.getStaff();
        List<String> staffRoles = staff.getUser().getAuthRoles().stream().map(r -> r.getRoleType()).collect(Collectors.toUnmodifiableList());
        return appointmentRepository.findByStaffEquals(staff).stream()
                .filter(a -> {
                    if(staffRoles.contains("VETERINARIAN")) {
                        return a.getIsVaccine() || a.getIsConsult();
                    }
                    if(staffRoles.contains("GROOMING")) {
                        return a.getIsGrooming();
                    }
                    return false;
                })
                .map(a -> {
            a.setStaffDetails(a.getStaff().getUser());
            return a;
        }).collect(Collectors.toUnmodifiableList());
    }
}
