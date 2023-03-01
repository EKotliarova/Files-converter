package org.converter.tree.handle.tags;

import org.converter.properties.TreeHandlingProperties;
import org.converter.tree.Tree;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Removes tags from the root specified in the configuration.
 */

@Component
@ConditionalOnProperty(name = "tree.handling.tags-to-remove-from-root")
public class TagsFromRootRemover extends TagsHandler {

    private final List<String> tagsToRemoveFromRoot;

    public TagsFromRootRemover(TreeHandlingProperties treeHandlingProperties) {

        this.tagsToRemoveFromRoot = treeHandlingProperties.getTagsToRemoveFromRoot();
    }

    @Override
    public void handle(String itemId, Tree tree) {
        for (var tag : tagsToRemoveFromRoot) {
            removeFromRoot(tree, tag);
        }
    }

    private void removeFromRoot(Tree tree, String tagToRemoveFromRoot) {
        var root = tree.getRoot();
        var nodeToRemove = root.get(tagToRemoveFromRoot);

        if (nodeToRemove == null) {
            return;
        }

        var fields = nodeToRemove.fields();
        while (fields.hasNext()) {
            var child = fields.next();
            root.set(child.getKey(), child.getValue());
        }

        root.remove(tagToRemoveFromRoot);
    }
}
