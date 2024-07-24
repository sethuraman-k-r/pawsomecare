package edu.fanshawe.pawsomecare.repository;

import edu.fanshawe.pawsomecare.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.fanshawe.pawsomecare.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmail(String email);

	List<User> findByAuthRolesIn(List<Role> roles);

}
