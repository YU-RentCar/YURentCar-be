package com.yu.yurentcar.domain.user.dao;

import com.yu.yurentcar.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDAO {
    private final UserRepository userRepository;

    @Autowired
    public UserDAO(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean checkNicknameDuplicate(String nickname){
        System.out.println(userRepository.existsByNickname(nickname));
        return userRepository.existsByNickname(nickname);
    }
}
