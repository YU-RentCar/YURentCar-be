package com.yu.yurentcar.domain.user.service;

import com.yu.yurentcar.domain.user.dao.UserDAO;
import com.yu.yurentcar.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl {
    private final UserDAO userDAO;
    private final UserRepository userRepository;

    public AuthServiceImpl(UserDAO userDAO, UserRepository userRepository) {
        this.userDAO = userDAO;
        this.userRepository = userRepository;
    }

    public boolean checkNicknameDuplicate(String nickname){
        return userRepository.existsByNickname(nickname);
    }
}
