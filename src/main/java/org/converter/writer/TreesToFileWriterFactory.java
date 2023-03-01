package org.converter.writer;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.converter.properties.OutputFileProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TreesToFileWriterFactory {

    private final OutputFileProperties outputFileProperties;
    private final List<TreesToFileWriter> treesToFileWriters;

    public TreesToFileWriter getFileWriter() {
        var fileName = outputFileProperties.getOutputFileName();
        var extension = FilenameUtils.getExtension(fileName);

        for (var fileWriter : treesToFileWriters) {
            if (fileWriter.supportsExtension(extension)) {
                return fileWriter;
            }
        }

        throw new IllegalArgumentException(String.format("Output file extension %s is not supported", extension));
    }
}
