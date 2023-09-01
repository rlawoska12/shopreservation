package com.zerobase.shopreservation.entity.dto;

import com.zerobase.shopreservation.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {

    private Long id;
    private String email;
    private String name;
    private LocalDate birth;
    private String phone;

    public static CustomerDto from (Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .name(customer.getName())
                .birth(customer.getBirth())
                .phone(customer.getPhone())
                .build();
    }
}
