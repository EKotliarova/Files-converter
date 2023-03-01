package org.converter.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.validator.routines.UrlValidator;

import java.util.Optional;

@UtilityClass
public class Utils {
    public Optional<String> getIdFromFilename(String filename) {
        var parts = filename.split("-");
        if (parts.length == 1) {
            return Optional.empty();
        }
        return Optional.of(parts[0]);
    }

    public boolean stringIsUrl(String string) {
        return new UrlValidator().isValid(string);
    }
}
