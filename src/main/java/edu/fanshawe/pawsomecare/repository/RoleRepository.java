package edu.fanshawe.pawsomecare.repository;

import edu.fanshawe.pawsomecare.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRoleTypeEquals(String role);

    List<Role> findByRoleTypeIn(List<String> roles);

}
