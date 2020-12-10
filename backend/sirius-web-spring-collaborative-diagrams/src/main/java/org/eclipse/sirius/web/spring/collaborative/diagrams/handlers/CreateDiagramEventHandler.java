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

import org.eclipse.sirius.web.collaborative.api.dto.CreateRepresentationInput;
import org.eclipse.sirius.web.collaborative.api.dto.CreateRepresentationSuccessPayload;
import org.eclipse.sirius.web.collaborative.api.services.EventHandlerResponse;
import org.eclipse.sirius.web.collaborative.api.services.IProjectEventHandler;
import org.eclipse.sirius.web.collaborative.api.services.Monitoring;
import org.eclipse.sirius.web.collaborative.diagrams.api.IDiagramCreationService;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.services.api.Context;
import org.eclipse.sirius.web.services.api.dto.ErrorPayload;
import org.eclipse.sirius.web.services.api.dto.IProjectInput;
import org.eclipse.sirius.web.services.api.objects.IEditingContext;
import org.eclipse.sirius.web.services.api.objects.IObjectService;
import org.eclipse.sirius.web.services.api.representations.IRepresentationDescriptionService;
import org.eclipse.sirius.web.services.api.representations.IRepresentationService;
import org.eclipse.sirius.web.services.api.representations.RepresentationDescriptor;
import org.eclipse.sirius.web.spring.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.web.trees.Tree;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * Handler used to create a new diagram representation.
 *
 * @author sbegaudeau
 * @author hmarchadour
 */
@Service
public class CreateDiagramEventHandler implements IProjectEventHandler {

    private final IRepresentationDescriptionService representationDescriptionService;

    private final IRepresentationService representationService;

    private final IDiagramCreationService diagramCreationService;

    private final IObjectService objectService;

    private final ICollaborativeDiagramMessageService messageService;

    private final Counter counter;

    public CreateDiagramEventHandler(IRepresentationDescriptionService representationDescriptionService, IRepresentationService representationService, IDiagramCreationService diagramCreationService,
            IObjectService objectService, ICollaborativeDiagramMessageService messageService, MeterRegistry meterRegistry) {
        this.representationDescriptionService = Objects.requireNonNull(representationDescriptionService);
        this.representationService = Objects.requireNonNull(representationService);
        this.diagramCreationService = Objects.requireNonNull(diagramCreationService);
        this.objectService = Objects.requireNonNull(objectService);
        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IProjectInput projectInput) {
        if (projectInput instanceof CreateRepresentationInput) {
            CreateRepresentationInput input = (CreateRepresentationInput) projectInput;
            // @formatter:off
            return this.representationDescriptionService.findRepresentationDescriptionById(input.getRepresentationDescriptionId())
                    .filter(DiagramDescription.class::isInstance)
                    .isPresent();
            // @formatter:on
        }
        return false;
    }

    @Override
    public EventHandlerResponse handle(IEditingContext editingContext, IProjectInput projectInput, Context context) {
        this.counter.increment();

        if (projectInput instanceof CreateRepresentationInput) {
            CreateRepresentationInput input = (CreateRepresentationInput) projectInput;

            // @formatter:off
            Optional<DiagramDescription> optionalDiagramDescription = this.representationDescriptionService.findRepresentationDescriptionById(input.getRepresentationDescriptionId())
                    .filter(DiagramDescription.class::isInstance)
                    .map(DiagramDescription.class::cast);
            // @formatter:on
            Optional<Object> optionalObject = this.objectService.getObject(editingContext, input.getObjectId());

            if (optionalDiagramDescription.isPresent() && optionalObject.isPresent()) {
                DiagramDescription diagramDescription = optionalDiagramDescription.get();
                Object object = optionalObject.get();

                Diagram diagram = this.diagramCreationService.create(input.getRepresentationName(), object, diagramDescription, editingContext);

                // @formatter:off
                RepresentationDescriptor representationDescriptor = RepresentationDescriptor.newRepresentationDescriptor(diagram.getId())
                        .projectId(editingContext.getProjectId())
                        .descriptionId(diagram.getDescriptionId())
                        .targetObjectId(diagram.getTargetObjectId())
                        .label(diagram.getLabel())
                        .representation(diagram)
                        .build();
                // @formatter:on

                this.representationService.save(representationDescriptor);

                return new EventHandlerResponse(false, representation -> representation instanceof Tree, new CreateRepresentationSuccessPayload(diagram));
            }
        }

        String message = this.messageService.invalidInput(projectInput.getClass().getSimpleName(), CreateRepresentationInput.class.getSimpleName());
        return new EventHandlerResponse(false, representation -> false, new ErrorPayload(message));
    }

}
