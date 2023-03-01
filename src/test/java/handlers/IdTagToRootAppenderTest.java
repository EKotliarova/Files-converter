package handlers;

import org.converter.properties.TreeHandlingProperties;
import org.converter.tree.handle.tags.IdTagToRootAppender;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IdTagToRootAppenderTest extends HandlerTest {

    private static IdTagToRootAppender idTagToRootAppender;

    @BeforeAll
    public static void setUp() {
        var properties = TreeHandlingProperties
                .builder()
                .idTag("id")
                .build();
        idTagToRootAppender = new IdTagToRootAppender(properties);
    }

    @ParameterizedTest
    @MethodSource("provideFiles")
    public void shouldRemoveDuplicatedTag(FilePaths filePaths) {

        var resultTree = getTree(filePaths.getAfterPathFile());
        var treeToHandle = getTree(filePaths.getBeforePathFile());

        idTagToRootAppender.handle("id", List.of(treeToHandle));

        assertEquals(resultTree.getRoot(), treeToHandle.getRoot());
    }

    private static Stream<FilePaths> provideFiles() {
        return Stream.of(
                new FilePaths(
                        "src/test/resources/handlers/id.appender/file_without_root_id.json",
                        "src/test/resources/handlers/id.appender/file_with_root_id.json"
                ),
                new FilePaths(
                        "src/test/resources/handlers/id.appender/file_with_root_id.json",
                        "src/test/resources/handlers/id.appender/file_with_root_id.json"
                )
        );
    }
}
