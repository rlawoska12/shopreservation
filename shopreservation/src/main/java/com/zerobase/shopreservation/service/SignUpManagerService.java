package com.zerobase.shopreservation.service;

import com.zerobase.shopreservation.entity.dto.SignUpForm;
import com.zerobase.shopreservation.entity.Manager;
import com.zerobase.shopreservation.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class SignUpManagerService {

    private final ManagerRepository managerRepository;

    public Manager signUp(SignUpForm form) {
        return managerRepository.save(Manager.from(form));
    }

    public boolean isEmailExist(String email) {
        return managerRepository.findByEmail(email.toLowerCase(Locale.ROOT))
                .isPresent();
    }
}
