/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
package org.eclipse.sirius.web.application.undo.services.recorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventConsumer;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DeleteFromDiagramInput;
import org.eclipse.sirius.components.collaborative.representations.change.IRepresentationChange;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.appearance.IAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.undoredo.DiagramNodeLayoutEvent;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.undo.services.api.INodeAppearanceChangeUndoRecorder;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramFadeElementChange;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramHideElementChange;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramNodeAppearanceChange;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramNodeLayoutChange;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramPinElementChange;
import org.springframework.stereotype.Service;

/**
 * Use to record data needed to perform the undo for the appearance changes of a node after it was deleted.
 *
 * @author mcharfadi
 */
@Service
public class DeleteFromDiagramChangeRecorder implements IDiagramEventConsumer {

    private final IDiagramQueryService diagramQueryService;

    private final List<INodeAppearanceChangeUndoRecorder> nodeAppearanceChangeUndoRecorders;

    public DeleteFromDiagramChangeRecorder(IDiagramQueryService diagramQueryService, List<INodeAppearanceChangeUndoRecorder> nodeAppearanceChangeUndoRecorders) {
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.nodeAppearanceChangeUndoRecorders = Objects.requireNonNull(nodeAppearanceChangeUndoRecorders);
    }

    @Override
    public void accept(IEditingContext editingContext, Diagram previousDiagram, List<IDiagramEvent> diagramEvents, List<ViewDeletionRequest> viewDeletionRequests, List<ViewCreationRequest> viewCreationRequests, ChangeDescription changeDescription) {
        if (editingContext instanceof EditingContext siriusEditingContext && changeDescription.getInput() instanceof DeleteFromDiagramInput deleteFromDiagramInput) {
            List<IAppearanceChange> undoAppearanceChanges = new ArrayList<>();
            List<IRepresentationChange> representationChanges = new ArrayList<>();

            deleteFromDiagramInput.nodeIds().forEach(nodeId -> {
                Optional<Node> optionalPreviousNode = this.diagramQueryService.findNodeById(previousDiagram, nodeId);
                if (optionalPreviousNode.isPresent()) {
                    var previousNode = optionalPreviousNode.get();
                    nodeAppearanceChangeUndoRecorders.stream()
                            .filter(handler -> handler.canHandle(previousNode))
                            .forEach(handler -> undoAppearanceChanges.addAll(handler.computeUndoNodeAppearanceChanges(previousNode, Optional.empty())));

                    if (previousNode.isPinned()) {
                        var diagramPinElementChange = new DiagramPinElementChange(deleteFromDiagramInput.id(), deleteFromDiagramInput.representationId(), Set.of(previousNode.getId()), true, false);
                        representationChanges.add(diagramPinElementChange);
                    }
                    if (previousNode.getState().equals(ViewModifier.Faded)) {
                        var diagramFadeElementChange = new DiagramFadeElementChange(deleteFromDiagramInput.id(), deleteFromDiagramInput.representationId(), Set.of(previousNode.getId()), true, false);
                        representationChanges.add(diagramFadeElementChange);
                    }
                    if (previousNode.getState().equals(ViewModifier.Hidden)) {
                        var diagramHideElementChange = new DiagramHideElementChange(deleteFromDiagramInput.id(), deleteFromDiagramInput.representationId(), Set.of(previousNode.getId()), true, false);
                        representationChanges.add(diagramHideElementChange);
                    }

                    var previousNodeLayoutData = previousDiagram.getLayoutData().nodeLayoutData();
                    if (previousNodeLayoutData.containsKey(previousNode.getId())) {
                        var undoPositionEvent = new DiagramNodeLayoutEvent(previousNode.getId(), previousNodeLayoutData.get(previousNode.getId()));
                        var nodeLayoutChange = new DiagramNodeLayoutChange(deleteFromDiagramInput.id(), deleteFromDiagramInput.representationId(), List.of(undoPositionEvent), List.of());
                        representationChanges.add(nodeLayoutChange);
                    }
                }

                var diagramNodeAppearanceChange = new DiagramNodeAppearanceChange(deleteFromDiagramInput.id(), deleteFromDiagramInput.representationId(), undoAppearanceChanges, List.of());
                representationChanges.add(diagramNodeAppearanceChange);
            });

            if (!representationChanges.isEmpty()) {
                if (!siriusEditingContext.getInputId2RepresentationChanges().containsKey(deleteFromDiagramInput.id()) || siriusEditingContext.getInputId2RepresentationChanges().get(deleteFromDiagramInput.id()).isEmpty()) {
                    siriusEditingContext.getInputId2RepresentationChanges().put(deleteFromDiagramInput.id(), representationChanges);
                } else {
                    siriusEditingContext.getInputId2RepresentationChanges().get(deleteFromDiagramInput.id()).addAll(representationChanges);
                }
            }
        }
    }

}
