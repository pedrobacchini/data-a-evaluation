package com.github.pedrobacchini.service.impl;

import com.github.pedrobacchini.service.FileArchiveService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class FileArchiveServiceImpl implements FileArchiveService {

    private static String TEMP_DIR = "/images/tmp/";

    public static String getBaseUrl() { return "http://localhost:8080" + TEMP_DIR; }

    public String saveFileLocal(InputStream inputStream, String fileName) throws IOException {
        String filePath = FileArchiveService.class.getClassLoader().getResource("static").getPath() + TEMP_DIR + fileName;
        File targetFile = new File(filePath);
        FileUtils.copyInputStreamToFile(inputStream, targetFile);
        return getBaseUrl() + targetFile.getName();
    }
}

