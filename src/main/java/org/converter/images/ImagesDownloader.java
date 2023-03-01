package org.converter.images;

import com.google.common.net.MediaType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.converter.properties.ImageDownloadingProperties;
import org.converter.utils.FolderUtils;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImagesDownloader {

    private static final String IMAGE_FORMAT = "image";
    private static final String DEFAULT_EXTENSION = "jpeg";
    private final ImageDownloadingProperties downloadingProperties;

    public CompletableFuture<Void> downloadImagesToFolder(
            String folderPath,
            Set<String> urls
    ) throws IOException {
        var folder = new File(folderPath);

        FolderUtils.deleteFolder(folder);
        FolderUtils.createFolder(folder);

        return CompletableFuture.allOf(
                urls
                        .stream()
                        .map(url -> CompletableFuture.runAsync(() -> downloadImageByUrl(url, folderPath)))
                        .toArray(CompletableFuture[]::new)
        );
    }

    private void downloadImageByUrl(String url, String folderPath) {
        var downloadResult = tryToDownloadImage(
                url, downloadingProperties.getDownloadTimeLimitInMillis()
        );

        if (downloadResult.isSuccessful()) {
            saveImage(downloadResult.getMediaType(), downloadResult.getImage(), folderPath, url);
        }
    }


    private DownloadResult tryToDownloadImage(String url, int downloadTimeLimitInMillis) {
        HttpURLConnection httpConnection = null;
        try {
            httpConnection = (HttpURLConnection) new URL(url).openConnection();
            httpConnection.setConnectTimeout(downloadTimeLimitInMillis);
            httpConnection.setReadTimeout(downloadTimeLimitInMillis);

            var contentType = httpConnection.getContentType();
            var mediaType = getMediaType(contentType);

            if (mediaType != null && !mediaType.type().equals(IMAGE_FORMAT)) {
                return DownloadResult
                        .builder()
                        .successful(false)
                        .build();
            }

            try (var inputStream = httpConnection.getInputStream()) {
                var image = ImageIO.read(inputStream);

                if (image == null) {
                    log.info("File is not an image {}", url);
                    return DownloadResult.builder().successful(false).build();
                }

                return DownloadResult
                        .builder()
                        .successful(true)
                        .mediaType(mediaType)
                        .image(image)
                        .build();
            }
        } catch (IOException e) {
            log.error("Image download failed: {}", url);
            return DownloadResult.builder().successful(false).build();
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
        }
    }

    private MediaType getMediaType(String contentType) {
        try {
            if (contentType != null) {
                return MediaType.parse(contentType);
            }
        } catch (IllegalArgumentException ignored) { }

        return null;
    }

    private void saveImage(MediaType mediaType, BufferedImage image, String folderPath, String url) {
        try {
            var extension = mediaType != null ? mediaType.subtype() : DEFAULT_EXTENSION;
            var filePath = new File(folderPath, UUID.randomUUID() + "." + extension).getAbsolutePath();
            ImageIO.write(image, extension, new File(filePath));
            log.info("Image saved: {}", url);
        } catch (IOException e) {
            log.error("Image saving failed: {}", url, e);
        }
    }

    @Builder
    @Getter
    private static class DownloadResult {
        private boolean successful;
        private MediaType mediaType;
        private BufferedImage image;
    }
}
