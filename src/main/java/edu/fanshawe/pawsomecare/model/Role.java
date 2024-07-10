package edu.fanshawe.pawsomecare.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name="user_roles")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Role {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String roleType;

    @ManyToMany(mappedBy = "authRoles")
    private List<User> users;

}
