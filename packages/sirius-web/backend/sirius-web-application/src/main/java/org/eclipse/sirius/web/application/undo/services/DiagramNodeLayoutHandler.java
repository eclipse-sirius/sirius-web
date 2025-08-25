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
import org.eclipse.sirius.components.diagrams.events.undoredo.DiagramNodePositionEvent;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.undo.events.DiagramNodeLayoutEventRecord;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Use to handle undo/redo by computing DiagramNodePositionEvent from DiagramNodeLayoutEventRecord.
 *
 * @author mcharfadi
 */
@Service
public class DiagramNodeLayoutHandler implements IRepresentationEventHandler {

    private final IRepresentationEventProcessorRegistry representationEventProcessorRegistry;

    private final IDiagramQueryService diagramQueryService;

    public DiagramNodeLayoutHandler(IRepresentationEventProcessorRegistry representationEventProcessorRegistry, IDiagramQueryService diagramQueryService) {
        this.representationEventProcessorRegistry = Objects.requireNonNull(representationEventProcessorRegistry);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
    }

    @Override
    public boolean canHandle(String mutationId, IEditingContext editingContext) {
        return editingContext instanceof EditingContext siriusEditingContext
                && siriusEditingContext.getInputId2GraphicalChange().get(mutationId) != null
                && siriusEditingContext.getInputId2GraphicalChange().get(mutationId) instanceof DiagramNodeLayoutEventRecord;
    }

    @Override
    public void undo(String mutationId, IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusEditingContext
                && siriusEditingContext.getInputId2GraphicalChange().get(mutationId) != null
                && siriusEditingContext.getInputId2GraphicalChange().get(mutationId) instanceof DiagramNodeLayoutEventRecord change) {

            // TODO handle shouldApplyChange ?
            var representationEventProcessorEntry = this.representationEventProcessorRegistry.get(editingContext.getId(), change.representationId());
            if (representationEventProcessorEntry != null && representationEventProcessorEntry.getRepresentationEventProcessor() instanceof DiagramEventProcessor eventProcessor) {
                var diagramContext = eventProcessor.getDiagramContext();
                change.undoChanges().forEach(nodeLayoutChange -> {
                    if (nodeLayoutChange instanceof DiagramNodePositionEvent nodePositionChangeEvent) {
                        diagramContext.getDiagramEvents().add(new DiagramNodePositionEvent(nodePositionChangeEvent.nodeId(), nodePositionChangeEvent.position()));
                    }
                });
            }
        }
    }

    @Override
    public void redo(String mutationId, IEditingContext editingContext) {
        // TODO
    }
}
