package com.dev.shop.main.service.impl;

import com.dev.shop.main.service.MainService;
import com.dev.shop.member.dao.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    private MemberDao memberDao;


}
