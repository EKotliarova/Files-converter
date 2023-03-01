package handlers;

import org.converter.properties.TreeHandlingProperties;
import org.converter.tree.handle.tags.NewRootTagAppender;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewRootTagAppenderTest extends HandlerTest {
    private static NewRootTagAppender newRootTagAppender;

    @BeforeAll
    public static void setUp() {
        var properties = TreeHandlingProperties
                .builder()
                .newRootTag("root")
                .build();
        newRootTagAppender = new NewRootTagAppender(properties);
    }

    @ParameterizedTest
    @MethodSource("provideFiles")
    public void shouldRemoveDuplicatedTag(FilePaths filePaths) {

        var resultTree = getTree(filePaths.getAfterPathFile());
        var treeToHandle = getTree(filePaths.getBeforePathFile());

        newRootTagAppender.handle(null, treeToHandle);

        assertEquals(resultTree.getRoot(), treeToHandle.getRoot());
    }

    private static Stream<FilePaths> provideFiles() {
        return Stream.of(
                new FilePaths(
                        "src/test/resources/handlers/new.root.tag/file_with_old_root_tag.json",
                        "src/test/resources/handlers/new.root.tag/file_with_new_root_tag.json"
                ),
                new FilePaths(
                        "src/test/resources/handlers/new.root.tag/file_with_new_root_tag.json",
                        "src/test/resources/handlers/new.root.tag/file_with_new_root_tag.json"
                )
        );
    }
}
