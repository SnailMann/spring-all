package com.snailmann.me.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.snailmann.me.mp.dao.UserMapper;
import com.snailmann.me.mp.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert() {
        log.info("插入 User");
        int res = userMapper.insert(User.builder().name("jerry").email("jerry@gmail.com").age(24).build());
        System.out.println(res);
    }

    @Test
    public void find_all_test_1() {
        log.info("根据条件查询 User 列表");
        List<User> users = userMapper.selectList(new QueryWrapper<User>().lambda().eq(User::getName, "jerry"));
        System.out.println(users);
    }

    @Test
    public void find_all_test_2() {
        log.info("查询全部 @Select 注解方式");
        List<User> users = userMapper.findAll();
        System.out.println(users);
    }

    @Test
    public void findPage() {
        log.info("[分页] 根据条件查询 User 列表");
        int offset = 1, limit = 2;
        IPage<User> page = new Page<>(1,2);
        IPage<User> res = userMapper.selectPage(page, new QueryWrapper<User>().lambda().eq(User::getAge, 24));
        System.out.printf("offset: %s, current: %s, size: %s%n", res.offset(), res.getCurrent(), res.getSize());
        System.out.println(page.getRecords());
    }

}