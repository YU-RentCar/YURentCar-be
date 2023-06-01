package com.yu.yurentcar.domain.user.entity;

import com.yu.yurentcar.BaseTimeEntity;
import com.yu.yurentcar.domain.user.dto.SignupRequestDto;
import com.yu.yurentcar.domain.user.entity.converter.CarSizeBoolArrayConverter;
import com.yu.yurentcar.domain.user.entity.converter.DriverLicenseSetToStringArrayConverter;
import com.yu.yurentcar.domain.user.entity.converter.OilTypeBitmapConverter;
import com.yu.yurentcar.domain.user.entity.converter.TransmissionBitmapConverter;
import com.yu.yurentcar.utils.enums.EnumValueConvertUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;

@Entity
@Table(name = "USER_APP")
@Getter
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor//(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    @Email
    @Column(updatable = false)
    private String username;

    @NotNull
    @Column(length = 100)
    private String password;

    @NotNull
    @Column
    private String name;

    @NotNull
    @Column
    private String nickname;

    @NotNull
    @Column
    private LocalDateTime birthday;

    @NotNull
    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    @Column(name = "total_point", columnDefinition = "integer default 0")
    @Builder.Default
    private Integer totalPoint = 0;

    @NotNull
    @Column(name = "join_type", length = 5)
    @Enumerated(EnumType.STRING)
    private JoinType joinType;

    @NotNull
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    //TODO : enum 데이터 통합하기
    @Convert(converter = DriverLicenseSetToStringArrayConverter.class)
    @Column(name="driver_license_set", columnDefinition = "varchar(10) array[7]")
    private EnumSet<DriverLicense> licenseEnumSet;

    //TODO : enum 데이터 통합하기
    @Convert(converter = CarSizeBoolArrayConverter.class)
    @Column(name="prefer_size", columnDefinition = "boolean[5]")
    private EnumSet<CarSize> preferSize;

    @Convert(converter = OilTypeBitmapConverter.class)
    @Column(name="prefer_oil_type", columnDefinition = "int")
    private OilTypeBitmap preferOilTypeSet;

    @Convert(converter = TransmissionBitmapConverter.class)
    @Column(name="prefer_transmission", columnDefinition = "int")
    private TransmissionBitmap preferTransmissionSet;

    @Max(10)
    @Column(name = "prefer_min_passenger")
    private Integer preferMinPassenger;

    public User updatePrefer(SignupRequestDto signupRequestDto) {
        this.preferSize = EnumValueConvertUtils.ofBoolListCode(CarSize.class, signupRequestDto.getCarSizes());
        this.preferMinPassenger = signupRequestDto.getMinCount();
        this.preferOilTypeSet = new OilTypeBitmap(EnumValueConvertUtils.ofBoolListCode(OilType.class, signupRequestDto.getOilTypes()));
        this.preferTransmissionSet = new TransmissionBitmap(EnumValueConvertUtils.ofBoolListCode(Transmission.class, signupRequestDto.getTransmissions()));
        return this;
    }
}
