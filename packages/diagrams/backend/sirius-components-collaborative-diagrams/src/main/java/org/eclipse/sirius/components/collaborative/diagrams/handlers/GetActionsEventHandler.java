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
package org.eclipse.sirius.components.collaborative.diagrams.handlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IActionsProvider;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.Action;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetActionsInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetActionsSuccessPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler used to get the node actions.
 *
 * @author arichard
 */
@Service
public class GetActionsEventHandler implements IDiagramEventHandler {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IDiagramQueryService diagramQueryService;

    private final List<IActionsProvider> actionsProviders;

    private final Counter counter;

    public GetActionsEventHandler(IRepresentationDescriptionSearchService representationDescriptionSearchService, IDiagramQueryService diagramQueryService, List<IActionsProvider> actionsProviders, MeterRegistry meterRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.actionsProviders = Objects.requireNonNull(actionsProviders);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER).tag(Monitoring.NAME, this.getClass().getSimpleName()).register(meterRegistry);
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof GetActionsInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, DiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        List<Action> actions = new ArrayList<>();

        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), diagramInput);

        if (diagramInput instanceof GetActionsInput actionsInput) {
            String diagramElementId = actionsInput.diagramElementId();

            Diagram diagram = diagramContext.diagram();
            var optionalDiagramDescription = this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId())
                    .filter(DiagramDescription.class::isInstance)
                    .map(DiagramDescription.class::cast);

            var optionalNode = this.diagramQueryService.findNodeById(diagram, diagramElementId);
            if (optionalDiagramDescription.isPresent() && optionalNode.isPresent()) {
                DiagramDescription diagramDescription = optionalDiagramDescription.get();
                IDiagramElement diagramElement = optionalNode.get();

                actions = this.actionsProviders.stream()
                        .filter(actionsProvider -> actionsProvider.canHandle(editingContext, diagramDescription, diagramElement))
                        .map(actionsProvider -> actionsProvider.handle(editingContext, diagramDescription, diagramElement))
                        .flatMap(Collection::stream)
                        .toList();

            }
        }

        payloadSink.tryEmitValue(new GetActionsSuccessPayload(diagramInput.id(), actions));
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
