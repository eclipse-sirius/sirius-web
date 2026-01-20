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
import java.util.UUID;
import java.util.stream.Stream;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventConsumer;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.representations.change.IRepresentationChange;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.components.NodeContainmentKind;
import org.eclipse.sirius.components.diagrams.components.NodeIdProvider;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.appearance.IAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.undoredo.DiagramLabelLayoutEvent;
import org.eclipse.sirius.components.diagrams.events.undoredo.DiagramNodeLayoutEvent;
import org.eclipse.sirius.components.diagrams.layoutdata.LabelLayoutData;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.undo.services.api.INodeAppearanceChangeUndoRecorder;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramFadeElementChange;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramHideElementChange;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramLabelLayoutChange;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramNodeAppearanceChange;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramNodeLayoutChange;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramNodeViewRequestChange;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramPinElementChange;
import org.springframework.stereotype.Service;

/**
 * Use to record data needed to perform the undo/redo for the ViewCreationRequest and ViewDeletionRequest changes.
 *
 * @author mcharfadi
 */
@Service
public class ViewRequestChangeRecorder implements IDiagramEventConsumer {

    private final IDiagramQueryService diagramQueryService;

    private final List<INodeAppearanceChangeUndoRecorder> nodeAppearanceChangeUndoRecorders;

    public ViewRequestChangeRecorder(IDiagramQueryService diagramQueryService, List<INodeAppearanceChangeUndoRecorder> nodeAppearanceChangeUndoRecorders) {
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.nodeAppearanceChangeUndoRecorders = Objects.requireNonNull(nodeAppearanceChangeUndoRecorders);
    }

    @Override
    public void accept(IEditingContext editingContext, Diagram previousDiagram, List<IDiagramEvent> diagramEvents, List<ViewDeletionRequest> viewDeletionRequests, List<ViewCreationRequest> viewCreationRequests, ChangeDescription changeDescription) {
        if (editingContext instanceof EditingContext siriusEditingContext && changeDescription.getInput() instanceof IDiagramInput diagramInput) {
            List<ViewCreationRequest> undoViewCreationRequests = new ArrayList<>();
            List<ViewDeletionRequest> undoViewDeletionRequests = new ArrayList<>();
            List<ViewCreationRequest> redoViewCreationRequests = new ArrayList<>(viewCreationRequests);
            List<ViewDeletionRequest> redoViewDeletionRequests = new ArrayList<>(viewDeletionRequests);
            List<IRepresentationChange> representationChanges = new ArrayList<>();
            List<IAppearanceChange> undoAppearanceChanges = new ArrayList<>();

            viewDeletionRequests.forEach(viewDeletionRequest -> {
                var nodeId = viewDeletionRequest.getElementId();
                var optionalPreviousNode = this.diagramQueryService.findNodeById(previousDiagram, nodeId);
                if (optionalPreviousNode.isPresent()) {
                    var previousNode = optionalPreviousNode.get();
                    var containmentKind = NodeContainmentKind.CHILD_NODE;
                    if (previousNode.isBorderNode()) {
                        containmentKind = NodeContainmentKind.BORDER_NODE;
                    }
                    Optional<String> optionalParentId = Optional.empty();
                    var parentId = previousDiagram.getId();
                    for (Node currentNode : previousDiagram.getNodes()) {
                        optionalParentId = this.findParentId(currentNode, previousNode.getId());
                    }
                    if (optionalParentId.isPresent()) {
                        parentId = optionalParentId.get();
                    }

                    var creationRequest = ViewCreationRequest.newViewCreationRequest()
                            .containmentKind(containmentKind)
                            .parentElementId(parentId)
                            .descriptionId(previousNode.getDescriptionId())
                            .targetObjectId(previousNode.getTargetObjectId())
                            .build();
                    undoViewCreationRequests.add(creationRequest);

                    this.nodeAppearanceChangeUndoRecorders.stream()
                            .filter(handler -> handler.canHandle(previousNode))
                            .forEach(handler -> undoAppearanceChanges.addAll(handler.computeUndoNodeAppearanceChanges(previousNode, Optional.empty())));

                    if (previousNode.isPinned()) {
                        var diagramPinElementChange = new DiagramPinElementChange(diagramInput.id(), diagramInput.representationId(), Set.of(previousNode.getId()), true, false);
                        representationChanges.add(diagramPinElementChange);
                    }
                    if (previousNode.getState().equals(ViewModifier.Faded)) {
                        var diagramFadeElementChange = new DiagramFadeElementChange(diagramInput.id(), diagramInput.representationId(), Set.of(previousNode.getId()), true, false);
                        representationChanges.add(diagramFadeElementChange);
                    }
                    if (previousNode.getState().equals(ViewModifier.Hidden)) {
                        var diagramHideElementChange = new DiagramHideElementChange(diagramInput.id(), diagramInput.representationId(), Set.of(previousNode.getId()), true, false);
                        representationChanges.add(diagramHideElementChange);
                    }

                    var previousNodeLayoutData = previousDiagram.getLayoutData().nodeLayoutData();
                    if (previousNodeLayoutData.containsKey(previousNode.getId())) {
                        var undoPositionEvent = new DiagramNodeLayoutEvent(previousNode.getId(), previousNodeLayoutData.get(previousNode.getId()));
                        var nodeLayoutChange = new DiagramNodeLayoutChange(diagramInput.id(), diagramInput.representationId(), List.of(undoPositionEvent), List.of());
                        representationChanges.add(nodeLayoutChange);
                    }

                    previousNode.getOutsideLabels().forEach(label -> {
                        var previousLabelLayoutData = previousDiagram.getLayoutData().labelLayoutData();
                        if (previousLabelLayoutData.containsKey(label.id())) {
                            var diagramLabelLayoutChange = this.getDiagramNodeLabelChange(label.id(), previousLabelLayoutData.get(label.id()), diagramInput);
                            representationChanges.addAll(diagramLabelLayoutChange);
                        }
                    });

                    Optional.ofNullable(previousNode.getInsideLabel())
                            .ifPresent(label -> {
                                var previousLabelLayoutData = previousDiagram.getLayoutData().labelLayoutData();
                                if (previousLabelLayoutData.containsKey(label.getId())) {
                                    var diagramLabelLayoutChange = this.getDiagramNodeLabelChange(label.getId(), previousLabelLayoutData.get(label.getId()), diagramInput);
                                    representationChanges.addAll(diagramLabelLayoutChange);
                                }
                            });

                }
                var diagramNodeAppearanceChange = new DiagramNodeAppearanceChange(diagramInput.id(), diagramInput.representationId(), undoAppearanceChanges, List.of());
                representationChanges.add(diagramNodeAppearanceChange);
            });

            viewCreationRequests.forEach(viewCreationRequest -> {
                var nodeId = new NodeIdProvider().getNodeId(viewCreationRequest.getParentElementId(), viewCreationRequest.getDescriptionId(), viewCreationRequest.getContainmentKind(), viewCreationRequest.getTargetObjectId());
                var deletionRequest = ViewDeletionRequest.newViewDeletionRequest()
                        .elementId(nodeId)
                        .build();
                undoViewDeletionRequests.add(deletionRequest);
            });

            if (!undoViewCreationRequests.isEmpty() || !undoViewDeletionRequests.isEmpty()) {
                var diagramNodeViewRequestChange = new DiagramNodeViewRequestChange(diagramInput.id(), diagramInput.representationId(), undoViewCreationRequests, undoViewDeletionRequests, redoViewCreationRequests, redoViewDeletionRequests);
                representationChanges.add(diagramNodeViewRequestChange);
                if (!siriusEditingContext.getInputId2RepresentationChanges().containsKey(diagramInput.id()) || siriusEditingContext.getInputId2RepresentationChanges().get(diagramInput.id()).isEmpty()) {
                    siriusEditingContext.getInputId2RepresentationChanges().put(diagramInput.id(), representationChanges);
                } else {
                    siriusEditingContext.getInputId2RepresentationChanges().get(diagramInput.id()).addAll(representationChanges);
                }
            }
        }
    }

    private Optional<String> findParentId(Node currentNode, String childId) {
        var parentNode = Stream.concat(currentNode.getChildNodes().stream(), currentNode.getBorderNodes().stream())
                .map(Node::getId)
                .filter(id -> id.equals(childId))
                .findFirst();

        if (parentNode.isEmpty()) {
            var childNodes = Stream.concat(currentNode.getBorderNodes().stream(), currentNode.getChildNodes().stream()).toList();
            for (Node node : childNodes) {
                return this.findParentId(node, childId);
            }
        }

        return parentNode;
    }

    private List<IRepresentationChange> getDiagramNodeLabelChange(String labelId, LabelLayoutData previousLabelLayoutData, IDiagramInput input) {
        List<IRepresentationChange> representationChanges = new ArrayList<>();
        var undoPositionEvent = new DiagramLabelLayoutEvent(labelId, previousLabelLayoutData);
        var nodeLayoutChange = new DiagramLabelLayoutChange(UUID.fromString(labelId), input.representationId(), List.of(undoPositionEvent), List.of());
        representationChanges.add(nodeLayoutChange);
        return representationChanges;
    }
}
