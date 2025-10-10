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
package org.eclipse.sirius.web.application.undo.services.handler;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramEventProcessor;
import org.eclipse.sirius.components.collaborative.representations.api.IRepresentationEventProcessorRegistry;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.events.HideDiagramElementEvent;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.undo.services.api.IRepresentationChangeHandler;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramHideElementChange;
import org.springframework.stereotype.Service;

/**
 * Used to handle the undo/redo for the HideDiagramElementEvent.
 *
 * @author mcharfadi
 */
@Service
public class HideElementChangeHandler implements IRepresentationChangeHandler {

    private final IRepresentationEventProcessorRegistry representationEventProcessorRegistry;

    public HideElementChangeHandler(IRepresentationEventProcessorRegistry representationEventProcessorRegistry) {
        this.representationEventProcessorRegistry = Objects.requireNonNull(representationEventProcessorRegistry);
    }

    @Override
    public boolean canHandle(UUID inputId, IEditingContext editingContext) {
        return editingContext instanceof EditingContext siriusEditingContext && siriusEditingContext.getInputId2RepresentationChanges().get(inputId) != null &&
                siriusEditingContext.getInputId2RepresentationChanges().get(inputId).stream()
                .anyMatch(DiagramHideElementChange.class::isInstance);
    }

    @Override
    public void redo(UUID inputId, IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusEditingContext) {
            siriusEditingContext.getInputId2RepresentationChanges().get(inputId).stream().filter(DiagramHideElementChange.class::isInstance)
                    .map(DiagramHideElementChange.class::cast)
                    .forEach(change -> {
                        var representationEventProcessorEntry = this.representationEventProcessorRegistry.get(editingContext.getId(), change.representationId());
                        if (representationEventProcessorEntry != null && representationEventProcessorEntry.getRepresentationEventProcessor() instanceof DiagramEventProcessor eventProcessor) {
                            var diagramContext = eventProcessor.getDiagramContext();
                            diagramContext.diagramEvents().add(new HideDiagramElementEvent(change.elementIds(), change.redoValue()));
                        }
                    });
        }
    }

    @Override
    public void undo(UUID inputId, IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusEditingContext) {
            siriusEditingContext.getInputId2RepresentationChanges().get(inputId).stream().filter(DiagramHideElementChange.class::isInstance)
                    .map(DiagramHideElementChange.class::cast)
                    .forEach(change -> {
                        var representationEventProcessorEntry = this.representationEventProcessorRegistry.get(editingContext.getId(), change.representationId());
                        if (representationEventProcessorEntry != null && representationEventProcessorEntry.getRepresentationEventProcessor() instanceof DiagramEventProcessor eventProcessor) {
                            var diagramContext = eventProcessor.getDiagramContext();
                            diagramContext.diagramEvents().add(new HideDiagramElementEvent(change.elementIds(), change.undoValue()));
                        }
                    });
        }
    }
}
