package com.zerobase.shopreservation.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Star {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Shop.class, fetch = FetchType.LAZY)
    private Shop shop;

    private Integer totalStar;

    private Integer reviewCount;
}
