package com.zerobase.shopreservation.entity;

import com.zerobase.shopreservation.entity.dto.SignUpForm;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String name;
    private String password;
    private LocalDate birth;
    private String phone;
    @OneToMany(targetEntity = Reservation.class, fetch = FetchType.LAZY)
    private List<Reservation> reservationList;

    public static Customer from(SignUpForm form) {
        return Customer.builder()
                .email(form.getEmail())
                .name(form.getName())
                .password(form.getPassword())
                .birth(form.getBirth())
                .phone(form.getPhone())
                .build();
    }
}