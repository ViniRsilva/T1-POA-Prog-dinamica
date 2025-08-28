package com.ages.volunteersmile.application.mapper;

import com.ages.volunteersmile.application.dto.UserDTO;
import com.ages.volunteersmile.application.dto.UserStatusDTO;
import com.ages.volunteersmile.domain.global.model.User;

public class UserDataMapper {

    public UserDataMapper(){}

    public UserDTO mapFrom(User user){
        var userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setStatus(UserStatusDTO.valueOf(user.getStatus()));
        userDTO.setCreatedAt(user.getCreatedAt());
        return userDTO;
    }

    public User mapTo(UserDTO userDTO, User user){
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        if(userDTO.getStatus() != null){
            user.setStatus(userDTO.getStatus().name());
        }
        return user;
    }
}
