package com.example.demo.upload.storage;

import java.nio.file.Path;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	 void init();

	    String store(MultipartFile file, long id);

	    Stream<Path> loadAll();

	    Path load(String filename);

	    Resource loadAsResource(String filename);

	    void deleteAll();

}