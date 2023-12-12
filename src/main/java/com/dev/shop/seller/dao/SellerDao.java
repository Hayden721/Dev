package com.dev.shop.seller.dao;

import com.dev.shop.seller.dto.SellerDetailsDto;
import com.dev.shop.seller.dto.SellerDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SellerDao {

    SellerDetailsDto selectSellerById(String sellerId);

    void insertSellerRegister(SellerDto sellerDto);
}
