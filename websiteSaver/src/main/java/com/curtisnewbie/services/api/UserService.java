package com.curtisnewbie.services.api;

import com.curtisnewbie.dao.UserEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author yongjie.zhuang
 */
public interface UserService {

    UserEntity loadUserByUsername(String s) throws UsernameNotFoundException;
}
