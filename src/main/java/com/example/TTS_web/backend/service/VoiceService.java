package com.example.TTS_web.backend.service;

import com.example.TTS_web.backend.dto.VoiceDTO;
import com.example.TTS_web.backend.entity.Voice;
import com.example.TTS_web.backend.mapper.VoiceMapper;
import com.example.TTS_web.backend.repository.VoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoiceService {
    private final VoiceRepository voiceRepository;
    private final VoiceMapper voiceMapper;

    public List<VoiceDTO> getAll(){
        return voiceRepository.findAll().stream().map(voiceMapper::toDTO).toList();
    }

    public VoiceDTO getById(long id){
        return voiceMapper.toDTO(voiceRepository.findById(id).orElse(new Voice()));
    }

    public VoiceDTO createModel(VoiceDTO voice){
        return voiceMapper.toDTO(voiceRepository.save(voiceMapper.toEntity(voice)));
    }

    public VoiceDTO updateModel(VoiceDTO voice){
        return voiceMapper.toDTO(voiceRepository.save(voiceMapper.toEntity(voice)));
    }

    public void deleteModel(long id){
        voiceRepository.deleteById(id);
    }
}
