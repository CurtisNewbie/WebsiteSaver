package com.curtisnewbie.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author yongjie.zhuang
 */
@Mapper
public interface UserMapper {

    UserEntity findByUsername(@Param("username") String username);
}
