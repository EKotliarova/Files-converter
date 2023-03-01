package org.converter.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TreesMap implements Iterable<Map.Entry<String, List<Tree>>> {

    private final Map<String, List<Tree>> itemIdsToTrees = new HashMap<>();

    public void setTreeToItemId(String itemId, Tree tree) {
        var itemTrees = itemIdsToTrees.computeIfAbsent(itemId, k -> new ArrayList<>());
        itemTrees.add(tree);
    }

    @Override
    public Iterator<Map.Entry<String, List<Tree>>> iterator() {
        return itemIdsToTrees.entrySet().iterator();
    }
}
