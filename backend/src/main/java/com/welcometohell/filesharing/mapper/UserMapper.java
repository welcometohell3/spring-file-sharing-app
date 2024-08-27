package com.welcometohell.filesharing.mapper;

import com.welcometohell.filesharing.dto.UserDto;
import com.welcometohell.filesharing.entity.User;

public interface UserMapper {

    UserDto toUserDto(User user);
}