package org.converter.tree.build;

import org.converter.tree.Tree;

public interface TreeReaderFromFile {

    Tree readTreeFromFile(String filePath);

    boolean supportsFileExtension(String extension);
}
