package com.example.TTS_web.backend.service;

import com.example.TTS_web.backend.dto.PublicationDTO;
import com.example.TTS_web.backend.entity.Publication;
import com.example.TTS_web.backend.mapper.PublicationMapper;
import com.example.TTS_web.backend.repository.PublicationRepository;
import com.example.TTS_web.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicationService {
    private final PublicationRepository publicationRepository;
    private final PublicationMapper publicationMapper;
    private final UserRepository userRepository;

    public List<PublicationDTO> getAll(){
        return publicationRepository.findAll().stream().map(publicationMapper::toDTO).toList();
    }

    public PublicationDTO getById(long id){
        return publicationMapper.toDTO(publicationRepository.findById(id).orElse(new Publication()));
    }

    public PublicationDTO publishModel(long id){
        Publication found = publicationRepository.findById(id).orElse(new Publication());
        found.setPublished(true);
        return publicationMapper.toDTO(publicationRepository.save(found));
    }

    public List<PublicationDTO> getByUserId(long id){
        return publicationRepository.findAllByUserId(id).stream().map(publicationMapper::toDTO).toList();
    }

    public PublicationDTO createModel(PublicationDTO baseModel, long userId){
        Publication entity = publicationMapper.toEntity(baseModel);
        entity.setUser(userRepository.getReferenceById(userId));
        return publicationMapper.toDTO(publicationRepository.save(entity));
    }

    public PublicationDTO updateModel(PublicationDTO baseModel){
        return publicationMapper.toDTO(publicationRepository.save(publicationMapper.toEntity(baseModel)));
    }
    public void deleteModel(long id){
        publicationRepository.deleteById(id);
    }
}
