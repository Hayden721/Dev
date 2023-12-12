package com.dev.shop.seller.service.impl;

import com.dev.shop.seller.dao.SellerDao;
import com.dev.shop.seller.dto.SellerDetailsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor

public class SellerDetailsServiceImpl implements UserDetailsService {

    private final SellerDao sellerDao;

    @Override
    public UserDetails loadUserByUsername(String sellerId) throws UsernameNotFoundException {
        log.info("--- [SellerCustomDetailsServiceImpl] 여기까지 옴");
        log.info("{}", sellerId);
        SellerDetailsDto dto = sellerDao.selectSellerById(sellerId);
//        log.info("--- [customDetailServiceImpl] 데이터값 확인 : {}", dto);
        if (dto.getSellerId() == null) {
            throw new UsernameNotFoundException("해당 아이디가 존재하지 않습니다.");

        }
        return dto;
    }
}
