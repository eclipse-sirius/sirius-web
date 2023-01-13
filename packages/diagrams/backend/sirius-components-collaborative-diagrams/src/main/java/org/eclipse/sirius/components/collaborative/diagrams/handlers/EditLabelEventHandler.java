/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.EditLabelInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.EditLabelSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.Environment;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeLabelKind;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handle "Edit Label" events.
 *
 * @author pcdavid
 */
@Service
public class EditLabelEventHandler implements IDiagramEventHandler {

    private final IObjectService objectService;

    private final IDiagramQueryService diagramQueryService;

    private final IDiagramDescriptionService diagramDescriptionService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final ICollaborativeDiagramMessageService messageService;

    private final Logger logger = LoggerFactory.getLogger(EditLabelEventHandler.class);

    private final Counter counter;

    public EditLabelEventHandler(IObjectService objectService, IDiagramQueryService diagramQueryService, IDiagramDescriptionService diagramDescriptionService,
            IRepresentationDescriptionSearchService representationDescriptionSearchService, ICollaborativeDiagramMessageService messageService, MeterRegistry meterRegistry) {
        this.objectService = Objects.requireNonNull(objectService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.diagramDescriptionService = Objects.requireNonNull(diagramDescriptionService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof EditLabelInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), EditLabelInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.representationId(), diagramInput);

        if (diagramInput instanceof EditLabelInput input) {
            Diagram diagram = diagramContext.getDiagram();
            var node = this.diagramQueryService.findNodeByLabelId(diagram, input.labelId());
            if (node.isPresent()) {
                this.invokeDirectEditTool(node.get(), editingContext, diagram, input.newText());

                payload = new EditLabelSuccessPayload(diagramInput.id(), diagram);
                changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, diagramInput.representationId(), diagramInput);
            } else {
                var edge = this.diagramQueryService.findEdgeByLabelId(diagram, input.labelId());
                if (edge.isPresent()) {
                    payload = new EditLabelSuccessPayload(diagramInput.id(), diagram);
                    try {
                        this.invokeDirectEditTool(edge.get(), input.labelId(), editingContext, diagram, input.newText());
                        changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, diagramInput.representationId(), diagramInput);
                    } catch (IllegalArgumentException e) {
                        payload = new ErrorPayload(diagramInput.id(), this.messageService.invalidNewValue(input.newText()));
                    }

                }
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private void invokeDirectEditTool(Node node, IEditingContext editingContext, Diagram diagram, String newText) {
        var optionalNodeDescription = this.findNodeDescription(node, diagram, editingContext);
        if (optionalNodeDescription.isPresent()) {
            NodeDescription nodeDescription = optionalNodeDescription.get();

            var optionalSelf = this.objectService.getObject(editingContext, node.getTargetObjectId());
            if (optionalSelf.isPresent()) {
                Object self = optionalSelf.get();

                VariableManager variableManager = new VariableManager();
                variableManager.put(Environment.ENVIRONMENT, new Environment(Environment.SIRIUS_COMPONENTS));
                variableManager.put(VariableManager.SELF, self);
                nodeDescription.getLabelEditHandler().apply(variableManager, newText);
                this.logger.debug("Edited label of diagram element {} to {}", node.getId(), newText);
            }
        }
    }

    private void invokeDirectEditTool(Edge edge, String labelId, IEditingContext editingContext, Diagram diagram, String newText) {
        var optionalEdgeDescription = this.findEdgeDescription(edge, diagram, editingContext);
        if (optionalEdgeDescription.isPresent()) {
            EdgeDescription edgeDescription = optionalEdgeDescription.get();

            var optionalSelf = this.objectService.getObject(editingContext, edge.getTargetObjectId());
            if (optionalSelf.isPresent()) {
                Object self = optionalSelf.get();

                var edgeLabelKind = EdgeLabelKind.CENTER_LABEL;
                if (edge.getBeginLabel() != null && edge.getBeginLabel().getId().equals(labelId)) {
                    edgeLabelKind = EdgeLabelKind.BEGIN_LABEL;
                } else if (edge.getEndLabel() != null && edge.getEndLabel().getId().equals(labelId)) {
                    edgeLabelKind = EdgeLabelKind.END_LABEL;
                }

                VariableManager variableManager = new VariableManager();
                variableManager.put(Environment.ENVIRONMENT, new Environment(Environment.SIRIUS_COMPONENTS));
                variableManager.put(VariableManager.SELF, self);
                // @formatter:off
                var semanticEdgeSource = this.diagramQueryService.findNodeById(diagram, edge.getSourceId())
                        .flatMap(node -> this.objectService.getObject(editingContext, node.getTargetObjectId()))
                        .orElse(null);
                var semanticEdgeTarget = this.diagramQueryService.findNodeById(diagram, edge.getTargetId())
                        .flatMap(node -> this.objectService.getObject(editingContext, node.getTargetObjectId()))
                        .orElse(null);
                variableManager.put(EdgeDescription.SEMANTIC_EDGE_SOURCE, semanticEdgeSource);
                variableManager.put(EdgeDescription.SEMANTIC_EDGE_TARGET, semanticEdgeTarget);
                // @formatter:on
                edgeDescription.getLabelEditHandler().editLabel(variableManager, edgeLabelKind, newText);
                this.logger.debug("Edited label of diagram element {} to {}", edge.getId(), newText);
            }
        }
    }

    private Optional<NodeDescription> findNodeDescription(Node node, Diagram diagram, IEditingContext editingContext) {
        // @formatter:off
        return this.representationDescriptionSearchService
                .findById(editingContext, diagram.getDescriptionId())
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast)
                .flatMap(diagramDescription -> this.diagramDescriptionService.findNodeDescriptionById(diagramDescription, node.getDescriptionId()));
        // @formatter:on
    }

    private Optional<EdgeDescription> findEdgeDescription(Edge edge, Diagram diagram, IEditingContext editingContext) {
        // @formatter:off
        return this.representationDescriptionSearchService
                   .findById(editingContext, diagram.getDescriptionId())
                   .filter(DiagramDescription.class::isInstance)
                   .map(DiagramDescription.class::cast)
                   .flatMap(diagramDescription -> this.diagramDescriptionService.findEdgeDescriptionById(diagramDescription, edge.getDescriptionId()));
        // @formatter:on
    }
}
