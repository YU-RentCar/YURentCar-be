package com.yu.yurentcar.domain.branch.service;

import com.yu.yurentcar.domain.branch.dto.BranchNameResponseDto;
import com.yu.yurentcar.domain.branch.entity.KeyStorage;
import com.yu.yurentcar.domain.branch.entity.Kiosk;
import com.yu.yurentcar.domain.branch.repository.KeyStorageRepository;
import com.yu.yurentcar.domain.branch.repository.KioskRepository;
import com.yu.yurentcar.domain.car.entity.Car;
import com.yu.yurentcar.domain.car.entity.Key;
import com.yu.yurentcar.domain.car.entity.KeyState;
import com.yu.yurentcar.domain.car.repository.CarRepository;
import com.yu.yurentcar.domain.car.repository.KeyRepository;
import com.yu.yurentcar.domain.reservation.entity.Reservation;
import com.yu.yurentcar.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Log4j2
public class KioskService {
    private final KeyStorageRepository keyStorageRepository;
    private final KioskRepository kioskRepository;
    private final CarRepository carRepository;
    private final KeyRepository keyRepository;
    private final ReservationRepository reservationRepository;

    @Value("${my.pi.base-url}")
    private String piBaseUrl;


    @Transactional
    public Long findKeyStorage(String name, Long reservationId, Long kioskId) {
        // reservationId으로 예약 건 조회 후 name이 같은지 확인
        Optional<Reservation> lookupReservation = reservationRepository.findById(reservationId);
        if (lookupReservation.isEmpty()) throw new RuntimeException("없는 예약입니다.");
        if (!lookupReservation.get().getUser().getName().equals(name)) throw new RuntimeException("예약자와 성함이 같지 않습니다.");
        // 날짜 비교해서 예약날짜가 오늘 날짜이고 종료시간 전인 경우만 키 조회 가능
        Reservation reservation = lookupReservation.get();
        if (reservation.getEndDate().isBefore(LocalDateTime.now()))
            throw new RuntimeException("차 키 조회가 불가능한 날짜입니다.");
        // 조건 통과 후 ID들로 조회 후 반환
        KeyStorage keyStorage = keyStorageRepository.findKeyStorageByReservationId(reservationId, kioskId);
        if (keyStorage == null) {
            throw new RuntimeException("차 키가 존재하지 않습니다. 관리자에게 문의하세요");
        }

        // 파이썬 서버로 요청 보내는 로직
        RestTemplate restTemplate = new RestTemplate();
        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        log.info("kioskId.toString() = " + kioskId.toString());
        // 파라미터 세팅
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("kioskId", kioskId.toString());
        map.add("slotNumber", keyStorage.getSlotNumber().toString());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        log.info("파이 주소 = " + piBaseUrl + "/receive-car-key");
        Boolean response = restTemplate.postForObject(piBaseUrl + "/receive-car-key", request, Boolean.class);

        if (Boolean.FALSE.equals(response)) {
            throw new RuntimeException("열리지 않았습니다. 다시 시도해주세요");
        }

        // 보관함 사용 가능으로 변환
        keyStorageRepository.save(keyStorage.updateAvailable(true));
        // 차키 사용중으로 변환
        Optional<Key> lookupKey = keyRepository.findByKeyStorage(keyStorage);
        Key key = lookupKey.orElseThrow(() -> new RuntimeException("없는 차키입니다."));
        keyRepository.save(key.updateKey(null, null, null, KeyState.USABLE));

        return keyStorage.getSlotNumber();
    }

    // 사용자가 웹에서 키 반납 버튼을 눌렀을 경우
    @Transactional
    public Long returnKey(Long kioskId) {
        Kiosk kiosk = kioskRepository.findById(kioskId)
                .orElseThrow(() -> new RuntimeException("없는 키오스크입니다."));
        // rfid 작동시키는 요청
        RestTemplate restTemplate = new RestTemplate();
        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 파라미터 세팅
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("kioskId", kioskId.toString());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<Boolean> response = restTemplate.postForEntity(piBaseUrl, request, Boolean.class);
        if (!Boolean.TRUE.equals(response.getBody())) {
            throw new RuntimeException("응답이 정상적이지 않습니다. 다시 시도해주세요.");
        }

        // RFID가 켜지고 나서 보내는 요청
        // 파이가 이 요청을 받으면 rfid 찍힐때까지 프리징
        // 무한 프리징은 아니고 파이에서 10초정도 기다림
        // 찍히면 rfid값을 응답
        // 안 찍히면 빈 문자열 응답
        // 헤더 설정
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 파라미터 세팅
        map = new LinkedMultiValueMap<>();
        map.add("kioskId", kioskId.toString());

        request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response2 = restTemplate.postForEntity(piBaseUrl + "/rfid-return", request, String.class);
        String rfid = response2.getBody();
        if (rfid == null) {
            throw new RuntimeException("응답이 정상적이지 않습니다. 다시 시도해주세요");
        }
        if (rfid.equals("")) {
            throw new RuntimeException("RFID가 태그되지 않았습니다.");
        }


        // rfid로 차 키 조회
        Key key = keyRepository.findByRfid(rfid)
                .orElseThrow(() -> new RuntimeException("없는 차키입니다."));
        // 차 키로 차량 조회
        Car car = carRepository.findById(key.getCar().getCarId())
                .orElseThrow(() -> new RuntimeException("없는 차량입니다."));
        // 지점이 소유한 차량인지 검증
        if (!car.getBranch().equals(kiosk.getBranch())) {
            throw new RuntimeException("이 지점이 소유한 차량이 아닙니다.");
        }
        // 키 보관함의 남은 슬롯 중 1에 가까운 숫자에 저장
        KeyStorage keyStorage = keyStorageRepository.findUsableSlotByKioskId(kioskId);
        if (keyStorage == null) {
            throw new RuntimeException("사용가능한 슬롯이 없습니다.");
        }


        // 파이에게 어떤 차 키 보관함을 열라는 요청
        // 헤더 설정
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 파라미터 세팅
        map = new LinkedMultiValueMap<>();
        map.add("kioskId", keyStorage.getKiosk().getKioskId().toString());
        map.add("slotNumber", keyStorage.getSlotNumber().toString());

        request = new HttpEntity<>(map, headers);

        ResponseEntity<Boolean> response3 = restTemplate.postForEntity(piBaseUrl + "/receive-car-key", request, Boolean.class);
        if (!Boolean.TRUE.equals(response3.getBody())) {
            throw new RuntimeException("응답이 정상적이지 않습니다. 다시 시도해주세요.");
        } else {
            // 상태 업데이트
            keyStorageRepository.save(keyStorage.updateAvailable(false));
            keyRepository.save(key.updateKey(null, keyStorage, null, KeyState.WAITING));
            return keyStorage.getSlotNumber();
        }
    }

    public BranchNameResponseDto getBranchNameByKioskId(Long kioskId) {
        kioskRepository.findById(kioskId)
                .orElseThrow(() -> new RuntimeException("없는 키오스크입니다."));
        return kioskRepository.findBranchNameByKioskId(kioskId);
    }
}