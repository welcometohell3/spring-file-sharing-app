package com.welcometohell.filesharing.mapper;

import com.welcometohell.filesharing.dto.UserDto;
import com.welcometohell.filesharing.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getEmail(),
                user.getRole());
    }
}
