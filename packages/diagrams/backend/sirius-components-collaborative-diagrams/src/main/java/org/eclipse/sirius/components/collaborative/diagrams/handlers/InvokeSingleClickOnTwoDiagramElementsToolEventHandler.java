/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.api.IConnectorToolsProvider;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IToolService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnTwoDiagramElementsToolInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.Environment;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.events.DoublePositionEvent;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnTwoDiagramElementsTool;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.representations.WorkbenchSelection;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handle "Invoke single click on two diagram elements tool" events.
 *
 * @author pcdavid
 * @author hmarchadour
 */
@Service
public class InvokeSingleClickOnTwoDiagramElementsToolEventHandler implements IDiagramEventHandler {

    private final IObjectService objectService;

    private final IDiagramQueryService diagramQueryService;

    private final IToolService toolService;

    private final ICollaborativeDiagramMessageService messageService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final List<IConnectorToolsProvider> connectorToolsProviders;

    private final Counter counter;

    public InvokeSingleClickOnTwoDiagramElementsToolEventHandler(IObjectService objectService, IDiagramQueryService diagramQueryService, IToolService toolService,
            ICollaborativeDiagramMessageService messageService, IRepresentationDescriptionSearchService representationDescriptionSearchService, List<IConnectorToolsProvider> connectorToolsProviders,
            MeterRegistry meterRegistry) {
        this.objectService = Objects.requireNonNull(objectService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.toolService = Objects.requireNonNull(toolService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
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
        return diagramInput instanceof InvokeSingleClickOnTwoDiagramElementsToolInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), InvokeSingleClickOnTwoDiagramElementsToolInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.getId(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.getRepresentationId(), diagramInput);

        if (diagramInput instanceof InvokeSingleClickOnTwoDiagramElementsToolInput) {
            InvokeSingleClickOnTwoDiagramElementsToolInput input = (InvokeSingleClickOnTwoDiagramElementsToolInput) diagramInput;
            Diagram diagram = diagramContext.getDiagram();
            // @formatter:off
            var optionalTool = this.toolService.findToolById(editingContext, diagram, input.getToolId())
                    .filter(SingleClickOnTwoDiagramElementsTool.class::isInstance)
                    .map(SingleClickOnTwoDiagramElementsTool.class::cast)
                    .or(this.findConnectorToolById(input.getDiagramSourceElementId(), input.getDiagramTargetElementId(), editingContext, diagram, input.getToolId()));
            // @formatter:on
            if (optionalTool.isPresent()) {
                Position sourcePosition = Position.at(input.getSourcePositionX(), input.getSourcePositionY());
                Position targetPosition = Position.at(input.getTargetPositionX(), input.getTargetPositionY());
                IStatus status = this.executeTool(editingContext, diagramContext, input.getDiagramSourceElementId(), input.getDiagramTargetElementId(), optionalTool.get(), sourcePosition,
                        targetPosition);
                if (status instanceof Success) {
                    WorkbenchSelection newSelection = null;
                    Object newSelectionParameter = ((Success) status).getParameters().get(Success.NEW_SELECTION);
                    if (newSelectionParameter instanceof WorkbenchSelection) {
                        newSelection = (WorkbenchSelection) newSelectionParameter;
                    }
                    payload = new InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload(diagramInput.getId(), newSelection);
                    changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, diagramInput.getRepresentationId(), diagramInput);
                } else if (status instanceof Failure) {
                    payload = new ErrorPayload(diagramInput.getId(), ((Failure) status).getMessage());
                }
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private IStatus executeTool(IEditingContext editingContext, IDiagramContext diagramContext, String sourceNodeId, String targetNodeId, SingleClickOnTwoDiagramElementsTool tool,
            Position sourcePosition, Position targetPosition) {
        IStatus result = new Failure(""); //$NON-NLS-1$
        Diagram diagram = diagramContext.getDiagram();
        Optional<Node> sourceNode = this.diagramQueryService.findNodeById(diagram, sourceNodeId);
        Optional<Node> targetNode = this.diagramQueryService.findNodeById(diagram, targetNodeId);
        Optional<Object> source = Optional.empty();
        Optional<Object> target = Optional.empty();
        Node sourceView = null;
        Node targetView = null;
        if (sourceNode.isPresent() && targetNode.isPresent()) {
            sourceView = sourceNode.get();
            targetView = targetNode.get();
            source = this.objectService.getObject(editingContext, sourceNode.get().getTargetObjectId());
            target = this.objectService.getObject(editingContext, targetNode.get().getTargetObjectId());
        }

        if (source.isPresent() && target.isPresent()) {
            VariableManager variableManager = new VariableManager();
            variableManager.put(IDiagramContext.DIAGRAM_CONTEXT, diagramContext);
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
            variableManager.put(Environment.ENVIRONMENT, new Environment(Environment.SIRIUS_COMPONENTS));
            variableManager.put(EdgeDescription.SEMANTIC_EDGE_SOURCE, source.get());
            variableManager.put(EdgeDescription.SEMANTIC_EDGE_TARGET, target.get());
            variableManager.put(EdgeDescription.EDGE_SOURCE, sourceView);
            variableManager.put(EdgeDescription.EDGE_TARGET, targetView);

            result = tool.getHandler().apply(variableManager);

            diagramContext.setDiagramEvent(new DoublePositionEvent(sourcePosition, targetPosition));
        }
        return result;
    }

    private Supplier<Optional<SingleClickOnTwoDiagramElementsTool>> findConnectorToolById(String diagramSourceElementId, String diagramTargetElementId, IEditingContext editingContext, Diagram diagram,
            String searchedToolId) {
        //@formatter:off
        var diagramDescription = this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId())
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast);
        //@formatter:on

        if (diagramDescription.isPresent()) {
          //@formatter:off
            List<IConnectorToolsProvider> compatibleConnectorToolsProviders = this.connectorToolsProviders.stream()
                    .filter(provider -> provider.canHandle(diagramDescription.get()))
                    .collect(Collectors.toList());
            //@formatter:on
            if (!compatibleConnectorToolsProviders.isEmpty()) {
                var diagramSourceElement = this.diagramQueryService.findNodeById(diagram, diagramSourceElementId);
                var diagramTargetElement = this.diagramQueryService.findNodeById(diagram, diagramTargetElementId);
                if (diagramSourceElement.isPresent() && diagramTargetElement.isPresent()) {
                    //@formatter:off
                    return () -> compatibleConnectorToolsProviders.stream()
                            .map(provider -> provider.getConnectorTools(diagramSourceElement.get(), diagramTargetElement.get(), diagram, editingContext))
                            .flatMap(List::stream)
                            .filter(tool -> tool.getId().equals(searchedToolId))
                            .filter(SingleClickOnTwoDiagramElementsTool.class::isInstance)
                            .map(SingleClickOnTwoDiagramElementsTool.class::cast)
                            .findFirst();
                    //@formatter:on
                }
            }
        }
        return () -> Optional.empty();
    }

}
