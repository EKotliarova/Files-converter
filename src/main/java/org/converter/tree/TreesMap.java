package org.converter.tree;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TreesMap implements Iterable<Map.Entry<String, Tree>> {

    private final Map<String, Tree> itemIdsToTrees = new HashMap<>();

    public Tree getExistedTreeForItem(String itemId) {
        return itemIdsToTrees.get(itemId);
    }

    public void setTreeToItemId(String itemId, Tree tree) {
        itemIdsToTrees.put(itemId, tree);
    }

    public List<Tree> getTrees() {
        return itemIdsToTrees.values().stream().toList();
    }

    @Override
    public Iterator<Map.Entry<String, Tree>> iterator() {
        return itemIdsToTrees.entrySet().iterator();
    }
}
