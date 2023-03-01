package org.converter;

import lombok.RequiredArgsConstructor;
import org.converter.flow.FlowManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ConversionRunner implements CommandLineRunner {
    private final FlowManager processor;

    @Override
    public void run(String... args) throws IOException {
        processor
                .readAllFiles()
                .handleTrees()
                .writeResultToFile()
                .downloadImages();
    }
}
