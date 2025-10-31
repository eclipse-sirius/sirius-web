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

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramService;
import org.eclipse.sirius.components.collaborative.diagrams.SingleClickOnTwoDiagramElementToolHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IConnectorToolsProvider;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IToolHandlerProvider;
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
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.representations.WorkbenchSelection;
import org.springframework.stereotype.Service;
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

    private final ICollaborativeDiagramMessageService messageService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final List<IToolHandlerProvider> toolHandlerProviders;

    private final Counter counter;

    public InvokeSingleClickOnTwoDiagramElementsToolEventHandler(IObjectSearchService objectSearchService, IDiagramQueryService diagramQueryService,
                                                                 ICollaborativeDiagramMessageService messageService, IRepresentationDescriptionSearchService representationDescriptionSearchService, List<IConnectorToolsProvider> connectorToolsProviders, List<IToolHandlerProvider> toolHandlerProviders,
                                                                 MeterRegistry meterRegistry) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.messageService = Objects.requireNonNull(messageService);
        this.toolHandlerProviders = Objects.requireNonNull(toolHandlerProviders);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof InvokeSingleClickOnTwoDiagramElementsToolInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, DiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), InvokeSingleClickOnTwoDiagramElementsToolInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.representationId(), diagramInput);

        if (diagramInput instanceof InvokeSingleClickOnTwoDiagramElementsToolInput input) {
            Diagram diagram = diagramContext.diagram();

            var sourceDiagramElement = this.diagramQueryService.findDiagramElementById(diagram, input.diagramSourceElementId());
            var targetDiagramElement = this.diagramQueryService.findDiagramElementById(diagram, input.diagramTargetElementId());

            var diagramDescription = this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId())
                    .filter(DiagramDescription.class::isInstance)
                    .map(DiagramDescription.class::cast);

            if (diagramDescription.isPresent() && sourceDiagramElement.isPresent() && targetDiagramElement.isPresent()) {
                var optionalToolHandler = this.toolHandlerProviders.stream()
                        .filter(toolHandlerProvider -> toolHandlerProvider.canHandle(editingContext, diagramDescription.get(), sourceDiagramElement.get().getDescriptionId(), input.toolId()))
                        .findFirst()
                        .flatMap(toolHandlerProvider -> toolHandlerProvider.getToolHandler(editingContext, diagramDescription.get(), sourceDiagramElement.get().getDescriptionId(), input.toolId()))
                        .filter(SingleClickOnTwoDiagramElementToolHandler.class::isInstance)
                        .map(SingleClickOnTwoDiagramElementToolHandler.class::cast)
                        .filter(singleClickOnTwoDiagramElementToolHandler -> singleClickOnTwoDiagramElementToolHandler.targetCandidatesId().contains(targetDiagramElement.get().getDescriptionId()));

                if (optionalToolHandler.isPresent()) {
                    IStatus status = this.executeTool(editingContext, diagramContext, input, optionalToolHandler.get());
                    if (status instanceof Success success) {
                        WorkbenchSelection newSelection = getWorkbenchSelection(success);
                        payload = new InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload(diagramInput.id(), newSelection, success.getMessages());
                        changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, diagramInput.representationId(), diagramInput);
                    } else if (status instanceof Failure failure) {
                        payload = new ErrorPayload(diagramInput.id(), failure.getMessages());
                    }
                }
            }

        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private WorkbenchSelection getWorkbenchSelection(Success success) {
        Object newSelectionParameter = success.getParameters().get(Success.NEW_SELECTION);
        if (newSelectionParameter instanceof WorkbenchSelection workbenchSelection) {
            return workbenchSelection;
        }
        return null;
    }

    private IStatus executeTool(IEditingContext editingContext, DiagramContext diagramContext, InvokeSingleClickOnTwoDiagramElementsToolInput input, SingleClickOnTwoDiagramElementToolHandler singleClickOnTwoDiagramElementToolHandler) {
        String sourceNodeId = input.diagramSourceElementId();
        String targetNodeId = input.diagramTargetElementId();
        IStatus result = new Failure("");
        Diagram diagram = diagramContext.diagram();

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
                variableManager.put(DiagramContext.DIAGRAM_CONTEXT, diagramContext);
                variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
                variableManager.put(Environment.ENVIRONMENT, new Environment(Environment.SIRIUS_COMPONENTS));
                variableManager.put(IDiagramService.DIAGRAM_SERVICES, new DiagramService(diagramContext));
                variableManager.put(EdgeDescription.SEMANTIC_EDGE_SOURCE, source.get());
                variableManager.put(EdgeDescription.SEMANTIC_EDGE_TARGET, target.get());
                variableManager.put(EdgeDescription.EDGE_SOURCE, sourceDiagramElement.get());
                variableManager.put(EdgeDescription.EDGE_TARGET, targetDiagramElement.get());

                input.variables().forEach(toolVariable -> this.addToolVariablesInVariableManager(toolVariable, editingContext, variableManager));

                result = singleClickOnTwoDiagramElementToolHandler.handler().apply(variableManager);
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

}
