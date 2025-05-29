package com.example.TTS_web.backend.controller;

import com.example.TTS_web.backend.dto.UserModelDTO;
import com.example.TTS_web.backend.entity.BaseModel;
import com.example.TTS_web.backend.entity.User;
import com.example.TTS_web.backend.service.BaseModelService;
import com.example.TTS_web.backend.service.TokenService;
import com.example.TTS_web.backend.service.UserDetailsServiceImpl;
import com.example.TTS_web.backend.service.UserModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/user_models")
public class UserModelController {
    private final UserModelService userModelService;
    private final TokenService tokenService;
    private final UserDetailsServiceImpl userDetailsService;
    private final BaseModelService baseModelService;

    @Value("${loss.storage-path}")
    private String lossPath;
    @Value("${accur.storage-path}")
    private String accuracyPath;
    @Value("${model.storage-path}")
    private String modelPath;

    @GetMapping
    public ResponseEntity<List<UserModelDTO>> getUserModels() {
        return ResponseEntity.ok(userModelService.getAll());
    }

    @GetMapping("/token")
    public ResponseEntity<Map<String, String>> generateToken(@RequestParam(name = "id") Long userId, @RequestParam(name = "base") String baseName) {
        Map<String, String> response = new HashMap<>();
        response.put("token",  tokenService.generateToken(48, userId, baseName));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/new_model")
    public ResponseEntity<String> addUserModel(
            @RequestParam("token") String token,
            @RequestParam("model") MultipartFile modelFile,
            @RequestParam("lossPlot") MultipartFile lossPlotFile,
            @RequestParam("accuracyPlot") MultipartFile accuracyPlotFile,
            @RequestParam("stoi") String stoi,
            @RequestParam("pesq") String pesq,
            @RequestParam("cd") String cd
    ){
        try {
            if (!tokenService.validateToken(token)) {
                tokenService.markAsUsed(token);
                return ResponseEntity.badRequest().body("Invalid token");
            }

            tokenService.markAsUsed(token);

            Map<String, Object> data = tokenService.getDataFromToken(token);
            Long userId = (Long) data.get("userId");
            String baseModel = (String) data.get("baseModel");

            // Генерируем уникальные имена файлов
            String modelFileName = userId + baseModel + ".pt";
            String lossPlotFileName = userId + baseModel +  ".png";
            String accuracyPlotFileName = userId + baseModel +  ".png";

            Path modelFilePath = Paths.get(modelPath, modelFileName);
            Path lossPlotFilePath = Paths.get(lossPath, lossPlotFileName);
            Path accuracyPlotFilePath = Paths.get(accuracyPath, accuracyPlotFileName);

            Files.createDirectories(modelFilePath.getParent());
            Files.createDirectories(lossPlotFilePath.getParent());
            Files.createDirectories(accuracyPlotFilePath.getParent());

            // Сохраняем файлы
            saveFile(modelFile, modelFilePath);
            saveFile(lossPlotFile, lossPlotFilePath);
            saveFile(accuracyPlotFile, accuracyPlotFilePath);

            UserModelDTO modelDTO = new UserModelDTO();
            User user = userDetailsService.loadUserById(userId);
            modelDTO.setName(user.getUsername());
            modelDTO.setPathToLossPlot(lossPlotFilePath.toString());
            modelDTO.setPathToAccuracyPlot(accuracyPlotFilePath.toString());
            modelDTO.setPathToModel(modelFilePath.toString());
            modelDTO.setPublished(false);
            modelDTO.setStoi(stoi);
            modelDTO.setPesq(pesq);
            modelDTO.setCd(cd);
            BaseModel model = baseModelService.getByName(baseModel);
            modelDTO.setBaseName(model.getName());

            userModelService.createModel(modelDTO, user, model);

            return ResponseEntity.ok("Модель и метрики успешно сохранены.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ошибка при обработке загрузки.");
        }
    }

    private void saveFile(MultipartFile file, Path path) throws Exception {
        try (InputStream is = file.getInputStream()) {
            Files.copy(is, path, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModelDTO> getUserModelById(@PathVariable Long id) {
        return ResponseEntity.ok(userModelService.getById(id));
    }

    @GetMapping(params = "userId")
    public ResponseEntity<List<UserModelDTO>> getModelByUserId(@RequestParam(name = "userId") Long userId) {
        return ResponseEntity.ok(userModelService.getByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<UserModelDTO> createUserModel(@RequestBody UserModelDTO userModelDTO) {
        return ResponseEntity.ok(userModelService.createModel(userModelDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserModelDTO> updateUserModel(@PathVariable Long id, @RequestBody UserModelDTO userModelDTO) {
        userModelDTO.setId(id);
        return ResponseEntity.ok(userModelService.updateModel(userModelDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserModel(@PathVariable Long id) {
        userModelService.deleteModel(id);
        return ResponseEntity.ok("Deleted UserModel " + id);
    }
}
