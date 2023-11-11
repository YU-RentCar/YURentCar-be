package com.yu.yurentcar.domain.branch.dto;

import com.yu.yurentcar.global.SiDoType;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BranchNameResponseDto {
    private String province;
    private String branchName;

    public BranchNameResponseDto(SiDoType siDo, String branchName) {
        this.province = siDo.getDesc();
        this.branchName = branchName;
    }
}
