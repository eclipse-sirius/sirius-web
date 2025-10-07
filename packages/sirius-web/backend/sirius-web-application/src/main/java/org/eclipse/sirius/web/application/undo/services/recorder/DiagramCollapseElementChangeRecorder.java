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
package org.eclipse.sirius.web.application.undo.services.recorder;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventConsumer;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.representations.change.IRepresentationChange;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.UpdateCollapsingStateEvent;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramCollapseElementChange;
import org.springframework.stereotype.Service;

/**
 * Use to record data needed to perform the undo/redo for UpdateCollapsingStateEvent.
 *
 * @author mcharfadi
 */
@Service
public class DiagramCollapseElementChangeRecorder implements IDiagramEventConsumer {

    @Override
    public void accept(IEditingContext editingContext, Diagram previousDiagram, List<IDiagramEvent> diagramEvents, List<ViewDeletionRequest> viewDeletionRequests, List<ViewCreationRequest> viewCreationRequests, ChangeDescription changeDescription) {
        if (editingContext instanceof EditingContext siriusEditingContext && changeDescription.getInput() instanceof IDiagramInput diagramInput) {
            List<IRepresentationChange> representationChanges = new ArrayList<>();
            diagramEvents.stream()
                    .filter(UpdateCollapsingStateEvent.class::isInstance)
                    .map(UpdateCollapsingStateEvent.class::cast)
                    .forEach(updateCollapsingStateEvent -> {
                        var redoCollapsingState = updateCollapsingStateEvent.collapsingState();
                        var undoCollapsingState = CollapsingState.COLLAPSED;
                        if (redoCollapsingState.equals(CollapsingState.COLLAPSED)) {
                            undoCollapsingState = CollapsingState.EXPANDED;
                        }
                        var diagramCollapseElementChange = new DiagramCollapseElementChange(diagramInput.id(), diagramInput.representationId(), updateCollapsingStateEvent.diagramElementId(), undoCollapsingState, redoCollapsingState);
                        representationChanges.add(diagramCollapseElementChange);
                    });

            if (!representationChanges.isEmpty()) {
                if (!siriusEditingContext.getInputId2RepresentationChanges().containsKey(diagramInput.id())
                        || siriusEditingContext.getInputId2RepresentationChanges().get(diagramInput.id()).isEmpty()) {
                    siriusEditingContext.getInputId2RepresentationChanges().put(diagramInput.id(), representationChanges);
                } else {
                    siriusEditingContext.getInputId2RepresentationChanges().get(diagramInput.id()).addAll(representationChanges);
                }
            }
        }
    }
}
