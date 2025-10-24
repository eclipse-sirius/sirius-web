/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.object.controllers;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.web.application.object.services.api.IObjectExporter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The entry point of the HTTP API to download objects.
 *
 * @author gdaniel
 */
@Controller
@RequestMapping("/api/editingcontexts/{editingContextId}/objects")
public class ObjectDownloadController {

    private final IEditingContextSearchService editingContextSearchService;

    private final IObjectSearchService objectSearchService;

    private final List<IObjectExporter> objectExporters;

    public ObjectDownloadController(IEditingContextSearchService editingContextSearchService, IObjectSearchService objectSearchService, List<IObjectExporter> objectExporters) {
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.objectExporters = Objects.requireNonNull(objectExporters);
    }

    @ResponseBody
    @GetMapping
    public ResponseEntity<Resource> downloadObjects(@PathVariable String editingContextId, @RequestParam String contentType, @RequestParam List<String> objectIds) {

        ResponseEntity<Resource> responseEntity = new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);

        Optional<IEditingContext> optionalEditingContext = this.editingContextSearchService.findById(editingContextId);

        if (optionalEditingContext.isPresent()) {
            List<Object> objects = objectIds.stream()
                    .map(objectId -> this.objectSearchService.getObject(optionalEditingContext.get(), objectId))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();

            Optional<IObjectExporter> optionalObjectExporter = this.objectExporters.stream()
                    .filter(objectExporter -> objectExporter.canHandle(objects, contentType))
                    .findFirst();

            if (optionalObjectExporter.isPresent()) {
                Optional<byte[]> optionalBytes = optionalObjectExporter.get().getBytes(objects, contentType);
                if (optionalBytes.isPresent()) {
                    ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                            .filename("objects.csv")
                            .build();

                    HttpHeaders responseHeaders = new HttpHeaders();
                    responseHeaders.setContentDisposition(contentDisposition);
                    responseHeaders.setContentType(MediaType.parseMediaType(contentType));
                    responseHeaders.setContentLength(optionalBytes.get().length);

                    InputStreamResource inputStreamResource = new InputStreamResource(new ByteArrayInputStream(optionalBytes.get()));
                    responseEntity = new ResponseEntity<>(inputStreamResource, responseHeaders, HttpStatus.OK);
                }
            }
        }
        return responseEntity;
    }

}
