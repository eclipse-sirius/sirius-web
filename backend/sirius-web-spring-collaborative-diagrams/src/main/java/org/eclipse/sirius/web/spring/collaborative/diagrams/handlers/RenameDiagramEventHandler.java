/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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

import org.eclipse.sirius.web.collaborative.api.dto.RenameRepresentationInput;
import org.eclipse.sirius.web.collaborative.api.dto.RenameRepresentationSuccessPayload;
import org.eclipse.sirius.web.collaborative.api.services.ChangeDescription;
import org.eclipse.sirius.web.collaborative.api.services.ChangeKind;
import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.api.services.IEditingContextEventHandler;
import org.eclipse.sirius.web.collaborative.api.services.Monitoring;
import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IInput;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.services.api.representations.IRepresentationService;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;
import org.eclipse.sirius.web.spring.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * Handler used to rename a diagram.
 *
 * @author arichard
 */
@Service
public class RenameDiagramEventHandler implements IEditingContextEventHandler {

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
    public boolean canHandle(IInput input) {
        return input instanceof RenameRepresentationInput;
    }

    @Override
    public EventHandlerResponse handle(IEditingContext editingContext, IInput input) {
        this.counter.increment();

        if (input instanceof RenameRepresentationInput) {
            RenameRepresentationInput renameRepresentationInput = (RenameRepresentationInput) input;
            UUID representationId = renameRepresentationInput.getRepresentationId();
            String newLabel = renameRepresentationInput.getNewLabel();
            Optional<RepresentationDescriptor> optionalRepresentationDescriptor = this.representationService.getRepresentation(representationId);
            if (optionalRepresentationDescriptor.isPresent()) {
                RepresentationDescriptor representationDescriptor = optionalRepresentationDescriptor.get();
                IRepresentation representation = representationDescriptor.getRepresentation();
                Optional<IRepresentation> optionalRepresentation = this.createDiagramWithNewLabel(representation, newLabel, editingContext);
                if (optionalRepresentation.isPresent()) {
                    return new EventHandlerResponse(new ChangeDescription(ChangeKind.REPRESENTATION_RENAMING, renameRepresentationInput.getRepresentationId()),
                            new RenameRepresentationSuccessPayload(input.getId(), optionalRepresentation.get()));
                }
            }
        }
        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), RenameRepresentationInput.class.getSimpleName());
        return new EventHandlerResponse(new ChangeDescription(ChangeKind.NOTHING, editingContext.getId()), new ErrorPayload(input.getId(), message));
    }

    private Optional<IRepresentation> createDiagramWithNewLabel(IRepresentation representation, String newLabel, IEditingContext editingContext) {
        if (representation instanceof Diagram) {
            Diagram previousDiagram = (Diagram) representation;

            // @formatter:off
            Diagram renamedDiagram = Diagram.newDiagram(previousDiagram)
                    .label(newLabel)
                    .build();

            RepresentationDescriptor representationDescriptor = RepresentationDescriptor.newRepresentationDescriptor(renamedDiagram.getId())
                    .projectId(editingContext.getId())
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
