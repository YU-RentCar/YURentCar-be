package com.yu.yurentcar.domain.user.service;


import com.yu.yurentcar.domain.user.dto.AdminAuthDto;
import com.yu.yurentcar.domain.user.entity.Admin;
import com.yu.yurentcar.domain.user.repository.AdminRepository;
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
public class CustomAdminDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("UserDetailsService loadUserByUsername " + username);

        Admin admin = adminRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("존재하지 않는 관리자입니다."));

        log.info("---------------------------");
        log.info(admin);

        AdminAuthDto adminAuth = new AdminAuthDto(
                admin.getUsername(),
                admin.getPassword(),
                admin.getBranch().getBranchId(),
                admin.getBranch().getBranchName(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );

        return adminAuth;
    }


}