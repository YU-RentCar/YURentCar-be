package com.yu.yurentcar.domain.user.service;

import com.yu.yurentcar.domain.branch.repository.BranchRepository;
import com.yu.yurentcar.domain.car.repository.BranchCarRepository;
import com.yu.yurentcar.domain.insurance.repository.InsuranceContractRepository;
import com.yu.yurentcar.domain.reservation.repository.ReservationRepository;
import com.yu.yurentcar.domain.user.dto.ChangeNicknameDto;
import com.yu.yurentcar.domain.user.dto.PreferFilterDto;
import com.yu.yurentcar.domain.user.dto.UserProfileDto;
import com.yu.yurentcar.domain.user.entity.CarSize;
import com.yu.yurentcar.domain.user.entity.OilType;
import com.yu.yurentcar.domain.user.entity.Transmission;
import com.yu.yurentcar.domain.user.entity.User;
import com.yu.yurentcar.domain.user.repository.UserRepository;
import com.yu.yurentcar.utils.enums.EnumValueConvertUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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
    public User changePreferFilter(String nickname, PreferFilterDto preferFilterDto) {
        Optional<User> user = userRepository.findByNickname(nickname);
        if (user.isPresent()) {
            return userRepository.save(user.get().updatePrefer(preferFilterDto));
        } else return null;
    }
    public PreferFilterDto lookupPreferFilter(String nickname) {
        Optional<User> user = userRepository.findByNickname(nickname);
        if (user.isPresent()) {
            return PreferFilterDto.builder()
                    .carSizes(EnumValueConvertUtils.toBoolListCode(CarSize.class, user.get().getPreferSize()))
                    .minCount(user.get().getPreferMinPassenger())
                    .oilTypes(EnumValueConvertUtils.toBoolListCode(OilType.class,user.get().getPreferOilTypeSet().getEnumSet()))
                    .transmissions(EnumValueConvertUtils.toBoolListCode(Transmission.class,user.get().getPreferTransmissionSet().getEnumSet()))
                    .build();
        } else return null;
    }

    public User changeNickname(ChangeNicknameDto changeNicknameDto) {
        Optional<User> user = userRepository.findByUsername(changeNicknameDto.getUsername());
        if (user.isPresent()) {
            User changedUser = User.builder()
                    .userId(user.get().getUserId())
                    .username(user.get().getUsername())
                    .password(user.get().getPassword())
                    .name(user.get().getName())
                    .nickname(changeNicknameDto.getNickname())
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
        if (user.isPresent()) {
            User changedUser = User.builder()
                    .userId(user.get().getUserId())
                    .username((userProfileDto.getUsername() != null) ? userProfileDto.getUsername() : user.get().getUsername())
                    .password(user.get().getPassword())
                    .name((userProfileDto.getName() != null) ? userProfileDto.getName() : user.get().getName())
                    .nickname((userProfileDto.getNickname() != null) ? userProfileDto.getNickname() : user.get().getNickname())
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


    public UserProfileDto lookupUserProfile(String username) {
        Optional<User> lookupUser = userRepository.findByUsername(username);
        if (lookupUser.isPresent()) {
            return UserProfileDto.builder()
                    .username(lookupUser.get().getUsername())
                    .name(lookupUser.get().getName())
                    .nickname(lookupUser.get().getNickname())
                    .phoneNumber(lookupUser.get().getPhoneNumber())
                    .build();
        } else return null;
    }
}