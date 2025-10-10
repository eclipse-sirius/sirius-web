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

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventConsumer;
import org.eclipse.sirius.components.collaborative.diagrams.dto.EdgeLayoutDataInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LabelLayoutDataInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LayoutDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.NodeLayoutDataInput;
import org.eclipse.sirius.components.collaborative.representations.change.IRepresentationChange;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.undoredo.DiagramEdgeLayoutEvent;
import org.eclipse.sirius.components.diagrams.events.undoredo.DiagramLabelLayoutEvent;
import org.eclipse.sirius.components.diagrams.events.undoredo.DiagramNodeLayoutEvent;
import org.eclipse.sirius.components.diagrams.layoutdata.EdgeLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.HandleLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.LabelLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.Position;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramEdgeLayoutChange;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramLabelLayoutChange;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramNodeLayoutChange;
import org.springframework.stereotype.Service;

/**
 * Used to record data needed to perform the undo for layout changes.
 *
 * @author mcharfadi
 */
@Service
public class DiagramLayoutChangeRecorder implements IDiagramEventConsumer {

    @Override
    public void accept(IEditingContext editingContext, Diagram previousDiagram, List<IDiagramEvent> diagramEvents, List<ViewDeletionRequest> viewDeletionRequests, List<ViewCreationRequest> viewCreationRequests, ChangeDescription changeDescription) {
        if (editingContext instanceof EditingContext siriusEditingContext && changeDescription.getInput() instanceof LayoutDiagramInput layoutDiagramInput) {
            List<IRepresentationChange> representationChanges = new ArrayList<>();
            List<DiagramNodeLayoutEvent> undoNodeLayoutEvents = new ArrayList<>();
            List<DiagramNodeLayoutEvent> redoNodeLayoutEvents = new ArrayList<>();
            List<DiagramLabelLayoutEvent> undoLabelLayoutEvents = new ArrayList<>();
            List<DiagramLabelLayoutEvent> redoLabelLayoutEvents = new ArrayList<>();
            List<DiagramEdgeLayoutEvent> undoEdgeLayoutEvents = new ArrayList<>();
            List<DiagramEdgeLayoutEvent> redoEdgeLayoutEvents = new ArrayList<>();

            var previousNodeLayout = previousDiagram.getLayoutData().nodeLayoutData();
            layoutDiagramInput.diagramLayoutData().nodeLayoutData()
                    .forEach(updatedNodeLayoutInput -> {
                        var previousNodeLayoutData = previousNodeLayout.get(updatedNodeLayoutInput.id());
                        if (previousNodeLayoutData != null) {
                            if (!isSameNodeLayoutData(previousNodeLayoutData, updatedNodeLayoutInput)) {
                                var updatedNodeLayout = new NodeLayoutData(updatedNodeLayoutInput.id(), updatedNodeLayoutInput.position(), updatedNodeLayoutInput.size(), updatedNodeLayoutInput.resizedByUser(), updatedNodeLayoutInput.movedByUser(), updatedNodeLayoutInput.handleLayoutData(), updatedNodeLayoutInput.minComputedSize());
                                undoNodeLayoutEvents.add(new DiagramNodeLayoutEvent(previousNodeLayoutData.id(), previousNodeLayoutData));
                                redoNodeLayoutEvents.add(new DiagramNodeLayoutEvent(updatedNodeLayoutInput.id(), updatedNodeLayout));
                            }
                        }
                    });

            var previousLabelLayout = previousDiagram.getLayoutData().labelLayoutData();
            layoutDiagramInput.diagramLayoutData().labelLayoutData()
                    .forEach(updatedLabelLayoutInput -> {
                        var previousLabelLayoutData = previousLabelLayout.get(updatedLabelLayoutInput.id());
                        if (previousLabelLayoutData != null) {
                            if (!isSamePosition(previousLabelLayoutData.position(), updatedLabelLayoutInput.position())) {
                                var updatedLabelLayout = new LabelLayoutData(updatedLabelLayoutInput.id(), updatedLabelLayoutInput.position(), updatedLabelLayoutInput.size(), updatedLabelLayoutInput.resizedByUser(), updatedLabelLayoutInput.movedByUser());
                                undoLabelLayoutEvents.add(new DiagramLabelLayoutEvent(previousLabelLayoutData.id(), previousLabelLayoutData));
                                redoLabelLayoutEvents.add(new DiagramLabelLayoutEvent(updatedLabelLayoutInput.id(), updatedLabelLayout));
                            }
                        }
                    });

            var previousEdgeLayout = previousDiagram.getLayoutData().edgeLayoutData();
            layoutDiagramInput.diagramLayoutData().edgeLayoutData()
                    .forEach(updatedEdgeLayoutInput -> {
                        var previousEdgelLayoutData = previousEdgeLayout.get(updatedEdgeLayoutInput.id());
                        if (previousEdgelLayoutData != null) {
                            if (!isSameEdgeLayoutData(previousEdgelLayoutData, updatedEdgeLayoutInput)) {
                                var updatedEdgeLayout = new EdgeLayoutData(updatedEdgeLayoutInput.id(), updatedEdgeLayoutInput.bendingPoints(), updatedEdgeLayoutInput.edgeAnchorLayoutData());
                                undoEdgeLayoutEvents.add(new DiagramEdgeLayoutEvent(previousEdgelLayoutData.id(), previousEdgelLayoutData));
                                redoEdgeLayoutEvents.add(new DiagramEdgeLayoutEvent(updatedEdgeLayoutInput.id(), updatedEdgeLayout));
                            }
                        }
                    });

            if (!undoNodeLayoutEvents.isEmpty()) {
                var nodeLayoutChange = new DiagramNodeLayoutChange(layoutDiagramInput.id(), layoutDiagramInput.representationId(), undoNodeLayoutEvents, redoNodeLayoutEvents);
                representationChanges.add(nodeLayoutChange);
            }

            if (!undoLabelLayoutEvents.isEmpty()) {
                var nodeLabelLayoutChange = new DiagramLabelLayoutChange(layoutDiagramInput.id(), layoutDiagramInput.representationId(), undoLabelLayoutEvents, redoLabelLayoutEvents);
                representationChanges.add(nodeLabelLayoutChange);
            }

            if (!undoEdgeLayoutEvents.isEmpty()) {
                var edgeLayoutChange = new DiagramEdgeLayoutChange(layoutDiagramInput.id(), layoutDiagramInput.representationId(), undoEdgeLayoutEvents, redoEdgeLayoutEvents);
                representationChanges.add(edgeLayoutChange);
            }

            if (!representationChanges.isEmpty()) {
                if (!siriusEditingContext.getInputId2RepresentationChanges().containsKey(layoutDiagramInput.id()) || siriusEditingContext.getInputId2RepresentationChanges().get(layoutDiagramInput.id()).isEmpty()) {
                    siriusEditingContext.getInputId2RepresentationChanges().put(layoutDiagramInput.id(), representationChanges);
                } else {
                    siriusEditingContext.getInputId2RepresentationChanges().get(layoutDiagramInput.id()).addAll(representationChanges);
                }
            }
        }
    }

    private boolean isSameEdgeLayoutData(EdgeLayoutData previousLayout, EdgeLayoutDataInput updatedLayout) {
        boolean isSame = previousLayout.bendingPoints().size() == updatedLayout.bendingPoints().size();
        if (isSame) {
            int count = 0;
            for (Position previousPosition : previousLayout.bendingPoints()) {
                if (!isSamePosition(previousPosition, updatedLayout.bendingPoints().get(count))) {
                    return false;
                } else {
                    count++;
                }
            }
        }
        return isSame;
    }

    private boolean isSameNodeLayoutData(NodeLayoutData previousLayout, NodeLayoutDataInput updatedLayout) {
        return isSamePosition(previousLayout.position(), updatedLayout.position())
                && isSameSize(previousLayout.size(), updatedLayout.size())
                && previousLayout.resizedByUser() == updatedLayout.resizedByUser()
                && isSameHandleLayoutDatas(previousLayout.handleLayoutData(), updatedLayout.handleLayoutData());
    }

    private boolean isSamePosition(Position previousPosition, Position updatedPosition) {
        return previousPosition.x() == updatedPosition.x() && previousPosition.y() == updatedPosition.y();
    }

    private boolean isSameSize(Size previousSize, Size updatedSize) {
        return previousSize.height() == updatedSize.height() && previousSize.width() == updatedSize.width();
    }

    private boolean isSameLabelLayoutData(LabelLayoutData previousLayout, LabelLayoutDataInput updatedLayout) {
        return isSamePosition(previousLayout.position(), updatedLayout.position());
    }

    private boolean isSameHandleLayoutDatas(List<HandleLayoutData> previousLayouts, List<HandleLayoutData> updatedLayouts) {
        boolean isSame = previousLayouts.size() == updatedLayouts.size();
        if (isSame) {
            int count = 0;
            for (HandleLayoutData previousLayout : previousLayouts) {
                if (!isSamePosition(previousLayout.position(), updatedLayouts.get(count).position()) || !previousLayout.handlePosition().equals(updatedLayouts.get(count).handlePosition())) {
                    return false;
                } else {
                    count++;
                }
            }
        }
        return isSame;
    }

}
