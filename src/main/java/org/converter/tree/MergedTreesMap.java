package org.converter.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MergedTreesMap {

    private final Map<String, Tree> itemIdsToTrees = new HashMap<>();

    public void setTreeToItemId(String itemId, Tree tree) {
        itemIdsToTrees.put(itemId, tree);
    }

    public List<Tree> getTrees() {
        return itemIdsToTrees.values().stream().toList();
    }
}
