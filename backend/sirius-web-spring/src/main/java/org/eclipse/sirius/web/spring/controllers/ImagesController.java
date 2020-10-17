/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.sirius.web.api.services.IImagePathService;
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

    private static final String EXTENSION_SEPARATOR = "."; //$NON-NLS-1$

    private static final String IMAGE_GIF_EXTENSION = "gif"; //$NON-NLS-1$

    private static final String IMAGE_JPG_EXTENSION = "jpeg"; //$NON-NLS-1$

    private static final String IMAGE_PNG_EXTENSION = "png"; //$NON-NLS-1$

    private static final String IMAGE_SVG_EXTENSION = "svg"; //$NON-NLS-1$

    private static final MediaType IMAGE_SVG = MediaType.valueOf("image/svg+xml"); //$NON-NLS-1$

    private final List<IImagePathService> pathResourcesServices;

    public ImagesController(List<IImagePathService> pathResourcesServices) {
        this.pathResourcesServices = Objects.requireNonNull(pathResourcesServices);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<Resource> getImage(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String imagePath = requestURI.substring(URLConstants.IMAGE_BASE_PATH.length());

        MediaType mediatype = this.getContentType(imagePath);
        if (mediatype != null && this.isImagePathAccessible(imagePath)) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(mediatype);
            Resource resource = new ClassPathResource(imagePath);
            if (resource.exists()) {
                return new ResponseEntity<>(resource, headers, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    private MediaType getContentType(String imagePath) {
        MediaType mediaType = null;
        int extensionSeparatorPos = imagePath.lastIndexOf(EXTENSION_SEPARATOR);
        String extention = imagePath.substring(extensionSeparatorPos + 1, imagePath.length());
        if (IMAGE_GIF_EXTENSION.equals(extention)) {
            mediaType = MediaType.IMAGE_GIF;
        } else if (IMAGE_PNG_EXTENSION.equals(extention)) {
            mediaType = MediaType.IMAGE_PNG;
        } else if (IMAGE_JPG_EXTENSION.equals(extention)) {
            mediaType = MediaType.IMAGE_JPEG;
        } else if (IMAGE_SVG_EXTENSION.equals(extention)) {
            mediaType = IMAGE_SVG;
        }
        return mediaType;
    }

    private boolean isImagePathAccessible(String imagePath) {
        // @formatter:off
        List<String> accessibleImagePaths = this.pathResourcesServices.stream()
                .map(IImagePathService::getPaths)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        // @formatter:on

        // @formatter:off
        return accessibleImagePaths.stream()
                .filter(accessibleImagePath -> imagePath.startsWith(accessibleImagePath))
                .findFirst()
                .isPresent();
        // @formatter:on
    }
}
