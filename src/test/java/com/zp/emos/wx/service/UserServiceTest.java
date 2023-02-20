package com.zp.emos.wx.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Set;


@SpringBootTest
@Slf4j
class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    void registerUser() {
    }

    @Test
    void searchUserPermissions() {
        Set<String> perms = userService.searchUserPermissions(5);
        log.info(perms.toString());
    }
}