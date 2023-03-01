package org.converter.tree.handle.tags;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.converter.properties.TreeHandlingProperties;
import org.converter.tree.Tree;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Adds a new root tag specified in the configuration.
 */

@Component
@ConditionalOnProperty(name="tree.handling.new-root-tag")
public class NewRootTagAppender extends TagsHandler {

    private final String newRootTag;

    public NewRootTagAppender(TreeHandlingProperties treeHandlingProperties) {

        newRootTag = treeHandlingProperties.getNewRootTag();
    }

    @Override
    public void handle(String itemId, Tree tree) {
        makeNewRoot(tree);
    }

    private void makeNewRoot(Tree tree) {
        var root = tree.getRoot();
        var newRootNode = root.has(newRootTag)
                ? (ObjectNode) root.get(newRootTag)
                : new ObjectNode(JsonNodeFactory.instance);

        var fields = root.fields();

        while (fields.hasNext()) {
            var child = fields.next();

            if (!Objects.equals(child.getKey(), newRootTag)) {
                newRootNode.set(child.getKey(), child.getValue());
            }
        }

        root.removeAll().set(newRootTag, newRootNode);
    }
}
