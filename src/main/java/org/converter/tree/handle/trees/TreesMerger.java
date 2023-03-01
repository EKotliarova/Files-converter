package org.converter.tree.handle.trees;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.converter.tree.Tree;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

/**
 * Merges trees adding the same tags into an array
 */

@Component
public class TreesMerger {

    public Tree merge(List<Tree> trees) {
        var mergedTree = trees.get(0).getRoot().deepCopy();
        for (var index = 0; index < trees.size() - 1; index++) {
            mergedTree = merge(mergedTree, trees.get(index + 1).getRoot());
        }
        return new Tree(mergedTree);
    }

    public static ObjectNode merge(ObjectNode treeOne, ObjectNode treeTwo) {
        ObjectNode result = JsonNodeFactory.instance.objectNode();

        treeOne.fields().forEachRemaining(entry -> {
            var key = entry.getKey();
            var valueOne = entry.getValue();

            if (treeTwo.has(key)) {
                var valueTwo = treeTwo.get(key);

                var set = new HashSet<JsonNode>();
                if (valueOne.isArray()) {
                    valueOne.elements().forEachRemaining(set::add);
                } else {
                    set.add(valueOne);
                }

                if (valueTwo.isArray()) {
                    valueTwo.elements().forEachRemaining(set::add);
                } else {
                    set.add(valueTwo);
                }

                var array = JsonNodeFactory.instance.arrayNode();
                set.forEach(array::add);
                if (array.size() == 1) {
                    result.set(key, array.get(0));
                } else {
                    result.set(key, array);
                }
            } else {
                result.set(key, valueOne);
            }
        });

        treeTwo.fields().forEachRemaining(entry -> {
            var key = entry.getKey();
            var valueTwo = entry.getValue();

            if (!treeOne.has(key)) {
                result.set(key, valueTwo);
            }
        });

        return result;
    }
}
