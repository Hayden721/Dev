package com.dev.shop.seller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Collections;


@Getter @Setter
@AllArgsConstructor
@Slf4j @ToString
// 로그인에만 사용한다.
public class SellerDetailsDto implements UserDetails {
    private Long sellerNo;
    private String sellerId;
    private String sellerEmail;
    private String sellerPw;
    private String sellerPhone;
    private String sellerName;
    private String sellerAuth;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(sellerAuth));
    }

    @Override
    public String getPassword() {
        return sellerPw;
    }

    @Override
    public String getUsername() {
        return sellerId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}