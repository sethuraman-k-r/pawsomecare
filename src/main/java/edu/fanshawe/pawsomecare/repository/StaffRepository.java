package edu.fanshawe.pawsomecare.repository;

import edu.fanshawe.pawsomecare.model.Staff;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends CrudRepository<Staff, Integer> {

}
