package com.dev.shop.config.handler;

import com.dev.shop.member.service.MemberService;
import com.dev.shop.reserve.service.ReserveService;
import com.dev.shop.test.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationScheduleHandler {

    private final ReserveService reserveService;

    @Scheduled(cron = "0 0 0/1 * * *")
    public void updateReservationStatus() {

        reserveService.updateReservationStatus();

        log.info("-------- ReservationHandler 확인 dubug");
    }

}
