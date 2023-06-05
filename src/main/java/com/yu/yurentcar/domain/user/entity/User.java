package com.yu.yurentcar.domain.user.entity;

import com.yu.yurentcar.domain.user.dto.PreferFilterDto;
import com.yu.yurentcar.domain.user.entity.converter.CarSizeBoolArrayConverter;
import com.yu.yurentcar.domain.user.entity.converter.DriverLicenseSetToStringArrayConverter;
import com.yu.yurentcar.domain.user.entity.converter.OilTypeBitmapConverter;
import com.yu.yurentcar.domain.user.entity.converter.TransmissionBitmapConverter;
import com.yu.yurentcar.global.BaseTimeEntity;
import com.yu.yurentcar.global.utils.enums.EnumValueConvertUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Objects;

@Entity
@Table(name = "USER_APP", indexes = @Index(name = "idx_username", columnList = "username", unique = true))
@Getter
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor//(access = AccessLevel.PRIVATE)
@ToString
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
    private Integer totalPoint;

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

    @Builder
    public User(String username, String password, String name, String nickname, LocalDateTime birthday, Gender gender, Integer totalPoint, JoinType joinType, String phoneNumber, EnumSet<DriverLicense> licenseEnumSet, EnumSet<CarSize> preferSize, OilTypeBitmap preferOilTypeSet, TransmissionBitmap preferTransmissionSet, Integer preferMinPassenger) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.birthday = birthday;
        this.gender = gender;
        this.totalPoint = Objects.requireNonNullElse(totalPoint, 0);
        this.joinType = joinType;
        this.phoneNumber = phoneNumber;
        this.licenseEnumSet = licenseEnumSet;
        this.preferSize = preferSize;
        this.preferOilTypeSet = preferOilTypeSet;
        this.preferTransmissionSet = preferTransmissionSet;
        this.preferMinPassenger = preferMinPassenger;
    }

    public User updatePrefer(PreferFilterDto preferFilterDto) {
        if(preferSize != null)
            this.preferSize = EnumValueConvertUtils.ofBoolListCode(CarSize.class, preferFilterDto.getCarSizes());
        if(preferMinPassenger != null)
            this.preferMinPassenger = preferFilterDto.getMinCount();
        if(preferOilTypeSet != null)
            this.preferOilTypeSet = new OilTypeBitmap(EnumValueConvertUtils.ofBoolListCode(OilType.class, preferFilterDto.getOilTypes()));
        if(preferTransmissionSet != null)
            this.preferTransmissionSet = new TransmissionBitmap(EnumValueConvertUtils.ofBoolListCode(Transmission.class, preferFilterDto.getTransmissions()));
        return this;
    }

    public User updateNickname(String nickname){
        if(username != null)
            this.nickname = nickname;
        return this;
    }

    public User updatePoint(Integer point) {
        if(point != null)
            this.totalPoint += point;
        return this;
    }

    // TODO : 추후 논의 후 추가
    /*
    public User updateProfile(UserProfileDto userProfileDto){
        if(username != null)
            this.username = userProfileDto.getUsername();
        if(name != null)
            this.name = userProfileDto.getName();
        if(nickname != null)
            this.nickname = userProfileDto.getNickname();
        if(phoneNumber != null)
            this.phoneNumber = userProfileDto.getPhoneNumber();
        return this;
    }
     */
}
