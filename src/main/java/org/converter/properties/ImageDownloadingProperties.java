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
@ConfigurationProperties("downloading.image")
public class ImageDownloadingProperties {

    @NotNull
    private Integer downloadTimeLimitInMillis;
    @NotBlank
    private String imagesOutputFolderPath;
}
