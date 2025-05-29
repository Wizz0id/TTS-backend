package com.example.TTS_web.backend.mapper;

import com.example.TTS_web.backend.dto.PublicationDTO;
import com.example.TTS_web.backend.entity.Publication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring", uses = {UserMapper.class, UserModelMapper.class})
public interface PublicationMapper {

    @Mapping(target = "user.username", source = "dto.username")
    @Mapping(target = "model", source = "dto.model")
    Publication toEntity(PublicationDTO dto);

    @Mapping(target = "username", source = "entity.user.username")
    @Mapping(target = "model", source = "entity.model")
    PublicationDTO toDTO(Publication entity);
}
