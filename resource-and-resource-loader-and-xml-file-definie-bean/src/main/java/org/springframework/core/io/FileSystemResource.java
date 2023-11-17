package org.springframework.core.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileSystemResource implements  Resource{
   private final String path;
    private final File file;

    public FileSystemResource(String path, File file) {
        this.file = file;
        this.path = file.getPath();
    }

    public FileSystemResource(String path) {
        this(path, new File(path));
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }


    public String getPath() {
        return path;
    }
}
