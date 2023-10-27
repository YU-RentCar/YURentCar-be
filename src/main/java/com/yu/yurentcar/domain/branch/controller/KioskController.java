package com.yu.yurentcar.domain.branch.controller;

import com.yu.yurentcar.domain.branch.service.KioskService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Log4j2
@RestController
@RequestMapping("api/v1/branches/kiosks")
public class KioskController {
    private final KioskService kioskService;

    public KioskController(KioskService kioskService) {
        this.kioskService = kioskService;
    }

    @GetMapping("/keyStorage")
    public ResponseEntity<Long> getKeyStorageByReservationId(@RequestParam String name, @RequestParam Long reservationId, @RequestParam Long kioskId) {
        log.info("getKeyStorageByReservationId = " + name + "\n" + reservationId + "\n" + kioskId);
        return ResponseEntity.status(HttpStatus.OK).body(kioskService.findKeyStorage(name, reservationId, kioskId));
    }

    @PostMapping("/return")
    public ResponseEntity<Long> returnKey(@RequestBody Map<String, String> rfid, @RequestParam Long kioskId) {
        log.info("returnKey = " + rfid);
        return ResponseEntity.status(HttpStatus.OK).body(kioskService.returnKey(rfid.get("rfid"), kioskId));
    }
}