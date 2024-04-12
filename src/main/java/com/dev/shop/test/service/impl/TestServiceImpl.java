package com.dev.shop.test.service.impl;

import com.dev.shop.test.dao.TestDao;
import com.dev.shop.test.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestDao testDao;


    @Override
    public void updateReservationStatus(Date currentTime) {

        List<LocalDateTime> endDateTime = testDao.selectEndDateTime();

    }
}
