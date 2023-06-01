package com.yu.yurentcar.domain.user.service;

import com.yu.yurentcar.domain.user.dto.SignupRequestDto;
import com.yu.yurentcar.domain.user.dto.UserProfileDto;
import com.yu.yurentcar.domain.user.entity.User;
import com.yu.yurentcar.domain.user.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Optional;

@Log4j2
@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean checkNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    //트랜스미션은 에러남
    //기존의 트랜스미션과 똑같은 값이면 에러 안 뜸... 왜지?
    public User saveSignInAdditionalInfo(SignupRequestDto signupRequestDto) {
        Optional<User> user = userRepository.findByNickname(signupRequestDto.getNickname());
        if (user.isPresent()) {
            return userRepository.save(user.get().updatePrefer(signupRequestDto));
        } else return null;
    }

    public User changeNickname(String username, String nickname) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            User changedUser = User.builder()
                    .userId(user.get().getUserId())
                    .username(user.get().getUsername())
                    .password(user.get().getPassword())
                    .name(user.get().getName())
                    .nickname(nickname)
                    .birthday(user.get().getBirthday())
                    .gender(user.get().getGender())
                    .totalPoint(user.get().getTotalPoint())
                    .joinType(user.get().getJoinType())
                    .phoneNumber(user.get().getPhoneNumber())
                    .licenseEnumSet(user.get().getLicenseEnumSet())
                    .preferSize(user.get().getPreferSize())
                    .preferOilTypeSet(user.get().getPreferOilTypeSet())
                    .preferTransmissionSet(user.get().getPreferTransmissionSet())
                    .preferMinPassenger(user.get().getPreferMinPassenger()).build();
            userRepository.save(changedUser);
            return changedUser;
        } else return null;
    }

    public User changeProfile(UserProfileDto userProfileDto) {
        Optional<User> user = userRepository.findByUsername(userProfileDto.getUsername());
        //"yyyy-MM-dd"형식의 날짜를 데이터베이스에 맞게 파싱하는 역할
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().append(formatter1)
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .toFormatter();
        if (user.isPresent()) {
            User changedUser = User.builder()
                    .userId(user.get().getUserId())
                    .username((userProfileDto.getUsername() != null) ? userProfileDto.getUsername() : user.get().getUsername())
                    .password(user.get().getPassword())
                    .name((userProfileDto.getName() != null) ? userProfileDto.getName() : user.get().getName())
                    .nickname((userProfileDto.getNickname() != null) ? userProfileDto.getNickname() : user.get().getNickname())
                    .birthday((userProfileDto.getBirthday() != null) ? LocalDateTime.parse(userProfileDto.getBirthday(), formatter) : user.get().getBirthday())
                    .gender(user.get().getGender())
                    .totalPoint(user.get().getTotalPoint())
                    .joinType(user.get().getJoinType())
                    .phoneNumber((userProfileDto.getPhoneNumber() != null) ? userProfileDto.getPhoneNumber() : user.get().getPhoneNumber())
                    .licenseEnumSet(user.get().getLicenseEnumSet())
                    .preferSize(user.get().getPreferSize())
                    .preferOilTypeSet(user.get().getPreferOilTypeSet())
                    .preferTransmissionSet(user.get().getPreferTransmissionSet())
                    .preferMinPassenger(user.get().getPreferMinPassenger()).build();
            userRepository.save(changedUser);
            return changedUser;
        } else return null;
    }

    public Integer lookupPoint(String nickname) {
        Optional<User> lookupUser = userRepository.findByNickname(nickname);
        return lookupUser.map(User::getTotalPoint).orElse(null);
    }
}
