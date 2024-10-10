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
package org.eclipse.sirius.web.application.object.controllers;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.web.application.object.dto.Direction;
import org.eclipse.sirius.web.application.object.dto.GetElementByIdRestInput;
import org.eclipse.sirius.web.application.object.dto.GetElementByIdRestSuccessPayload;
import org.eclipse.sirius.web.application.object.dto.GetElementsRestInput;
import org.eclipse.sirius.web.application.object.dto.GetElementsRestSuccessPayload;
import org.eclipse.sirius.web.application.object.dto.GetRelationshipsByRelatedElementRestInput;
import org.eclipse.sirius.web.application.object.dto.GetRelationshipsByRelatedElementRestSuccessPayload;
import org.eclipse.sirius.web.application.object.dto.GetRootElementsRestInput;
import org.eclipse.sirius.web.application.object.dto.GetRootElementsRestSuccessPayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for the Object Endpoint.
 *
 * @author arichard
 */
@RestController
@RequestMapping("/api/rest/projects/{projectId}/commits/{commitId}")
public class ObjectRestController {

    private static final int TIMEOUT = 20;

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public ObjectRestController(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @GetMapping(path = "/elements")
    public ResponseEntity<List<Object>> getElements(@PathVariable UUID projectId, @PathVariable UUID commitId) {
        ResponseEntity<List<Object>> responseEntity = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        var optionalEditingContextEventProcessor = this.editingContextEventProcessorRegistry.getOrCreateEditingContextEventProcessor(projectId.toString());
        if (optionalEditingContextEventProcessor.isPresent()) {
            var editingContextEventProcessor = optionalEditingContextEventProcessor.get();
            var payload = editingContextEventProcessor.handle(new GetElementsRestInput(UUID.randomUUID())).block(Duration.ofSeconds(TIMEOUT));
            if (payload instanceof GetElementsRestSuccessPayload successPayload) {
                responseEntity = new ResponseEntity<>(successPayload.elements(), HttpStatus.OK);
            }
        }
        return responseEntity;
    }

    @GetMapping(path = "/elements/{elementId}")
    public ResponseEntity<Object> getElementById(@PathVariable UUID projectId, @PathVariable UUID commitId, @PathVariable UUID elementId) {
        ResponseEntity<Object> responseEntity = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        var optionalEditingContextEventProcessor = this.editingContextEventProcessorRegistry.getOrCreateEditingContextEventProcessor(projectId.toString());
        if (optionalEditingContextEventProcessor.isPresent()) {
            var editingContextEventProcessor = optionalEditingContextEventProcessor.get();
            var payload = editingContextEventProcessor.handle(new GetElementByIdRestInput(UUID.randomUUID(), elementId.toString())).block(Duration.ofSeconds(TIMEOUT));
            if (payload instanceof GetElementByIdRestSuccessPayload successPayload) {
                responseEntity = new ResponseEntity<>(successPayload.element(), HttpStatus.OK);
            }
        }
        return responseEntity;
    }

    @GetMapping(path = "/elements/{relatedElementId}/relationships")
    public ResponseEntity<List<Object>> getRelationshipsByRelatedElement(@PathVariable UUID projectId, @PathVariable UUID commitId, @PathVariable UUID relatedElementId, Optional<Direction> direction) {
        ResponseEntity<List<Object>> responseEntity = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        var optionalEditingContextEventProcessor = this.editingContextEventProcessorRegistry.getOrCreateEditingContextEventProcessor(projectId.toString());
        if (optionalEditingContextEventProcessor.isPresent()) {
            var editingContextEventProcessor = optionalEditingContextEventProcessor.get();
            Direction directionParam;
            if (direction.isPresent()) {
                directionParam = direction.get();
            } else {
                directionParam = Direction.BOTH;
            }
            var payload = editingContextEventProcessor.handle(new GetRelationshipsByRelatedElementRestInput(UUID.randomUUID(), relatedElementId.toString(), directionParam)).block(Duration.ofSeconds(TIMEOUT));
            if (payload instanceof GetRelationshipsByRelatedElementRestSuccessPayload successPayload) {
                responseEntity = new ResponseEntity<>(successPayload.relationships(), HttpStatus.OK);
            }
        }
        return responseEntity;
    }

    @GetMapping(path = "/roots")
    public ResponseEntity<List<Object>> getRootElements(@PathVariable UUID projectId, @PathVariable UUID commitId) {
        ResponseEntity<List<Object>> responseEntity = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        var optionalEditingContextEventProcessor = this.editingContextEventProcessorRegistry.getOrCreateEditingContextEventProcessor(projectId.toString());
        if (optionalEditingContextEventProcessor.isPresent()) {
            var editingContextEventProcessor = optionalEditingContextEventProcessor.get();
            var payload = editingContextEventProcessor.handle(new GetRootElementsRestInput(UUID.randomUUID())).block(Duration.ofSeconds(TIMEOUT));
            if (payload instanceof GetRootElementsRestSuccessPayload successPayload) {
                responseEntity = new ResponseEntity<>(successPayload.rootElements(), HttpStatus.OK);
            }
        }
        return responseEntity;
    }
}
