package com.zerobase.shopreservation.entity.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInForm {
    private String email;
    private String password;
}
