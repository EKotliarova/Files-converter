package org.converter.tree.build;

import org.converter.tree.Tree;

import java.util.Optional;

public interface TreeBuilderFromFile {

    Tree getOrUpdateTreeFromFile(String filePath, Optional<Tree> tree);

    boolean supportsFileExtension(String extension);
}
