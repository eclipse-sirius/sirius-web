/*******************************************************************************
 * Copyright (c) 2022, 2026 Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IConnectorToolsProvider;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ConnectorToolsDescriptionCandidates;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetConnectorToolsCandidatesSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetConnectorToolsInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnTwoDiagramElementsTool;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

/**
 * Event handler for "get connector tools" query.
 *
 * @author nvannier
 */
@Service
public class GetConnectorToolsEventHandler implements IDiagramEventHandler {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IDiagramQueryService diagramQueryService;

    private final ICollaborativeMessageService messageService;

    private final List<IConnectorToolsProvider> connectorToolsProviders;

    private final Counter counter;

    public GetConnectorToolsEventHandler(IRepresentationDescriptionSearchService representationDescriptionSearchService, IDiagramQueryService diagramQueryService,
                                         ICollaborativeMessageService messageService, List<IConnectorToolsProvider> connectorToolsProviders, MeterRegistry meterRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.messageService = Objects.requireNonNull(messageService);
        this.connectorToolsProviders = Objects.requireNonNull(connectorToolsProviders);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IDiagramInput diagramInput) {
        return diagramInput instanceof GetConnectorToolsInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, DiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), diagramInput);
        IPayload payload = null;

        if (diagramInput instanceof GetConnectorToolsInput connectorToolsInput) {
            Diagram diagram = diagramContext.diagram();
            var diagramDescription = this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId())
                    .filter(DiagramDescription.class::isInstance)
                    .map(DiagramDescription.class::cast);

            var optionalSourceDiagramElement = this.findDiagramElement(diagram, connectorToolsInput.sourceDiagramElementId());

            if (diagramDescription.isPresent() && optionalSourceDiagramElement.isPresent()) {
                var connectorTools = connectorToolsProviders.stream().filter(connectorToolsProvider -> connectorToolsProvider.canHandle(diagramDescription.get()))
                        .flatMap(provider -> provider.getConnectorTools(editingContext, diagram, optionalSourceDiagramElement.get()).stream())
                        .toList();

                var targetDescriptionIdCandidates = connectorTools.stream()
                        .filter(SingleClickOnTwoDiagramElementsTool.class::isInstance)
                        .map(SingleClickOnTwoDiagramElementsTool.class::cast)
                        .flatMap(singleClickOnTwoDiagramElementsTool -> singleClickOnTwoDiagramElementsTool.candidates().stream())
                        .flatMap(singleClickOnTwoDiagramElementsCandidate -> singleClickOnTwoDiagramElementsCandidate.targets().stream())
                        .map(IDiagramElementDescription::getId)
                        .toList();

                payload = new GetConnectorToolsCandidatesSuccessPayload(diagramInput.id(), new ConnectorToolsDescriptionCandidates(targetDescriptionIdCandidates));
            }
        } else {
            String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), GetConnectorToolsInput.class.getSimpleName());
            payload = new ErrorPayload(diagramInput.id(), message);
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private Optional<Object> findDiagramElement(Diagram diagram, String diagramElementId) {
        Object diagramElement = null;
        var findNodeById = this.diagramQueryService.findNodeById(diagram, diagramElementId);
        if (findNodeById.isPresent()) {
            diagramElement = findNodeById.get();
        } else {
            var findEdgeById = this.diagramQueryService.findEdgeById(diagram, diagramElementId);
            if (findEdgeById.isPresent()) {
                diagramElement = findEdgeById.get();
            }
        }
        return Optional.ofNullable(diagramElement);
    }

}
