package com.yu.yurentcar.domain.user.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum Gender {
    WOMAN("female", "F"), MAN("male", "M");

    private final String kakaoValue;
    private final String naverValue;

     public static Gender findByKakao(String value) {
         return Arrays.stream(Gender.values())
                 .filter(gender -> gender.kakaoValue.equals(value))
                 .findAny()
                 .orElse(null);
     }

    public static Gender findByNaver(String value) {
        return Stream.of(Gender.values())
                .filter(gender -> gender.naverValue.equals(value))
                .findAny()
                .orElse(null);
    }
}
