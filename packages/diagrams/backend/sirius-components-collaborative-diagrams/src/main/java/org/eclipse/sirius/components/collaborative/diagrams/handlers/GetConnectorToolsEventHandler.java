/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
import java.util.stream.Stream;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
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
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ConnectorTool;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnTwoDiagramElementsTool;
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

    private final Counter counter;

    public GetConnectorToolsEventHandler(IRepresentationDescriptionSearchService representationDescriptionSearchService, IDiagramQueryService diagramQueryService,
                                          ICollaborativeMessageService messageService, MeterRegistry meterRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.messageService = Objects.requireNonNull(messageService);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
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

            if (diagramDescription.isPresent()) {
                String sourceDiagramElementId = connectorToolsInput.sourceDiagramElementId();
                var sourceDiagramElement = this.diagramQueryService.findDiagramElementById(diagram, sourceDiagramElementId);

                List<ConnectorTool> connectorTools = new ArrayList<>();
                sourceDiagramElement.ifPresent(diagramElement -> diagramDescription.get().getPalettes().stream()
                        .flatMap(palette -> Stream.concat(
                                palette.getTools().stream(),
                                palette.getToolSections().stream()
                                        .flatMap(toolSection -> toolSection.getTools().stream())
                        ))
                        .filter(SingleClickOnTwoDiagramElementsTool.class::isInstance)
                        .map(SingleClickOnTwoDiagramElementsTool.class::cast)
                        .forEach(tool -> {
                            var sourceCandidatesDescriptionId = tool.getCandidates().stream()
                                    .flatMap(singleClickOnTwoDiagramElementsCandidate -> singleClickOnTwoDiagramElementsCandidate.getSources().stream())
                                    .map(IDiagramElementDescription::getId);

                            if (sourceCandidatesDescriptionId.anyMatch(descriptionId -> descriptionId.equals(diagramElement.getDescriptionId()))) {
                                var targetCandidates = tool.getCandidates().stream()
                                        .flatMap(singleClickOnTwoDiagramElementsCandidate -> singleClickOnTwoDiagramElementsCandidate.getTargets().stream())
                                        .map(IDiagramElementDescription::getId)
                                        .toList();
                                connectorTools.add(new ConnectorTool(tool.getId(), tool.getLabel(), tool.getIconURL(), targetCandidates));

                            }
                        }));
                payload = new GetConnectorToolsSuccessPayload(diagramInput.id(), connectorTools);
            }
        } else {
            String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), GetConnectorToolsInput.class.getSimpleName());
            payload = new ErrorPayload(diagramInput.id(), message);
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

}
