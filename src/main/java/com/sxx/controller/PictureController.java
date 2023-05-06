package com.sxx.controller;

import com.sxx.common.BusinessException;
import com.sxx.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/common")
public class PictureController {

    @Value("${picture.path}")
    private String path;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        log.info(file.getOriginalFilename());
        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        log.info(fileName);
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            file.transferTo(new File(path + fileName));
        } catch (IOException e) {
            throw new BusinessException("上传失败");
        }
        return R.success(fileName);
    }

    @GetMapping("/download")
    public void download(HttpServletResponse response, String name) {
        FileInputStream fileInputStream = null;
        ServletOutputStream outputStream = null;
        try {
            fileInputStream = new FileInputStream(new File(path + name));
            outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");

            int length = 0;
            byte[] bytes = new byte[1024];
            while ((length = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,length);
                outputStream.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException();
            }
            try {
                fileInputStream.close();
            } catch (IOException e) {
                throw new BusinessException("上传失败");
            }
        }
    }
}
