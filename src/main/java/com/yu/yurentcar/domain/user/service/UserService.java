package com.yu.yurentcar.domain.user.service;

import com.yu.yurentcar.domain.user.dto.DriverLicenseResponseDto;
import com.yu.yurentcar.domain.user.dto.PreferFilterDto;
import com.yu.yurentcar.domain.user.dto.UserProfileDto;
import com.yu.yurentcar.domain.user.entity.CarSize;
import com.yu.yurentcar.domain.user.entity.OilType;
import com.yu.yurentcar.domain.user.entity.Transmission;
import com.yu.yurentcar.domain.user.entity.User;
import com.yu.yurentcar.domain.user.repository.UserRepository;
import com.yu.yurentcar.global.utils.enums.EnumValueConvertUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Log4j2
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public boolean checkNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Transactional
    public Boolean changePreferFilter(String username, PreferFilterDto preferFilterDto) {
        Optional<User> user = userRepository.findByUsername(username);
        userRepository.save(user.orElseThrow(() -> new RuntimeException("없는 유저 입니다."))
                .updatePrefer(preferFilterDto));
        return true;
    }

    @Transactional(readOnly = true)
    public PreferFilterDto getPreferFilter(String nickname) {
        Optional<User> user = userRepository.findByNickname(nickname);
        return user.map(value -> PreferFilterDto.builder()
                .carSizes(EnumValueConvertUtils.toBoolListCode(CarSize.class, value.getPreferSize()))
                .minCount(value.getPreferMinPassenger())
                .oilTypes(EnumValueConvertUtils.toBoolListCode(OilType.class, value.getPreferOilTypeSet().getEnumSet()))
                .transmissions(EnumValueConvertUtils.toBoolListCode(Transmission.class, value.getPreferTransmissionSet().getEnumSet()))
                .build()).orElseThrow(() -> new RuntimeException("없는 유저입니다."));
    }

    @Transactional
    public Boolean changeNickname(String username, String nickname) {
        if(checkNicknameDuplicate(nickname)) throw new RuntimeException("이미 존재하는 닉네임 입니다.");
        Optional<User> user = userRepository.findByUsername(username);
        user.map(value -> userRepository.save(value.updateNickname(nickname))).orElseThrow(() -> new RuntimeException("없는 유저입니다."));
        return true;
    }

    /*
    // TODO : 이후 추가되면 수정 필요
    @Transactional
    public User changeProfile(UserProfileDto userProfileDto) {
        Optional<User> user = userRepository.findByUsername(userProfileDto.getUsername());
        if (user.isPresent()) {
            return userRepository.save(user.get().updateProfile(userProfileDto));
        } else return null;
    }
     */

    @Transactional(readOnly = true)
    public Integer getPoint(String username) {
        Optional<User> lookupUser = userRepository.findByUsername(username);
        return lookupUser.map(User::getTotalPoint).orElseThrow(() -> new RuntimeException("없는 유저 입니다."));
    }

    @Transactional(readOnly = true)
    public UserProfileDto getUserProfile(String username) {
        Optional<User> lookupUser = userRepository.findByUsername(username);
        return lookupUser.map(user -> UserProfileDto.builder()
                .username(user.getUsername())
                .name(user.getName())
                .nickname(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .build()).orElseThrow(() -> new RuntimeException("없는 유저 입니다."));
    }

    @Transactional(readOnly = true)
    public DriverLicenseResponseDto getLicense(String username) {
        Optional<User> lookupUser = userRepository.findByUsername(username);
        if (lookupUser.isEmpty()) throw new RuntimeException("없는 유저 입니다.");
        return lookupUser.map(user -> DriverLicenseResponseDto.builder()
                .driverLicense(user.getLicenseEnumSet().iterator().next().getDbValue())
                .build()).orElseThrow(() -> new RuntimeException("없는 유저 입니다."));
    }
}