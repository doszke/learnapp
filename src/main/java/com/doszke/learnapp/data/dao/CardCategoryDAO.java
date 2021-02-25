package com.doszke.learnapp.data.dao;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "category")
public class CardCategoryDAO implements Mappable{

    @Transient
    public static final String REMOVE = "Xvw5pqCsJ8xX9soSOlwWFsFdfkf7s1";

    @Id
    @Column(name = "category_id")
    @EqualsAndHashCode.Exclude
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private SubjectDAO subject;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<CardDAO> cards;

    //TODO remove this
    public void add(CardDAO card) {
        cards.add(card);
    }

    @Override
    public String[] map() {
        return new String[]{name};
    }
}
