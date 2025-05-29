package com.example.TTS_web.backend.mapper;

import com.example.TTS_web.backend.dto.BaseModelDTO;
import com.example.TTS_web.backend.entity.BaseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {VoiceMapper.class})
public interface BaseModelMapper {

    @Mapping(target = "voiceList", source = "dto.voiceList")
    BaseModel toEntity(BaseModelDTO dto);

    @Mapping(target = "voiceList", source = "entity.voiceList")
    BaseModelDTO toDTO(BaseModel entity);
}
