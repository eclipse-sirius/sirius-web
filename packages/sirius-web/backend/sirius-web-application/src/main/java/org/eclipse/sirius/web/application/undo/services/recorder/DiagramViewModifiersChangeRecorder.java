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
import java.util.Objects;
import java.util.Set;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventConsumer;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.representations.change.IRepresentationChange;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.events.FadeDiagramElementEvent;
import org.eclipse.sirius.components.diagrams.events.HideDiagramElementEvent;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.ResetViewModifiersEvent;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.undo.services.changes.ResetViewModifiersChange;
import org.springframework.stereotype.Service;

/**
 * Use to record data needed to perform the undo/redo for ResetViewModifiersEvent.
 *
 * @author mcharfadi
 */
@Service
public class DiagramViewModifiersChangeRecorder implements IDiagramEventConsumer {

    private final IDiagramQueryService diagramQueryService;

    public DiagramViewModifiersChangeRecorder(IDiagramQueryService diagramQueryService) {
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
    }

    @Override
    public void accept(IEditingContext editingContext, Diagram previousDiagram, List<IDiagramEvent> diagramEvents, List<ViewDeletionRequest> viewDeletionRequests, List<ViewCreationRequest> viewCreationRequests, ChangeDescription changeDescription) {
        if (editingContext instanceof EditingContext siriusEditingContext && changeDescription.getInput() instanceof IDiagramInput diagramInput) {
            List<IRepresentationChange> representationChanges = new ArrayList<>();

            diagramEvents.stream()
                    .filter(ResetViewModifiersEvent.class::isInstance)
                    .map(ResetViewModifiersEvent.class::cast)
                    .forEach(resetViewModifiersEvent -> {
                        var elementIds = resetViewModifiersEvent.getElementIds();
                        List<IDiagramEvent> undoEvents = new ArrayList<>();
                        elementIds.forEach(elementId -> {
                            var node = this.diagramQueryService.findNodeById(previousDiagram, elementId);
                            if (node.isPresent() && node.get().getState().equals(ViewModifier.Faded)) {
                                undoEvents.add(new FadeDiagramElementEvent(Set.of(elementId), true));
                            }
                            if (node.isPresent() && node.get().getState().equals(ViewModifier.Hidden)) {
                                undoEvents.add(new HideDiagramElementEvent(Set.of(elementId), true));
                            }
                        });
                        var diagramPinElementChange = new ResetViewModifiersChange(diagramInput.id(), diagramInput.representationId(), elementIds, undoEvents);
                        representationChanges.add(diagramPinElementChange);
                    });

            if (!representationChanges.isEmpty()) {
                if (!siriusEditingContext.getInputId2RepresentationChanges().containsKey(diagramInput.id()) || siriusEditingContext.getInputId2RepresentationChanges().get(diagramInput.id()).isEmpty()) {
                    siriusEditingContext.getInputId2RepresentationChanges().put(diagramInput.id(), representationChanges);
                } else {
                    siriusEditingContext.getInputId2RepresentationChanges().get(diagramInput.id()).addAll(representationChanges);
                }
            }
        }
    }
}
