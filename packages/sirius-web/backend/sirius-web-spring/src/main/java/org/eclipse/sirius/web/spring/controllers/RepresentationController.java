/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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

import java.io.ByteArrayInputStream;
import java.util.Objects;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;

import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ExportRepresentationInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ExportRepresentationPayload;
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
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The entry point of the HTTP API to export representations.
 * <p>
 * This endpoint will be available on the API base path prefix followed by the representation Id used as a suffix. As
 * such, users will be able to send representation export request to the following URL:
 * </p>
 *
 * <pre>
 * PROTOCOL://DOMAIN.TLD(:PORT)/API_BASE_PATH/editingcontexts/EDITING_CONTEXT_ID/representations/REPRESENTATION_ID
 * </pre>
 *
 * <p>
 * In a development environment, the URL will most likely be:
 * </p>
 *
 * <pre>
 * http://localhost:8080/api/editingcontexts/EDITING_CONTEXT_ID/representations/REPRESENTATION_ID
 * </pre>
 *
 * @author rpage
 */
@Controller
@RequestMapping(URLConstants.REPRESENTATION_BASE_PATH)
public class RepresentationController {
    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public RepresentationController(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @GetMapping(path = "/{representationId}")
    @ResponseBody
    public ResponseEntity<Resource> getRepresentation(HttpServletRequest request, @PathVariable String editingContextId, @PathVariable String representationId) {
        IDiagramInput input = new ExportRepresentationInput(UUID.randomUUID(), representationId);
        // @formatter:off
        return this.editingContextEventProcessorRegistry
                .dispatchEvent(editingContextId, input)
                .map(payload -> {
                    ResponseEntity<Resource> response;
                    if (payload instanceof ExportRepresentationPayload exportPayload) {
                        byte[] bytes = exportPayload.content().getBytes();
                        String name = exportPayload.name();
                        response = this.toResponseEntity(name, bytes);
                    } else {
                        response = new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
                    }
                    return response;
                })
                .block();
        // @formatter:on
    }

    private ResponseEntity<Resource> toResponseEntity(String name, byte[] bytes) {
        // @formatter:off
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment") 
                .filename(name)
                .build();
        // @formatter:on

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(contentDisposition);
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setContentLength(bytes.length);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(bytes));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
