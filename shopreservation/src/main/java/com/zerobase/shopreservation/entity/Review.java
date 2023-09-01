package com.zerobase.shopreservation.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Customer.class, fetch = FetchType.LAZY)
    private Customer customer;

    @ManyToOne(targetEntity = Shop.class, fetch = FetchType.LAZY)
    private Shop shop;

    private String title;

    private String contents;
}
