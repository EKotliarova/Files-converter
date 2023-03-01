package org.converter.writer;

import org.converter.tree.Tree;

import java.io.IOException;
import java.util.List;

public interface TreesToFileWriter {

    void writeToFile(List<Tree> trees) throws IOException;

    boolean supportsExtension(String extension);
}
