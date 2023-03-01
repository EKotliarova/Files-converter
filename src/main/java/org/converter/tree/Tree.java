package org.converter.tree;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;

@Getter
public class Tree {

    private final ObjectNode root;

    public Tree(JsonNode root) {
        this.root = (ObjectNode) root;
    }

    public Tree(ObjectNode root) {
        this.root = root;
    }
}
