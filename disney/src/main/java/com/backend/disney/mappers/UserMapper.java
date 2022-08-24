package com.backend.disney.mappers;

import com.backend.disney.models.Role;
import com.backend.disney.models.User;
import com.backend.disney.modelsDTO.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User mapDTOToEntity(UserDTO dto, Role role, String password) {

       User user = new User();
user.setUsername(dto.getUsername());
user.setPassword(password);
user.setRole(role);
        return user;
    }

    public UserDTO mapEntityToMovieDTOToResponse(User user) {

        UserDTO dto= new UserDTO();
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole().getName());
        return dto;
    }
}
