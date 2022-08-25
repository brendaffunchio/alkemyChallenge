package com.backend.disney.services;

import com.backend.disney.models.User;
import com.backend.disney.modelsDTO.UserDTO;

public interface IUserServices {

   UserDTO register(UserDTO dto) throws Exception;

   User getByEmail(String email);
}


