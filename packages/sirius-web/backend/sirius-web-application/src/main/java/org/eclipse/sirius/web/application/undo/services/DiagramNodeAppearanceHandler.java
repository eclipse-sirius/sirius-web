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

import org.eclipse.sirius.components.collaborative.diagrams.DiagramEventProcessor;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.representations.api.IRepresentationEventHandler;
import org.eclipse.sirius.components.collaborative.representations.api.IRepresentationEventProcessorRegistry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.events.appearance.EditAppearanceEvent;
import org.eclipse.sirius.components.diagrams.events.appearance.IAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.INodeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBackgroundAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBorderColorAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBorderRadiusAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBorderSizeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBorderStyleAppearanceChange;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.undo.events.DiagramNodeAppearanceEventRecord;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Use to handle undo/redo by computing EditAppearanceEvent from DiagramNodeAppearanceEventRecord.
 *
 * @author mcharfadi
 */
@Service
public class DiagramNodeAppearanceHandler implements IRepresentationEventHandler {

    private final IRepresentationEventProcessorRegistry representationEventProcessorRegistry;

    private final IDiagramQueryService diagramQueryService;

    public DiagramNodeAppearanceHandler(IRepresentationEventProcessorRegistry representationEventProcessorRegistry, IDiagramQueryService diagramQueryService) {
        this.representationEventProcessorRegistry = Objects.requireNonNull(representationEventProcessorRegistry);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
    }

    @Override
    public boolean canHandle(String mutationId, IEditingContext editingContext) {
        return editingContext instanceof EditingContext siriusEditingContext
                && siriusEditingContext.getInputId2GraphicalChange().get(mutationId) != null
                && siriusEditingContext.getInputId2GraphicalChange().get(mutationId) instanceof DiagramNodeAppearanceEventRecord;
    }

    @Override
    public void undo(String mutationId, IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusEditingContext
                && siriusEditingContext.getInputId2GraphicalChange().get(mutationId) != null
                && siriusEditingContext.getInputId2GraphicalChange().get(mutationId) instanceof DiagramNodeAppearanceEventRecord change) {

            var representationEventProcessorEntry = this.representationEventProcessorRegistry.get(editingContext.getId(), change.representationId());
            if (representationEventProcessorEntry != null && representationEventProcessorEntry.getRepresentationEventProcessor() instanceof DiagramEventProcessor eventProcessor) {
                var diagramContext = eventProcessor.getDiagramContext();
                var shouldApplyChange = true;
                var redoChanges = change.redoChanges().stream()
                        .filter(INodeAppearanceChange.class::isInstance)
                        .map(INodeAppearanceChange.class::cast).toList();


                for (INodeAppearanceChange redoChange: redoChanges) {
                    var currentNode = this.diagramQueryService.findNodeById(diagramContext.getDiagram(), redoChange.nodeId());
                    if (shouldApplyChange && currentNode.isPresent() && currentNode.get().getStyle() instanceof RectangularNodeStyle currentStyle) {
                        shouldApplyChange = shouldApplyChange(redoChange, currentStyle);
                    }
                }
                if (shouldApplyChange) {
                    diagramContext.getDiagramEvents().add(new EditAppearanceEvent(change.undoChanges()));
                }
            }
        }
    }

    @Override
    public void redo(String mutationId, IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusEditingContext
                && siriusEditingContext.getInputId2GraphicalChange().get(mutationId) != null
                && siriusEditingContext.getInputId2GraphicalChange().get(mutationId) instanceof DiagramNodeAppearanceEventRecord change) {

            var representationEventProcessorEntry = this.representationEventProcessorRegistry.get(editingContext.getId(), change.representationId());
            if (representationEventProcessorEntry != null && representationEventProcessorEntry.getRepresentationEventProcessor() instanceof DiagramEventProcessor eventProcessor) {
                var diagramContext = eventProcessor.getDiagramContext();
                var shouldApplyChange = true;
                var undoChanges = change.undoChanges().stream()
                        .filter(INodeAppearanceChange.class::isInstance)
                        .map(INodeAppearanceChange.class::cast).toList();


                for (INodeAppearanceChange undoChange: undoChanges) {
                    var currentNode = this.diagramQueryService.findNodeById(diagramContext.getDiagram(), undoChange.nodeId());
                    if (shouldApplyChange && currentNode.isPresent() && currentNode.get().getStyle() instanceof RectangularNodeStyle currentStyle) {
                        shouldApplyChange = shouldApplyChange(undoChange, currentStyle);
                    }
                }
                if (shouldApplyChange) {
                    diagramContext.getDiagramEvents().add(new EditAppearanceEvent(change.redoChanges()));
                }
            }
        }
    }

    private boolean shouldApplyChange(IAppearanceChange appearanceChange, RectangularNodeStyle currentStyle) {
        boolean shouldApply;
        shouldApply = appearanceChange instanceof NodeBackgroundAppearanceChange nodeBackgroundAppearanceChange
            && nodeBackgroundAppearanceChange.background().equals(currentStyle.getBackground());

        shouldApply = shouldApply || appearanceChange instanceof NodeBorderColorAppearanceChange nodeBorderColorAppearanceChange
            && nodeBorderColorAppearanceChange.borderColor().equals(currentStyle.getBorderColor());

        shouldApply = shouldApply || appearanceChange instanceof NodeBorderRadiusAppearanceChange nodeBorderRadiusAppearanceChange
            && nodeBorderRadiusAppearanceChange.borderRadius() == currentStyle.getBorderRadius();

        shouldApply = shouldApply || appearanceChange instanceof NodeBorderSizeAppearanceChange nodeBorderSizeAppearanceChange
            && nodeBorderSizeAppearanceChange.borderSize() == currentStyle.getBorderSize();

        shouldApply = shouldApply || appearanceChange instanceof NodeBorderStyleAppearanceChange nodeBorderStyleAppearanceChange
            && nodeBorderStyleAppearanceChange.borderStyle().equals(currentStyle.getBorderStyle());

        return shouldApply;
    }
}
