package edu.fanshawe.pawsomecare.repository;

import edu.fanshawe.pawsomecare.model.OfferService;
import edu.fanshawe.pawsomecare.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {

    Optional<Service> findByServiceNameEquals(OfferService service);

}