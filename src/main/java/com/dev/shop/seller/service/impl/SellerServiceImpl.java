package com.dev.shop.seller.service.impl;

import com.dev.shop.seller.dto.SellerDto;
import com.dev.shop.seller.dao.SellerDao;
import com.dev.shop.seller.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {
    private final SellerDao sellerDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:sss");
    Date time = new Date();
    String localTime = format.format(time);

    @Override
    public void sellerRegister(SellerDto sellerDto) {

        sellerDto.setSellerPw(bCryptPasswordEncoder.encode(sellerDto.getSellerPw()));
        sellerDto.setSellerAuth("SELLER");
        sellerDto.setAppendDate(localTime);
        sellerDto.setUpdateDate(localTime);

        sellerDao.insertSellerRegister(sellerDto);
    }

}
