package com.yu.yurentcar.global.utils;

import com.yu.yurentcar.domain.reservation.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class MailUtils {
    private final JavaMailSender javaMailSender;

    public boolean mailFormatCheck(String mail) {
        return Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
                .matcher(mail)
                .matches();
    }

    public String makeMessageFromReservation(Reservation reservation) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return String.format(
                """
                안녕하세요 %s님,
                YU렌트카의 예약이 확정되었습니다.
                에약 id : %d
                예약 금액 : %d
                차량 종류 : %s
                차량 번호 : %s
                """
        , reservation.getUser().getNickname(), reservation.getReservationId(),reservation.getReservationPrice(),
                reservation.getCar().getCarSpec().getCarName(), reservation.getCar().getCarNumber());

    }

    @Async
    public void sendMail(String mailAddress, String subject, String content) {
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo(mailAddress);
        msg.setSubject(subject);
        msg.setText(content);
        javaMailSender.send(msg);

    }
}
