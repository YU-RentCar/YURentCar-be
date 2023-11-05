package com.yu.yurentcar.domain.car.service;

import com.yu.yurentcar.domain.branch.entity.KeyStorage;
import com.yu.yurentcar.domain.branch.repository.KeyStorageRepository;
import com.yu.yurentcar.domain.car.dto.KeyDto;
import com.yu.yurentcar.domain.car.dto.KeyManagementDto;
import com.yu.yurentcar.domain.car.entity.Car;
import com.yu.yurentcar.domain.car.entity.Key;
import com.yu.yurentcar.domain.car.entity.KeyState;
import com.yu.yurentcar.domain.car.repository.CarRepository;
import com.yu.yurentcar.domain.car.repository.KeyRepository;
import com.yu.yurentcar.domain.user.entity.Admin;
import com.yu.yurentcar.domain.user.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Service
public class KeyService {
    private final KeyStorageRepository keyStorageRepository;
    private final CarRepository carRepository;
    private final AdminRepository adminRepository;
    private final KeyRepository keyRepository;

    @Transactional
    public Long postKey(String adminUsername, KeyDto keyDto) {
        Optional<Admin> lookupAdmin = adminRepository.findByUsername(adminUsername);
        if (lookupAdmin.isEmpty()) throw new RuntimeException("없는 관리자입니다.");
        Optional<Car> lookupCar = carRepository.findByCarNumber(keyDto.getCarNumber());
        if (lookupCar.isEmpty()) throw new RuntimeException("없는 차량입니다.");
        Optional<KeyStorage> lookupKeyStorage = keyStorageRepository.findById(keyDto.getKeyStorageId());
        if (lookupKeyStorage.isEmpty()) throw new RuntimeException("없는 차키 보관함입니다.");
        //차량의 지점 정보와 관리자 지점 정보가 같은지 검증
        if (!lookupAdmin.get().getBranch().equals(lookupCar.get().getBranch())) {
            throw new RuntimeException("다른 지점 관리자입니다. 권한이 없습니다.");
        }
        Key key = keyRepository.save(
                Key.builder()
                        .car(lookupCar.get())
                        .keyStorage(lookupKeyStorage.get())
                        .rfid(keyDto.getRfid())
                        .keyState(Enum.valueOf(KeyState.class, keyDto.getState()))
                        .build()
        );
        return key.getKeyId();
    }

    @Transactional
    public Boolean patchKey(String adminUsername, KeyDto keyDto, Long keyId) {
        Optional<Admin> lookupAdmin = adminRepository.findByUsername(adminUsername);
        if (lookupAdmin.isEmpty()) throw new RuntimeException("없는 관리자입니다.");
        Optional<Car> lookupCar = carRepository.findByCarNumber(keyDto.getCarNumber());
        if (lookupCar.isEmpty()) throw new RuntimeException("없는 차량입니다.");
        Optional<KeyStorage> lookupKeyStorage = keyStorageRepository.findById(keyDto.getKeyStorageId());
        if (lookupKeyStorage.isEmpty()) throw new RuntimeException("없는 차키 보관함입니다.");
        if (!lookupAdmin.get().getBranch().equals(lookupCar.get().getBranch())) {
            throw new RuntimeException("다른 지점 관리자입니다. 권한이 없습니다.");
        }
        Optional<Key> lookupKey = keyRepository.findById(keyId);
        keyRepository.save(lookupKey.orElseThrow(() -> new RuntimeException("없는 차키입니다."))
                .updateKey(lookupCar.get(), lookupKeyStorage.get(), keyDto.getRfid(), Enum.valueOf(KeyState.class, keyDto.getState())));
        return true;
    }

    @Transactional
    public Boolean deleteKey(String adminUsername, Long keyId) {
        Optional<Admin> lookupAdmin = adminRepository.findByUsername(adminUsername);
        if (lookupAdmin.isEmpty()) throw new RuntimeException("없는 관리자입니다.");
        Optional<Key> lookupKey = keyRepository.findById(keyId);
        if (lookupKey.isEmpty()) throw new RuntimeException("없는 차키입니다.");

        if (!lookupAdmin.get().getBranch().equals(lookupKey.get().getKeyStorage().getKiosk().getBranch())) {
            throw new RuntimeException("다른 지점 관리자입니다. 권한이 없습니다.");
        }
        keyRepository.delete(lookupKey.get());
        return true;
    }

    @Transactional(readOnly = true)
    public List<KeyManagementDto> getKeyListByAdmin(String adminUsername) {
        Optional<Admin> lookupAdmin = adminRepository.findByUsername(adminUsername);
        if (lookupAdmin.isEmpty()) throw new RuntimeException("없는 관리자입니다.");

        return keyRepository.findKeysByAdmin(adminUsername);
    }
}