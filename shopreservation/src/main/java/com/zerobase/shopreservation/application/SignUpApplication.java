package com.zerobase.shopreservation.application;

import com.zerobase.shopreservation.entity.dto.SignUpForm;
import com.zerobase.shopreservation.exception.CustomException;
import com.zerobase.shopreservation.service.SignUpCustomerService;
import com.zerobase.shopreservation.service.SignUpManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.zerobase.shopreservation.exception.ErrorCode.ALREADY_REGISTER_USER;

@Service
@RequiredArgsConstructor
public class SignUpApplication {

    private final SignUpManagerService signUpManagerService;
    private final SignUpCustomerService signUpCustomerService;

    public String managerSignUp(SignUpForm form) {
        if (signUpManagerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ALREADY_REGISTER_USER);
        } else {
            signUpManagerService.signUp(form);
            return "회원 가입에 성공하였습니다.";
        }
    }

    public String customerSignUp(SignUpForm form) {
        if (signUpCustomerService.isEmailExist(form.getEmail())) {
            throw new CustomException(ALREADY_REGISTER_USER);
        } else {
            signUpCustomerService.signUp(form);
            return "회원 가입에 성공하였습니다.";
        }
    }
}
