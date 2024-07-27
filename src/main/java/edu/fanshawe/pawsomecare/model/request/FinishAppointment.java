package edu.fanshawe.pawsomecare.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class FinishAppointment {

    private Integer appointmentId;
    private Integer vaccineId;
    private String analysis;
    private Date nextTime;
    private List<Map<String, Object>> medicines;

}
