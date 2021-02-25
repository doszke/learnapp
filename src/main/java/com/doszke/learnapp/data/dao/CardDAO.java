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
    @Column(name = "card_id")
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CardCategoryDAO category;

    @Override
    public String[] map() {
        return new String[]{front, back, category.getName()};
    }
}
