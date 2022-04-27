package com.snailmann.me.mp.controller;

import com.snailmann.me.mp.dto.PageResp;
import com.snailmann.me.mp.entity.User;
import com.snailmann.me.mp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wenjie.li
 */
@RestController
@RequestMapping("/spring-orm/mp/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public PageResp<User> search(Integer id, String name, String email, Integer age, int page, int size) {
        return userService.search(User.builder()
                .id(id).name(name).email(email).age(age).build(), page, size);
    }
}
