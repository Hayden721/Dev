package com.dev.shop.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


@Getter @Setter
@AllArgsConstructor
@Slf4j @ToString
public class MemberDetailsDto implements UserDetails {

    private Long memberNo;
    private String memberId;
    private String memberPw;
    private String memberEmail;
    private String memberPhone;
    private String memberName;
    private String memberAuth;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(memberAuth));
    }

    @Override
    public String getPassword() {
        return memberPw;
    }

    @Override
    public String getUsername() {
        return memberId;
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