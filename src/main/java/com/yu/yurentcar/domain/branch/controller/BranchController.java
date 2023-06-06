package com.yu.yurentcar.domain.branch.controller;

import com.yu.yurentcar.domain.branch.service.BranchService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.geo.Point;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/v1/branches")
public class BranchController {
    public final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping
    public ResponseEntity<List<String>> getBranchList(@RequestParam String siDo) {
        log.info("===BranchController===");
        log.info("getBranchList : " + siDo);
        return ResponseEntity.ok(branchService.getBranchNameList(siDo));
    }

    @GetMapping("location")
    public ResponseEntity<Point> getBranchGeoPoint(@RequestParam @Valid String province, @RequestParam @Valid String branchName) {
        return ResponseEntity.ok(branchService.getGeoPoint(province, branchName));
    }
}
