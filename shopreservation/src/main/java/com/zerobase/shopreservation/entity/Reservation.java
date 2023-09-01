package com.zerobase.shopreservation.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(targetEntity = Customer.class, fetch = FetchType.LAZY)
    private Customer customer;
    @ManyToOne(targetEntity = Shop.class, fetch = FetchType.LAZY)
    private Shop shop;
    private LocalDateTime reservationDateTime;
    private Integer tableCount;
    private boolean approval;
    private boolean refusal;
    private boolean visitation;
}
