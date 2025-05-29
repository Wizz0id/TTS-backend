package com.example.TTS_web.backend.service;

import com.example.TTS_web.backend.dto.UserModelDTO;
import com.example.TTS_web.backend.entity.BaseModel;
import com.example.TTS_web.backend.entity.User;
import com.example.TTS_web.backend.entity.UserModel;
import com.example.TTS_web.backend.mapper.UserModelMapper;
import com.example.TTS_web.backend.repository.UserModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserModelService {
    private final UserModelRepository userModelRepository;
    private final UserModelMapper userModelMapper;

    public List<UserModelDTO> getAll(){
        return userModelRepository.findAll().stream().map(userModelMapper::toDTO).toList();
    }

    public UserModelDTO getById(long id){
        return userModelMapper.toDTO(userModelRepository.findById(id).orElse(new UserModel()));
    }

    public List<UserModelDTO> getByUserId(long userId){
        return userModelRepository.findByUserId(userId).stream().map(userModelMapper::toDTO).toList();
    }

    public UserModelDTO createModel(UserModelDTO userModelDTO){
        return userModelMapper.toDTO(userModelRepository.save(userModelMapper.toEntity(userModelDTO)));
    }
    public void createModel(UserModelDTO userModelDTO, User user, BaseModel baseModel){
        UserModel userModel = userModelMapper.toEntity(userModelDTO);
        userModel.setStoi(userModel.getStoi());
        userModel.setPathToModel(userModelDTO.getPathToModel().replace("uploads\\", ""));
        userModel.setPathToLossPlot(userModelDTO.getPathToLossPlot().replace("uploads\\", ""));
        userModel.setPathToAccuracyPlot(userModelDTO.getPathToAccuracyPlot().replace("uploads\\", ""));
        userModel.setUser(user);
        userModel.setBasic(baseModel);
        userModelRepository.save(userModel);
    }

    public UserModelDTO updateModel(UserModelDTO userModelDTO){
        return userModelMapper.toDTO(userModelRepository.save(userModelMapper.toEntity(userModelDTO)));
    }

    public void deleteModel(long id){
        userModelRepository.deleteById(id);
    }
}
