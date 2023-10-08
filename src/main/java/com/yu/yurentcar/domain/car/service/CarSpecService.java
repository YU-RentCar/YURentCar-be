package com.yu.yurentcar.domain.car.service;

import com.yu.yurentcar.domain.car.dto.CarSpecRequestDto;
import com.yu.yurentcar.domain.car.entity.CarBrand;
import com.yu.yurentcar.domain.car.entity.CarSpecification;
import com.yu.yurentcar.domain.car.repository.CarSpecificationRepository;
import com.yu.yurentcar.domain.user.entity.Admin;
import com.yu.yurentcar.domain.user.entity.CarSize;
import com.yu.yurentcar.domain.user.entity.OilType;
import com.yu.yurentcar.domain.user.entity.Transmission;
import com.yu.yurentcar.domain.user.repository.AdminRepository;
import com.yu.yurentcar.global.utils.enums.EnumValueConvertUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class CarSpecService {
    private final AdminRepository adminRepository;
    private final CarSpecificationRepository carSpecificationRepository;

    public CarSpecService(CarSpecificationRepository carSpecificationRepository,
                          AdminRepository adminRepository) {
        this.carSpecificationRepository = carSpecificationRepository;
        this.adminRepository = adminRepository;
    }

    @Transactional(readOnly = true)
    public List<String> getCarSize() {
        return EnumValueConvertUtils.getEnumToDescList(CarSize.class);
    }

    @Transactional(readOnly = true)
    public List<String> getOilTypes() {
        return EnumValueConvertUtils.getEnumToDescList(OilType.class);
    }

    @Transactional(readOnly = true)
    public List<String> getTransmissions() {
        return EnumValueConvertUtils.getEnumToDescList(Transmission.class);
    }

    @Transactional
    public Long postCarSpec(CarSpecRequestDto carSpecRequestDto, String username) {
        Optional<Admin> lookupAdmin = adminRepository.findByUsername(username);
        if (lookupAdmin.isEmpty()) throw new RuntimeException("없는 관리자입니다.");

        Optional<CarSpecification> lookupCarSpec = carSpecificationRepository
                .findByCarBrandAndCarNameAndOilTypeAndReleaseDateAndTransmission(
                        Enum.valueOf(CarBrand.class, carSpecRequestDto.getCarBrand()),
                        carSpecRequestDto.getCarName(),
                        EnumValueConvertUtils.ofDesc(OilType.class, carSpecRequestDto.getOilType()),
                        carSpecRequestDto.getReleaseDate(),
                        EnumValueConvertUtils.ofDesc(Transmission.class, carSpecRequestDto.getTransmission())
                );
        if (lookupCarSpec.isPresent()) throw new RuntimeException("이미 등록된 차량제원입니다.");
        CarSpecification carSpecification = carSpecificationRepository.save(
                CarSpecification.builder()
                        .carName(carSpecRequestDto.getCarName())
                        .maxPassenger(carSpecRequestDto.getMaxPassenger())
                        .carSize(EnumValueConvertUtils.ofDesc(CarSize.class, carSpecRequestDto.getCarSize()))
                        .oilType(EnumValueConvertUtils.ofDesc(OilType.class, carSpecRequestDto.getOilType()))
                        .transmission(EnumValueConvertUtils.ofDesc(Transmission.class, carSpecRequestDto.getTransmission()))
                        .carBrand(Enum.valueOf(CarBrand.class, carSpecRequestDto.getCarBrand()))
                        .releaseDate(carSpecRequestDto.getReleaseDate())
                        .build()
        );
        return carSpecification.getCarSpecId();
    }

    @Transactional
    public Boolean deleteCarSpec(Long carSpecId, String username) {
        Optional<Admin> lookupAdmin = adminRepository.findByUsername(username);
        if (lookupAdmin.isEmpty()) throw new RuntimeException("없는 관리자입니다.");

        Optional<CarSpecification> lookupCarSpec = carSpecificationRepository.findById(carSpecId);
        if (lookupCarSpec.isEmpty()) throw new RuntimeException("없는 차량제원입니다.");
        carSpecificationRepository.delete(lookupCarSpec.get());
        return true;
    }

}
