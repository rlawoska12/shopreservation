package com.zerobase.shopreservation.application;

import com.zerobase.shopreservation.jwt.JwtAuthenticationProvider;
import com.zerobase.shopreservation.entity.dto.SignInForm;
import com.zerobase.shopreservation.entity.Customer;
import com.zerobase.shopreservation.entity.Manager;
import com.zerobase.shopreservation.service.customer.CustomerService;
import com.zerobase.shopreservation.service.manager.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.zerobase.shopreservation.jwt.UserType.*;

@Service
@RequiredArgsConstructor
public class SignInApplication {

    private final ManagerService managerService;
    private final CustomerService customerService;
    private final JwtAuthenticationProvider provider;

    public String managerLoginToken(SignInForm form) {

        Manager manager = managerService.findValidManager(form);

        return provider.createToken(manager.getEmail(), manager.getId(), MANAGER);
    }

    public String customerLoginToken(SignInForm form) {

        Customer customer = customerService.findValidCustomer(form);

        return provider.createToken(customer.getEmail(), customer.getId(), CUSTOMER);
    }
}
