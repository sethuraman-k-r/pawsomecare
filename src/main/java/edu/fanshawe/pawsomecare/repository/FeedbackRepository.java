package edu.fanshawe.pawsomecare.repository;

import edu.fanshawe.pawsomecare.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
}