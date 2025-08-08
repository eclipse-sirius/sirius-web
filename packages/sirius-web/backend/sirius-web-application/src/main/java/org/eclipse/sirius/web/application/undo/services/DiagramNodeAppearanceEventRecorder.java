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

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.EditRectangularNodeAppearanceInput;
import org.eclipse.sirius.components.collaborative.representations.api.IRepresentationEventRecorder;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.events.appearance.IAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBackgroundAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBorderColorAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBorderRadiusAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBorderSizeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBorderStyleAppearanceChange;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.undo.events.DiagramNodeAppearanceEventRecord;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Use to record DiagramNodeAppearanceEventRecord from EditRectangularNodeAppearanceInput.
 *
 * @author mcharfadi
 */
@Service
public class DiagramNodeAppearanceEventRecorder implements IRepresentationEventRecorder {

    private final IDiagramQueryService diagramQueryService;

    public DiagramNodeAppearanceEventRecorder(IDiagramQueryService diagramQueryService) {
        this.diagramQueryService = diagramQueryService;
    }

    @Override
    public void recordChange(IEditingContext editingContext, IRepresentation previousRepresentation, IRepresentation updatedRepresentation, IRepresentationInput input) {
        handleNodeAppearanceChanges(editingContext, previousRepresentation, updatedRepresentation, input);

    }

    private void handleNodeAppearanceChanges(IEditingContext editingContext, IRepresentation previousRepresentation, IRepresentation updatedRepresentation, IRepresentationInput input) {
        if (editingContext instanceof EditingContext siriusEditingContext && input instanceof EditRectangularNodeAppearanceInput editAppearanceInput && previousRepresentation instanceof Diagram previousDiagram && updatedRepresentation instanceof Diagram updatedDiagram) {
            var nodeId = editAppearanceInput.nodeId();

            Optional<Node> previousNode = this.diagramQueryService.findNodeById(previousDiagram, nodeId);

            List<IAppearanceChange> undoAppearanceChanges = new ArrayList<>();
            List<IAppearanceChange> redoAppearanceChanges = new ArrayList<>();

            Optional.ofNullable(editAppearanceInput.appearance().background()).ifPresent(background -> {
                if (previousNode.isPresent() && previousNode.get().getStyle() instanceof RectangularNodeStyle previousNodeStyle) {
                    undoAppearanceChanges.add(new NodeBackgroundAppearanceChange(nodeId, previousNodeStyle.getBackground()));
                    redoAppearanceChanges.add(new NodeBackgroundAppearanceChange(nodeId, background));
                }
            });

            Optional.ofNullable(editAppearanceInput.appearance().borderColor()).ifPresent(borderColor -> {
                if (previousNode.isPresent() && previousNode.get().getStyle() instanceof RectangularNodeStyle previousNodeStyle) {
                    undoAppearanceChanges.add(new NodeBorderColorAppearanceChange(nodeId, previousNodeStyle.getBorderColor()));
                    redoAppearanceChanges.add(new NodeBorderColorAppearanceChange(nodeId, borderColor));
                }
            });

            Optional.ofNullable(editAppearanceInput.appearance().borderRadius()).ifPresent(borderRadius -> {
                if (previousNode.isPresent() && previousNode.get().getStyle() instanceof RectangularNodeStyle previousNodeStyle) {
                    undoAppearanceChanges.add(new NodeBorderRadiusAppearanceChange(nodeId, previousNodeStyle.getBorderRadius()));
                    redoAppearanceChanges.add(new NodeBorderRadiusAppearanceChange(nodeId, borderRadius));
                }
            });

            Optional.ofNullable(editAppearanceInput.appearance().borderSize()).ifPresent(borderSize -> {
                if (previousNode.isPresent() && previousNode.get().getStyle() instanceof RectangularNodeStyle previousNodeStyle) {
                    undoAppearanceChanges.add(new NodeBorderSizeAppearanceChange(nodeId, previousNodeStyle.getBorderSize()));
                    redoAppearanceChanges.add(new NodeBorderSizeAppearanceChange(nodeId, borderSize));
                }
            });

            Optional.ofNullable(editAppearanceInput.appearance().borderStyle()).ifPresent(borderStyle -> {
                if (previousNode.isPresent() && previousNode.get().getStyle() instanceof RectangularNodeStyle previousNodeStyle) {
                    undoAppearanceChanges.add(new NodeBorderStyleAppearanceChange(nodeId, previousNodeStyle.getBorderStyle()));
                    redoAppearanceChanges.add(new NodeBorderStyleAppearanceChange(nodeId, borderStyle));
                }
            });

            if (!undoAppearanceChanges.isEmpty()) {
                var nodeChange = new DiagramNodeAppearanceEventRecord(editAppearanceInput.id().toString(), editAppearanceInput.representationId(), undoAppearanceChanges, redoAppearanceChanges);
                siriusEditingContext.getInputId2GraphicalChange().put(editAppearanceInput.id().toString(), nodeChange);
            }

        }
    }
}
