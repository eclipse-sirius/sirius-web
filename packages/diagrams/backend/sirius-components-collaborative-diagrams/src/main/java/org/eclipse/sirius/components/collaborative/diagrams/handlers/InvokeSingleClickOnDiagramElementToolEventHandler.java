/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo and others.
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
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IToolService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolSuccessPayload;
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
import org.eclipse.sirius.components.diagrams.events.SinglePositionEvent;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.representations.WorkbenchSelection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handle "Invoke single click on diagram element tool" events.
 *
 * @author pcdavid
 */
@Service
public class InvokeSingleClickOnDiagramElementToolEventHandler implements IDiagramEventHandler {

    private final Logger logger = LoggerFactory.getLogger(InvokeSingleClickOnDiagramElementToolEventHandler.class);

    private final IObjectService objectService;

    private final IDiagramQueryService diagramQueryService;

    private final IToolService toolService;

    private final ICollaborativeDiagramMessageService messageService;

    private final Counter counter;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    public InvokeSingleClickOnDiagramElementToolEventHandler(IObjectService objectService, IDiagramQueryService diagramQueryService, IToolService toolService,
            ICollaborativeDiagramMessageService messageService, MeterRegistry meterRegistry, IRepresentationDescriptionSearchService representationDescriptionSearchService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.toolService = Objects.requireNonNull(toolService);
        this.messageService = Objects.requireNonNull(messageService);

        // @formatter:off
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
        // @formatter:on

        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof InvokeSingleClickOnDiagramElementToolInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext, IDiagramInput diagramInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), InvokeSingleClickOnDiagramElementToolInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.getId(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.getRepresentationId(), diagramInput);

        if (diagramInput instanceof InvokeSingleClickOnDiagramElementToolInput) {
            InvokeSingleClickOnDiagramElementToolInput input = (InvokeSingleClickOnDiagramElementToolInput) diagramInput;
            Diagram diagram = diagramContext.getDiagram();
            // @formatter:off
            var optionalTool = this.toolService.findToolById(editingContext, diagram, input.getToolId())
                    .filter(SingleClickOnDiagramElementTool.class::isInstance)
                    .map(SingleClickOnDiagramElementTool.class::cast);
            // @formatter:on
            if (optionalTool.isPresent()) {
                IStatus status = this.executeTool(editingContext, diagramContext, input.getDiagramElementId(), optionalTool.get(), input.getStartingPositionX(), input.getStartingPositionY(),
                        input.getSelectedObjectId());
                if (status instanceof Success) {
                    WorkbenchSelection newSelection = null;
                    Object newSelectionParameter = ((Success) status).getParameters().get(Success.NEW_SELECTION);
                    if (newSelectionParameter instanceof WorkbenchSelection) {
                        newSelection = (WorkbenchSelection) newSelectionParameter;
                    }
                    payload = new InvokeSingleClickOnDiagramElementToolSuccessPayload(diagramInput.getId(), newSelection);
                    changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, diagramInput.getRepresentationId(), diagramInput);
                } else if (status instanceof Failure) {
                    payload = new ErrorPayload(diagramInput.getId(), ((Failure) status).getMessage());
                }
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private IStatus executeTool(IEditingContext editingContext, IDiagramContext diagramContext, String diagramElementId, SingleClickOnDiagramElementTool tool, double startingPositionX,
            double startingPositionY, String selectedObjectId) {
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
            variableManager.put(Environment.ENVIRONMENT, new Environment(Environment.SIRIUS_COMPONENTS));
            variableManager.put(VariableManager.SELF, self.get());
            variableManager.put(Node.SELECTED_NODE, node.orElse(null));

            String selectionDescriptionId = tool.getSelectionDescriptionId();
            if (selectionDescriptionId != null && selectedObjectId != null) {
                var selectionDescriptionOpt = this.representationDescriptionSearchService.findById(editingContext, selectionDescriptionId);
                var selectedObjectOpt = this.objectService.getObject(editingContext, selectedObjectId);
                if (selectionDescriptionOpt.isPresent() && selectedObjectOpt.isPresent()) {
                    variableManager.put(SingleClickOnDiagramElementTool.SELECTED_OBJECT, selectedObjectOpt.get());
                }
            }
            if (selectionDescriptionId == null || selectedObjectId != null) {
                result = tool.getHandler().apply(variableManager);
                Position newPosition = Position.at(startingPositionX, startingPositionY);

                diagramContext.setDiagramEvent(new SinglePositionEvent(newPosition));
            }
        }
        return result;
    }

}
