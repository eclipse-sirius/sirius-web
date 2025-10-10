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
package org.eclipse.sirius.web.application.undo.services.recorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.diagrams.RectangularNodeAppearanceHandler;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.events.appearance.IAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.INodeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBackgroundAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBorderColorAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBorderRadiusAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBorderSizeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.NodeBorderStyleAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.ResetNodeAppearanceChange;
import org.eclipse.sirius.web.application.project.services.api.IDiagramImporterNodeStyleAppearanceChangeHandler;
import org.eclipse.sirius.web.application.undo.services.api.INodeAppearanceChangeUndoRecorder;
import org.springframework.stereotype.Service;

/**
 * Used to record undo appearance changes of a rectangular node style when a deleteViewRequest or a node appearance change is performed.
 *
 * @author mcharfadi
 */
@Service
public class RectangularNodeUndoAppearanceChangeRecorder implements INodeAppearanceChangeUndoRecorder {

    private final List<IDiagramImporterNodeStyleAppearanceChangeHandler> diagramImporterNodeStyleAppearanceChangeHandlers;

    public RectangularNodeUndoAppearanceChangeRecorder(List<IDiagramImporterNodeStyleAppearanceChangeHandler> diagramImporterNodeStyleAppearanceChangeHandlers) {
        this.diagramImporterNodeStyleAppearanceChangeHandlers = Objects.requireNonNull(diagramImporterNodeStyleAppearanceChangeHandlers);
    }

    @Override
    public boolean canHandle(Node previousNode) {
        return previousNode.getStyle() instanceof RectangularNodeStyle;
    }

    @Override
    public List<IAppearanceChange> computeUndoNodeAppearanceChanges(Node previousNode, Optional<INodeAppearanceChange> change) {
        return change.map(nodeAppearanceChange -> computeAppearanceFromChange(previousNode, nodeAppearanceChange))
                .orElseGet(() -> this.diagramImporterNodeStyleAppearanceChangeHandlers.stream()
                .filter(handler -> handler.canHandle(previousNode.getStyle()))
                .findFirst()
                .map(handler -> previousNode.getCustomizedStyleProperties().stream()
                        .flatMap(customizedStyleProperty -> handler.handle(previousNode.getId(), previousNode.getStyle(), customizedStyleProperty).stream())
                        .toList())
                .orElse(List.of()));
    }

    private List<IAppearanceChange> computeAppearanceFromChange(Node previousNode, INodeAppearanceChange change) {
        List<IAppearanceChange> appearanceChanges = new ArrayList<>();

        if (previousNode.getStyle() instanceof RectangularNodeStyle rectangularNodeStyle) {
            if (change instanceof ResetNodeAppearanceChange) {
                this.diagramImporterNodeStyleAppearanceChangeHandlers.stream()
                    .filter(handler -> handler.canHandle(previousNode.getStyle()))
                    .findFirst()
                    .map(handler -> previousNode.getCustomizedStyleProperties().stream()
                            .flatMap(customizedStyleProperty -> handler.handle(previousNode.getId(), previousNode.getStyle(), customizedStyleProperty).stream())
                            .toList())
                    .ifPresent(appearanceChanges::addAll);
            }

            if (change instanceof NodeBackgroundAppearanceChange) {
                if (previousNode.getCustomizedStyleProperties().contains(RectangularNodeAppearanceHandler.BACKGROUND)) {
                    appearanceChanges.add(new NodeBackgroundAppearanceChange(previousNode.getId(), rectangularNodeStyle.getBackground()));
                } else {
                    appearanceChanges.add(new ResetNodeAppearanceChange(previousNode.getId(), RectangularNodeAppearanceHandler.BACKGROUND));
                }
            }

            if (change instanceof NodeBorderColorAppearanceChange) {
                if (previousNode.getCustomizedStyleProperties().contains(RectangularNodeAppearanceHandler.BORDER_COLOR)) {
                    appearanceChanges.add(new NodeBorderColorAppearanceChange(previousNode.getId(), rectangularNodeStyle.getBorderColor()));
                } else {
                    appearanceChanges.add(new ResetNodeAppearanceChange(previousNode.getId(), RectangularNodeAppearanceHandler.BORDER_COLOR));
                }
            }

            if (change instanceof NodeBorderRadiusAppearanceChange) {
                if (previousNode.getCustomizedStyleProperties().contains(RectangularNodeAppearanceHandler.BORDER_RADIUS)) {
                    appearanceChanges.add(new NodeBorderRadiusAppearanceChange(previousNode.getId(), rectangularNodeStyle.getBorderRadius()));
                } else {
                    appearanceChanges.add(new ResetNodeAppearanceChange(previousNode.getId(), RectangularNodeAppearanceHandler.BORDER_RADIUS));
                }
            }

            if (change instanceof NodeBorderStyleAppearanceChange) {
                if (previousNode.getCustomizedStyleProperties().contains(RectangularNodeAppearanceHandler.BORDER_STYLE)) {
                    appearanceChanges.add(new NodeBorderStyleAppearanceChange(previousNode.getId(), rectangularNodeStyle.getBorderStyle()));
                } else {
                    appearanceChanges.add(new ResetNodeAppearanceChange(previousNode.getId(), RectangularNodeAppearanceHandler.BORDER_STYLE));
                }
            }

            if (change instanceof NodeBorderSizeAppearanceChange) {
                if (previousNode.getCustomizedStyleProperties().contains(RectangularNodeAppearanceHandler.BORDER_SIZE)) {
                    appearanceChanges.add(new NodeBorderSizeAppearanceChange(previousNode.getId(), rectangularNodeStyle.getBorderSize()));
                } else {
                    appearanceChanges.add(new ResetNodeAppearanceChange(previousNode.getId(), RectangularNodeAppearanceHandler.BORDER_SIZE));
                }
            }
        }

        return appearanceChanges;
    }
}
