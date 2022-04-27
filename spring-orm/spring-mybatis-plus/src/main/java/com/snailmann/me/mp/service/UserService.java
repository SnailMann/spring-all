package com.snailmann.me.mp.service;

import com.snailmann.me.mp.dto.PageResp;
import com.snailmann.me.mp.entity.User;

public interface UserService {
    PageResp<User> search(User user, int page, int size);
}
