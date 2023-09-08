/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDropNodeCompatibilityProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DropNodeCompatibilityEntry;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetDropNodeCompatibilitySuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetDropNodeCompatibiliyInput;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Event handler for the "get drop node compatibility" query.
 *
 * @author pcdavid
 */
@Service
public class GetDropNodeCompatibilityEventHandler implements IDiagramEventHandler {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final List<IDropNodeCompatibilityProvider> dropNodeCompatibilityProviders;

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    public GetDropNodeCompatibilityEventHandler(IRepresentationDescriptionSearchService representationDescriptionSearchService, List<IDropNodeCompatibilityProvider> dropNodeCompatibilityProviders,
            ICollaborativeMessageService messageService, MeterRegistry meterRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.dropNodeCompatibilityProviders = Objects.requireNonNull(dropNodeCompatibilityProviders);
        this.messageService = Objects.requireNonNull(messageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof GetDropNodeCompatibiliyInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), diagramInput);
        IPayload payload = null;

        if (diagramInput instanceof GetDropNodeCompatibiliyInput) {
            Diagram diagram = diagramContext.getDiagram();
            var diagramDescription = this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId())
                    .filter(DiagramDescription.class::isInstance)
                    .map(DiagramDescription.class::cast);

            if (diagramDescription.isPresent()) {
                List<DropNodeCompatibilityEntry> result = new ArrayList<>();

                this.dropNodeCompatibilityProviders.stream()
                        .filter(provider -> provider.canHandle(diagramDescription.get()))
                        .forEach(provider -> result.addAll(provider.getDropNodeCompatibility(diagram, editingContext)));

                payload = new GetDropNodeCompatibilitySuccessPayload(diagramInput.id(), result);
            }
        } else {
            String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), GetDropNodeCompatibiliyInput.class.getSimpleName());
            payload = new ErrorPayload(diagramInput.id(), message);
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
