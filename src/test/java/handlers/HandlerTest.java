package handlers;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.converter.tree.Tree;
import org.converter.tree.build.TreeReaderFromJSON;

import java.io.File;

public class HandlerTest {

    protected final TreeReaderFromJSON treeReaderFromJSON = new TreeReaderFromJSON();

    @Value
    @AllArgsConstructor
    protected static class FilePaths {
        String beforePathFile;
        String afterPathFile;
    }

    protected Tree getTree(String filePath) {
        return  treeReaderFromJSON.readTreeFromFile(
                new File(filePath).getAbsolutePath()
        );
    }
}
