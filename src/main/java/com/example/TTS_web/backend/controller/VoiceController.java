package com.example.TTS_web.backend.controller;

import com.example.TTS_web.backend.dto.VoiceDTO;
import com.example.TTS_web.backend.service.VoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/voices")
public class VoiceController {
    private final VoiceService voiceService;

    @GetMapping
    public ResponseEntity<List<VoiceDTO>> getVoices() {
        return ResponseEntity.ok(voiceService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoiceDTO> getVoice(@PathVariable Long id) {
        return ResponseEntity.ok(voiceService.getById(id));
    }

    @PostMapping
    public ResponseEntity<VoiceDTO> createVoice(@RequestBody VoiceDTO voice) {
        return ResponseEntity.ok(voiceService.createModel(voice));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VoiceDTO> updateVoice(@PathVariable Long id, @RequestBody VoiceDTO voice) {
        voice.setId(id);
        return ResponseEntity.ok(voiceService.updateModel(voice));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVoice(@PathVariable Long id) {
        voiceService.deleteModel(id);
        return ResponseEntity.ok("Deleted Voice " + id);
    }
}
