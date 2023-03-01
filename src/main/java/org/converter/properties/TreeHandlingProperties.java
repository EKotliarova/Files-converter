package org.converter.properties;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@ConfigurationProperties("tree.handling")
public class TreeHandlingProperties {

    final List<Duplicates> duplicates;
    final List<String> tagsToRemoveFromRoot;
    final String newRootTag;
    final String idTag;

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class Duplicates {
        final String tagToKeep;
        final String tagToDelete;
    }
}
