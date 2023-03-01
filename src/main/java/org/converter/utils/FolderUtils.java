package org.converter.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

@Slf4j
@UtilityClass
public class FolderUtils {

    public void deleteFolder(File folder) throws IOException {
        if (folder.exists()) {
            FileUtils.deleteDirectory(folder);
        }
    }

    public void createFolder(File folder) throws IOException {
        if (!folder.exists() && !folder.mkdir()) {
            log.error("Failed to create folder {}", folder.getAbsolutePath());
            throw new IOException(String.format("Failed to create folder %s", folder.getAbsolutePath()));
        }
    }
}
