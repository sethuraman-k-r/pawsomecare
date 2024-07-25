package edu.fanshawe.pawsomecare.model.request;

import edu.fanshawe.pawsomecare.model.Gender;
import edu.fanshawe.pawsomecare.model.OfferService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AppointmentRequest {

    private Integer petId;
    private Integer clinicId;
    private Integer staffId;
    private OfferService service;
    private Date apptTime;
    private String reason;
    private List<Integer> grooms;

}
