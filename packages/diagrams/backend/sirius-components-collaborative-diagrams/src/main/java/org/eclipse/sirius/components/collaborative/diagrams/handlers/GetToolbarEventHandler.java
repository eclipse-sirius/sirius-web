/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IToolbarProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramToolbar;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetToolbarInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetToolbarSuccessPayload;
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
 * Handler used to get the diagram toolbar.
 *
 * @author tgiraudet
 */
@Service
public class GetToolbarEventHandler implements IDiagramEventHandler {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IToolbarProvider toolbarProvider;

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    public GetToolbarEventHandler(IRepresentationDescriptionSearchService representationDescriptionSearchService, IToolbarProvider toolbarProvider, ICollaborativeMessageService messageService, MeterRegistry meterRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.toolbarProvider = Objects.requireNonNull(toolbarProvider);
        this.messageService = Objects.requireNonNull(messageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IDiagramInput diagramInput) {
        return diagramInput instanceof GetToolbarInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, DiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), GetToolbarInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.id(), message);

        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), diagramInput);

        if (diagramInput instanceof GetToolbarInput) {
            Diagram diagram = diagramContext.diagram();
            var optionalDiagramDescription = this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId())
                    .filter(DiagramDescription.class::isInstance)
                    .map(DiagramDescription.class::cast);
            if (optionalDiagramDescription.isPresent()) {
                DiagramDescription diagramDescription = optionalDiagramDescription.get();
                var optionalDiagramToolbar = this.toolbarProvider.getDiagramToolbar(editingContext, diagramContext, diagramDescription);
                if (optionalDiagramToolbar.isPresent()) {
                    DiagramToolbar toolbar = optionalDiagramToolbar.get();
                    payload = new GetToolbarSuccessPayload(diagramInput.id(), toolbar);
                }
            }
        }
        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
