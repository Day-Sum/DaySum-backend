package com.jung.daysum.service;

import com.jung.daysum.domain.User;
import com.jung.daysum.dto.UserDto;

public interface UserService {
    User findUser(Long userId);
    User findLoginUser();
    UserDto.Response findUserProfile();
}
