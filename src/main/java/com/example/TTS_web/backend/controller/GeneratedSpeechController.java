package com.example.TTS_web.backend.controller;

import com.example.TTS_web.backend.dto.GeneratedSpeechDTO;
import com.example.TTS_web.backend.service.SpeechService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/speeches")
public class GeneratedSpeechController {
    private final SpeechService speechService;

    @GetMapping
    public ResponseEntity<List<GeneratedSpeechDTO>> getSpeeches() {
        return ResponseEntity.ok(speechService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneratedSpeechDTO> getSpeech(@PathVariable Long id) {
        return ResponseEntity.ok(speechService.getById(id));
    }

    @PostMapping("/{modelId}/generate")
    public ResponseEntity<GeneratedSpeechDTO> createSpeech(@PathVariable Long modelId, @RequestBody GeneratedSpeechDTO speech) throws IOException {
        speech.setModelId(modelId);
        return ResponseEntity.ok(speechService.createSpeech(speech));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneratedSpeechDTO> updateSpeech(@PathVariable Long id, @RequestBody GeneratedSpeechDTO speechDTO) {
        speechDTO.setId(id);
        return ResponseEntity.ok(speechService.updateSpeech(speechDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSpeech(@PathVariable Long id) {
        speechService.deleteSpeech(id);
        return ResponseEntity.ok("Deleted Speech " + id);
    }
}
