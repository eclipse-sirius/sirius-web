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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Edge;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.description.EdgeDescription;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.representations.Failure;
import org.eclipse.sirius.web.representations.ISemanticRepresentationMetadata;
import org.eclipse.sirius.web.representations.IStatus;
import org.eclipse.sirius.web.representations.Success;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeKind;
import org.eclipse.sirius.web.spring.collaborative.api.Monitoring;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.web.spring.collaborative.diagrams.dto.DeleteFromDiagramInput;
import org.eclipse.sirius.web.spring.collaborative.diagrams.dto.DeleteFromDiagramSuccessPayload;
import org.eclipse.sirius.web.spring.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handle "Delete from Diagram" events.
 *
 * @author pcdavid
 */
@Service
public class DeleteFromDiagramEventHandler implements IDiagramEventHandler {

    private final IObjectService objectService;

    private final IDiagramQueryService diagramQueryService;

    private final IDiagramDescriptionService diagramDescriptionService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final ICollaborativeDiagramMessageService messageService;

    private final Logger logger = LoggerFactory.getLogger(DeleteFromDiagramEventHandler.class);

    private final Counter counter;

    public DeleteFromDiagramEventHandler(IObjectService objectService, IDiagramQueryService diagramQueryService, IDiagramDescriptionService diagramDescriptionService,
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
        return diagramInput instanceof DeleteFromDiagramInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext,
            ISemanticRepresentationMetadata diagramMetadata, IDiagramInput diagramInput) {
        this.counter.increment();

        if (diagramInput instanceof DeleteFromDiagramInput) {
            this.handleDelete(payloadSink, changeDescriptionSink, editingContext, diagramContext, diagramMetadata, (DeleteFromDiagramInput) diagramInput);
        } else {
            String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), DeleteFromDiagramInput.class.getSimpleName());
            payloadSink.tryEmitValue(new ErrorPayload(diagramInput.getId(), message));
            changeDescriptionSink.tryEmitNext(new ChangeDescription(ChangeKind.NOTHING, diagramInput.getRepresentationId(), diagramInput));
        }
    }

    private void handleDelete(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext,
            ISemanticRepresentationMetadata diagramMetadata, DeleteFromDiagramInput diagramInput) {
        List<String> errors = new ArrayList<>();
        boolean atLeastOneOk = false;
        Diagram diagram = diagramContext.getDiagram();
        for (String edgeId : diagramInput.getEdgeIds()) {
            var optionalElement = this.diagramQueryService.findEdgeById(diagram, edgeId);
            if (optionalElement.isPresent()) {
                IStatus status = this.invokeDeleteEdgeTool(optionalElement.get(), editingContext, diagramMetadata.getDescriptionId().toString(), diagramContext);
                if (status instanceof Success) {
                    atLeastOneOk = true;
                }
            } else {
                String message = this.messageService.edgeNotFound(edgeId);
                errors.add(message);
            }
        }
        for (String nodeId : diagramInput.getNodeIds()) {
            var optionalElement = this.diagramQueryService.findNodeById(diagram, nodeId);
            if (optionalElement.isPresent()) {
                IStatus status = this.invokeDeleteNodeTool(optionalElement.get(), editingContext, diagramMetadata.getDescriptionId().toString(), diagramContext);
                if (status instanceof Success) {
                    atLeastOneOk = true;
                }
            } else {
                String message = this.messageService.nodeNotFound(nodeId);
                errors.add(message);
            }
        }

        this.sendResponse(payloadSink, changeDescriptionSink, errors, atLeastOneOk, diagramContext, diagramInput);
    }

    private void sendResponse(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, List<String> errors, boolean atLeastOneSuccess, IDiagramContext diagramContext,
            DeleteFromDiagramInput diagramInput) {

        var changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, diagramInput.getRepresentationId(), diagramInput);
        IPayload payload = new DeleteFromDiagramSuccessPayload(diagramInput.getId(), diagramContext.getDiagram());
        if (!errors.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.messageService.deleteFailed());
            for (String error : errors) {
                stringBuilder.append(error);
            }

            changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.getRepresentationId(), diagramInput);
            if (atLeastOneSuccess) {
                changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, diagramInput.getRepresentationId(), diagramInput);
            }

            payload = new ErrorPayload(diagramInput.getId(), stringBuilder.toString());
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private IStatus invokeDeleteNodeTool(Node node, IEditingContext editingContext, String diagramDescriptionId, IDiagramContext diagramContext) {
        IStatus result = new Failure(""); //$NON-NLS-1$
        var optionalNodeDescription = this.findNodeDescription(node, diagramDescriptionId, editingContext);

        if (optionalNodeDescription.isPresent()) {
            var optionalSelf = this.objectService.getObject(editingContext, node.getTargetObjectId());
            if (optionalSelf.isPresent()) {
                VariableManager variableManager = new VariableManager();
                variableManager.put(VariableManager.SELF, optionalSelf.get());
                variableManager.put(IDiagramContext.DIAGRAM_CONTEXT, diagramContext);
                variableManager.put(Node.SELECTED_NODE, node);
                NodeDescription nodeDescription = optionalNodeDescription.get();
                this.logger.debug("Deleted diagram element {}", node.getId()); //$NON-NLS-1$
                result = nodeDescription.getDeleteHandler().apply(variableManager);
            } else {
                String message = this.messageService.semanticObjectNotFound(node.getTargetObjectId());
                this.logger.debug(message);
            }
        } else {
            String message = this.messageService.nodeDescriptionNotFound(node.getId());
            this.logger.debug(message);
        }
        return result;
    }

    private IStatus invokeDeleteEdgeTool(Edge edge, IEditingContext editingContext, String diagramDescriptionId, IDiagramContext diagramContext) {
        IStatus result = new Failure(""); //$NON-NLS-1$
        var optionalEdgeDescription = this.findEdgeDescription(edge, diagramDescriptionId, editingContext);
        if (optionalEdgeDescription.isPresent()) {
            var optionalSelf = this.objectService.getObject(editingContext, edge.getTargetObjectId());
            if (optionalSelf.isPresent()) {
                VariableManager variableManager = new VariableManager();
                variableManager.put(VariableManager.SELF, optionalSelf.get());
                variableManager.put(IDiagramContext.DIAGRAM_CONTEXT, diagramContext);
                // @formatter:off
                this.diagramQueryService.findNodeById(diagramContext.getDiagram(), edge.getSourceId())
                        .flatMap(node -> this.objectService.getObject(editingContext, node.getTargetObjectId()))
                        .ifPresent(semanticElement -> variableManager.put(EdgeDescription.SEMANTIC_EDGE_SOURCE, semanticElement));
                this.diagramQueryService.findNodeById(diagramContext.getDiagram(), edge.getTargetId())
                        .flatMap(node -> this.objectService.getObject(editingContext, node.getTargetObjectId()))
                        .ifPresent(semanticElement -> variableManager.put(EdgeDescription.SEMANTIC_EDGE_TARGET, semanticElement));
                // @formatter:on
                EdgeDescription edgeDescription = optionalEdgeDescription.get();
                this.logger.debug("Deleted diagram edge {}", edge.getId()); //$NON-NLS-1$
                result = edgeDescription.getDeleteHandler().apply(variableManager);
            } else {
                String message = this.messageService.semanticObjectNotFound(edge.getTargetObjectId());
                this.logger.debug(message);
            }
        } else {
            String message = this.messageService.edgeDescriptionNotFound(edge.getId());
            this.logger.debug(message);
        }
        return result;
    }

    private Optional<NodeDescription> findNodeDescription(Node node, String diagramDescriptionId, IEditingContext editingContext) {
        // @formatter:off
        return this.representationDescriptionSearchService
                .findById(editingContext, diagramDescriptionId)
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast)
                .flatMap(diagramDescription -> this.diagramDescriptionService.findNodeDescriptionById(diagramDescription, node.getDescriptionId()));
        // @formatter:on
    }

    private Optional<EdgeDescription> findEdgeDescription(Edge edge, String diagramDescriptionId, IEditingContext editingContext) {
        // @formatter:off
        return this.representationDescriptionSearchService
                .findById(editingContext, diagramDescriptionId)
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast)
                .flatMap(diagramDescription -> this.diagramDescriptionService.findEdgeDescriptionById(diagramDescription, edge.getDescriptionId()));
        // @formatter:on
    }
}
