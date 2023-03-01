package org.converter.properties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@RequiredArgsConstructor
@ConfigurationProperties("files.output")
public class OutputFileProperties {

    @NotBlank
    private String outputFolderPath;

    @NotNull
    private String outputFileName;
}
