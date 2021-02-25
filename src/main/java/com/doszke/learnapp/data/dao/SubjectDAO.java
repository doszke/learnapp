package com.doszke.learnapp.data.dao;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "subject")
public class SubjectDAO implements Mappable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subject_id")
    private long id;

    @Column
    @NotEmpty(message = "Subject name cannot be empty. ")
    @Size(min = 3, max = 40, message = "Subject name must be of length between 3 and 40. ")
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserDAO user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subject")
    private Set<CardCategoryDAO> categories;

    @Override
    public String[] map() {
        return new String[]{name};
    }
}
