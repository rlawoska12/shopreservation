package com.zerobase.shopreservation.controller.customer;

import com.zerobase.shopreservation.entity.dto.ReservationDto;
import com.zerobase.shopreservation.entity.dto.ReservationForm;
import com.zerobase.shopreservation.entity.dto.ReviewForm;
import com.zerobase.shopreservation.entity.dto.ShopDto;
import com.zerobase.shopreservation.jwt.JwtAuthenticationProvider;
import com.zerobase.shopreservation.jwt.UserVo;
import com.zerobase.shopreservation.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final JwtAuthenticationProvider provider;

    @GetMapping("/shop")
    public List<ShopDto> findByShopName(@RequestParam String name) {

        return customerService.findByShopName(name);
    }

    @PostMapping("/reservation")
    public String addReservation(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                 @RequestBody ReservationForm form) {

        UserVo vo = provider.getUserVo(token);
        customerService.addReservation(form, vo.getId());

        return "가게가 예약되었습니다.";
    }

    @GetMapping("/reservation")
    public List<ReservationDto> findReservation(@RequestHeader(name = "X-AUTH-TOKEN") String token) {

        UserVo vo = provider.getUserVo(token);

        return customerService.findByCustomerId(vo.getId());
    }

    @PutMapping("/reservation/{reservationId}")
    public String visitConfirm(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                               @PathVariable Long reservationId) {

        UserVo vo = provider.getUserVo(token);

        customerService.visitConfirm(vo.getId(), reservationId);

        return "예약 방문이 확인되었습니다.";
    }

    @PostMapping("/reservation/{reservationId}/review")
    public String createReview(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                 @PathVariable Long reservationId,
                                 @RequestBody ReviewForm form) {

        UserVo vo = provider.getUserVo(token);
        customerService.createReview(vo.getId(), reservationId, form);

        return "리뷰가 작성되었습니다.";
    }
}
