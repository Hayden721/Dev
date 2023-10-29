package com.dev.shop.user.service.impl;

import com.dev.shop.user.dao.UserDao;
import com.dev.shop.user.dto.User;
import com.dev.shop.user.service.face.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    public String getData() {
        return userDao.selectData();
    }

    @Override
    public boolean userlogin(User user) {
        int valueChk = userDao.selectEmailPw(user);

        if(valueChk > 0) {
            log.info("{}", valueChk);
            return true;
        }
        return false;
    }
}
