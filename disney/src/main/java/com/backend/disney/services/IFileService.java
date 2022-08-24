package com.backend.disney.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileService {

    String saveFileInFolder(MultipartFile file) throws IOException;

}
