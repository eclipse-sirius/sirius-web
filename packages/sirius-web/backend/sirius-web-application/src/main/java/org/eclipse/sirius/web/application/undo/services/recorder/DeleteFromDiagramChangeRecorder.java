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

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventConsumer;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolInput;
import org.eclipse.sirius.components.collaborative.representations.change.IRepresentationChange;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.appearance.IAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.undoredo.DiagramLabelLayoutEvent;
import org.eclipse.sirius.components.diagrams.events.undoredo.DiagramNodeLayoutEvent;
import org.eclipse.sirius.components.diagrams.layoutdata.LabelLayoutData;
import org.eclipse.sirius.components.view.emf.diagram.tools.DeleteMultipleDiagramElementToolHandler;
import org.eclipse.sirius.components.view.emf.diagram.tools.DeleteOneDiagramElementToolHandler;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.undo.services.api.IEdgeAppearanceChangeUndoRecorder;
import org.eclipse.sirius.web.application.undo.services.api.ILabelAppearanceChangeUndoRecorder;
import org.eclipse.sirius.web.application.undo.services.api.INodeAppearanceChangeUndoRecorder;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramFadeElementChange;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramHideElementChange;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramLabelAppearanceChange;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramLabelLayoutChange;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramNodeAppearanceChange;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramNodeLayoutChange;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramPinElementChange;
import org.springframework.stereotype.Service;

/**
 * Used to record data needed to perform the undo for the appearance changes of a node after it was deleted.
 *
 * @author mcharfadi
 */
@Service
public class DeleteFromDiagramChangeRecorder implements IDiagramEventConsumer {

    private final IDiagramQueryService diagramQueryService;

    private final List<INodeAppearanceChangeUndoRecorder> nodeAppearanceChangeUndoRecorders;

    private final IEdgeAppearanceChangeUndoRecorder edgeAppearanceChangeUndoRecorder;

    private final List<ILabelAppearanceChangeUndoRecorder> labelAppearanceChangeUndoRecorders;

    public DeleteFromDiagramChangeRecorder(IDiagramQueryService diagramQueryService, List<INodeAppearanceChangeUndoRecorder> nodeAppearanceChangeUndoRecorders, IEdgeAppearanceChangeUndoRecorder edgeAppearanceChangeUndoRecorder, List<ILabelAppearanceChangeUndoRecorder> labelAppearanceChangeUndoRecorders) {
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.nodeAppearanceChangeUndoRecorders = Objects.requireNonNull(nodeAppearanceChangeUndoRecorders);
        this.edgeAppearanceChangeUndoRecorder = Objects.requireNonNull(edgeAppearanceChangeUndoRecorder);
        this.labelAppearanceChangeUndoRecorders = Objects.requireNonNull(labelAppearanceChangeUndoRecorders);
    }

    @Override
    public void accept(IEditingContext editingContext, Diagram previousDiagram, List<IDiagramEvent> diagramEvents, List<ViewDeletionRequest> viewDeletionRequests, List<ViewCreationRequest> viewCreationRequests, ChangeDescription changeDescription) {
        if (editingContext instanceof EditingContext siriusEditingContext && changeDescription.getInput() instanceof InvokeSingleClickOnDiagramElementToolInput invokeSingleClickOnDiagramElementToolInput && (invokeSingleClickOnDiagramElementToolInput.toolId()
                .equals(DeleteOneDiagramElementToolHandler.DELETE_ELEMENT_TOOL_ID) || invokeSingleClickOnDiagramElementToolInput.toolId()
                .equals(DeleteMultipleDiagramElementToolHandler.DELETE_ELEMENT_TOOL_ID))) {
            List<IAppearanceChange> undoAppearanceChanges = new ArrayList<>();
            List<IRepresentationChange> representationChanges = new ArrayList<>();

            invokeSingleClickOnDiagramElementToolInput.diagramElementIds().forEach(diagramElementId -> {
                Optional<Edge> optionalPreviousEdge = this.diagramQueryService.findEdgeById(previousDiagram, diagramElementId);
                if (optionalPreviousEdge.isPresent()) {
                    var previousEdge = optionalPreviousEdge.get();
                    undoAppearanceChanges.addAll(this.edgeAppearanceChangeUndoRecorder.computeEdgeAppearanceChanges(previousEdge, Optional.empty()));
                } else {
                    Optional<Node> optionalPreviousNode = this.diagramQueryService.findNodeById(previousDiagram, diagramElementId);
                    if (optionalPreviousNode.isPresent()) {
                        var previousNode = optionalPreviousNode.get();
                        this.nodeAppearanceChangeUndoRecorders.stream()
                                .filter(handler -> handler.canHandle(previousNode))
                                .forEach(handler -> undoAppearanceChanges.addAll(handler.computeUndoNodeAppearanceChanges(previousNode, Optional.empty())));

                        if (previousNode.isPinned()) {
                            var diagramPinElementChange = new DiagramPinElementChange(invokeSingleClickOnDiagramElementToolInput.id(), invokeSingleClickOnDiagramElementToolInput.representationId(), Set.of(previousNode.getId()), true, false);
                            representationChanges.add(diagramPinElementChange);
                        }
                        if (previousNode.getState().equals(ViewModifier.Faded)) {
                            var diagramFadeElementChange = new DiagramFadeElementChange(invokeSingleClickOnDiagramElementToolInput.id(), invokeSingleClickOnDiagramElementToolInput.representationId(), Set.of(previousNode.getId()), true, false);
                            representationChanges.add(diagramFadeElementChange);
                        }
                        if (previousNode.getState().equals(ViewModifier.Hidden)) {
                            var diagramHideElementChange = new DiagramHideElementChange(invokeSingleClickOnDiagramElementToolInput.id(), invokeSingleClickOnDiagramElementToolInput.representationId(), Set.of(previousNode.getId()), true, false);
                            representationChanges.add(diagramHideElementChange);
                        }

                        var previousNodeLayoutData = previousDiagram.getLayoutData().nodeLayoutData();
                        if (previousNodeLayoutData.containsKey(previousNode.getId())) {
                            var undoPositionEvent = new DiagramNodeLayoutEvent(previousNode.getId(), previousNodeLayoutData.get(previousNode.getId()));
                            var nodeLayoutChange = new DiagramNodeLayoutChange(invokeSingleClickOnDiagramElementToolInput.id(), invokeSingleClickOnDiagramElementToolInput.representationId(), List.of(undoPositionEvent), List.of());
                            representationChanges.add(nodeLayoutChange);
                        }

                        previousNode.getOutsideLabels().forEach(label -> {
                            var previousLabelLayoutData = previousDiagram.getLayoutData().labelLayoutData();
                            if (previousLabelLayoutData.containsKey(label.id())) {
                                var diagramLabelLayoutChange = this.getDiagramNodeLabelChange(label.id(), previousLabelLayoutData.get(label.id()), invokeSingleClickOnDiagramElementToolInput.representationId());
                                representationChanges.addAll(diagramLabelLayoutChange);
                            }
                            var undoLabelAppearanceChanges = new ArrayList<IAppearanceChange>();
                            this.labelAppearanceChangeUndoRecorders.stream()
                                    .filter(labelAppearanceChangeUndoRecorder -> labelAppearanceChangeUndoRecorder.canHandle(previousNode))
                                    .findFirst()
                                    .map(labelAppearanceChangeUndoRecorder -> labelAppearanceChangeUndoRecorder.computeUndoLabelAppearanceChanges(previousNode, label.id(), Optional.empty()))
                                    .map(undoLabelAppearanceChanges::addAll);
                            var diagramNodeLabelAppearanceChange = new DiagramLabelAppearanceChange(invokeSingleClickOnDiagramElementToolInput.id(), invokeSingleClickOnDiagramElementToolInput.representationId(), undoLabelAppearanceChanges, List.of());
                            representationChanges.add(diagramNodeLabelAppearanceChange);
                        });

                        Optional.ofNullable(previousNode.getInsideLabel())
                                .ifPresent(label -> {
                                    var previousLabelLayoutData = previousDiagram.getLayoutData().labelLayoutData();
                                    if (previousLabelLayoutData.containsKey(label.getId())) {
                                        var diagramLabelLayoutChange = this.getDiagramNodeLabelChange(label.getId(), previousLabelLayoutData.get(label.getId()),
                                                invokeSingleClickOnDiagramElementToolInput.representationId());
                                        representationChanges.addAll(diagramLabelLayoutChange);
                                    }
                                    var undoLabelAppearanceChanges = new ArrayList<IAppearanceChange>();
                                    this.labelAppearanceChangeUndoRecorders.stream()
                                            .filter(labelAppearanceChangeUndoRecorder -> labelAppearanceChangeUndoRecorder.canHandle(previousNode))
                                            .findFirst()
                                            .map(labelAppearanceChangeUndoRecorder -> labelAppearanceChangeUndoRecorder.computeUndoLabelAppearanceChanges(previousNode, label.getId(), Optional.empty()))
                                            .map(undoLabelAppearanceChanges::addAll);
                                    var diagramNodeLabelAppearanceChange = new DiagramLabelAppearanceChange(invokeSingleClickOnDiagramElementToolInput.id(), invokeSingleClickOnDiagramElementToolInput.representationId(), undoLabelAppearanceChanges, List.of());
                                    representationChanges.add(diagramNodeLabelAppearanceChange);
                                });
                    }
                }
            });

            if (!undoAppearanceChanges.isEmpty()) {
                var diagramNodeAppearanceChange = new DiagramNodeAppearanceChange(invokeSingleClickOnDiagramElementToolInput.id(), invokeSingleClickOnDiagramElementToolInput.representationId(), undoAppearanceChanges, List.of());
                representationChanges.add(diagramNodeAppearanceChange);
            }

            if (!representationChanges.isEmpty()) {
                if (!siriusEditingContext.getInputId2RepresentationChanges().containsKey(invokeSingleClickOnDiagramElementToolInput.id()) || siriusEditingContext.getInputId2RepresentationChanges()
                        .get(invokeSingleClickOnDiagramElementToolInput.id()).isEmpty()) {
                    siriusEditingContext.getInputId2RepresentationChanges().put(invokeSingleClickOnDiagramElementToolInput.id(), representationChanges);
                } else {
                    siriusEditingContext.getInputId2RepresentationChanges().get(invokeSingleClickOnDiagramElementToolInput.id()).addAll(representationChanges);
                }
            }
        }
    }

    private List<IRepresentationChange> getDiagramNodeLabelChange(String labelId, LabelLayoutData previousLabelLayoutData, String representationId) {
        List<IRepresentationChange> representationChanges = new ArrayList<>();
        var undoPositionEvent = new DiagramLabelLayoutEvent(labelId, previousLabelLayoutData);
        var nodeLayoutChange = new DiagramLabelLayoutChange(UUID.fromString(labelId), representationId, List.of(undoPositionEvent), List.of());
        representationChanges.add(nodeLayoutChange);
        return representationChanges;
    }

}
