package org.converter.tree.build;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.converter.tree.Tree;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Component
public class TreeReaderFromJSON implements TreeReaderFromFile {

    private static final List<String> SUPPORTED_FILE_EXTENSIONS = List.of("json");

    @Override
    public Tree readTreeFromFile(String filePath) {
        log.info("Reading file {}", filePath);
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(filePath))) {
            var mapper = new ObjectMapper();
            return new Tree(mapper.readTree(reader));
        } catch (IOException e) {
            log.error("Parsing of file {} failed", filePath, e);
            throw new RuntimeException(e);
        }
    }

    private ObjectNode mergeTrees(ObjectNode first, ObjectNode second, ObjectMapper objectMapper) {
        var mergedNode = first.deepCopy();
        mergedNode.setAll(second);
        Iterator<String> fieldNames = mergedNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();

            JsonNode valueOfFirst = first.get(fieldName);
            JsonNode valueOfSecond = second.get(fieldName);

            if (valueOfFirst != null && valueOfFirst.equals(valueOfSecond)) {
                ArrayNode arrayNode = objectMapper.createArrayNode();
                arrayNode.add(valueOfFirst);
                arrayNode.add(valueOfSecond);

                mergedNode.set(fieldName, arrayNode);
            }
        }
        return mergedNode;
    }

    @Override
    public boolean supportsFileExtension(String extension) {
        return SUPPORTED_FILE_EXTENSIONS.contains(extension);
    }
}
