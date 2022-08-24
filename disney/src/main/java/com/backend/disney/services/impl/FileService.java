package com.backend.disney.services.impl;

import com.backend.disney.services.IFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService implements IFileService {
    @Override
    public String saveFileInFolder(MultipartFile file) throws IOException {
        Path directorioImagenes = Paths.get("src//main/resources//static/images");
        String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();

        byte[] bytesImg = file.getBytes();
        Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + file.getOriginalFilename());
        Files.write(rutaCompleta, bytesImg);

        return file.getOriginalFilename();
    }
}
