package com.yu.yurentcar.domain.car.service;

import com.yu.yurentcar.domain.branch.entity.Branch;
import com.yu.yurentcar.domain.branch.repository.BranchRepository;
import com.yu.yurentcar.domain.car.dto.*;
import com.yu.yurentcar.domain.car.entity.*;
import com.yu.yurentcar.domain.car.repository.CarEventRepository;
import com.yu.yurentcar.domain.car.repository.CarRepository;
import com.yu.yurentcar.domain.car.repository.CarSpecificationRepository;
import com.yu.yurentcar.domain.user.entity.Admin;
import com.yu.yurentcar.domain.user.entity.CarSize;
import com.yu.yurentcar.domain.user.entity.OilType;
import com.yu.yurentcar.domain.user.entity.Transmission;
import com.yu.yurentcar.domain.user.repository.AdminRepository;
import com.yu.yurentcar.global.utils.FileUploadUtil;
import com.yu.yurentcar.global.utils.enums.EnumValueConvertUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
public class CarService {
    private final BranchRepository branchRepository;
    private final AdminRepository adminRepository;
    private final CarSpecificationRepository carSpecificationRepository;
    private final CarRepository carRepository;
    private final CarEventRepository carEventRepository;

    public CarService(BranchRepository branchRepository, CarRepository carRepository,
                      CarSpecificationRepository carSpecificationRepository,
                      AdminRepository adminRepository, CarEventRepository carEventRepository) {
        this.branchRepository = branchRepository;
        this.carRepository = carRepository;
        this.carSpecificationRepository = carSpecificationRepository;
        this.adminRepository = adminRepository;
        this.carEventRepository = carEventRepository;
    }

    @Transactional(readOnly = true)
    public List<UsableCarResponseDto> getUsableCarList(UsableCarSearchRequestDto requestDto) {
        return carRepository.searchUsableCar(requestDto);
    }

    @Transactional(readOnly = true)
    public CarDetailsResponseDto getCarDetails(String carNumber) {
        return carRepository.findCarDetailsByCarNumber(carNumber);
    }

    @Transactional(readOnly = true)
    public CarResponseDto getCarResponseDTO(String carNumber) {
        return carRepository.findCarResponseDtoByCarNumber(carNumber);
    }

    @Transactional(readOnly = true)
    public CarSpecDto getCarSpecByCarNumber(String carNumber) {
        return carRepository.findCarSpecByCarNumber(carNumber);
    }
    //Table 수정으로 이동
//    @Transactional(readOnly = true)
//    public List<String> getAccidentListByCarNumber(String carNumber) {
//        return carRepository.findAccidentListByCarNumber(carNumber);
//    }
//
//    @Transactional(readOnly = true)
//    public List<String> getRepairListByCarNumber(String carNumber) {
//        return carRepository.findRepairListByCarNumber(carNumber);
//    }

    @Transactional(readOnly = true)
    public List<CarResponseDto> getCarListByCarNumbers(String[] carNumbers) {
        List<CarResponseDto> list = carRepository.findCarsByCarNumbers(carNumbers);
        return Arrays.stream(carNumbers)
                .flatMap(cn -> list.stream()
                        .filter(c -> c.getCarNumber().equals(cn)))
                .collect(Collectors.toList());
    }

    @Transactional
    public Long postCar(CarRequestDto carRequestDto, String adminUsername, MultipartFile file) throws IOException {
        Optional<Car> lookupCar = carRepository.findByCarNumber(carRequestDto.getCarNumber());
        Optional<Admin> lookupAdmin = adminRepository.findByUsername(adminUsername);
        if (lookupCar.isPresent()) throw new RuntimeException("이미 존재하는 차량입니다.");
        if (lookupAdmin.isEmpty()) throw new RuntimeException("없는 관리자입니다.");
        Optional<Branch> lookupBranch = branchRepository.findById(lookupAdmin.get().getBranch().getBranchId());
        if (lookupBranch.isEmpty()) throw new RuntimeException("없는 지점입니다.");

        //등록하려는 차량의 차량 제원이 있는지 검사
        Optional<CarSpecification> lookupCarSpec = carSpecificationRepository.findByCarBrandAndCarNameAndOilTypeAndReleaseDateAndTransmission(
                EnumValueConvertUtils.ofDesc(CarBrand.class, carRequestDto.getCarBrand()),
                carRequestDto.getCarName(),
                EnumValueConvertUtils.ofDesc(OilType.class, carRequestDto.getOilType()),
                carRequestDto.getReleaseDate(),
                EnumValueConvertUtils.ofDesc(Transmission.class, carRequestDto.getTransmission()));

        CarSpecification carSpec;
        if (lookupCarSpec.isEmpty()) {
            //차량 제원이 없는 경우 등록
            carSpec = carSpecificationRepository.saveAndFlush(
                    CarSpecification.builder()
                            .carName(carRequestDto.getCarName())
                            .maxPassenger(carRequestDto.getMaxPassenger())
                            .carSize(EnumValueConvertUtils.ofDesc(CarSize.class, carRequestDto.getCarSize()))
                            .oilType(EnumValueConvertUtils.ofDesc(OilType.class, carRequestDto.getOilType()))
                            .transmission(EnumValueConvertUtils.ofDesc(Transmission.class, carRequestDto.getTransmission()))
                            .carBrand(EnumValueConvertUtils.ofDesc(CarBrand.class, carRequestDto.getCarBrand()))
                            .releaseDate(carRequestDto.getReleaseDate())
                            .build()

            );
        }// 있는 경우 그대로 사용
        else carSpec = lookupCarSpec.get();

        String projectPath;
        String fileName = "";

        if (file != null) {
            //저장할 경로를 지정
            projectPath = System.getProperty("user.dir") + "\\files";
            //식별자
            UUID uuid = UUID.randomUUID();
            //랜덤식별자_원래파일이름 = 저장될 파일이름 지정
            fileName = uuid + "_" + file.getOriginalFilename();
            //파일 저장
            FileUploadUtil.saveFile(projectPath, fileName, file);
        }
        // 차량 등록
        Car car = carRepository.saveAndFlush(
                Car.builder()
                        .carSpec(carSpec)
                        .branch(lookupBranch.get())
                        .carNumber(carRequestDto.getCarNumber())
                        .totalDistance(carRequestDto.getTotalDistance())
                        .carState(CarState.USABLE)
                        .carPrice(carRequestDto.getCarPrice())
                        .discountRate(carRequestDto.getDiscountRate())
                        .discountReason(carRequestDto.getDiscountReason())
                        .carDescription(carRequestDto.getCarDescription())
                        /*저장되는 경로*/
                        .photoUrl(file == null ? "" : fileName)
                        .build()
        );

        //수리내역 사고내역 저장
        List<CarEvent> carEvents = new ArrayList<>();
        if (carRequestDto.getAccidentList() != null) {
            for (int i = 0; i < carRequestDto.getAccidentList().size(); i++) {
                CarEvent carEvent = CarEvent.builder()
                        .car(car)
                        .title(carRequestDto.getAccidentList().get(i).getTitle())
                        .eventDate(carRequestDto.getAccidentList().get(i).getEventDate())
                        .content(carRequestDto.getAccidentList().get(i).getContent())
                        .isRepair(false)
                        .build();
                carEvents.add(carEvent);
            }
        }
        if (carRequestDto.getRepairList() != null) {
            for (int i = 0; i < carRequestDto.getRepairList().size(); i++) {
                CarEvent carEvent = CarEvent.builder()
                        .car(car)
                        .title(carRequestDto.getRepairList().get(i).getTitle())
                        .eventDate(carRequestDto.getRepairList().get(i).getEventDate())
                        .content(carRequestDto.getRepairList().get(i).getContent())
                        .isRepair(true)
                        .build();
                carEvents.add(carEvent);
            }
        }
        carEventRepository.saveAll(carEvents);
        return car.getCarId();
    }

    @Transactional
    public Boolean patchCar(CarRequestDto carRequestDto, String adminUsername, Long carId, MultipartFile file) throws IOException {
        Optional<Car> lookupCar = carRepository.findById(carId);
        if (lookupCar.isEmpty()) throw new RuntimeException("없는 차량입니다.");
        Optional<Admin> lookupAdmin = adminRepository.findByUsername(adminUsername);
        if (lookupAdmin.isEmpty()) throw new RuntimeException("없는 관리자입니다.");
        Optional<Branch> lookupBranch = branchRepository.findById(lookupAdmin.get().getBranch().getBranchId());
        if (lookupBranch.isEmpty()) throw new RuntimeException("없는 지점입니다.");
        if (!lookupAdmin.get().getBranch().equals(lookupCar.get().getBranch()))
            throw new RuntimeException("변경하려는 차의 지점과 관리자의 지점이 다릅니다. 권한이 없습니다.");

        //변경하려는 차량의 차량 제원이 있는지 검사
        Optional<CarSpecification> lookupCarSpec = carSpecificationRepository.findByCarBrandAndCarNameAndOilTypeAndReleaseDateAndTransmission(
                EnumValueConvertUtils.ofDesc(CarBrand.class, carRequestDto.getCarBrand()),
                carRequestDto.getCarName(),
                EnumValueConvertUtils.ofDesc(OilType.class, carRequestDto.getOilType()),
                carRequestDto.getReleaseDate(),
                EnumValueConvertUtils.ofDesc(Transmission.class, carRequestDto.getTransmission()));

        CarSpecification carSpec;

        if (lookupCarSpec.isEmpty()) {
            //차량 제원이 없는 경우 등록
            carSpec = carSpecificationRepository.saveAndFlush(
                    CarSpecification.builder()
                            .carName(carRequestDto.getCarName())
                            .maxPassenger(carRequestDto.getMaxPassenger())
                            .carSize(EnumValueConvertUtils.ofDesc(CarSize.class, carRequestDto.getCarSize()))
                            .oilType(EnumValueConvertUtils.ofDesc(OilType.class, carRequestDto.getOilType()))
                            .transmission(EnumValueConvertUtils.ofDesc(Transmission.class, carRequestDto.getTransmission()))
                            .carBrand(EnumValueConvertUtils.ofDesc(CarBrand.class, carRequestDto.getCarBrand()))
                            .releaseDate(carRequestDto.getReleaseDate())
                            .build()

            );
        } else carSpec = lookupCarSpec.get();

        // 기존에 사진이 있는 경우 저장된 사진 삭제
        File lookupFile;
        if (!lookupCar.get().getPhotoUrl().equals("")) {
            log.info("기존의 이미지를 지웁니다.");
            log.info(System.getProperty("user.dir") + "\\files\\" + URLDecoder.decode(lookupCar.get().getPhotoUrl()));
            try {
                lookupFile = new File(System.getProperty("user.dir") + "\\files\\" + URLDecoder.decode(lookupCar.get().getPhotoUrl(), "UTF-8"));
                lookupFile.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String projectPath;
        String fileName = null;

        if (file != null) {
            //저장할 경로를 지정
            projectPath = System.getProperty("user.dir") + "\\files";
            //식별자
            UUID uuid = UUID.randomUUID();
            //랜덤식별자_원래파일이름 = 저장될 파일이름 지정
            fileName = uuid + "_" + file.getOriginalFilename();
            //파일 저장
            FileUploadUtil.saveFile(projectPath, fileName, file);
        }

        // 차량 변경
        Car car = carRepository.saveAndFlush(
                lookupCar.get().updateCar(carRequestDto, carSpec, lookupBranch.get(), fileName)
        );

        // 수리내역 삭제
        carEventRepository.deleteRepairListByCarId(car.getCarId());
        // 사고내역 삭제
        carEventRepository.deleteAccidentListByCarId(car.getCarId());
        //수리내역 사고내역 저장
        List<CarEvent> carEvents = new ArrayList<>();
        if (carRequestDto.getAccidentList() != null) {
            for (int i = 0; i < carRequestDto.getAccidentList().size(); i++) {
                CarEvent carEvent = CarEvent.builder()
                        .car(car)
                        .title(carRequestDto.getAccidentList().get(i).getTitle())
                        .eventDate(carRequestDto.getAccidentList().get(i).getEventDate())
                        .content(carRequestDto.getAccidentList().get(i).getContent())
                        .isRepair(false)
                        .build();
                carEvents.add(carEvent);
            }
        }
        if (carRequestDto.getRepairList() != null) {
            for (int i = 0; i < carRequestDto.getRepairList().size(); i++) {
                CarEvent carEvent = CarEvent.builder()
                        .car(car)
                        .title(carRequestDto.getRepairList().get(i).getTitle())
                        .eventDate(carRequestDto.getRepairList().get(i).getEventDate())
                        .content(carRequestDto.getRepairList().get(i).getContent())
                        .isRepair(true)
                        .build();
                carEvents.add(carEvent);
            }
        }
        carEventRepository.saveAll(carEvents);
        return true;
    }

    @Transactional
    public Boolean deleteCar(String adminUsername, String carNumber) {
        Optional<Admin> lookupAdmin = adminRepository.findByUsername(adminUsername);
        Optional<Car> lookupCar = carRepository.findByCarNumber(carNumber);
        if (lookupAdmin.isEmpty()) throw new RuntimeException("없는 관리자입니다.");
        if (lookupCar.isEmpty()) throw new RuntimeException("없는 차량입니다.");
        //삭제하려는 관리자와 차량의 지점이 같은지 확인
        if (!lookupAdmin.get().getBranch().equals(lookupCar.get().getBranch()))
            throw new RuntimeException("다른 지점 관리자입니다. 권한이 없습니다.");

        // 기존에 사진이 있는 경우 저장된 사진 삭제
        File lookupFile;
        if (!lookupCar.get().getPhotoUrl().equals("")) {
            log.info("이미지를 지웁니다.");
            log.info(System.getProperty("user.dir") + "\\files\\" + URLDecoder.decode(lookupCar.get().getPhotoUrl()));
            try {
                lookupFile = new File(System.getProperty("user.dir") + "\\files\\" + URLDecoder.decode(lookupCar.get().getPhotoUrl(), "UTF-8"));
                lookupFile.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 수리내역 삭제
        carEventRepository.deleteRepairListByCarId(lookupCar.get().getCarId());
        // 사고내역 삭제
        carEventRepository.deleteAccidentListByCarId(lookupCar.get().getCarId());
        // 차량 삭제
        carRepository.delete(lookupCar.get());

        return true;
    }
}
