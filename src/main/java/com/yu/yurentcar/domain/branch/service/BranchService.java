package com.yu.yurentcar.domain.branch.service;

import com.yu.yurentcar.domain.branch.repository.BranchRepository;
import com.yu.yurentcar.global.SiDoType;
import com.yu.yurentcar.global.utils.enums.EnumValueConvertUtils;
import lombok.extern.log4j.Log4j2;
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
            siDo = EnumValueConvertUtils.ofCode(SiDoType.class, siDoString);
        } catch (RuntimeException e) {
            siDo = null;
        }
        return branchRepository.findBranchNameListBySiDo(siDo);
    }
}
