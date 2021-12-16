/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.description.EdgeDescription;
import org.eclipse.sirius.web.diagrams.tools.CreateEdgeTool;
import org.eclipse.sirius.web.diagrams.tools.ITool;
import org.eclipse.sirius.web.diagrams.tools.ToolSection;
import org.eclipse.sirius.web.representations.Failure;
import org.eclipse.sirius.web.representations.ISemanticRepresentationMetadata;
import org.eclipse.sirius.web.representations.IStatus;
import org.eclipse.sirius.web.representations.Success;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeKind;
import org.eclipse.sirius.web.spring.collaborative.api.Monitoring;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.web.spring.collaborative.diagrams.dto.InvokeEdgeToolOnDiagramInput;
import org.eclipse.sirius.web.spring.collaborative.diagrams.dto.InvokeEdgeToolOnDiagramSuccessPayload;
import org.eclipse.sirius.web.spring.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handle "Invoke edge tool on diagram" events.
 *
 * @author pcdavid
 * @author hmarchadour
 */
@Service
public class InvokeEdgeToolOnDiagramEventHandler implements IDiagramEventHandler {

    private final IObjectService objectService;

    private final IDiagramQueryService diagramQueryService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final ICollaborativeDiagramMessageService messageService;

    private final Counter counter;

    public InvokeEdgeToolOnDiagramEventHandler(IObjectService objectService, IDiagramQueryService diagramQueryService, IRepresentationDescriptionSearchService representationDescriptionSearchService,
            ICollaborativeDiagramMessageService messageService, MeterRegistry meterRegistry) {
        this.objectService = Objects.requireNonNull(objectService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
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
        return diagramInput instanceof InvokeEdgeToolOnDiagramInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext,
            ISemanticRepresentationMetadata diagramMetadata, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), InvokeEdgeToolOnDiagramInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.getId(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.getRepresentationId(), diagramInput);

        if (diagramInput instanceof InvokeEdgeToolOnDiagramInput) {
            InvokeEdgeToolOnDiagramInput input = (InvokeEdgeToolOnDiagramInput) diagramInput;
            Diagram diagram = diagramContext.getDiagram();
            // @formatter:off
            var optionalTool = this.findToolById(editingContext, diagramMetadata.getDescriptionId().toString(), input.getToolId())
                    .filter(CreateEdgeTool.class::isInstance)
                    .map(CreateEdgeTool.class::cast);
            // @formatter:on
            if (optionalTool.isPresent()) {
                IStatus status = this.executeTool(editingContext, diagramContext, input.getDiagramSourceElementId(), input.getDiagramTargetElementId(), optionalTool.get());
                if (status instanceof Success) {
                    payload = new InvokeEdgeToolOnDiagramSuccessPayload(diagramInput.getId(), diagram);
                    changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, diagramInput.getRepresentationId(), diagramInput);
                }
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private Optional<ITool> findToolById(IEditingContext editingContext, String diagramDescriptionId, String toolId) {
        // @formatter:off
        var optionalDiagramDescription = this.representationDescriptionSearchService.findById(editingContext, diagramDescriptionId)
                                             .filter(DiagramDescription.class::isInstance)
                                             .map(DiagramDescription.class::cast);
        // @formatter:on
        if (optionalDiagramDescription.isPresent()) {
            return this.findToolById(optionalDiagramDescription.get(), toolId);
        } else {
            return Optional.empty();
        }
    }

    private Optional<ITool> findToolById(DiagramDescription diagramDescription, String toolId) {
        // @formatter:off
        return diagramDescription.getToolSections().stream()
                                 .map(ToolSection::getTools)
                                 .flatMap(Collection::stream)
                                 .filter(tool -> Objects.equals(tool.getId(), toolId))
                                 .findFirst();
        // @formatter:on
    }

    private IStatus executeTool(IEditingContext editingContext, IDiagramContext diagramContext, String sourceNodeId, String targetNodeId, CreateEdgeTool tool) {
        IStatus result = new Failure(""); //$NON-NLS-1$
        Diagram diagram = diagramContext.getDiagram();
        Optional<Node> sourceNode = this.diagramQueryService.findNodeById(diagram, sourceNodeId);
        Optional<Node> targetNode = this.diagramQueryService.findNodeById(diagram, targetNodeId);
        Optional<Object> source = Optional.empty();
        Optional<Object> target = Optional.empty();
        if (sourceNode.isPresent() && targetNode.isPresent()) {
            source = this.objectService.getObject(editingContext, sourceNode.get().getTargetObjectId());
            target = this.objectService.getObject(editingContext, targetNode.get().getTargetObjectId());
        }

        if (source.isPresent() && target.isPresent()) {
            VariableManager variableManager = new VariableManager();
            variableManager.put(IDiagramContext.DIAGRAM_CONTEXT, diagramContext);
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
            variableManager.put(EdgeDescription.SEMANTIC_EDGE_SOURCE, source.get());
            variableManager.put(EdgeDescription.SEMANTIC_EDGE_TARGET, target.get());

            result = tool.getHandler().apply(variableManager);
        }
        return result;
    }

}
