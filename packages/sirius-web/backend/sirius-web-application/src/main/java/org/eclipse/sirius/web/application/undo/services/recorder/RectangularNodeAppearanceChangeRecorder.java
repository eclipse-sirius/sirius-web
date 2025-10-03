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
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.diagrams.RectangularNodeAppearanceHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.appearance.EditAppearanceEvent;
import org.eclipse.sirius.components.diagrams.events.appearance.IAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.INodeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBackgroundAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBorderColorAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBorderRadiusAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBorderSizeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBorderStyleAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.ResetNodeAppearanceChange;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventConsumer;
import org.eclipse.sirius.web.application.undo.services.changes.DiagramNodeAppearanceChange;
import org.springframework.stereotype.Service;

/**
 * Use to record data needed to perform the undo/redo for the appearance changes of rectangular nodes.
 *
 * @author mcharfadi
 */
@Service
public class RectangularNodeAppearanceChangeRecorder implements IDiagramEventConsumer {

    private final IDiagramQueryService diagramQueryService;

    public RectangularNodeAppearanceChangeRecorder(IDiagramQueryService diagramQueryService) {
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
    }

    @Override
    public void accept(IEditingContext editingContext, Diagram previousDiagram, List<IDiagramEvent> diagramEvents, List<ViewDeletionRequest> viewDeletionRequests, List<ViewCreationRequest> viewCreationRequests, ChangeDescription changeDescription) {
        if (editingContext instanceof EditingContext siriusEditingContext && changeDescription.getInput() instanceof IDiagramInput diagramInput) {
            var editAppearanceChanges = diagramEvents.stream()
                    .filter(EditAppearanceEvent.class::isInstance)
                    .map(EditAppearanceEvent.class::cast)
                    .map(EditAppearanceEvent::changes)
                    .flatMap(Collection::stream)
                    .toList();

            var undoAppearanceChanges = editAppearanceChanges.stream()
                    .filter(INodeAppearanceChange.class::isInstance)
                    .map(INodeAppearanceChange.class::cast)
                    .map(change -> this.computeAppearanceChanges(previousDiagram, change))
                    .flatMap(Collection::stream)
                    .toList();

            if (!undoAppearanceChanges.isEmpty()) {
                List<IAppearanceChange> redoAppearanceChanges = new ArrayList<>(editAppearanceChanges);
                var diagramNodeAppearanceChange = new DiagramNodeAppearanceChange(diagramInput.id(), diagramInput.representationId(), undoAppearanceChanges, redoAppearanceChanges);

                if (!siriusEditingContext.getInputId2RepresentationChanges().containsKey(diagramInput.id())
                        || siriusEditingContext.getInputId2RepresentationChanges().get(diagramInput.id()).isEmpty()) {
                    siriusEditingContext.getInputId2RepresentationChanges().put(diagramInput.id(), List.of(diagramNodeAppearanceChange));
                } else {
                    siriusEditingContext.getInputId2RepresentationChanges().get(diagramInput.id()).add(diagramNodeAppearanceChange);
                }


            }
        }
    }

    private List<IAppearanceChange> computeAppearanceChanges(Diagram previousDiagram, INodeAppearanceChange change) {
        List<IAppearanceChange> appearanceChanges = new ArrayList<>();

        Optional<Node> previousNode = this.diagramQueryService.findNodeById(previousDiagram, change.nodeId());
        if (previousNode.isPresent() && previousNode.get().getStyle() instanceof RectangularNodeStyle rectangularNodeStyle) {
            if (change instanceof NodeBackgroundAppearanceChange) {
                if (previousNode.get().getCustomizedStyleProperties().contains(RectangularNodeAppearanceHandler.BACKGROUND)) {
                    appearanceChanges.add(new NodeBackgroundAppearanceChange(previousNode.get().getId(), rectangularNodeStyle.getBackground()));
                } else {
                    appearanceChanges.add(new ResetNodeAppearanceChange(previousNode.get().getId(), RectangularNodeAppearanceHandler.BACKGROUND));
                }
            }

            if (change instanceof NodeBorderColorAppearanceChange) {
                if (previousNode.get().getCustomizedStyleProperties().contains(RectangularNodeAppearanceHandler.BORDER_COLOR)) {
                    appearanceChanges.add(new NodeBorderColorAppearanceChange(previousNode.get().getId(), rectangularNodeStyle.getBorderColor()));
                } else {
                    appearanceChanges.add(new ResetNodeAppearanceChange(previousNode.get().getId(), RectangularNodeAppearanceHandler.BORDER_COLOR));
                }
            }

            if (change instanceof NodeBorderRadiusAppearanceChange) {
                if (previousNode.get().getCustomizedStyleProperties().contains(RectangularNodeAppearanceHandler.BORDER_RADIUS)) {
                    appearanceChanges.add(new NodeBorderRadiusAppearanceChange(previousNode.get().getId(), rectangularNodeStyle.getBorderRadius()));
                } else {
                    appearanceChanges.add(new ResetNodeAppearanceChange(previousNode.get().getId(), RectangularNodeAppearanceHandler.BORDER_RADIUS));
                }
            }

            if (change instanceof NodeBorderStyleAppearanceChange) {
                if (previousNode.get().getCustomizedStyleProperties().contains(RectangularNodeAppearanceHandler.BORDER_STYLE)) {
                    appearanceChanges.add(new NodeBorderStyleAppearanceChange(previousNode.get().getId(), rectangularNodeStyle.getBorderStyle()));
                } else {
                    appearanceChanges.add(new ResetNodeAppearanceChange(previousNode.get().getId(), RectangularNodeAppearanceHandler.BORDER_STYLE));
                }
            }

            if (change instanceof NodeBorderSizeAppearanceChange) {
                if (previousNode.get().getCustomizedStyleProperties().contains(RectangularNodeAppearanceHandler.BORDER_SIZE)) {
                    appearanceChanges.add(new NodeBorderSizeAppearanceChange(previousNode.get().getId(), rectangularNodeStyle.getBorderSize()));
                } else {
                    appearanceChanges.add(new ResetNodeAppearanceChange(previousNode.get().getId(), RectangularNodeAppearanceHandler.BORDER_SIZE));
                }
            }
        }

        return appearanceChanges;
    }

}
