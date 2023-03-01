package org.converter.tree.build;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.converter.tree.Tree;
import org.json.XML;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class TreeBuilderFromXML implements TreeBuilderFromFile {

    private static final List<String> SUPPORTED_FILE_EXTENSIONS = List.of("xml");

    @Override
    public Tree getOrUpdateTreeFromFile(String filePath, Optional<Tree> tree) {
        log.info("Reading file {}", filePath);
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(filePath))) {
            var json = XML.toJSONObject(reader).toString();
            var mapper = new ObjectMapper();
            return tree.isPresent()
                    ? new Tree(mapper.readerForUpdating(tree.get().getRoot()).readTree(json))
                    : new Tree(mapper.readTree(json));
        } catch (IOException e) {
            log.error("Parsing of file {} failed", filePath, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean supportsFileExtension(String extension) {
        return SUPPORTED_FILE_EXTENSIONS.contains(extension);
    }
}
