package org.converter.flow;

import lombok.RequiredArgsConstructor;
import org.converter.properties.ImageDownloadingProperties;
import org.converter.tree.TreesMap;
import org.converter.images.ImagesDownloader;
import org.converter.tree.build.TreesMapBuilder;
import org.converter.tree.handle.TreesMapHandler;
import org.converter.tree.search.TreeUrlsSearcher;
import org.converter.utils.FolderUtils;
import org.converter.writer.TreesToFileWriterFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class FlowManager {

    private final TreesMapBuilder treesMapBuilder;
    private final TreesMapHandler treesMapHandler;
    private final ImagesDownloader imagesDownloader;
    private final TreeUrlsSearcher treeUrlsSearcher;
    private final TreesToFileWriterFactory treesToFileWriterFactory;
    private final ImageDownloadingProperties imageDownloadingProperties;
    private TreesMap treesMap;

    public FlowManager readAllFiles() {
        treesMap = treesMapBuilder.buildTreesCollectionFromFiles();
        return this;
    }

    public FlowManager handleTrees() {
        treesMapHandler.handle(treesMap);
        return this;
    }

    public FlowManager writeResultToFile() throws IOException {
        var writer = treesToFileWriterFactory.getFileWriter();
        writer.writeToFile(treesMap.getTrees());
        return this;
    }

    public void downloadImages() throws IOException {
        var downloadTasks = new ArrayList<CompletableFuture<Void>>();

        FolderUtils.createFolder(new File(imageDownloadingProperties.getImagesOutputFolderPath()));

        for (var entry : treesMap) {
            var urls = treeUrlsSearcher.search(entry.getValue());
            var outputFolder = new File(imageDownloadingProperties.getImagesOutputFolderPath(), entry.getKey());

            FolderUtils.createFolder(outputFolder);

            downloadTasks.add(imagesDownloader.downloadImagesToFolder(outputFolder.getAbsolutePath(), urls));
        }

        downloadTasks.forEach(CompletableFuture::join);
    }
}