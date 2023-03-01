package org.converter.tree.handle;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.converter.tree.TreesMap;
import org.converter.tree.Tree;
import org.converter.tree.handle.tags.TagsHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TreesMapHandler {

    private final List<TagsHandler> tagsHandlers;

    public void handle(TreesMap treesMap) {
        treesMap.forEach(entry -> handleTrees(entry.getKey(), entry.getValue()));
    }

    private void handleTrees(String itemId, List<Tree> trees) {
        tagsHandlers.forEach(tagsHandler -> tagsHandler.handle(itemId, trees));
        log.info("Item data processing with id {} finished", itemId);
    }
}