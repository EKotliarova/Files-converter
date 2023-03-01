package org.converter.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.converter.tree.Tree;
import org.converter.properties.OutputFileProperties;
import org.converter.utils.FolderUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TreesToJSONWriter implements TreesToFileWriter {

    private final OutputFileProperties outputFileProperties;

    private final static String SUPPORTED_EXTENSION = "json";

    public void writeToFile(List<Tree> trees) throws IOException {
        var result = new ObjectMapper().createArrayNode();
        trees.forEach(tree -> result.add(tree.getRoot()));

        createOutputFolder();

        try (
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter(
                                new File(
                                        outputFileProperties.getOutputFolderPath(),
                                        outputFileProperties.getOutputFileName()
                                )
                                        .getAbsolutePath(),
                                false
                        )
                )
        ) {
            writer.write(result.toPrettyString());
        } catch (IOException e) {
            log.error("File write error", e);
            throw e;
        }
    }

    private void createOutputFolder() throws IOException {
        var outputFolderPath = outputFileProperties.getOutputFolderPath();
        var folder = new File(outputFolderPath);
        FolderUtils.createFolder(folder);
    }

    @Override
    public boolean supportsExtension(String extension) {
        return extension.equals(SUPPORTED_EXTENSION);
    }
}
