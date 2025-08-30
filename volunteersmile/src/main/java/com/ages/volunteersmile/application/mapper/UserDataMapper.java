package com.ages.volunteersmile.application.mapper;

import org.springframework.stereotype.Component;

import com.ages.volunteersmile.application.dto.UserDTO;
import com.ages.volunteersmile.domain.global.model.User;

@Component
public class UserDataMapper {

    public UserDataMapper(){}

    public UserDTO mapFrom(User user){
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getAppRole());
        if (user.getStatus() != null) dto.setStatus(user.getStatus());
        return dto;
    }

    public User mapTo(UserDTO dto, User user){
        if (dto.getName() != null) user.setName(dto.getName());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getStatus() != null) user.setStatus(dto.getStatus());
        if (dto.getRole() != null) user.setAppRole(dto.getRole());
        return user;
    }
}
