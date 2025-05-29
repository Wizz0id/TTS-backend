package com.example.TTS_web.backend.controller;

import com.example.TTS_web.backend.dto.BaseModelDTO;
import com.example.TTS_web.backend.service.BaseModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/base_models")
public class BaseModelController {
    private final BaseModelService baseModelService;

    @GetMapping
    public ResponseEntity<List<BaseModelDTO>> getBaseModels() {
        return ResponseEntity.ok(baseModelService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseModelDTO> getBaseModel(@PathVariable Long id) {
        return ResponseEntity.ok(baseModelService.getById(id));
    }

    @PostMapping
    public ResponseEntity<BaseModelDTO> createBaseModel(@RequestBody BaseModelDTO baseModelDTO) {
        return ResponseEntity.ok(baseModelService.createModel(baseModelDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseModelDTO> updateBaseModel(@PathVariable Long id, @RequestBody BaseModelDTO baseModelDTO) {
        baseModelDTO.setId(id);
        return ResponseEntity.ok(baseModelService.updateModel(baseModelDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBaseModel(@PathVariable Long id) {
        baseModelService.deleteModel(id);
        return ResponseEntity.ok("Deleted BaseModel " + id);
    }
}
