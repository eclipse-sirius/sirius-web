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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.eclipse.sirius.components.collaborative.diagrams.EdgeAppearanceHandler;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.events.appearance.IAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.EdgeColorAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.EdgeLineStyleAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.EdgeSizeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.EdgeSourceArrowStyleAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.EdgeTargetArrowStyleAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.EdgeTypeStyleAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.IEdgeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.edgestyle.ResetEdgeAppearanceChange;
import org.eclipse.sirius.web.application.undo.services.api.IEdgeAppearanceChangeUndoRecorder;
import org.springframework.stereotype.Service;

/**
 * Used to record data needed to perform the undo for the edge appearance changes.
 *
 * @author mcharfadi
 */
@Service
public class EdgeAppearanceChangeUndoRecorder implements IEdgeAppearanceChangeUndoRecorder {

    @Override
    public List<IAppearanceChange> computeEdgeAppearanceChanges(Edge previousEdge, Optional<IEdgeAppearanceChange> optionalChange) {
        List<IAppearanceChange> appearanceChanges = new ArrayList<>();
        optionalChange.ifPresent(change -> {
            var previousCustomizedStyleProperties = previousEdge.getCustomizedStyleProperties();
            var previousEdgeStyle = previousEdge.getStyle();
            if (change instanceof EdgeColorAppearanceChange) {
                if (previousCustomizedStyleProperties.contains(EdgeAppearanceHandler.COLOR)) {
                    appearanceChanges.add(new EdgeColorAppearanceChange(change.edgeId(), previousEdgeStyle.getColor()));
                } else {
                    appearanceChanges.add(new ResetEdgeAppearanceChange(change.edgeId(), EdgeAppearanceHandler.COLOR));
                }
            }
            if (change instanceof EdgeLineStyleAppearanceChange) {
                if (previousCustomizedStyleProperties.contains(EdgeAppearanceHandler.LINESTYLE)) {
                    appearanceChanges.add(new EdgeLineStyleAppearanceChange(change.edgeId(), previousEdgeStyle.getLineStyle()));
                } else {
                    appearanceChanges.add(new ResetEdgeAppearanceChange(change.edgeId(), EdgeAppearanceHandler.LINESTYLE));
                }
            }
            if (change instanceof EdgeSizeAppearanceChange) {
                if (previousCustomizedStyleProperties.contains(EdgeAppearanceHandler.SIZE)) {
                    appearanceChanges.add(new EdgeSizeAppearanceChange(change.edgeId(), previousEdgeStyle.getSize()));
                } else {
                    appearanceChanges.add(new ResetEdgeAppearanceChange(change.edgeId(), EdgeAppearanceHandler.SIZE));
                }
            }
            if (change instanceof EdgeSourceArrowStyleAppearanceChange) {
                if (previousCustomizedStyleProperties.contains(EdgeAppearanceHandler.SOURCE_ARROW)) {
                    appearanceChanges.add(new EdgeSourceArrowStyleAppearanceChange(change.edgeId(), previousEdgeStyle.getSourceArrow()));
                } else {
                    appearanceChanges.add(new ResetEdgeAppearanceChange(change.edgeId(), EdgeAppearanceHandler.SOURCE_ARROW));
                }
            }
            if (change instanceof EdgeTargetArrowStyleAppearanceChange) {
                if (previousCustomizedStyleProperties.contains(EdgeAppearanceHandler.TARGET_ARROW)) {
                    appearanceChanges.add(new EdgeTargetArrowStyleAppearanceChange(change.edgeId(), previousEdgeStyle.getTargetArrow()));
                } else {
                    appearanceChanges.add(new ResetEdgeAppearanceChange(change.edgeId(), EdgeAppearanceHandler.TARGET_ARROW));
                }
            }
            if (change instanceof EdgeTypeStyleAppearanceChange) {
                if (previousCustomizedStyleProperties.contains(EdgeAppearanceHandler.EDGE_TYPE)) {
                    appearanceChanges.add(new EdgeTypeStyleAppearanceChange(change.edgeId(), previousEdgeStyle.getEdgeType()));
                } else {
                    appearanceChanges.add(new ResetEdgeAppearanceChange(change.edgeId(), EdgeAppearanceHandler.EDGE_TYPE));
                }
            }
            if (change instanceof ResetEdgeAppearanceChange) {
                appearanceChanges.addAll(getResetEdgeApperanceChange(change.edgeId(), previousCustomizedStyleProperties, previousEdgeStyle));
            }
        });

        if (optionalChange.isEmpty()) {
            appearanceChanges.addAll(getResetEdgeApperanceChange(previousEdge.getId(), previousEdge.getCustomizedStyleProperties(), previousEdge.getStyle()));
        }

        return appearanceChanges;
    }

    private List<IAppearanceChange> getResetEdgeApperanceChange(String edgeId, Set<String> previousCustomizedStyleProperties, EdgeStyle previousEdgeStyle) {
        List<IAppearanceChange> appearanceChanges = new ArrayList<>();
        previousCustomizedStyleProperties.forEach(customizedStyleProperty -> {
            switch (customizedStyleProperty) {
                case EdgeAppearanceHandler.COLOR -> appearanceChanges.add(new EdgeColorAppearanceChange(edgeId, previousEdgeStyle.getColor()));
                case EdgeAppearanceHandler.SIZE -> appearanceChanges.add(new EdgeSizeAppearanceChange(edgeId, previousEdgeStyle.getSize()));
                case EdgeAppearanceHandler.LINESTYLE -> appearanceChanges.add(new EdgeLineStyleAppearanceChange(edgeId, previousEdgeStyle.getLineStyle()));
                case EdgeAppearanceHandler.SOURCE_ARROW -> appearanceChanges.add(new EdgeSourceArrowStyleAppearanceChange(edgeId, previousEdgeStyle.getSourceArrow()));
                case EdgeAppearanceHandler.TARGET_ARROW -> appearanceChanges.add(new EdgeTargetArrowStyleAppearanceChange(edgeId, previousEdgeStyle.getTargetArrow()));
                case EdgeAppearanceHandler.EDGE_TYPE -> appearanceChanges.add(new EdgeTypeStyleAppearanceChange(edgeId, previousEdgeStyle.getEdgeType()));
                default -> {
                    //We do nothing, the style property is not supported
                }
            }
        });
        return appearanceChanges;
    }
}
