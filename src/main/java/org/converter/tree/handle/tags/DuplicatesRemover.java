package org.converter.tree.handle.tags;

import org.converter.properties.TreeHandlingProperties;
import org.converter.tree.Tree;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Removes tags with duplicate information specified in the configuration.
 */

@Component
@ConditionalOnProperty(name="tree.handling.duplicates")
public class DuplicatesRemover extends TagsHandler {

    private final List<TreeHandlingProperties.Duplicates> duplicates;

    public DuplicatesRemover(TreeHandlingProperties treeHandlingProperties) {

       duplicates = treeHandlingProperties.getDuplicates();
    }

    @Override
    public void handle(String itemId, Tree tree) {
        for (var tags : duplicates) {
            removeDuplicate(tree, tags.getTagToKeep(), tags.getTagToDelete());
        }
    }

    private void removeDuplicate(Tree tree, String tag, String duplicateTag) {
        var root = tree.getRoot();

        if (root.findValue(tag) == null || root.findValue(duplicateTag) == null) {
            return;
        }

        var parentNode = root.findParent(duplicateTag);
        parentNode.remove(duplicateTag);
    }
}
