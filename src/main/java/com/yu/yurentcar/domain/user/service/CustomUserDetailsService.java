package com.yu.yurentcar.domain.user.service;


import com.yu.yurentcar.domain.user.dto.UserAuthDto;
import com.yu.yurentcar.domain.user.entity.User;
import com.yu.yurentcar.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository customUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("UserDetailsService loadUserByUsername " + username);


        //Optional<User> result = customUserRepository.findByUserIdAndType(username, LoginType.LOCAL);
        User user = customUserRepository.findByUsername(username).get();

//        if(result.isEmpty()) {
//            throw new UsernameNotFoundException("Check Email or Social ");
//        }
//
//        User user = result.get();
        //////////////////////////////////

        /*List<User> result = customUserRepository.findByUserIdAndType(username, LoginType.LOCAL);
        if(result.isEmpty()) {
            throw new UsernameNotFoundException(("Check Email or Social "));
        }
        User user = result.get(0);*/

        log.info("---------------------------");
        log.info(user);

        UserAuthDto userAuth = new UserAuthDto(
                user.getUsername(),
                user.getPassword(),
                user.getJoinType(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );

        userAuth.setNickname(user.getNickname());

        return userAuth;
    }


}