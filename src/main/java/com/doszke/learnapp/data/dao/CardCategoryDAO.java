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
    @EqualsAndHashCode.Exclude
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public long getId(){
        return id;
    }

    @Column
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private List<CardDAO> cards;

    public void add(CardDAO card) {
        cards.add(card);
    }

    @Override
    public String[] map() {
        return new String[]{name};
    }
}
