package com.example.TTS_web.backend.mapper;

import com.example.TTS_web.backend.dto.UserHistoryDTO;
import com.example.TTS_web.backend.entity.UserHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, GeneratedSpeechMapper.class})
public interface UserHistoryMapper {

    @Mapping(target = "time", source = "dto.dateTime")
    UserHistory toEntity(UserHistoryDTO dto);

    @Mapping(target = "username", source = "entity.user.username")
    @Mapping(target = "userId", source = "entity.user.id")
    @Mapping(target = "speechId", source = "entity.speech.id")
    @Mapping(target = "text", source = "entity.speech.text")
    UserHistoryDTO toDTO(UserHistory entity);

}
