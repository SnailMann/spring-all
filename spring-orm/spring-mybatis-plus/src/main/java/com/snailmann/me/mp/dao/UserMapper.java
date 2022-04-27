package com.snailmann.me.mp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snailmann.me.mp.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wenjie.li
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user")
    List<User> findAll();
}
