package edu.fanshawe.pawsomecare.model.request;

import edu.fanshawe.pawsomecare.model.OfferService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FeedbackRequest {

    private Integer apptId;
    private String title;
    private String description;
    private Integer rating;

}
