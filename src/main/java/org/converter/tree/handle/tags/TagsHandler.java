package org.converter.tree.handle.tags;

import lombok.RequiredArgsConstructor;
import org.converter.tree.Tree;
import org.springframework.core.Ordered;

@RequiredArgsConstructor
public abstract class TagsHandler implements Ordered {
    public abstract void handle(String itemId, Tree tree);

    @Override
    public int getOrder() {
        return TagsHandlerOrderManager.getOrder(this.getClass());
    }
}
