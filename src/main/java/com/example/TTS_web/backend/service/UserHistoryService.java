package com.example.TTS_web.backend.service;

import com.example.TTS_web.backend.dto.UserHistoryDTO;
import com.example.TTS_web.backend.entity.UserHistory;
import com.example.TTS_web.backend.mapper.UserHistoryMapper;
import com.example.TTS_web.backend.repository.UserHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserHistoryService {
    private final UserHistoryRepository userHistoryRepository;
    private final UserHistoryMapper userHistoryMapper;

    public List<UserHistoryDTO> getAll() {
        return userHistoryRepository.findAll().stream().map(userHistoryMapper::toDTO).toList();
    }

    public UserHistoryDTO getById(long id) {
        return userHistoryMapper.toDTO(userHistoryRepository.findById(id).orElse(new UserHistory()));
    }

    public List<UserHistoryDTO> getByUserId(long userId) {
        return userHistoryRepository.findByUserId(userId).stream().map(userHistoryMapper::toDTO).toList();
    }

    public UserHistoryDTO createHistory(UserHistoryDTO userHistoryDTO) {
        return userHistoryMapper.toDTO(userHistoryRepository.save(userHistoryMapper.toEntity(userHistoryDTO)));
    }

    public UserHistoryDTO updateHistory(UserHistoryDTO userHistoryDTO) {
        return userHistoryMapper.toDTO(userHistoryRepository.save(userHistoryMapper.toEntity(userHistoryDTO)));
    }

    public void deleteHistory(long id) {
        userHistoryRepository.deleteById(id);
    }
}
