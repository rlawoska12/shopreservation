package com.zerobase.shopreservation.controller;

import com.zerobase.shopreservation.entity.dto.SignInForm;
import com.zerobase.shopreservation.application.SignInApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signIn")
public class SignInController {

    private final SignInApplication signInApplication;

    @PostMapping("/manager")
    public String signInManager(@RequestBody SignInForm form) {
        return signInApplication.managerLoginToken(form);
    }

    @PostMapping("/customer")
    public String signInCustomer(@RequestBody SignInForm form) {
        return signInApplication.customerLoginToken(form);
    }
}
