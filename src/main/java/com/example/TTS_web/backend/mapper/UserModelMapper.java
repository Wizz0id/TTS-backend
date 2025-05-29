package com.example.TTS_web.backend.mapper;

import com.example.TTS_web.backend.dto.UserModelDTO;
import com.example.TTS_web.backend.entity.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {BaseModelMapper.class, UserMapper.class})
public interface UserModelMapper {

    @Mapping(target = "basic.name", source = "dto.baseName", ignore = true)
    @Mapping(target = "user.username", source = "dto.userName", ignore = true)
    @Mapping(target = "stoi", source = "dto.stoi", ignore = true)
    UserModel toEntity(UserModelDTO dto);

    @Mapping(target = "baseName", source = "entity.basic.name")
    @Mapping(target = "userName", source = "entity.user.username")
    UserModelDTO toDTO(UserModel entity);
}
