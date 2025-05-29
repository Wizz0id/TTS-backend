package com.example.TTS_web.backend.mapper;

import com.example.TTS_web.backend.dto.UserDTO;
import com.example.TTS_web.backend.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO dto);

    UserDTO toDTO(User entity);
}
