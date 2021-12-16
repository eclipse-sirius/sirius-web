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
import org.eclipse.sirius.web.representations.ISemanticRepresentationMetadata;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeKind;
import org.eclipse.sirius.web.spring.collaborative.api.Monitoring;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.web.spring.collaborative.diagrams.dto.EditLabelInput;
import org.eclipse.sirius.web.spring.collaborative.diagrams.dto.EditLabelSuccessPayload;
import org.eclipse.sirius.web.spring.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
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
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext,
            ISemanticRepresentationMetadata diagramMetadata, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), EditLabelInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.getId(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.getRepresentationId(), diagramInput);

        if (diagramInput instanceof EditLabelInput) {
            EditLabelInput input = (EditLabelInput) diagramInput;
            Diagram diagram = diagramContext.getDiagram();
            var node = this.diagramQueryService.findNodeByLabelId(diagram, input.getLabelId());
            if (node.isPresent()) {
                this.invokeDirectEditTool(node.get(), editingContext, diagramMetadata.getDescriptionId().toString(), input.getNewText());

                payload = new EditLabelSuccessPayload(diagramInput.getId(), diagram);
                changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, diagramInput.getRepresentationId(), diagramInput);
            } else {
                var edge = this.diagramQueryService.findEdgeByLabelId(diagram, input.getLabelId());
                if (edge.isPresent()) {
                    this.invokeDirectEditTool(edge.get(), editingContext, diagramMetadata.getDescriptionId().toString(), input.getNewText());

                    payload = new EditLabelSuccessPayload(diagramInput.getId(), diagram);
                    changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, diagramInput.getRepresentationId(), diagramInput);
                }
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private void invokeDirectEditTool(Node node, IEditingContext editingContext, String diagramDescriptionId, String newText) {
        var optionalNodeDescription = this.findNodeDescription(node, diagramDescriptionId, editingContext);
        if (optionalNodeDescription.isPresent()) {
            NodeDescription nodeDescription = optionalNodeDescription.get();

            var optionalSelf = this.objectService.getObject(editingContext, node.getTargetObjectId());
            if (optionalSelf.isPresent()) {
                Object self = optionalSelf.get();

                VariableManager variableManager = new VariableManager();
                variableManager.put(VariableManager.SELF, self);
                nodeDescription.getLabelEditHandler().apply(variableManager, newText);
                this.logger.debug("Edited label of diagram element {} to {}", node.getId(), newText); //$NON-NLS-1$
            }
        }
    }

    private void invokeDirectEditTool(Edge edge, IEditingContext editingContext, String diagramDescriptionId, String newText) {
        var optionalEdgeDescription = this.findEdgeDescription(edge, diagramDescriptionId, editingContext);
        if (optionalEdgeDescription.isPresent()) {
            EdgeDescription edgeDescription = optionalEdgeDescription.get();

            var optionalSelf = this.objectService.getObject(editingContext, edge.getTargetObjectId());
            if (optionalSelf.isPresent()) {
                Object self = optionalSelf.get();

                VariableManager variableManager = new VariableManager();
                variableManager.put(VariableManager.SELF, self);
                edgeDescription.getLabelEditHandler().apply(variableManager, newText);
                this.logger.debug("Edited label of diagram element {} to {}", edge.getId(), newText); //$NON-NLS-1$
            }
        }
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
