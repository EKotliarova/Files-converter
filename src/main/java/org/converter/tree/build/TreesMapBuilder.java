package org.converter.tree.build;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.converter.tree.TreesMap;
import org.converter.properties.InputFilesProperties;
import org.converter.utils.Utils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TreesMapBuilder {

    private final InputFilesProperties inputFilesProperties;
    private final TreeReaderFromFileFactory treeReaderFromFileFactory;

    public TreesMap buildTreesCollectionFromFiles() {
        var folderPath = new File(inputFilesProperties.getInputFolderPath()).getAbsolutePath();
        var treesMap = new TreesMap();
        Optional.ofNullable(new File(folderPath).listFiles())
                .ifPresentOrElse(
                        files -> Arrays.stream(files).forEach(
                                file -> updateTreesMapFromFile(treesMap, file)
                        ),
                        () -> {
                            log.error("Incorrect path {}", folderPath);
                            throw new InvalidPathException(folderPath, "Incorrect path");
                        }
                );
        return treesMap;
    }

    private void updateTreesMapFromFile(TreesMap treesMap, File file) {
        var treeReader = treeReaderFromFileFactory.getTreeReaderForFile(file.getName());
        treeReader.ifPresent(builder -> {
            var itemId = Utils.getIdFromFilename(file.getName());

            if (itemId.isEmpty()) {
                log.error(
                        "Name of file {} does not contain id. Please rename file: {id}-{...}.ext",
                        file.getName()
                );
                return;
            }

            var tree = builder.readTreeFromFile(file.getAbsolutePath());
            treesMap.setTreeToItemId(itemId.get(), tree);
        });
    }
}
