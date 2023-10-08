package com.yu.yurentcar.domain.user.dto;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminLoginDto {
    private String username;
    private String password;
}
