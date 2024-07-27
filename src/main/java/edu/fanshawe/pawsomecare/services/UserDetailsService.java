package edu.fanshawe.pawsomecare.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import edu.fanshawe.pawsomecare.model.Clinic;
import edu.fanshawe.pawsomecare.model.Role;
import edu.fanshawe.pawsomecare.model.Staff;
import edu.fanshawe.pawsomecare.model.request.AddStaffRequest;
import edu.fanshawe.pawsomecare.repository.ClinicRepository;
import edu.fanshawe.pawsomecare.repository.RoleRepository;
import edu.fanshawe.pawsomecare.repository.StaffRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.fanshawe.pawsomecare.model.User;
import edu.fanshawe.pawsomecare.model.request.AuthRequest;
import edu.fanshawe.pawsomecare.model.request.ChangePasswordRequest;
import edu.fanshawe.pawsomecare.model.request.UserRequest;
import edu.fanshawe.pawsomecare.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;
	private final StaffRepository staffRepository;
	private final ClinicRepository clinicRepository;

	public UserDetails loadUserByUsername(String username, String password) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("No user found with the email: " + username);
		} else {
			if (!passwordEncoder.matches(password, user.getPassword())) {
				throw new UsernameNotFoundException("Invalid credentials");
			}
			return user;
		}
	}

	public User createNewUser(AuthRequest authRequest) {
		Optional<Role> role = roleRepository.findByRoleTypeEquals("CLIENT");
		if(userRepository.findAll().isEmpty()) {
			role = roleRepository.findByRoleTypeEquals("ADMIN");
		}
		User user = new User();
		user.setEmail(authRequest.getEmail());
		user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
		user.setUsername(authRequest.getUsername());
		user.setFirstname(authRequest.getUsername());
		user.setAuthRoles(Arrays.asList(role.orElse(null)));
		user.setCreatedAt(Timestamp.from(Instant.now()));
		user.setIsActive(true);
		user.setUpdatedOn(null);
		return userRepository.save(user);
	}

	public User updateUserDetails(User user, UserRequest userRequest) {
		if (userRequest.getFirstname() != null) {
			user.setFirstname(userRequest.getFirstname());
		}
		if (userRequest.getLastname() != null) {
			user.setLastname(userRequest.getLastname());
		}
		user.setUsername(user.getFirstname() + " " + (user.getLastname() != null ? user.getLastname() : ""));
		if (userRequest.getDob() != null) {
			user.setDob(Timestamp.from(userRequest.getDob().toInstant()));
		}
		if (userRequest.getIncome() != 0.0d) {
			user.setAnnualIncome(userRequest.getIncome());
		}
		if (userRequest.getAddress() != null) {
			user.setAddress(userRequest.getAddress());
		}
		if (userRequest.getContact() != null) {
			user.setContact(userRequest.getContact());
		}
		if(userRequest.getGender() != null) {
			user.setGender(userRequest.getGender());
		}
		user.setUpdatedOn(Timestamp.from(Instant.now()));
		return userRepository.save(user);
	}

	public User updateUserPassword(User user, ChangePasswordRequest changeRequest) throws Exception {
		if (passwordEncoder.matches(changeRequest.getOldPassword(), user.getPassword())) {
			if (changeRequest.getNewPassword() != null && !StringUtils.isEmpty(changeRequest.getNewPassword().trim())
					&& changeRequest.getNewPassword().contentEquals(changeRequest.getConfirmPassword())) {
				user.setPassword(passwordEncoder.encode(changeRequest.getNewPassword()));
				return userRepository.save(user);
			}
			throw new Exception("Password not matched");
		}
		throw new Exception("Incorrect password");
	}

	public User addNewClinicalStaff(AddStaffRequest staffRequest) {
		Optional<Staff> oStaff = staffRepository.findById(staffRequest.getStaffId());
		List<Clinic> clinics = clinicRepository.findByIdIn(staffRequest.getClinicIds());
		Optional<Role> oRole = roleRepository.findByRoleTypeEquals(staffRequest.getUserRole());
		boolean canStaffAdd = oStaff.isEmpty();
		if(canStaffAdd) {
			Staff staff = new Staff();
			User user = new User();

			staff.setId(staffRequest.getStaffId());
			staff.setConsultFee(staffRequest.getConsultFee());
			staff.setClinics(clinics);
			staffRepository.save(staff);

			user.setUsername(staffRequest.getStaffName());
			user.setFirstname(staffRequest.getStaffName());
			user.setEmail(staffRequest.getEmail());
			user.setGender(staffRequest.getGender());
			user.setIsActive(true);
			user.setCreatedAt(Timestamp.from(Instant.now()));
			user.setPassword(passwordEncoder.encode(staffRequest.getStaffId().toString()));
			user.setAuthRoles(Arrays.asList(oRole.get()));
			user.setStaff(staff);
			userRepository.save(user);

			return user;
		}
		return oStaff.get().getUser();
	}

	public List<User> getClinicStaffs(boolean allStaffs, Integer clinicId) {
		List<Role> staffRoles = roleRepository.findByRoleTypeIn(Arrays.asList("VETERINARIAN", "GROOMING"));
		if(allStaffs) {
			List<User> staffs = userRepository.findByAuthRolesIn(staffRoles);
			return staffs;
		} else {
			List<Clinic> clinics = clinicRepository.findByIdIn(Arrays.asList(clinicId));
			if(!clinics.isEmpty()) {
				Clinic clinic = clinics.get(0);
				return clinic.getStaffs().stream().map(s -> s.getUser()).collect(Collectors.toUnmodifiableList());
			}
			return Arrays.asList();
		}
	}

}
