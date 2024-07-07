package edu.fanshawe.pawsomecare.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdoptionRequest {

    private Integer petId;
    private String description;

}
