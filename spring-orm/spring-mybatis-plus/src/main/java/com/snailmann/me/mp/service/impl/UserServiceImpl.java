package com.snailmann.me.mp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.snailmann.me.mp.dao.UserMapper;
import com.snailmann.me.mp.dto.PageResp;
import com.snailmann.me.mp.entity.User;
import com.snailmann.me.mp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wenjie.li
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageResp<User> search(User user, int offset, int size) {
        Page<User> page = new Page<>(offset, size);
        var query = new QueryWrapper<User>().lambda();
        if (StringUtils.isNotBlank(user.getName())) {
            query.like(User::getName, user.getName());
        }
        if (StringUtils.isNotBlank(user.getEmail())) {
            query.like(User::getEmail, user.getEmail());
        }
        Page<User> res = userMapper.selectPage(page, query);
        return PageResp.offsetOf(res.getTotal(), (int) res.getCurrent(), null, null, res.getRecords());
    }
}
