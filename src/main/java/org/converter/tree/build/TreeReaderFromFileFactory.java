package org.converter.tree.build;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TreeReaderFromFileFactory {

    private final List<TreeReaderFromFile> treeReadersFromFiles;

    public Optional<TreeReaderFromFile> getTreeReaderForFile(String fileName) {
        var extension = FilenameUtils.getExtension(fileName);
        var treeReader = treeReadersFromFiles
                .stream()
                .filter(builder -> builder.supportsFileExtension(extension))
                .findFirst();

        if (treeReader.isEmpty()) {
            log.error("{} file format is not supported", fileName);
        }

        return treeReader;
    }
}
