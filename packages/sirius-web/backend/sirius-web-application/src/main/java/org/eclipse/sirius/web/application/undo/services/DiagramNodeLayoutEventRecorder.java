/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.undo.services;

import org.eclipse.sirius.components.collaborative.diagrams.dto.LayoutDiagramInput;
import org.eclipse.sirius.components.collaborative.representations.api.IRepresentationEventRecorder;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.components.diagrams.events.undoredo.IDiagramNodeLayoutEvent;
import org.eclipse.sirius.web.application.undo.events.DiagramNodeLayoutEventRecord;
import org.eclipse.sirius.components.diagrams.events.undoredo.DiagramNodePositionEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Use to record DiagramNodeLayoutEventRecord from LayoutDiagramInput.
 *
 * @author mcharfadi
 */
@Service
public class DiagramNodeLayoutEventRecorder implements IRepresentationEventRecorder {

    @Override
    public void recordChange(IEditingContext editingContext, IRepresentation previousRepresentation, IRepresentation updatedRepresentation, IRepresentationInput input) {
        if (editingContext instanceof EditingContext siriusEditingContext && input instanceof LayoutDiagramInput layoutDiagramInput && previousRepresentation instanceof Diagram previousDiagram) {
            List<IDiagramNodeLayoutEvent> undoValues = new ArrayList<>();
            List<IDiagramNodeLayoutEvent> redoValues = new ArrayList<>();

            var previousNodeLayout = previousDiagram.getLayoutData().nodeLayoutData();
            layoutDiagramInput.diagramLayoutData().nodeLayoutData().forEach(updatedNodeLayout -> {
                var previousNodeLayoutData = previousNodeLayout.get(updatedNodeLayout.id());
                if (previousNodeLayoutData.position().x() != updatedNodeLayout.position().x() || previousNodeLayoutData.position().y() != updatedNodeLayout.position().y()) {
                    undoValues.add(new DiagramNodePositionEvent(previousNodeLayoutData.id(), previousNodeLayoutData.position()));
                    redoValues.add(new DiagramNodePositionEvent(updatedNodeLayout.id(), updatedNodeLayout.position()));
                }
            });

            if (!undoValues.isEmpty() && !redoValues.isEmpty()) {
                var nodeChange = new DiagramNodeLayoutEventRecord(layoutDiagramInput.id().toString(), layoutDiagramInput.representationId(), undoValues, redoValues);
                siriusEditingContext.getInputId2GraphicalChange().put(layoutDiagramInput.id().toString(), nodeChange);
            }
        }
    }
}
