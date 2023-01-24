/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.api.IConnectorToolsProvider;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetConnectorToolsInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetConnectorToolsSuccessPayload;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.tools.ITool;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Event handler for "get connector tools" query.
 *
 * @author nvannier
 */
@Service
public class GetConnectorToolsEventHandler implements IDiagramEventHandler {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IDiagramQueryService diagramQueryService;

    private final List<IConnectorToolsProvider> connectorToolsProviders;

    private final ICollaborativeMessageService messageService;

    private final Counter counter;

    public GetConnectorToolsEventHandler(IRepresentationDescriptionSearchService representationDescriptionSearchService, IDiagramQueryService diagramQueryService,
            List<IConnectorToolsProvider> connectorToolsProviders, ICollaborativeMessageService messageService, MeterRegistry meterRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.connectorToolsProviders = Objects.requireNonNull(connectorToolsProviders);
        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof GetConnectorToolsInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), diagramInput);
        IPayload payload = null;

        if (diagramInput instanceof GetConnectorToolsInput) {
            GetConnectorToolsInput connectorToolsInput = (GetConnectorToolsInput) diagramInput;
            Diagram diagram = diagramContext.getDiagram();
            //@formatter:off
            var diagramDescription = this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId())
                    .filter(DiagramDescription.class::isInstance)
                    .map(DiagramDescription.class::cast);
            //@formatter:on

            if (diagramDescription.isPresent()) {
                //@formatter:off
                List<IConnectorToolsProvider> compatibleConnectorToolsProviders = this.connectorToolsProviders.stream()
                        .filter(provider -> provider.canHandle(diagramDescription.get()))
                        .toList();
                //@formatter:on
                if (!compatibleConnectorToolsProviders.isEmpty()) {
                    String sourceDiagramElementId = connectorToolsInput.sourceDiagramElementId();
                    String targetDiagramElementId = connectorToolsInput.targetDiagramElementId();

                    var sourceDiagramElement = this.findDiagramElement(diagram, sourceDiagramElementId);
                    var targetDiagramElement = this.findDiagramElement(diagram, targetDiagramElementId);

                    List<ITool> connectorTools = new ArrayList<>();

                    if (sourceDiagramElement.isPresent() && targetDiagramElement.isPresent()) {
                        //@formatter:off
                        compatibleConnectorToolsProviders.stream()
                            .map(provider -> provider.getConnectorTools(sourceDiagramElement.get(), targetDiagramElement.get(), diagram, editingContext))
                            .flatMap(List::stream)
                            .distinct()
                            .forEach(connectorTools::add);
                        //@formatter:on
                    }

                    payload = new GetConnectorToolsSuccessPayload(diagramInput.id(), connectorTools);
                } else {
                    payload = new GetConnectorToolsSuccessPayload(diagramInput.id(), List.of());
                }
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
        if (diagram.getId().equals(diagramElementId)) {
            diagramElement = diagram;
        } else {
            var findNodeById = this.diagramQueryService.findNodeById(diagram, diagramElementId);
            if (findNodeById.isPresent()) {
                Node node = findNodeById.get();
                diagramElement = node;
            }
        }
        return Optional.ofNullable(diagramElement);
    }

}
