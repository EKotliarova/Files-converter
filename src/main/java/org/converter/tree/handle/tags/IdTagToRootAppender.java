package org.converter.tree.handle.tags;

import org.converter.properties.TreeHandlingProperties;
import org.converter.tree.Tree;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Adds an id with a tag specified in the configuration to the root.
 */

@Component
@ConditionalOnProperty(name="tree.handling.id-tag")
public class IdTagToRootAppender extends TagsHandler {

    private final String idTag;

    public IdTagToRootAppender(TreeHandlingProperties treeHandlingProperties) {

        idTag = treeHandlingProperties.getIdTag();
    }

    @Override
    public void handle(String itemId, List<Tree> trees) {
        trees.forEach(tree -> setIdToRoot(tree, itemId));
    }

    private void setIdToRoot(Tree tree, String id) {
        var root = tree.getRoot();

        if (root.findValue(idTag) != null) {
            var parentNode = root.findParent(idTag);
            parentNode.remove(idTag);
        }

        root.put(idTag, id);
    }
}
