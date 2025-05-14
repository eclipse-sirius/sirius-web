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
package org.eclipse.sirius.components.collaborative.diagrams.handlers.nodeactions;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.api.nodeactions.IManageVisibilityMenuActionProvider;
import org.eclipse.sirius.components.collaborative.diagrams.dto.managevisibility.GetManageVisibilityActionsInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.managevisibility.GetManageVisibilityActionsSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.managevisibility.ManageVisibilityAction;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * Handler used to get the manage visibility actions.
 *
 * @author mcharfadi
 */
@Service
public class GetManageVisibilityActionsEventHandler implements IDiagramEventHandler {

    private final List<IManageVisibilityMenuActionProvider> manageVisibilityMenuActionsProvider;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IDiagramQueryService diagramQueryService;

    private final Counter counter;

    public GetManageVisibilityActionsEventHandler(List<IManageVisibilityMenuActionProvider> manageVisibilityMenuActionsProvider, IRepresentationDescriptionSearchService representationDescriptionSearchService, IDiagramQueryService diagramQueryService, MeterRegistry meterRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.manageVisibilityMenuActionsProvider = Objects.requireNonNull(manageVisibilityMenuActionsProvider);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER).tag(Monitoring.NAME, this.getClass().getSimpleName()).register(meterRegistry);
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof GetManageVisibilityActionsInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext, IDiagramInput diagramInput) {

        this.counter.increment();

        List<ManageVisibilityAction> actions = new ArrayList<>();

        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), diagramInput);

        if (diagramInput instanceof GetManageVisibilityActionsInput actionsInput) {
            String diagramElementId = actionsInput.diagramElementId();

            Diagram diagram = diagramContext.getDiagram();
            var optionalDiagramDescription = this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId())
                    .filter(DiagramDescription.class::isInstance)
                    .map(DiagramDescription.class::cast);

            var optionalNode = this.diagramQueryService.findNodeById(diagram, diagramElementId);
            if (optionalDiagramDescription.isPresent() && optionalNode.isPresent()) {
                DiagramDescription diagramDescription = optionalDiagramDescription.get();
                IDiagramElement diagramElement = optionalNode.get();

                actions = this.manageVisibilityMenuActionsProvider.stream()
                        .filter(actionsProvider -> actionsProvider.canHandle(editingContext, diagramDescription, diagramElement))
                        .map(actionsProvider -> actionsProvider.handle(editingContext, diagramDescription, diagramElement))
                        .flatMap(Collection::stream)
                        .toList();

            }
        }

        payloadSink.tryEmitValue(new GetManageVisibilityActionsSuccessPayload(diagramInput.id(), actions));
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
