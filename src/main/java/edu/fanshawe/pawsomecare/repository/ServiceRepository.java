package edu.fanshawe.pawsomecare.repository;

import edu.fanshawe.pawsomecare.model.OfferService;
import edu.fanshawe.pawsomecare.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {

    List<Service> findByServiceNameIn(List<OfferService> services);

}