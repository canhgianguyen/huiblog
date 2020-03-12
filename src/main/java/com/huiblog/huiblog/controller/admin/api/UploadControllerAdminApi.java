package com.huiblog.huiblog.controller.admin.api;

import com.huiblog.huiblog.model.api.BaseApiResult;
import com.huiblog.huiblog.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class UploadControllerAdminApi {

    @Autowired
    FileStorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        String message = "";
        BaseApiResult result = new BaseApiResult();
        try {
            String newFilename = storageService.store(file);
            message = "You successfully uploaded " +
                    file.getOriginalFilename() + "!";
            result.setMessage(message);
            result.setSuccess(true);
            result.setData("/files/" +
                    newFilename);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }
}
