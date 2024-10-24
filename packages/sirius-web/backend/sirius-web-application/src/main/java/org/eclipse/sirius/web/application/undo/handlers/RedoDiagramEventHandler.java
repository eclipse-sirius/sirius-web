/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.application.undo.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.changes.LayoutDiagramRepresentationChange;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramRedoInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.FadeDiagramElementInput;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.core.api.representations.IRepresentationChangeEvent;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks;

/**
 * Diagram handler used to handle redo events.
 *
 * @author gcoutable
 */
@Service
public class RedoDiagramEventHandler implements IDiagramEventHandler {

    private final ICollaborativeDiagramMessageService messageService;

    public RedoDiagramEventHandler(ICollaborativeDiagramMessageService messageService) {
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean canHandle(IDiagramInput diagramInput) {
        return diagramInput instanceof DiagramRedoInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDiagramContext diagramContext,
            IDiagramInput diagramInput) {
        String message = this.messageService.invalidInput(diagramInput.getClass().getSimpleName(), FadeDiagramElementInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(diagramInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, diagramInput.representationId(), diagramInput);
        Map<String, Object> parameters = new HashMap<>();
        boolean semanticChange = false;
        boolean everythingWentWell = true;
        Optional<Diagram> optionalDiagram = Optional.empty();
        List<ViewCreationRequest> viewCreationRequests = new ArrayList<>();
        List<ViewDeletionRequest> viewDeletionRequests = new ArrayList<>();
        if (editingContext instanceof EditingContext siriusEditingContext && diagramInput instanceof DiagramRedoInput diagramRedoInput) {
            var emfChangeDescription = siriusEditingContext.getInputId2change().get(diagramRedoInput.mutationId());
            if (emfChangeDescription != null && !emfChangeDescription.getObjectChanges().isEmpty()) {
                emfChangeDescription.applyAndReverse();
                semanticChange = true;
            }

            var representationChangeEvents = siriusEditingContext.getRepresentationChangesDescription().get(diagramRedoInput.mutationId());
            boolean graphicalChange = representationChangeEvents != null && !representationChangeEvents.isEmpty();
            if (representationChangeEvents != null) {
                // TODO: tant que tous les changements graphiques peuvent s'oppérer sur la representation => c'est plus un [while] qu'un [for]
                for (IRepresentationChangeEvent change : representationChangeEvents) {
                    if (change instanceof LayoutDiagramRepresentationChange layoutDiagramRepresentationChange) {
                        optionalDiagram = this.handleLayoutDiagramChange(diagramContext, layoutDiagramRepresentationChange);
                        everythingWentWell = everythingWentWell && optionalDiagram.isPresent();
                    }
                    // TODO: put remaining diagram changes from `RedoEventHandler` here
                }
            }

            everythingWentWell = everythingWentWell && (semanticChange || graphicalChange);
        }

        if (everythingWentWell) {
            String changeKind = ChangeKind.NOTHING;
            if (!viewDeletionRequests.isEmpty()) {
                diagramContext.getViewDeletionRequests().addAll(viewDeletionRequests);
                changeKind = this.updateChangeKind(changeKind, ChangeKind.SEMANTIC_CHANGE);
            }
            if (!viewCreationRequests.isEmpty()) {
                diagramContext.getViewCreationRequests().addAll(viewCreationRequests);
                changeKind = this.updateChangeKind(changeKind, ChangeKind.SEMANTIC_CHANGE);
            }
            if (semanticChange) {
                changeKind = this.updateChangeKind(changeKind, ChangeKind.SEMANTIC_CHANGE);
            }
            if (optionalDiagram.isPresent()) {
                var newDiagram = optionalDiagram.get();
                parameters.put(IDiagramEventHandler.NEXT_DIAGRAM_PARAMETER, newDiagram);
                changeKind = this.updateChangeKind(changeKind, ChangeKind.LAYOUT_DIAGRAM);
            }
            payload = new SuccessPayload(diagramInput.id());
            changeDescriptionSink.tryEmitNext(new ChangeDescription(changeKind, editingContext.getId(), diagramInput, parameters));
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private String updateChangeKind(String actual, String toApply) {
        // TODO: update this to add priority on handled change kind.
        String updatedChangeKind = actual;
        if (toApply.equals(ChangeKind.SEMANTIC_CHANGE)) {
            updatedChangeKind = toApply;
        }
        if (updatedChangeKind.equals(ChangeKind.SEMANTIC_CHANGE)) {
            return  updatedChangeKind;
        }

        if (toApply.equals(ChangeKind.LAYOUT_DIAGRAM) && actual.equals(ChangeKind.NOTHING)) {
            updatedChangeKind = toApply;
        }

        return  updatedChangeKind;
    }

    private Optional<Diagram> handleLayoutDiagramChange(IDiagramContext diagramContext, LayoutDiagramRepresentationChange layoutDiagramRepresentationChange) {
        Optional<Diagram> optionalDiagram = Optional.empty();
        //TODO: Should check if the previous position is valid, if the position of an object in LayoutDiagramRepresentionChange#previousLayout is the current position of that same object in the current diagram
        boolean canRedoLayout = true;
        if (canRedoLayout) {
            //            layoutDiagramRepresentationChange.changes().nodeLayoutData().entrySet().stream()
            //                    .map(entry -> {
            //                        var currentNodeLayoutDataImpactedByChange = currentDiagram.getLayoutData().nodeLayoutData().get(entry.getKey());
            //                        if(currentNodeLayoutDataImpactedByChange == null) {
            //
            //                        }
            //                    });

            var newLayout = layoutDiagramRepresentationChange.newLayout();
            var laidOutDiagram = Diagram.newDiagram(diagramContext.getDiagram())
                    .layoutData(newLayout)
                    .build();
            optionalDiagram = Optional.of(laidOutDiagram);
        }

        return optionalDiagram;
    }
}
