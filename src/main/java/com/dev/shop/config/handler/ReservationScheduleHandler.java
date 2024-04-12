package com.dev.shop.config.handler;

import com.dev.shop.test.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class ReservationScheduleHandler {

    private final TestService testService;

    @Scheduled(fixedDelay = 3600000)
    public void updateReservationStatus() {

        Date currentTime = new Date();
        testService.updateReservationStatus(currentTime);
    }

}
