package com.example.TTS_web.backend.controller;

import com.example.TTS_web.backend.dto.UserHistoryDTO;
import com.example.TTS_web.backend.service.UserHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/history")
public class UserHistoryController {
    private final UserHistoryService userHistoryService;

    @GetMapping
    public ResponseEntity<List<UserHistoryDTO>> getUserHistory() {
        return ResponseEntity.ok(userHistoryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserHistoryDTO> getUserHistoryById(@PathVariable long id) {
        return ResponseEntity.ok(userHistoryService.getById(id));
    }

    @GetMapping(params = "userId")
    public ResponseEntity<List<UserHistoryDTO>> getUserHistoryByUserId(@RequestParam(name = "userId") long userId) {
        return ResponseEntity.ok(userHistoryService.getByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<UserHistoryDTO> createUserHistory(@RequestBody UserHistoryDTO userHistoryDTO) {
        return ResponseEntity.ok(userHistoryService.createHistory(userHistoryDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserHistoryDTO> updateUserHistory(@PathVariable long id, @RequestBody UserHistoryDTO userHistoryDTO) {
        userHistoryDTO.setId(id);
        return ResponseEntity.ok(userHistoryService.updateHistory(userHistoryDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserHistory(@PathVariable long id) {
        userHistoryService.deleteHistory(id);
        return ResponseEntity.ok("Deleted History " + id);
    }
}
