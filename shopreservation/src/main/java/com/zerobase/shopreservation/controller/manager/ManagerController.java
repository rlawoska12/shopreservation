package com.zerobase.shopreservation.controller.manager;

import com.zerobase.shopreservation.entity.dto.ReservationDto;
import com.zerobase.shopreservation.jwt.JwtAuthenticationProvider;
import com.zerobase.shopreservation.jwt.UserVo;
import com.zerobase.shopreservation.entity.dto.ShopForm;
import com.zerobase.shopreservation.service.manager.ManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {

    private final ManagerService managerService;
    private final JwtAuthenticationProvider provider;

    @PostMapping("/shop")
    public String registerShop(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                               @RequestBody ShopForm shopForm) {

        UserVo vo = provider.getUserVo(token);

        managerService.registerShop(shopForm, vo.getId());
        return "가게가 등록되었습니다.";
    }

    @GetMapping("/reservation")
    public List<ReservationDto> findReservation(@RequestHeader(name = "X-AUTH-TOKEN") String token) {

        UserVo vo = provider.getUserVo(token);

        return managerService.findByManagerId(vo.getId());
    }

    @PutMapping("/reservation/{reservationId}/approve")
    public String approveReservation(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                     @PathVariable Long reservationId) {

        UserVo vo = provider.getUserVo(token);

        managerService.approveReservation(vo.getId(), reservationId);

        return "예약이 승인되었습니다.";
    }

    @PutMapping("/reservation/{reservationId}/refuse")
    public String refuseReservation(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                    @PathVariable Long reservationId) {

        UserVo vo = provider.getUserVo(token);

        managerService.refuseReservation(vo.getId(), reservationId);

        return "예약이 거절되었습니다.";
    }
}
