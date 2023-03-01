package handlers;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.converter.tree.Tree;
import org.converter.tree.build.TreeBuilderFromJSON;

import java.io.File;
import java.util.Optional;

public class HandlerTest {

    protected final TreeBuilderFromJSON treeBuilderFromJSON = new TreeBuilderFromJSON();

    @Value
    @AllArgsConstructor
    protected static class FilePaths {
        String beforePathFile;
        String afterPathFile;
    }

    protected Tree getTree(String filePath) {
        return  treeBuilderFromJSON.getOrUpdateTreeFromFile(
                new File(filePath).getAbsolutePath(),
                Optional.empty()
        );
    }
}
