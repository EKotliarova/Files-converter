package org.converter.tree.search;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import org.converter.tree.Tree;
import org.converter.utils.Utils;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

@Component
public class TreeUrlsSearcher {

    public Set<String> search(Tree tree) {

        var urls = new HashSet<String>();
        var stack = new LinkedList<JsonNode>();
        stack.add(tree.getRoot());

        while (!stack.isEmpty()) {
            var current = stack.getFirst();
            stack.removeFirst();

            if (current.getNodeType() == JsonNodeType.STRING) {
                var value = current.asText();

                if (Utils.stringIsUrl(value)) {
                    urls.add(value);
                }
            }

            if (current.isContainerNode()) {
                for (JsonNode child : current) {
                        stack.add(child);
                }
            }
        }

        return urls;
    }
}
