package org.converter.tree.handle.tags;

import lombok.RequiredArgsConstructor;
import org.converter.tree.Tree;
import org.springframework.core.Ordered;

import java.util.List;

@RequiredArgsConstructor
public abstract class TagsHandler implements Ordered {
    public abstract void handle(String itemId, List<Tree> trees);

    @Override
    public int getOrder() {
        return TagsHandlerOrderManager.getOrder(this.getClass());
    }
}
