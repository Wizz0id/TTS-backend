package com.example.TTS_web.backend.mapper;

import com.example.TTS_web.backend.dto.GeneratedSpeechDTO;
import com.example.TTS_web.backend.entity.GeneratedSpeech;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BaseModelMapper.class})
public interface GeneratedSpeechMapper {

    @Mapping(target = "model.id", source = "dto.modelId", ignore = true)
    GeneratedSpeech toEntity(GeneratedSpeechDTO dto);

    @Mapping(target = "modelId", source = "entity.model.id")
    GeneratedSpeechDTO toDTO(GeneratedSpeech entity);
}
