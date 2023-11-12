package com.yu.yurentcar.domain.reservation.service;

import com.yu.yurentcar.domain.reservation.dto.PointDetailsResponseDto;
import com.yu.yurentcar.domain.reservation.dto.PointDto;
import com.yu.yurentcar.domain.reservation.dto.PointRequestDto;
import com.yu.yurentcar.domain.reservation.entity.Pay;
import com.yu.yurentcar.domain.reservation.entity.Point;
import com.yu.yurentcar.domain.reservation.entity.PointType;
import com.yu.yurentcar.domain.reservation.entity.Review;
import com.yu.yurentcar.domain.reservation.repository.PayRepository;
import com.yu.yurentcar.domain.reservation.repository.PointRepository;
import com.yu.yurentcar.domain.reservation.repository.ReviewRepository;
import com.yu.yurentcar.domain.user.entity.Admin;
import com.yu.yurentcar.domain.user.entity.User;
import com.yu.yurentcar.domain.user.repository.AdminRepository;
import com.yu.yurentcar.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.yu.yurentcar.domain.reservation.entity.PointType.*;

@RequiredArgsConstructor
@Service
public class PointService {
    private final PointRepository pointRepository;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final ReviewRepository reviewRepository;
    private final PayRepository payRepository;

    @Transactional(readOnly = true)
    public List<PointDetailsResponseDto> getPointList(String nickname, String adminUsername) {
        if (adminUsername != null) {
            adminRepository.findByUsername(adminUsername).orElseThrow(() -> new RuntimeException("없는 사용자입니다."));
        }
        return pointRepository.findAllPointByNickname(nickname);
    }

    @Transactional
    public void updatePoint(PointDto pointDto) {
        switch (pointDto.getType()) {
            case REVIEW -> {
                if (pointDto.getReviewId() == null) throw new RuntimeException();
            }
            case ETC -> {
                if (pointDto.getAdminId() == null) throw new RuntimeException();
            }
            case USE -> {
                if (pointDto.getPayId() == null) throw new RuntimeException();
            }
            default -> throw new RuntimeException("적립 대상이 아닙니다");
        }
        pointRepository.save(pointDto.toEntity());
    }

    @Transactional
    public int getPoint(String adminUsername, String nickname) {
        Optional<Admin> lookupAdmin = adminRepository.findByUsername(adminUsername);
        Optional<User> lookupUser = userRepository.findByNickname(nickname);
        if (lookupAdmin.isEmpty()) throw new RuntimeException("없는 관리자입니다.");
        if (lookupUser.isEmpty()) throw new RuntimeException("없는 사용자입니다.");

        return lookupUser.get().getTotalPoint();
    }

    @Transactional
    public Long postPoint(String adminUsername, PointRequestDto pointRequestDto) {
        Optional<Admin> lookupAdmin = adminRepository.findByUsername(adminUsername);
        Optional<User> lookupUser = userRepository.findByNickname(pointRequestDto.getNickname());
        if (lookupAdmin.isEmpty()) throw new RuntimeException("없는 관리자입니다.");
        if (lookupUser.isEmpty()) throw new RuntimeException("없는 사용자입니다.");

        // PointType에 맞게 데이터 추가
        switch (Enum.valueOf(PointType.class, pointRequestDto.getType())) {
            case REVIEW -> {
                if (pointRequestDto.getReviewId() == null) throw new RuntimeException("리뷰ID가 입력되지 않았습니다.");
                Optional<Review> lookupReview = reviewRepository.findById(pointRequestDto.getReviewId());
                if (lookupReview.isEmpty()) throw new RuntimeException("없는 리뷰입니다.");
                Point point = pointRepository.save(
                        Point.builder()
                                .price(pointRequestDto.getPrice())
                                .reason(pointRequestDto.getReason())
                                .type(REVIEW)
                                .adminId(lookupAdmin.get())
                                .userId(lookupUser.get())
                                .reviewId(lookupReview.get())
                                .build()
                );
                userRepository.save(lookupUser.orElseThrow(() -> new RuntimeException("없는 유저 입니다."))
                        .updatePoint(pointRequestDto.getPrice()));
                return point.getPointId();
            }
            case ETC -> {
                Point point = pointRepository.save(
                        Point.builder()
                                .price(pointRequestDto.getPrice())
                                .reason(pointRequestDto.getReason())
                                .type(ETC)
                                .adminId(lookupAdmin.get())
                                .userId(lookupUser.get())
                                .build()
                );
                userRepository.save(lookupUser.orElseThrow(() -> new RuntimeException("없는 유저 입니다."))
                        .updatePoint(pointRequestDto.getPrice()));
                return point.getPointId();
            }
            case USE -> {
                if (pointRequestDto.getPayId() == null) throw new RuntimeException("결제ID가 입력되지 않았습니다.");
                Optional<Pay> lookupPay = payRepository.findById(pointRequestDto.getPayId());
                if (lookupPay.isEmpty()) throw new RuntimeException("없는 결제입니다.");
                Point point = pointRepository.save(
                        Point.builder()
                                .price(pointRequestDto.getPrice())
                                .reason(pointRequestDto.getReason())
                                .type(USE)
                                .adminId(lookupAdmin.get())
                                .userId(lookupUser.get())
                                .payId(lookupPay.get())
                                .build()
                );
                userRepository.save(lookupUser.orElseThrow(() -> new RuntimeException("없는 유저 입니다."))
                        .updatePoint(pointRequestDto.getPrice()));
                return point.getPointId();
            }
            default -> throw new RuntimeException("적립 대상이 아닙니다");
        }
    }
}