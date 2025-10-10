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
package org.eclipse.sirius.web.application.undo.services.handler;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramEventProcessor;
import org.eclipse.sirius.components.collaborative.representations.api.IRepresentationEventProcessorRegistry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.events.undoredo.DiagramEdgeLayoutEvent;
import org.eclipse.sirius.components.diagrams.events.undoredo.DiagramLabelLayoutEvent;
import org.eclipse.sirius.components.diagrams.events.undoredo.DiagramNodeLayoutEvent;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.undo.services.api.IRepresentationChangeHandler;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramEdgeLayoutChange;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramLabelLayoutChange;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramNodeLayoutChange;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

/**
 * Use to handle the undo/redo for the FadeDiagramElementEvent.
 *
 * @author mcharfadi
 */
@Service
public class DiagramLayoutChangeHandler implements IRepresentationChangeHandler {

    private final IRepresentationEventProcessorRegistry representationEventProcessorRegistry;

    public DiagramLayoutChangeHandler(IRepresentationEventProcessorRegistry representationEventProcessorRegistry) {
        this.representationEventProcessorRegistry = Objects.requireNonNull(representationEventProcessorRegistry);
    }

    @Override
    public boolean canHandle(UUID inputId, IEditingContext editingContext) {
        return editingContext instanceof EditingContext siriusEditingContext && siriusEditingContext.getInputId2RepresentationChanges().get(inputId) != null &&
                siriusEditingContext.getInputId2RepresentationChanges().get(inputId).stream()
                .anyMatch(change -> change instanceof DiagramNodeLayoutChange || change instanceof DiagramLabelLayoutChange || change instanceof DiagramEdgeLayoutChange);
    }

    @Override
    public void redo(UUID inputId, IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusEditingContext) {
            siriusEditingContext.getInputId2RepresentationChanges().get(inputId).stream().filter(DiagramNodeLayoutChange.class::isInstance)
                .map(DiagramNodeLayoutChange.class::cast)
                .forEach(change -> {
                    var representationEventProcessorEntry = this.representationEventProcessorRegistry.get(editingContext.getId(), change.representationId());
                    if (representationEventProcessorEntry != null && representationEventProcessorEntry.getRepresentationEventProcessor() instanceof DiagramEventProcessor eventProcessor) {
                        var diagramContext = eventProcessor.getDiagramContext();
                        change.redoChanges().forEach(nodeLayoutChange -> {
                            diagramContext.diagramEvents().add(new DiagramNodeLayoutEvent(nodeLayoutChange.nodeId(), nodeLayoutChange.nodeLayoutData()));
                        });
                    }
                });

            siriusEditingContext.getInputId2RepresentationChanges().get(inputId).stream().filter(DiagramLabelLayoutChange.class::isInstance)
                .map(DiagramLabelLayoutChange.class::cast)
                .forEach(change -> {
                    var representationEventProcessorEntry = this.representationEventProcessorRegistry.get(editingContext.getId(), change.representationId());
                    if (representationEventProcessorEntry != null && representationEventProcessorEntry.getRepresentationEventProcessor() instanceof DiagramEventProcessor eventProcessor) {
                        var diagramContext = eventProcessor.getDiagramContext();
                        change.redoChanges().forEach(labelLayoutChange -> {
                            diagramContext.diagramEvents().add(new DiagramLabelLayoutEvent(labelLayoutChange.nodeId(), labelLayoutChange.labelLayoutData()));
                        });
                    }
                });

            siriusEditingContext.getInputId2RepresentationChanges().get(inputId).stream().filter(DiagramLabelLayoutChange.class::isInstance)
                .map(DiagramEdgeLayoutChange.class::cast)
                .forEach(change -> {
                    var representationEventProcessorEntry = this.representationEventProcessorRegistry.get(editingContext.getId(), change.representationId());
                    if (representationEventProcessorEntry != null && representationEventProcessorEntry.getRepresentationEventProcessor() instanceof DiagramEventProcessor eventProcessor) {
                        var diagramContext = eventProcessor.getDiagramContext();
                        change.redoChanges().forEach(edgeLayoutChange -> {
                            diagramContext.diagramEvents().add(new DiagramEdgeLayoutEvent(edgeLayoutChange.edgeId(), edgeLayoutChange.edgeLayoutData()));
                        });
                    }
                });
        }
    }

    @Override
    public void undo(UUID inputId, IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusEditingContext) {
            siriusEditingContext.getInputId2RepresentationChanges().get(inputId).stream().filter(DiagramNodeLayoutChange.class::isInstance)
                .map(DiagramNodeLayoutChange.class::cast)
                .forEach(change -> {
                    var representationEventProcessorEntry = this.representationEventProcessorRegistry.get(editingContext.getId(), change.representationId());
                    if (representationEventProcessorEntry != null && representationEventProcessorEntry.getRepresentationEventProcessor() instanceof DiagramEventProcessor eventProcessor) {
                        var diagramContext = eventProcessor.getDiagramContext();
                        change.undoChanges().forEach(nodeLayoutChange -> {
                            diagramContext.diagramEvents().add(new DiagramNodeLayoutEvent(nodeLayoutChange.nodeId(), nodeLayoutChange.nodeLayoutData()));
                        });
                    }
                });

            siriusEditingContext.getInputId2RepresentationChanges().get(inputId).stream().filter(DiagramLabelLayoutChange.class::isInstance)
                .map(DiagramLabelLayoutChange.class::cast)
                .forEach(change -> {
                    var representationEventProcessorEntry = this.representationEventProcessorRegistry.get(editingContext.getId(), change.representationId());
                    if (representationEventProcessorEntry != null && representationEventProcessorEntry.getRepresentationEventProcessor() instanceof DiagramEventProcessor eventProcessor) {
                        var diagramContext = eventProcessor.getDiagramContext();
                        change.undoChanges().forEach(labelLayoutChange -> {
                            diagramContext.diagramEvents().add(new DiagramLabelLayoutEvent(labelLayoutChange.nodeId(), labelLayoutChange.labelLayoutData()));
                        });
                    }
                });

            siriusEditingContext.getInputId2RepresentationChanges().get(inputId).stream().filter(DiagramEdgeLayoutChange.class::isInstance)
                .map(DiagramEdgeLayoutChange.class::cast)
                .forEach(change -> {
                    var representationEventProcessorEntry = this.representationEventProcessorRegistry.get(editingContext.getId(), change.representationId());
                    if (representationEventProcessorEntry != null && representationEventProcessorEntry.getRepresentationEventProcessor() instanceof DiagramEventProcessor eventProcessor) {
                        var diagramContext = eventProcessor.getDiagramContext();
                        change.undoChanges().forEach(edgeLayoutChange -> {
                            diagramContext.diagramEvents().add(new DiagramEdgeLayoutEvent(edgeLayoutChange.edgeId(), edgeLayoutChange.edgeLayoutData()));
                        });
                    }
                });
        }

    }
}
