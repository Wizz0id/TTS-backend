package com.example.TTS_web.backend.service;

import com.example.TTS_web.backend.dto.BaseModelDTO;
import com.example.TTS_web.backend.entity.BaseModel;
import com.example.TTS_web.backend.mapper.BaseModelMapper;
import com.example.TTS_web.backend.repository.BaseModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseModelService {
    private final BaseModelRepository baseModelRepository;
    private final BaseModelMapper baseModelMapper;

    public List<BaseModelDTO> getAll(){
        return baseModelRepository.findAll().stream().map(baseModelMapper::toDTO).toList();
    }

    public BaseModel getByName(String name){
        return baseModelRepository.findByName(name).orElse(null);
    }

    public BaseModelDTO getById(long id){
        return baseModelMapper.toDTO(baseModelRepository.findById(id).orElse(new BaseModel()));
    }

    public BaseModelDTO createModel(BaseModelDTO baseModel){
        return baseModelMapper.toDTO(baseModelRepository.save(baseModelMapper.toEntity(baseModel)));
    }

    public BaseModelDTO updateModel(BaseModelDTO baseModel){
        return baseModelMapper.toDTO(baseModelRepository.save(baseModelMapper.toEntity(baseModel)));
    }
    public void deleteModel(long id){
        baseModelRepository.deleteById(id);
    }
}
