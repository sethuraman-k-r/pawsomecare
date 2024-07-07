package edu.fanshawe.pawsomecare.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.fanshawe.pawsomecare.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

	User findByEmail(String email);

}
