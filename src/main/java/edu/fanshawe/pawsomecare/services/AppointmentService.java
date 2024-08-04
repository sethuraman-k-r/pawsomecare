package edu.fanshawe.pawsomecare.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.fanshawe.pawsomecare.model.*;
import edu.fanshawe.pawsomecare.model.request.AppointmentRequest;
import edu.fanshawe.pawsomecare.model.request.FeedbackRequest;
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
    private final FeedbackRepository feedbackRepository;
    private final AppointmentDetailsRepository appointmentDetailsRepository;

    public Appointment bookAppointment(AppointmentRequest appointmentRequest, User user) throws Exception {
        Optional<Pet> oPet = petRepository.findById(appointmentRequest.getPetId());
        Optional<Clinic> oClinic = clinicRepository.findById(appointmentRequest.getClinicId());
        Staff vetStaff = null;
        Staff grmStaff = null;
        if(appointmentRequest.getVetStaffId() != -1) {
            Optional<Staff> oVetStaff = staffRepository.findById(appointmentRequest.getVetStaffId());
            if(oVetStaff.isEmpty()) {
                throw new Exception("Staff details not found");
            }
            vetStaff = oVetStaff.get();
        }
        if(appointmentRequest.getGrmStaffId() != -1) {
            Optional<Staff> oGrmStaff = staffRepository.findById(appointmentRequest.getGrmStaffId());
            if(oGrmStaff.isEmpty()) {
                throw new Exception("Staff details not found");
            }
            grmStaff = oGrmStaff.get();
        }
        List<edu.fanshawe.pawsomecare.model.Service> services = serviceRepository.findByServiceNameIn(appointmentRequest.getServices());
        if(oPet.isEmpty()) {
            throw new Exception("Pet details not found");
        }
        if(oClinic.isEmpty()) {
            throw new Exception("Clinic unavailable");
        }
        List<Grooming> groomings = null;
        if(!appointmentRequest.getGrooms().isEmpty()) {
            groomings = groomingRepository.findAllById(appointmentRequest.getGrooms());
        }

        AppointmentDetails appointmentDetails = new AppointmentDetails();
        appointmentDetails.setGroomingStaff(grmStaff);
        appointmentDetails.setGroomingCost(0.0d);
        appointmentDetails.setVeterinaryStaff(vetStaff);
        appointmentDetails.setVeterinarianCost(0.0d);
        if(grmStaff != null) {
            appointmentDetails.setGroomingStatus(AppointmentStatus.OPEN);
        }
        if(vetStaff != null) {
            appointmentDetails.setVeterinarianStatus(AppointmentStatus.OPEN);
        }
        appointmentDetailsRepository.save(appointmentDetails);

        Appointment appointment = new Appointment();
        appointment.setPet(oPet.get());
        appointment.setClinic(oClinic.get());
        appointment.setReason(appointmentRequest.getReason());
        appointment.setApptTime(Timestamp.from(appointmentRequest.getApptTime().toInstant()));
        appointment.setCreatedAt(Timestamp.from(Instant.now()));
        appointment.setUser(user);
        appointment.setStatus(AppointmentStatus.OPEN);
        appointment.setAppointmentDetails(appointmentDetails);
        appointmentRepository.save(appointment);

        if(groomings != null && !groomings.isEmpty()) {
            groomingRepository.saveAll(groomings.stream().map(grooming -> {
                grooming.getAppointments().add(appointment);
                return grooming;
            }).collect(Collectors.toUnmodifiableList()));
        }

        if(!services.isEmpty()) {
            serviceRepository.saveAll(services.stream().map(service -> {
                service.getAppointments().add(appointment);
                return service;
            }).collect(Collectors.toUnmodifiableList()));
        }

        return appointment;
    }

    public Appointment finishAppointment(FinishAppointment finishAppointment, User user) throws Exception {
//        Optional<Appointment> oAppt = appointmentRepository.findByIdAndStaff(finishAppointment.getAppointmentId(), user.getStaff());
//        if(oAppt.isEmpty()) {
//            throw new Exception("Appointment is not present");
//        }
//        Appointment appointment = oAppt.get();
//        appointment.setConsultDetail(finishAppointment.getAnalysis());
//        appointment.setNextVisitSuggest(Timestamp.from(finishAppointment.getNextTime().toInstant()));
//        AtomicReference<Double> medicineCost = new AtomicReference<>(0.0d);
//        Map<String, Object> presInfo = new HashMap<>();
//        List<Map<String, Object>> presMeds = new ArrayList<>();
//        if(finishAppointment.getMedicines() != null) {
//            finishAppointment.getMedicines().stream().forEach(m -> {
//                Map<String, Object> med = m;
//                Integer medicineId = (Integer) med.get("id");
//                Integer numbers = (Integer) med.get("nos");
//                Optional<Medicine> oMedicine = medicineRepository.findById(medicineId);
//                if(oMedicine.isPresent()) {
//                    Medicine medicine = oMedicine.get();
//                    medicine.setCount(medicine.getCount() - numbers);
//                    medicineRepository.save(medicine);
//                    medicineCost.updateAndGet(v -> v + (medicine.getPerCost() * numbers));
//
//                    Map<String, Object> medInfo = new HashMap<>();
//                    medInfo.put("medicine", medicine.getName());
//                    medInfo.put("mrng", med.get("mrng"));
//                    medInfo.put("noon", med.get("noon"));
//                    medInfo.put("evng", med.get("evng"));
//                    medInfo.put("ngt", med.get("night"));
//                    medInfo.put("count", numbers);
//                    medInfo.put("cost", (medicine.getPerCost() * numbers));
//                    presMeds.add(medInfo);
//                }
//            });
//        }
//
//
//        Map<String, Object> vac = new HashMap<>();
//        Double vaccineCost = 0.0d;
//        if(finishAppointment.getVaccineId() != -1 && finishAppointment.getVaccineId() != null) {
//            Optional<Vaccine> oVaccine = vaccineRepository.findById(finishAppointment.getVaccineId());
//            if(oVaccine.isPresent()) {
//                Vaccine vaccine = oVaccine.get();
//                appointment.setVaccine(vaccine);
//                vaccineCost += vaccine.getAmount();
//                vac.put("name", vaccine.getName());
//                vac.put("cost", vaccineCost);
//            }
//        }
//
//        List<Map<String, Object>> grooms = new ArrayList<>();
//        AtomicReference<Double> groomingCost = new AtomicReference<>(0.0d);
//        if(appointment.getGroomings() != null) {
//            appointment.getGroomings().forEach(g -> {
//                Map<String, Object> groom = new HashMap<>();
//                groomingCost.updateAndGet(v -> v + g.getCost());
//                groom.put("name", g.getName());
//                groom.put("cost", g.getCost());
//                grooms.add(groom);
//            });
//        }
//
//        presInfo.put("medicine", presMeds);
//        presInfo.put("vaccine", Arrays.asList(vac));
//        presInfo.put("grooming", grooms);
////        presInfo.put("fee", appointment.getStaff().getConsultFee());
//
//        appointment.setTabletPrescribed(objectMapper.writeValueAsString(presInfo));
//
////        Double finalAmount = vaccineCost + groomingCost.get() + medicineCost.get() + appointment.getStaff().getConsultFee();
////        appointment.setAmount(finalAmount);
//        appointment.setUpdatedOn(Timestamp.from(Instant.now()));
//        appointment.setStatus(AppointmentStatus.CLOSED);
//        appointmentRepository.save(appointment);
//
//        return appointment;
        return null;
    }

    public List<Appointment> getPetAppointments(User user) {

        Comparator<Appointment> dateComp = Comparator.comparing(Appointment::getApptTime);

        List<Appointment> appointments = appointmentRepository.findByUserEquals(user).stream().map(a -> {
            List<User> staffs = new ArrayList<>();
            if(a.getAppointmentDetails().getVeterinaryStaff() != null) {
                staffs.add(a.getAppointmentDetails().getVeterinaryStaff().getUser());
            }
            if(a.getAppointmentDetails().getGroomingStaff() != null) {
                staffs.add(a.getAppointmentDetails().getGroomingStaff().getUser());
            }
            a.setStaffDetails(staffs);
            return a;
        }).collect(Collectors.toList());
        appointments.sort(dateComp);
        return appointments;
    }

    public List<Appointment> getStaffPetAppointments(User user) {
//        Staff staff = user.getStaff();
//        List<String> staffRoles = staff.getUser().getAuthRoles().stream().map(r -> r.getRoleType()).collect(Collectors.toUnmodifiableList());
//        return appointmentRepository.findByStaffEquals(staff).stream()
//                .filter(a -> {
//                    if(staffRoles.contains("VETERINARIAN")) {
//                        return a.getIsVaccine() || a.getIsConsult();
//                    }
//                    if(staffRoles.contains("GROOMING")) {
//                        return a.getIsGrooming();
//                    }
//                    return false;
//                })
//                .map(a -> {
////            a.setStaffDetails(a.getStaff().getUser());
//            return a;
//        }).collect(Collectors.toUnmodifiableList());

        return Arrays.asList();
    }

    public boolean doRateService(FeedbackRequest feedbackRequest, User user) {
       Optional<Appointment> appointmentOptional = appointmentRepository.findByIdAndUser(feedbackRequest.getApptId(), user);
        if(appointmentOptional.isPresent()) {
            Appointment appointment = appointmentOptional.get();
            AppointmentDetails appointmentDetails = appointment.getAppointmentDetails();
            Feedback feedback = new Feedback();
            feedback.setRate(feedbackRequest.getRating());
            feedback.setCreatedAt(Timestamp.from(Instant.now()));
            feedback.setTitle(feedbackRequest.getTitle());
            feedback.setDescription(feedbackRequest.getDescription());
            feedback = feedbackRepository.save(feedback);


            appointmentDetails.setFeedback(feedback);
            appointmentDetailsRepository.save(appointmentDetails);

            return true;
        }
        return false;
    }
}
