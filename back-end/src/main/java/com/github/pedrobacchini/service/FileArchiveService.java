package com.github.pedrobacchini.service;

import java.io.IOException;
import java.io.InputStream;

public interface FileArchiveService {

    String saveFileLocal(InputStream inputStream, String fileName) throws IOException;
}
