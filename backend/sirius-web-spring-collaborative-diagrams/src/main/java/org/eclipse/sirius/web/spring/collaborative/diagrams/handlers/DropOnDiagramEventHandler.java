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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.events.CreationEvent;
import org.eclipse.sirius.web.representations.Status;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeDescription;
import org.eclipse.sirius.web.spring.collaborative.api.ChangeKind;
import org.eclipse.sirius.web.spring.collaborative.api.EventHandlerResponse;
import org.eclipse.sirius.web.spring.collaborative.diagrams.DiagramChangeKind;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.web.spring.collaborative.diagrams.dto.DropOnDiagramInput;
import org.eclipse.sirius.web.spring.collaborative.diagrams.dto.DropOnDiagramSuccessPayload;
import org.eclipse.sirius.web.spring.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.springframework.stereotype.Service;

/**
 * Handle "Drop in Diagram" events.
 *
 * @author hmarchadour
 */
@Service
public class DropOnDiagramEventHandler implements IDiagramEventHandler {

    private final IObjectService objectService;

    private final IDiagramQueryService diagramQueryService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final ICollaborativeDiagramMessageService messageService;

    public DropOnDiagramEventHandler(IObjectService objectService, IDiagramQueryService diagramQueryService, IRepresentationDescriptionSearchService representationDescriptionSearchService,
            ICollaborativeDiagramMessageService messageService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof DropOnDiagramInput;
    }

    @Override
    public EventHandlerResponse handle(IEditingContext editingContext, IDiagramContext diagramContext, IDiagramInput diagramInput) {
        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), DropOnDiagramInput.class.getSimpleName());
        EventHandlerResponse result = new EventHandlerResponse(new ChangeDescription(ChangeKind.NOTHING, diagramInput.getRepresentationId()), new ErrorPayload(diagramInput.getId(), message));
        if (diagramInput instanceof DropOnDiagramInput) {
            DropOnDiagramInput input = (DropOnDiagramInput) diagramInput;
            result = new EventHandlerResponse(new ChangeDescription(ChangeKind.NOTHING, diagramInput.getRepresentationId()), new ErrorPayload(diagramInput.getId(), this.messageService.invalidDrop()));
            List<Object> objects = input.getObjectIds().stream().map(objectId -> this.objectService.getObject(editingContext, objectId)).flatMap(Optional::stream).collect(Collectors.toList());
            Diagram diagram = diagramContext.getDiagram();
            if (!objects.isEmpty()) {
                Optional<UUID> optionalDiagramTargetElementId = Optional.ofNullable(input.getDiagramTargetElementId());
                Status status = this.executeTool(editingContext, diagramContext, objects, optionalDiagramTargetElementId, input.getStartingPositionX(), input.getStartingPositionY());
                if (Objects.equals(Status.OK, status)) {
                    return new EventHandlerResponse(new ChangeDescription(DiagramChangeKind.DIAGRAM_LAYOUT_CHANGE, diagramInput.getRepresentationId()),
                            new DropOnDiagramSuccessPayload(diagramInput.getId(), diagram));
                }
            }
        }
        return result;
    }

    private Status executeTool(IEditingContext editingContext, IDiagramContext diagramContext, List<Object> objects, Optional<UUID> optionalDiagramTargetElementId, double startingPositionX,
            double startingPositionY) {
        Status result = Status.ERROR;
        Diagram diagram = diagramContext.getDiagram();
        Optional<Node> node = optionalDiagramTargetElementId.flatMap(diagramTargetElementId -> this.diagramQueryService.findNodeById(diagram, diagramTargetElementId));
        // @formatter:off
        var optionalDropHandler = this.representationDescriptionSearchService.findById(diagram.getDescriptionId())
            .filter(DiagramDescription.class::isInstance)
            .map(DiagramDescription.class::cast)
            .map(DiagramDescription::getDropHandler);
        // @formatter:on

        if (optionalDropHandler.isPresent()) {
            result = Status.OK;
            var dropHandler = optionalDropHandler.get();
            Position newPosition = Position.at(startingPositionX, startingPositionY);
            diagramContext.setDiagramEvent(new CreationEvent(newPosition));
            for (Object self : objects) {
                VariableManager variableManager = new VariableManager();
                if (node.isPresent()) {
                    variableManager.put(Node.SELECTED_NODE, node.get());
                }
                variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
                variableManager.put(IDiagramContext.DIAGRAM_CONTEXT, diagramContext);
                variableManager.put(VariableManager.SELF, self);
                Status dropResult = dropHandler.apply(variableManager);
                if (result != Status.ERROR) {
                    // Let all drops finished but keep the error state
                    result = dropResult;
                }
            }
        }
        return result;
    }
}
