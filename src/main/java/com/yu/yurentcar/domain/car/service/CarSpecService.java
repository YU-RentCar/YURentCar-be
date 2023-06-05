package com.yu.yurentcar.domain.car.service;

import com.yu.yurentcar.domain.car.repository.CarSpecificationRepository;
import com.yu.yurentcar.domain.user.entity.CarSize;
import com.yu.yurentcar.domain.user.entity.OilType;
import com.yu.yurentcar.domain.user.entity.Transmission;
import com.yu.yurentcar.global.utils.enums.EnumValueConvertUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
public class CarSpecService {
    private final CarSpecificationRepository carSpecificationRepository;

    public CarSpecService(CarSpecificationRepository carSpecificationRepository) {
        this.carSpecificationRepository = carSpecificationRepository;
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
}
