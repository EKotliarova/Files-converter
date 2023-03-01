package handlers;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.converter.properties.TreeHandlingProperties;
import org.converter.tree.build.TreeBuilderFromJSON;
import org.converter.tree.handle.tags.DuplicatesRemover;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DuplicatesRemoverTest extends HandlerTest {
    private static DuplicatesRemover duplicatesRemover;

    @BeforeAll
    public static void setUp() {
        var duplicates = new ArrayList<TreeHandlingProperties.Duplicates>();
        duplicates.add(new TreeHandlingProperties.Duplicates("tagToKeep", "tagToDelete"));
        var properties = TreeHandlingProperties
                .builder()
                .duplicates(duplicates)
                .build();
        duplicatesRemover = new DuplicatesRemover(properties);
    }


    @ParameterizedTest
    @MethodSource("provideFiles")
    public void shouldRemoveDuplicatedTag(FilePaths filePaths) {

        var resultTree = getTree(filePaths.getAfterPathFile());
        var treeToHandle = getTree(filePaths.getBeforePathFile());

        duplicatesRemover.handle(null, treeToHandle);

        assertEquals(resultTree.getRoot(), treeToHandle.getRoot());
    }

    private static Stream<FilePaths> provideFiles() {
        return Stream.of(
                new FilePaths(
                        "src/test/resources/handlers/duplicates.remover/file_with_duplicates.json",
                        "src/test/resources/handlers/duplicates.remover/file_without_duplicates.json"
                ),
                new FilePaths(
                        "src/test/resources/handlers/duplicates.remover/file_without_duplicates.json",
                        "src/test/resources/handlers/duplicates.remover/file_without_duplicates.json"
                ),
                new FilePaths(
                        "src/test/resources/handlers/empty_file.json",
                        "src/test/resources/handlers/empty_file.json"
                )
        );
    }
}
