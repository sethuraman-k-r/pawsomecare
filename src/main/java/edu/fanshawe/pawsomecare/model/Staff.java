package edu.fanshawe.pawsomecare.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


/**
 * The persistent class for the Staff database table.
 * 
 */
@Entity
@Getter
@Setter
@Table(name = "staffs")
@ToString
@RequiredArgsConstructor
public class Staff implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private double consultFee;

	@OneToOne(mappedBy = "staff")
	private User user;

	//bi-directional many-to-many association to Clinic
	@ManyToMany
	@JoinTable(
		name="staff_clinic"
		, joinColumns={
			@JoinColumn(name="staff_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="clinic_id")
			}
		)
	@ToString.Exclude
	private List<Clinic> clinics;

}