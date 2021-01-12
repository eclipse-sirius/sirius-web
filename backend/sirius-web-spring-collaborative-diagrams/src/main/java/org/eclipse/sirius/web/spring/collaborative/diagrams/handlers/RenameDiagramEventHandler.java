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
package org.eclipse.sirius.web.spring.collaborative.diagrams.handlers;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.collaborative.api.dto.RenameRepresentationSuccessPayload;
import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.api.services.IProjectEventHandler;
import org.eclipse.sirius.web.collaborative.api.services.Monitoring;
import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.services.api.Context;
import org.eclipse.sirius.web.services.api.projects.IProjectInput;
import org.eclipse.sirius.web.services.api.representations.IRepresentationService;
import org.eclipse.sirius.web.services.api.representations.RenameRepresentationInput;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;
import org.eclipse.sirius.web.spring.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.web.trees.Tree;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * Handler used to rename a diagram.
 *
 * @author arichard
 */
@Service
public class RenameDiagramEventHandler implements IProjectEventHandler {

    private final IRepresentationService representationService;

    private final ICollaborativeDiagramMessageService messageService;

    private final Counter counter;

    public RenameDiagramEventHandler(IRepresentationService representationService, ICollaborativeDiagramMessageService messageService, MeterRegistry meterRegistry) {
        this.representationService = Objects.requireNonNull(representationService);
        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IProjectInput projectInput) {
        return projectInput instanceof RenameRepresentationInput;
    }

    @Override
    public EventHandlerResponse handle(IEditingContext editingContext, IProjectInput projectInput, Context context) {
        this.counter.increment();

        if (projectInput instanceof RenameRepresentationInput) {
            RenameRepresentationInput input = (RenameRepresentationInput) projectInput;
            UUID representationId = input.getRepresentationId();
            String newLabel = input.getNewLabel();
            Optional<RepresentationDescriptor> optionalRepresentationDescriptor = this.representationService.getRepresentation(representationId);
            if (optionalRepresentationDescriptor.isPresent()) {
                RepresentationDescriptor representationDescriptor = optionalRepresentationDescriptor.get();
                IRepresentation representation = representationDescriptor.getRepresentation();
                Optional<IRepresentation> optionalRepresentation = this.createDiagramWithNewLabel(representation, newLabel, editingContext, context);
                if (optionalRepresentation.isPresent()) {
                    return new EventHandlerResponse(true, r -> r instanceof Tree, new RenameRepresentationSuccessPayload(optionalRepresentation.get()));
                }
            }
        }
        String message = this.messageService.invalidInput(projectInput.getClass().getSimpleName(), RenameRepresentationInput.class.getSimpleName());
        return new EventHandlerResponse(false, representation -> false, new ErrorPayload(message));
    }

    private Optional<IRepresentation> createDiagramWithNewLabel(IRepresentation representation, String newLabel, IEditingContext editingContext, Context context) {
        if (representation instanceof Diagram) {
            Diagram previousDiagram = (Diagram) representation;

            // @formatter:off
            Diagram renamedDiagram = Diagram.newDiagram(previousDiagram)
                    .label(newLabel)
                    .build();

            RepresentationDescriptor representationDescriptor = RepresentationDescriptor.newRepresentationDescriptor(renamedDiagram.getId())
                    .projectId(editingContext.getProjectId())
                    .descriptionId(renamedDiagram.getDescriptionId())
                    .targetObjectId(renamedDiagram.getTargetObjectId())
                    .label(renamedDiagram.getLabel())
                    .representation(renamedDiagram)
                    .build();
            // @formatter:on

            this.representationService.save(representationDescriptor);
            return Optional.of(renamedDiagram);
        }
        return Optional.empty();
    }
}
