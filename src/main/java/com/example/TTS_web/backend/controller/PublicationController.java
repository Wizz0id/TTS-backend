package com.example.TTS_web.backend.controller;

import com.example.TTS_web.backend.dto.PublicationDTO;
import com.example.TTS_web.backend.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/publications")
public class PublicationController {
    private final PublicationService publicationService;

    @GetMapping
    public ResponseEntity<List<PublicationDTO>> getAllPublications() {
        return ResponseEntity.ok(publicationService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicationDTO> getBaseModel(@PathVariable Long id) {
        return ResponseEntity.ok(publicationService.getById(id));
    }

    @GetMapping("/{id}/publish")
    public ResponseEntity<PublicationDTO> publishModel(@PathVariable Long id) {
        return ResponseEntity.ok(publicationService.publishModel(id));
    }

    @GetMapping(params = "userId")
    public ResponseEntity<List<PublicationDTO>> getModelByUserId(@RequestParam(name = "userId") Long userId) {
        return ResponseEntity.ok(publicationService.getByUserId(userId));
    }

    @PostMapping(params = "userId")
    public ResponseEntity<Map<String, String>> createPublication(@RequestParam(name = "userId") Long userId, @RequestBody PublicationDTO publicationDTO) {
        Map<String, String> response = new HashMap<>();
        if (publicationService.createModel(publicationDTO, userId) != null) {
            response.put("message", "Публикация отправлена на проверку");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Ошибка при попытке публикации");
            return ResponseEntity.status(500).body(response);
        }
    }
}
