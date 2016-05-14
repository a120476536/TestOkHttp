package com.forokhttp.request.packing;

import java.io.File;

import okhttp3.MediaType;

/**
 * Created by huangyaping on 16/4/29.
 */
public class FilePacking {
    /**
     * 文件包装处理
     */
    public File file;
    public String fileName;
    public MediaType mediaType;
    private long fileSize;

    public FilePacking(File file, MediaType mediaType) {
        this.file = file;
        this.fileName = file.getName();
        this.mediaType = mediaType;
        this.fileSize = file.length();
    }

    public String getFileName() {
        if (fileName != null) {
            return fileName;
        } else {
            return "nofilename";
        }
    }

    public File getFile() {
        return file;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public long getFileSize() {
        return fileSize;
    }
}
