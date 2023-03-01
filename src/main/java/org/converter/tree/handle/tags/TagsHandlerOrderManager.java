package org.converter.tree.handle.tags;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TagsHandlerOrderManager {

    private static final List<Class> HANDLERS_ORDER = List.of(
            TagsFromRootRemover.class,
            DuplicatesRemover.class,
            NewRootTagAppender.class,
            IdTagToRootAppender.class
    );

    private static final Map<Class, Integer> ORDER_MAP = IntStream.range(0, HANDLERS_ORDER.size())
            .boxed()
            .collect(Collectors.toMap(HANDLERS_ORDER::get, Function.identity()));

    public static int getOrder(Class handlerClass) {
        var index = ORDER_MAP.get(handlerClass);

        if (index == null) {
            throw new RuntimeException(
                    String.format("Please, add new handler %s to the handlers sequence!", handlerClass.getName())
            );
        }

        return index;
    }

}
