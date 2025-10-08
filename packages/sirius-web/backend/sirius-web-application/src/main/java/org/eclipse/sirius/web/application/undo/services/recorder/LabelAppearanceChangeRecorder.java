/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventConsumer;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.representations.change.IRepresentationChange;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.appearance.EditAppearanceEvent;
import org.eclipse.sirius.components.diagrams.events.appearance.IAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.ILabelAppearanceChange;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.application.undo.services.api.ILabelAppearanceChangeUndoRecorder;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramLabelAppearanceChange;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Use to record data needed to perform the undo/redo for the label appearance changes.
 *
 * @author mcharfadi
 */
@Service
public class LabelAppearanceChangeRecorder implements IDiagramEventConsumer {

    private final IDiagramQueryService diagramQueryService;

    private final List<ILabelAppearanceChangeUndoRecorder> labelAppearanceChangeUndoRecorders;

    public LabelAppearanceChangeRecorder(IDiagramQueryService diagramQueryService, List<ILabelAppearanceChangeUndoRecorder> labelAppearanceChangeUndoRecorders) {
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.labelAppearanceChangeUndoRecorders = Objects.requireNonNull(labelAppearanceChangeUndoRecorders);
    }

    @Override
    public void accept(IEditingContext editingContext, Diagram previousDiagram, List<IDiagramEvent> diagramEvents, List<ViewDeletionRequest> viewDeletionRequests, List<ViewCreationRequest> viewCreationRequests, ChangeDescription changeDescription) {
        if (editingContext instanceof EditingContext siriusEditingContext && changeDescription.getInput() instanceof IDiagramInput diagramInput) {
            List<IRepresentationChange> representationChanges = new ArrayList<>();
            var editAppearanceChanges = diagramEvents.stream()
                    .filter(EditAppearanceEvent.class::isInstance)
                    .map(EditAppearanceEvent.class::cast)
                    .map(EditAppearanceEvent::changes)
                    .flatMap(Collection::stream)
                    .toList();

            var undoAppearanceChanges = editAppearanceChanges.stream()
                    .filter(ILabelAppearanceChange.class::isInstance)
                    .map(ILabelAppearanceChange.class::cast)
                    .map(change -> this.computeAppearanceChanges(previousDiagram, change))
                    .flatMap(Collection::stream)
                    .toList();

            if (!undoAppearanceChanges.isEmpty()) {
                List<IAppearanceChange> redoAppearanceChanges = new ArrayList<>(editAppearanceChanges);
                var diagramNodeAppearanceChange = new DiagramLabelAppearanceChange(diagramInput.id(), diagramInput.representationId(), undoAppearanceChanges, redoAppearanceChanges);
                representationChanges.add(diagramNodeAppearanceChange);
                if (!siriusEditingContext.getInputId2RepresentationChanges().containsKey(diagramInput.id()) || siriusEditingContext.getInputId2RepresentationChanges().get(diagramInput.id()).isEmpty()) {
                    siriusEditingContext.getInputId2RepresentationChanges().put(diagramInput.id(), representationChanges);
                } else {
                    siriusEditingContext.getInputId2RepresentationChanges().get(diagramInput.id()).addAll(representationChanges);
                }
            }
        }
    }

    private List<IAppearanceChange> computeAppearanceChanges(Diagram previousDiagram, ILabelAppearanceChange change) {
        List<IAppearanceChange> appearanceChanges = new ArrayList<>();
        this.diagramQueryService.findNodeByLabelId(previousDiagram, change.labelId())
            .ifPresent(previousNode ->
                this.labelAppearanceChangeUndoRecorders.stream()
                    .filter(labelAppearanceChangeUndoRecorder -> labelAppearanceChangeUndoRecorder.canHandle(previousNode))
                    .findFirst()
                    .map(labelAppearanceChangeUndoRecorder -> labelAppearanceChangeUndoRecorder.computeUndoLabelAppearanceChanges(previousNode, change.labelId(), Optional.of(change)))
                    .map(appearanceChanges::addAll)
            );
        this.diagramQueryService.findEdgeByLabelId(previousDiagram, change.labelId())
            .ifPresent(previousEdge ->
                this.labelAppearanceChangeUndoRecorders.stream()
                    .filter(labelAppearanceChangeUndoRecorder -> labelAppearanceChangeUndoRecorder.canHandle(previousEdge))
                    .findFirst()
                    .map(labelAppearanceChangeUndoRecorder -> labelAppearanceChangeUndoRecorder.computeUndoLabelAppearanceChanges(previousEdge, change.labelId(), Optional.of(change)))
                    .map(appearanceChanges::addAll)
            );
        return appearanceChanges;
    }

}
