package com.zerobase.shopreservation.entity.dto;

import com.zerobase.shopreservation.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationDto {

    private Long id;

    private CustomerDto customerDto;

    private ShopDto shopDto;

    private LocalDateTime reservationDateTime;

    private boolean approval;

    private boolean refusal;

    public static ReservationDto from(Reservation reservation) {

        return ReservationDto.builder()
                .id(reservation.getId())
                .customerDto(CustomerDto.from(reservation.getCustomer()))
                .shopDto(ShopDto.from(reservation.getShop()))
                .reservationDateTime(reservation.getReservationDateTime())
                .approval(reservation.isApproval())
                .refusal(reservation.isRefusal())
                .build();
    }
}
