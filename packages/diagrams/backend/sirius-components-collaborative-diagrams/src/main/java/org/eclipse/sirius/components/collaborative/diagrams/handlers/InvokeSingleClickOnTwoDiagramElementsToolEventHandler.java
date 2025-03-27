/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IConnectorToolsProvider;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IToolService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnTwoDiagramElementsToolInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolVariable;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.Environment;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
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

    private final IObjectSearchService objectSearchService;

    private final IDiagramQueryService diagramQueryService;

    private final IToolService toolService;

    private final ICollaborativeDiagramMessageService messageService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final List<IConnectorToolsProvider> connectorToolsProviders;

    private final Counter counter;

    public InvokeSingleClickOnTwoDiagramElementsToolEventHandler(IObjectSearchService objectSearchService, IDiagramQueryService diagramQueryService, IToolService toolService,
            ICollaborativeDiagramMessageService messageService, IRepresentationDescriptionSearchService representationDescriptionSearchService, List<IConnectorToolsProvider> connectorToolsProviders,
            MeterRegistry meterRegistry) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.toolService = Objects.requireNonNull(toolService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.connectorToolsProviders = Objects.requireNonNull(connectorToolsProviders);
        this.messageService = Objects.requireNonNull(messageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof InvokeSingleClickOnTwoDiagramElementsToolInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), InvokeSingleClickOnTwoDiagramElementsToolInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.representationId(), diagramInput);

        if (diagramInput instanceof InvokeSingleClickOnTwoDiagramElementsToolInput input) {
            Diagram diagram = diagramContext.getDiagram();
            var optionalTool = this.toolService.findToolById(editingContext, diagram, input.toolId())
                    .filter(SingleClickOnTwoDiagramElementsTool.class::isInstance)
                    .map(SingleClickOnTwoDiagramElementsTool.class::cast)
                    .or(this.findConnectorToolById(input.diagramSourceElementId(), input.diagramTargetElementId(), editingContext, diagram, input.toolId()));
            if (optionalTool.isPresent()) {
                IStatus status = this.executeTool(editingContext, diagramContext, input, optionalTool.get());
                if (status instanceof Success success) {
                    WorkbenchSelection newSelection = null;
                    Object newSelectionParameter = success.getParameters().get(Success.NEW_SELECTION);
                    if (newSelectionParameter instanceof WorkbenchSelection workbenchSelection) {
                        newSelection = workbenchSelection;
                    }
                    payload = new InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload(diagramInput.id(), newSelection, success.getMessages());
                    changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, diagramInput.representationId(), diagramInput);
                } else if (status instanceof Failure failure) {
                    payload = new ErrorPayload(diagramInput.id(), failure.getMessages());
                }
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private IStatus executeTool(IEditingContext editingContext, IDiagramContext diagramContext, InvokeSingleClickOnTwoDiagramElementsToolInput input, SingleClickOnTwoDiagramElementsTool tool) {
        String sourceNodeId = input.diagramSourceElementId();
        String targetNodeId = input.diagramTargetElementId();
        IStatus result = new Failure("");
        Diagram diagram = diagramContext.getDiagram();

        var sourceDiagramElement = this.diagramQueryService.findDiagramElementById(diagram, sourceNodeId);
        var targetDiagramElement = this.diagramQueryService.findDiagramElementById(diagram, targetNodeId);
        Optional<Object> source = Optional.empty();
        Optional<Object> target = Optional.empty();

        if (sourceDiagramElement.isPresent() && targetDiagramElement.isPresent()) {
            if (sourceDiagramElement.get() instanceof Node node) {
                source = this.objectSearchService.getObject(editingContext, node.getTargetObjectId());
            }
            if (sourceDiagramElement.get() instanceof Edge edge) {
                source = this.objectSearchService.getObject(editingContext, edge.getTargetObjectId());
            }

            if (targetDiagramElement.get() instanceof Node node) {
                target = this.objectSearchService.getObject(editingContext, node.getTargetObjectId());
            }
            if (targetDiagramElement.get() instanceof Edge edge) {
                target = this.objectSearchService.getObject(editingContext, edge.getTargetObjectId());
            }

            if (source.isPresent() && target.isPresent()) {
                VariableManager variableManager = new VariableManager();
                variableManager.put(IDiagramContext.DIAGRAM_CONTEXT, diagramContext);
                variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
                variableManager.put(Environment.ENVIRONMENT, new Environment(Environment.SIRIUS_COMPONENTS));
                variableManager.put(IDiagramService.DIAGRAM_SERVICES, new DiagramService(diagramContext));
                variableManager.put(EdgeDescription.SEMANTIC_EDGE_SOURCE, source.get());
                variableManager.put(EdgeDescription.SEMANTIC_EDGE_TARGET, target.get());
                variableManager.put(EdgeDescription.EDGE_SOURCE, sourceDiagramElement.get());
                variableManager.put(EdgeDescription.EDGE_TARGET, targetDiagramElement.get());

                input.variables().forEach(toolVariable -> this.addToolVariablesInVariableManager(toolVariable, editingContext, variableManager));

                result = tool.getHandler().apply(variableManager);
            }
        }
        return result;
    }

    private void addToolVariablesInVariableManager(ToolVariable toolvariable, IEditingContext editingContext, VariableManager variableManager) {
        switch (toolvariable.type()) {
            case STRING -> variableManager.put(toolvariable.name(), toolvariable.value());
            case OBJECT_ID -> {
                var optionalObject = this.objectSearchService.getObject(editingContext, toolvariable.value());
                variableManager.put(toolvariable.name(), optionalObject.orElse(null));
            }
            case OBJECT_ID_ARRAY -> {
                String value = toolvariable.value();
                List<String> objectsIds = List.of(value.split(","));
                List<Object> objects = objectsIds.stream()
                        .map(objectId -> this.objectSearchService.getObject(editingContext, objectId))
                        .map(optionalObject -> optionalObject.orElse(null))
                        .toList();
                variableManager.put(toolvariable.name(), objects);
            }
            default -> {
                //We do nothing, the variable type is not supported
            }
        }
    }

    private Supplier<Optional<SingleClickOnTwoDiagramElementsTool>> findConnectorToolById(String diagramSourceElementId, String diagramTargetElementId, IEditingContext editingContext, Diagram diagram,
            String searchedToolId) {
        var diagramDescription = this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId())
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast);

        if (diagramDescription.isPresent()) {
            List<IConnectorToolsProvider> compatibleConnectorToolsProviders = this.connectorToolsProviders.stream()
                    .filter(provider -> provider.canHandle(diagramDescription.get()))
                    .toList();

            if (!compatibleConnectorToolsProviders.isEmpty()) {
                var diagramSourceElement = this.diagramQueryService.findNodeById(diagram, diagramSourceElementId);
                var diagramTargetElement = this.diagramQueryService.findNodeById(diagram, diagramTargetElementId);
                if (diagramSourceElement.isPresent() && diagramTargetElement.isPresent()) {
                    return () -> compatibleConnectorToolsProviders.stream()
                            .map(provider -> provider.getConnectorTools(diagramSourceElement.get(), diagramTargetElement.get(), diagram, editingContext))
                            .flatMap(List::stream)
                            .filter(tool -> tool.getId().equals(searchedToolId))
                            .filter(SingleClickOnTwoDiagramElementsTool.class::isInstance)
                            .map(SingleClickOnTwoDiagramElementsTool.class::cast)
                            .findFirst();
                }
            }
        }
        return () -> Optional.empty();
    }

}
