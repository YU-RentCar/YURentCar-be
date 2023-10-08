package com.yu.yurentcar.domain.user.service;

import com.yu.yurentcar.domain.user.dto.AdminLoginDto;
import com.yu.yurentcar.domain.user.entity.Admin;
import com.yu.yurentcar.domain.user.repository.AdminRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Log4j2
@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Transactional(readOnly = true)
    public Boolean loginAdmin(AdminLoginDto adminLoginDto) {
        Optional<Admin> lookupAdmin = adminRepository.findByUsername(adminLoginDto.getUsername());
        if (lookupAdmin.isEmpty()) throw new RuntimeException("존재하지 않는 아이디입니다.");
        if (!lookupAdmin.get().getPassword().equals(adminLoginDto.getPassword()))
            throw new RuntimeException("입력하신 패스워드가 틀렸습니다.");
        return true;
    }
}