package edu.fanshawe.pawsomecare.model.request;

import edu.fanshawe.pawsomecare.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AddStaffRequest {

    private String staffName;
    private String email;
    private Integer staffId;
    private Gender gender;
    private List<Integer> clinicIds;
    private double consultFee;
    private String userRole;

}
