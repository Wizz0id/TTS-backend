package com.example.TTS_web.backend.service;

import com.example.TTS_web.backend.dto.TTSRequestDTO;
import com.example.TTS_web.backend.dto.GeneratedSpeechDTO;
import com.example.TTS_web.backend.entity.GeneratedSpeech;
import com.example.TTS_web.backend.mapper.BaseModelMapper;
import com.example.TTS_web.backend.mapper.GeneratedSpeechMapper;
import com.example.TTS_web.backend.repository.SpeechRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpeechService {
    private final SpeechRepository speechRepository;
    private final BaseModelService baseModelService;
    private final BaseModelMapper baseModelMapper;
    private final GeneratedSpeechMapper speechMapper;
    @Value("${audio.storage-path}")
    private String storagePath;

    public List<GeneratedSpeechDTO> getAll() {
        return speechRepository.findAll().stream().map(speechMapper::toDTO).toList();
    }

    public GeneratedSpeechDTO getById(long id) {
        return speechMapper.toDTO(speechRepository.findById(id).orElse(new GeneratedSpeech()));
    }

    public GeneratedSpeechDTO createSpeech(GeneratedSpeechDTO generatedSpeechDTO) throws IOException {
        GeneratedSpeech found = speechRepository.findByText(generatedSpeechDTO.getText(), generatedSpeechDTO.getModelId()).orElse(null);
        if(found != null) {
            return speechMapper.toDTO(found);
        }
        TTSRequestDTO requestDTO = new TTSRequestDTO();
        requestDTO.setText(generatedSpeechDTO.getText());
        requestDTO.setSpeaker(generatedSpeechDTO.getSpeaker());
        String pythonServiceUrl = "http://localhost:5000/generate-audio";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TTSRequestDTO> entity = new HttpEntity<>(requestDTO, headers);

        ResponseEntity<byte[]> response = restTemplate.postForEntity(pythonServiceUrl, entity, byte[].class);

        if (response.getStatusCode() == HttpStatus.OK && response.hasBody()) {
            byte[] audioData = response.getBody();
            String fileName = "audio_" + System.currentTimeMillis() + ".wav";
            Path targetPath = Paths.get(storagePath, fileName);
            Files.createDirectories(targetPath.getParent());

            try (FileOutputStream fos = new FileOutputStream(targetPath.toFile())) {
                fos.write(audioData);
            }
            generatedSpeechDTO.setPathToAudio(targetPath.toString());
        }
        GeneratedSpeech speech = speechMapper.toEntity(generatedSpeechDTO);
        speech.setModel(baseModelMapper.toEntity(baseModelService.getById(generatedSpeechDTO.getModelId())));
        return speechMapper.toDTO(speechRepository.save(speech));
    }

    public GeneratedSpeechDTO updateSpeech(GeneratedSpeechDTO generatedSpeechDTO) {
        return speechMapper.toDTO(speechRepository.save(speechMapper.toEntity(generatedSpeechDTO)));
    }
    public void deleteSpeech(long id) {
        speechRepository.deleteById(id);
    }
}
