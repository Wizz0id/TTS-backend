package com.example.TTS_web.backend.mapper;

import com.example.TTS_web.backend.dto.VoiceDTO;
import com.example.TTS_web.backend.entity.Voice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {BaseModelMapper.class})
public interface VoiceMapper {

    Voice toEntity(VoiceDTO dto);

    VoiceDTO toDTO(Voice entity);
}
