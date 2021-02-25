package com.doszke.learnapp.data.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
public class UserDAO implements Mappable {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    @NotEmpty(message = "Your username cannot be empty. ")
    @Size(min = 5, max = 20, message = "Your username needs to be at lest 5 characters long and cannot be longer than 20 characters. ")
    private String userName;

    @Column
    @Email(message = "Please provide a valid e-mail. ")
    @NotBlank(message = "E-mail is required. ")
    private String email;

    @Column
    @Size(min = 5, message = "Your password needs to be at lest 5 characters long. ")
    @NotBlank(message = "Password is required. ")
    private String password;

    @Column
    @NotBlank(message = "Name is required. ")
    private String name;

    @Column
    private Boolean active;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleDAO> roles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<SubjectDAO> subjects;


    @Override
    public String[] map() {
        return new String[0];
    }

}
