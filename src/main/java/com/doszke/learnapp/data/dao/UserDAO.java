package com.doszke.learnapp.data.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
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
    @Length(min = 5, max = 20, message = "Your username needs to be at lest 5 characters long and cannot be longer than 20 characters. ")
    private String userName;

    @Column
    @Email(message = "Please provide a valid e-mail. ")
    @NotEmpty(message = "E-mail is required. ")
    private String email;

    @Column
    @Length(min = 8, max = 20, message = "Your password needs to be at lest 5 characters long and cannot be longer than 20 characters. ")
    @NotEmpty(message = "Password is required. ")
    private String password;

    @Column
    @NotEmpty(message = "Name is required. ")
    private String name;

    @Column
    private Boolean active;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleDAO> roles;


    @Override
    public String[] map() {
        return new String[0];
    }

}
