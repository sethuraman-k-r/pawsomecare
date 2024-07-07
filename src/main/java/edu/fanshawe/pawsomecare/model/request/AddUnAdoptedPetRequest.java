package edu.fanshawe.pawsomecare.model.request;

import edu.fanshawe.pawsomecare.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class AddUnAdoptedPetRequest {

    private String name;
    private Date dob;
    private Gender gender;
    private Integer category;
    private double weight;

}
