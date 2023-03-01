package handlers;

import org.converter.properties.TreeHandlingProperties;
import org.converter.tree.handle.tags.TagsFromRootRemover;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagsFromRootRemoverTest extends HandlerTest {

    private static TagsFromRootRemover tagsFromRootRemover;

    @BeforeAll
    public static void setUp() {
        var tagsToRemoveFromRoot = List.of("extra_tag_one", "extra_tag_two");
         var properties = TreeHandlingProperties
                .builder()
                .tagsToRemoveFromRoot(tagsToRemoveFromRoot)
                .build();
        tagsFromRootRemover = new TagsFromRootRemover(properties);
    }

    @ParameterizedTest
    @MethodSource("provideFiles")
    public void shouldRemoveDuplicatedTag(FilePaths filePaths) {

        var resultTree = getTree(filePaths.getAfterPathFile());
        var treeToHandle = getTree(filePaths.getBeforePathFile());

       tagsFromRootRemover.handle(null, List.of(treeToHandle));

        assertEquals(resultTree.getRoot(), treeToHandle.getRoot());
    }

    private static Stream<FilePaths> provideFiles() {
        return Stream.of(
                new FilePaths(
                        "src/test/resources/handlers/root.tags.remover/file_with_extra_root_tags.json",
                        "src/test/resources/handlers/root.tags.remover/file_without_extra_root_tags.json"
                ),
                new FilePaths(
                        "src/test/resources/handlers/root.tags.remover/file_without_extra_root_tags.json",
                        "src/test/resources/handlers/root.tags.remover/file_without_extra_root_tags.json"
                )
        );
    }
}
