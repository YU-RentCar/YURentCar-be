package com.yu.yurentcar.domain.user.service;

import com.yu.yurentcar.domain.user.dto.UserAuthDto;
import com.yu.yurentcar.domain.user.entity.*;
import com.yu.yurentcar.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 소셜로부터 사용자 프로필 정보 가져오기
    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("----------------");
        log.info("userRequest : " + userRequest);

        String clientName = userRequest.getClientRegistration().getClientName();

        log.info("clientName : " + clientName);
        log.info(userRequest.getAdditionalParameters());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("==============");
        oAuth2User.getAttributes().forEach((k,v) -> log.info(k+": "+v));

        String username, name = null;
        Gender gender = null;
        JoinType joinType;
        LocalDateTime birthday;
        String phoneNumber;

        Map<String, Object> attributes = oAuth2User.getAttributes();

        joinType = JoinType.valueOf(clientName.toUpperCase());


        switch (clientName) {
            case "Naver" -> {
                attributes = (Map<String, Object>) attributes.get("response");
                username = (String) attributes.get("email");
                name = (String) attributes.get("name");
                gender = Gender.findByNaver((String) attributes.get("gender"));
                String year = (String) attributes.get("birthyear");
                String day = (String) attributes.get("birthday");
                birthday = LocalDate.parse(year+day, DateTimeFormatter.ofPattern("yyyyMM-dd")).atStartOfDay();
                phoneNumber = (String) attributes.get("mobile");
            }
            case "Kakao" -> {
                attributes = (Map<String, Object>) attributes.get("kakao_account");
                username = (String) attributes.get("email");
                //String year = (String) attributes.get("birthyear");
                String year = "1999";
                String day = (String) attributes.get("birthday");
                birthday = LocalDate.parse(year+day, DateTimeFormatter.ofPattern("yyyyMMdd")).atStartOfDay();
                //phoneNumber = ((String) attributes.get("phone_number")).replace("+82 ", "");
                phoneNumber="010-1234-5678";
            }
            default -> throw new RuntimeException("지원하지 않는 소셜입니다");
        }

        User user = saveSocialUser(username, name, gender, birthday, phoneNumber, joinType);

        UserAuthDto userAuthDto = UserAuthDto.customBuilder()
                .username(user.getUsername())
                .password(user.getPassword())
                .name(user.getName())
                .nickname(user.getNickname())
                .birthday(user.getBirthday())
                .type(user.getJoinType())
                .attributes(attributes)
                .authorities(Arrays.asList(new SimpleGrantedAuthority("USER_ROLE")))
                .build();


        return userAuthDto;
    }

    public User saveSocialUser(String username, String name, Gender gender, LocalDateTime birthday, String phoneNumber, JoinType joinType) {
        Optional<User> originUser = userRepository.findByUsernameAndJoinType(username, joinType);
        if (originUser.isPresent())
            return originUser.get();

        User newUser = User.builder()
                .username(username)
                .name(name)
                .password(passwordEncoder.encode("1111"))
                .nickname("hi")
                .gender(gender)
                .birthday(birthday)
                .phoneNumber(phoneNumber)
                .joinType(joinType)
                .licenseEnumSet(EnumSet.noneOf(DriverLicense.class))
                .preferSize(EnumSet.noneOf(CarSize.class))
                .preferOilTypeSet(new OilTypeBitmap(EnumSet.noneOf(OilType.class)))
                .preferTransmissionSet(new TransmissionBitmap(EnumSet.noneOf(Transmission.class)))
                .preferMinPassenger(1)
                .build();
        userRepository.saveAndFlush(newUser);

        return newUser;
    }
}
