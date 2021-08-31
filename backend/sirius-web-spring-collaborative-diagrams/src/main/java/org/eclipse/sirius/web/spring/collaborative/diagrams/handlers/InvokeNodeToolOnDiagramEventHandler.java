/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo and others.
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
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.events.CreationEvent;
import org.eclipse.sirius.web.diagrams.tools.CreateNodeTool;
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
import org.eclipse.sirius.web.spring.collaborative.diagrams.dto.InvokeNodeToolOnDiagramInput;
import org.eclipse.sirius.web.spring.collaborative.diagrams.dto.InvokeNodeToolOnDiagramSuccessPayload;
import org.eclipse.sirius.web.spring.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handle "Invoke node tool on diagram" events.
 *
 * @author pcdavid
 */
@Service
public class InvokeNodeToolOnDiagramEventHandler implements IDiagramEventHandler {

    private final Logger logger = LoggerFactory.getLogger(InvokeNodeToolOnDiagramEventHandler.class);

    private final IObjectService objectService;

    private final IDiagramQueryService diagramQueryService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final ICollaborativeDiagramMessageService messageService;

    private final Counter counter;

    public InvokeNodeToolOnDiagramEventHandler(IObjectService objectService, IDiagramQueryService diagramQueryService, ICollaborativeDiagramMessageService messageService, MeterRegistry meterRegistry,
            IRepresentationDescriptionSearchService representationDescriptionSearchService) {
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
        return diagramInput instanceof InvokeNodeToolOnDiagramInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext,
            ISemanticRepresentationMetadata diagramMetadata, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), InvokeNodeToolOnDiagramInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.getId(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.getRepresentationId(), diagramInput);

        if (diagramInput instanceof InvokeNodeToolOnDiagramInput) {
            InvokeNodeToolOnDiagramInput input = (InvokeNodeToolOnDiagramInput) diagramInput;
            Diagram diagram = diagramContext.getDiagram();
            // @formatter:off
            var optionalTool = this.findToolById(editingContext, diagramMetadata.getDescriptionId().toString(), input.getToolId())
                    .filter(CreateNodeTool.class::isInstance)
                    .map(CreateNodeTool.class::cast);
            // @formatter:on
            if (optionalTool.isPresent()) {
                IStatus status = this.executeTool(editingContext, diagramContext, input.getDiagramElementId(), optionalTool.get(), input.getStartingPositionX(), input.getStartingPositionY(),
                        input.getSelectedObjectId());
                if (status instanceof Success) {
                    payload = new InvokeNodeToolOnDiagramSuccessPayload(diagramInput.getId(), diagram);
                    changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, diagramInput.getRepresentationId(), diagramInput);
                }
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private Optional<ITool> findToolById(IEditingContext editingContext, String diagramDescriptionId, String toolId) {
        // @formatter:off
        var optionalDiagramDescription = this.representationDescriptionSearchService.findById(editingContext, diagramDescriptionId.toString())
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

    private IStatus executeTool(IEditingContext editingContext, IDiagramContext diagramContext, String diagramElementId, CreateNodeTool tool, double startingPositionX, double startingPositionY,
            String selectedObjectId) {
        IStatus result = new Failure(""); //$NON-NLS-1$
        Diagram diagram = diagramContext.getDiagram();
        Optional<Node> node = this.diagramQueryService.findNodeById(diagram, diagramElementId);
        Optional<Object> self = Optional.empty();
        if (node.isPresent()) {
            self = this.objectService.getObject(editingContext, node.get().getTargetObjectId());
        } else if (Objects.equals(diagram.getId(), diagramElementId)) {
            self = this.objectService.getObject(editingContext, diagram.getTargetObjectId());
        } else {
            this.logger.warn("The node creation tool {0} cannot be applied on the current diagram {1} and editing context {2}", tool.getId(), diagram.getId(), editingContext.getId()); //$NON-NLS-1$
        }

        // Else, cannot find the node with the given optionalDiagramElementId

        if (self.isPresent()) {
            VariableManager variableManager = new VariableManager();
            variableManager.put(IDiagramContext.DIAGRAM_CONTEXT, diagramContext);
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
            variableManager.put(VariableManager.SELF, self.get());
            node.ifPresent(selectedNode -> variableManager.put(Node.SELECTED_NODE, selectedNode));

            String selectionDescriptionId = tool.getSelectionDescriptionId();
            if (selectionDescriptionId != null && selectedObjectId != null) {
                var selectionDescriptionOpt = this.representationDescriptionSearchService.findById(editingContext, selectionDescriptionId);
                var selectedObjectOpt = this.objectService.getObject(editingContext, selectedObjectId);
                if (selectionDescriptionOpt.isPresent() && selectedObjectOpt.isPresent()) {
                    variableManager.put(CreateNodeTool.SELECTED_OBJECT, selectedObjectOpt.get());
                }
            }
            if (selectionDescriptionId == null || selectedObjectId != null) {
                result = tool.getHandler().apply(variableManager);
                Position newPosition = Position.at(startingPositionX, startingPositionY);

                diagramContext.setDiagramEvent(new CreationEvent(newPosition));
            }
        }
        return result;
    }

}
