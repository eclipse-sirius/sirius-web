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

import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
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

import io.swagger.v3.oas.annotations.Operation;

/**
 * REST Controller for the Object Endpoint.
 *
 * @author arichard
 */
@RestController
@RequestMapping("/api/rest/projects/{projectId}/commits/{commitId}")
public class ObjectRestController {

    private static final int TIMEOUT = 20;

    private final IEditingContextDispatcher editingContextDispatcher;

    public ObjectRestController(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Operation(description = "Get all the elements in a given project at the given commit.")
    @GetMapping(path = "/elements")
    public ResponseEntity<List<Object>> getElements(@PathVariable UUID projectId, @PathVariable UUID commitId) {
        var payload = this.editingContextDispatcher.dispatchQuery(projectId.toString(), new GetElementsRestInput(UUID.randomUUID()))
                .block(Duration.ofSeconds(TIMEOUT));
        if (payload instanceof GetElementsRestSuccessPayload successPayload) {
            return new ResponseEntity<>(successPayload.elements(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(description = "Get element with the given id (elementId) in the given project at the given commit.")
    @GetMapping(path = "/elements/{elementId}")
    public ResponseEntity<Object> getElementById(@PathVariable UUID projectId, @PathVariable UUID commitId, @PathVariable UUID elementId) {
        var payload = this.editingContextDispatcher.dispatchQuery(projectId.toString(), new GetElementByIdRestInput(UUID.randomUUID(), elementId.toString()))
                .block(Duration.ofSeconds(TIMEOUT));
        if (payload instanceof GetElementByIdRestSuccessPayload successPayload) {
            return new ResponseEntity<>(successPayload.element(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(description = "Get relationships that are incoming, outgoing, or both relative to the given related element.")
    @GetMapping(path = "/elements/{relatedElementId}/relationships")
    public ResponseEntity<List<Object>> getRelationshipsByRelatedElement(@PathVariable UUID projectId, @PathVariable UUID commitId, @PathVariable UUID relatedElementId, Optional<Direction> direction) {
        Direction directionParam = direction.orElse(Direction.BOTH);

        var payload = this.editingContextDispatcher.dispatchQuery(projectId.toString(), new GetRelationshipsByRelatedElementRestInput(UUID.randomUUID(), relatedElementId.toString(), directionParam))
                .block(Duration.ofSeconds(TIMEOUT));
        if (payload instanceof GetRelationshipsByRelatedElementRestSuccessPayload successPayload) {
            return new ResponseEntity<>(successPayload.relationships(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(description = "Get all the root elements in the given project at the given commit.")
    @GetMapping(path = "/roots")
    public ResponseEntity<List<Object>> getRootElements(@PathVariable UUID projectId, @PathVariable UUID commitId) {
        var payload = this.editingContextDispatcher.dispatchQuery(projectId.toString(), new GetRootElementsRestInput(UUID.randomUUID()))
                .block(Duration.ofSeconds(TIMEOUT));
        if (payload instanceof GetRootElementsRestSuccessPayload successPayload) {
            return new ResponseEntity<>(successPayload.rootElements(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
