package com.yu.yurentcar.domain.branch.service;

import com.yu.yurentcar.domain.branch.repository.BranchRepository;
import com.yu.yurentcar.global.SiDoType;
import com.yu.yurentcar.global.utils.enums.EnumValueConvertUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Service
public class BranchService {
    private final BranchRepository branchRepository;

    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Transactional(readOnly = true)
    public List<String> getBranchNameList(String siDoString) {
        SiDoType siDo;
        try {
            siDo = EnumValueConvertUtils.ofDesc(SiDoType.class, siDoString);
        } catch (RuntimeException e) {
            siDo = null;
        }
        return branchRepository.findBranchNameListBySiDo(siDo);
    }

    @Transactional(readOnly = true)
    public Point getGeoPoint(String province, String branchName) {
        SiDoType siDo = EnumValueConvertUtils.ofDesc(SiDoType.class, province);
        return branchRepository.getGeoPointByBranchName(siDo, branchName);
    }
}
