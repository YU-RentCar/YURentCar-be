package com.yu.yurentcar.domain.user.service;

import com.yu.yurentcar.domain.user.dto.AdminLoginDto;
import com.yu.yurentcar.domain.user.entity.Admin;
import com.yu.yurentcar.domain.user.repository.AdminRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public Admin loginAdmin(AdminLoginDto adminLoginDto) {
        Admin lookupAdmin = adminRepository.findByUsername(adminLoginDto.getUsername())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 아이디입니다."));

        if (!passwordEncoder.matches(adminLoginDto.getPassword(), lookupAdmin.getPassword()))
            throw new RuntimeException("입력하신 패스워드가 틀렸습니다.");

        return lookupAdmin;
    }

    @Transactional(readOnly = true)
    public Boolean validAdminByUsername(String username) {
        return adminRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public Admin getAdminByUsername(String username) {
        return adminRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("존재하지 않는 관리자입니다."));
    }
}