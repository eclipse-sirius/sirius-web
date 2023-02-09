/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.web.spring.controllers;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.sirius.components.core.api.IImagePathService;
import org.eclipse.sirius.web.services.api.id.IDParser;
import org.eclipse.sirius.web.services.api.images.ICustomImageContentService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

/**
 * The entry point of the HTTP API to get images.
 * <p>
 * This endpoint will be available on the API base path prefix with image segment anf followed by the image path used as
 * a suffix. As such, users will be able to send image request to the following URL:
 * </p>
 *
 * <pre>
 * PROTOCOL://DOMAIN.TLD(:PORT)/API_BASE_PATH/images/(FOLDERS/)IMAGE_NAME
 * </pre>
 *
 * <p>
 * In a development environment, the URL will most likely be:
 * </p>
 *
 * <pre>
 * http://localhost:8080/api/images/IMAGE_FULL_PATH
 * </pre>
 *
 * <p>
 * Only images of type GIF, PNG, JPEG and SVG are supported.</br>
 * Only images contained in folders declared with {@link IImagePathService} will be searched.
 * </p>
 *
 * @author lfasani
 */
@Controller
@RequestMapping(URLConstants.IMAGE_BASE_PATH + "/**/*")
public class ImagesController {

    private static final String EXTENSION_SEPARATOR = ".";

    private static final String IMAGE_GIF_EXTENSION = "gif";

    private static final String IMAGE_JPG_EXTENSION = "jpg";

    private static final String IMAGE_JPEG_EXTENSION = "jpeg";

    private static final String IMAGE_PNG_EXTENSION = "png";

    private static final String IMAGE_SVG_EXTENSION = "svg";

    private static final String CUSTOM_IMAGE_PREFIX = "/custom/";

    private static final MediaType IMAGE_SVG = MediaType.valueOf("image/svg+xml");

    private static final String TIMER = "siriusweb_images";

    private final List<IImagePathService> pathResourcesServices;

    private final ICustomImageContentService customImageContentService;

    private final Timer timer;

    public ImagesController(List<IImagePathService> pathResourcesServices, ICustomImageContentService customImageContentService, MeterRegistry meterRegistry) {
        this.pathResourcesServices = Objects.requireNonNull(pathResourcesServices);
        this.customImageContentService = Objects.requireNonNull(customImageContentService);
        this.timer = Timer.builder(TIMER).register(meterRegistry);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<Resource> getImage(HttpServletRequest request) {
        ResponseEntity<Resource> response = new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);

        long start = System.currentTimeMillis();

        String requestURI = request.getRequestURI();
        String imagePath = URLDecoder.decode(requestURI.substring(URLConstants.IMAGE_BASE_PATH.length()), StandardCharsets.UTF_8);

        MediaType mediatype = this.getContentType(imagePath);
        if (mediatype != null) {
            if (this.isImagePathAccessible(imagePath)) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(mediatype);
                Resource resource = new ClassPathResource(imagePath);
                if (resource.exists()) {
                    response = new ResponseEntity<>(resource, headers, HttpStatus.OK);
                }
            }
        } else if (imagePath.startsWith(CUSTOM_IMAGE_PREFIX)) {
            response = this.getCustomImage(imagePath);
        }

        long end = System.currentTimeMillis();
        this.timer.record(end - start, TimeUnit.MILLISECONDS);

        return response;
    }

    private ResponseEntity<Resource> getCustomImage(String imagePath) {
        ResponseEntity<Resource> response = new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
        String[] imageDescriptor = imagePath.substring(CUSTOM_IMAGE_PREFIX.length()).split("/");
        Optional<UUID> optionalImageId = Optional.empty();
        if (imageDescriptor.length == 1) {
            optionalImageId = new IDParser().parse(imageDescriptor[0]);
        }
        Optional<String> mediaType = optionalImageId.flatMap(this.customImageContentService::getImageContentTypeById);
        Optional<byte[]> contents = optionalImageId.flatMap(this.customImageContentService::getImageContentById);
        if (mediaType.isPresent() && contents.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf(mediaType.get()));
            Resource resource = new ByteArrayResource(contents.get());
            response = new ResponseEntity<>(resource, headers, HttpStatus.OK);
        }
        return response;
    }

    private MediaType getContentType(String imagePath) {
        MediaType mediaType = null;
        int extensionSeparatorIndex = imagePath.lastIndexOf(EXTENSION_SEPARATOR);
        if (extensionSeparatorIndex != -1) {
            String extension = imagePath.substring(extensionSeparatorIndex + 1, imagePath.length());
            if (IMAGE_GIF_EXTENSION.equalsIgnoreCase(extension)) {
                mediaType = MediaType.IMAGE_GIF;
            } else if (IMAGE_PNG_EXTENSION.equalsIgnoreCase(extension)) {
                mediaType = MediaType.IMAGE_PNG;
            } else if (IMAGE_JPEG_EXTENSION.equalsIgnoreCase(extension) || IMAGE_JPG_EXTENSION.equalsIgnoreCase(extension)) {
                mediaType = MediaType.IMAGE_JPEG;
            } else if (IMAGE_SVG_EXTENSION.equalsIgnoreCase(extension)) {
                mediaType = IMAGE_SVG;
            }
        }
        return mediaType;
    }

    private boolean isImagePathAccessible(String imagePath) {
        // @formatter:off
        List<String> accessibleImagePaths = this.pathResourcesServices.stream()
                .map(IImagePathService::getPaths)
                .flatMap(Collection::stream)
                .toList();
        // @formatter:on

        return accessibleImagePaths.stream().anyMatch(imagePath::startsWith);
    }
}
