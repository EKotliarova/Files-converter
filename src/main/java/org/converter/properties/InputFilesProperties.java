package org.converter.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@RequiredArgsConstructor
@ConfigurationProperties("files.input")
public class InputFilesProperties {

    @NotBlank
    private String inputFolderPath;
}
