package com.doszke.learnapp.data.dao;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "card")
public class CardDAO implements Mappable{

    @Transient
    public static final CardDAO EMPTY = new CardDAO();

    @Id
    @EqualsAndHashCode.Exclude
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public long getId(){
        return id;
    }

    @Column
    private String front;

    @Column
    private String back;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CardCategoryDAO category;

    @Override
    public String[] map() {
        return new String[]{front, back, category.getName()};
    }
}
