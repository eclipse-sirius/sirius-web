/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.application.images.controllers;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.core.api.IImagePathService;
import org.eclipse.sirius.components.graphql.api.URLConstants;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.images.services.api.IImageApplicationService;
import org.eclipse.sirius.web.application.images.services.api.IProjectImageApplicationService;
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

import jakarta.servlet.http.HttpServletRequest;

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

    private static final MediaType IMAGE_SVG = MediaType.valueOf("image/svg+xml");

    private final List<IImagePathService> pathResourcesServices;

    private final IProjectImageApplicationService projectImageApplicationService;

    private final IImageApplicationService imageApplicationService;

    public ImagesController(List<IImagePathService> pathResourcesServices, IProjectImageApplicationService projectImageApplicationService, IImageApplicationService imageApplicationService) {
        this.pathResourcesServices = pathResourcesServices;
        this.projectImageApplicationService = Objects.requireNonNull(projectImageApplicationService);
        this.imageApplicationService = Objects.requireNonNull(imageApplicationService);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<Resource> getImage(HttpServletRequest request) {
        ResponseEntity<Resource> response = new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);

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
        }
        if (response.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            response = this.getImage(imagePath);
        }

        return response;
    }

    private ResponseEntity<Resource> getImage(String imagePath) {
        var imageId = imagePath.substring("/".length());
        ResponseEntity<Resource> response = new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);

        var optionalImageId = new UUIDParser().parse(imageId);
        if (optionalImageId.isPresent()) {
            var id = optionalImageId.get();
            var optionalProjectImage = this.projectImageApplicationService.findById(id);
            var optionalImage = this.imageApplicationService.findById(id);
            if (optionalProjectImage.isPresent()) {
                var image = optionalProjectImage.get();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.valueOf(image.getContentType()));
                Resource resource = new ByteArrayResource(image.getContent());
                response = new ResponseEntity<>(resource, headers, HttpStatus.OK);
            } else if (optionalImage.isPresent()) {
                var image = optionalImage.get();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.valueOf(image.getContentType()));
                Resource resource = new ByteArrayResource(image.getContent());
                response = new ResponseEntity<>(resource, headers, HttpStatus.OK);
            }
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
        return this.pathResourcesServices.stream()
                .map(IImagePathService::getPaths)
                .flatMap(Collection::stream)
                .anyMatch(imagePath::startsWith);
    }
}
