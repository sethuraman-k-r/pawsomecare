package edu.fanshawe.pawsomecare.model.request;

import edu.fanshawe.pawsomecare.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UpdatePetRequest {

    private Integer petId;
    private String petName;
    private Double petWeight;

}
