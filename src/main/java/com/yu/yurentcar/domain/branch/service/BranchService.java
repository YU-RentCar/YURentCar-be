package com.yu.yurentcar.domain.branch.service;

import com.yu.yurentcar.domain.branch.repository.BranchRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class BranchService {
    private final BranchRepository branchRepository;

    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    public List<String> getBranchNameList(String siDo) {
        return branchRepository.findBranchNameListBySiDo(siDo);
    }
}
