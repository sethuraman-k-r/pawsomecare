package edu.fanshawe.pawsomecare.services;

import edu.fanshawe.pawsomecare.model.*;
import edu.fanshawe.pawsomecare.model.request.AdoptionRequest;
import edu.fanshawe.pawsomecare.model.request.AppointmentRequest;
import edu.fanshawe.pawsomecare.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppointmentService {

    private final ClinicRepository clinicRepository;
    private final StaffRepository staffRepository;
    private final PetRepository petRepository;
    private final AppointmentRepository appointmentRepository;
    private final ServiceRepository serviceRepository;
    private final GroomingRepository groomingRepository;

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
