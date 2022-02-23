/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.incremental.provider;

import java.util.Optional;

import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.MoveEvent;
import org.eclipse.sirius.components.diagrams.events.ResizeEvent;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.LabelLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.utils.RectangleSide;

/**
 * Provides the position logic to apply to a BorderNode Label.
 *
 * @author lfasani
 */
public class BorderNodeLabelPositionProvider {

    public void updateLabelPosition(Optional<IDiagramEvent> optionalDiagramElementEvent, RectangleSide side, NodeLayoutData borderNodeLayoutData) {
        LabelLayoutData label = borderNodeLayoutData.getLabel();
        if (label != null) {
            // @formatter:off
            boolean isBorderNodeMoved = optionalDiagramElementEvent
                    .filter(MoveEvent.class::isInstance)
                    .map(MoveEvent.class::cast)
                    .map(MoveEvent::getNodeId)
                    .filter(borderNodeLayoutData.getId()::equals)
                    .isPresent();

            boolean isBorderNodeResized = optionalDiagramElementEvent
                    .filter(ResizeEvent.class::isInstance)
                    .map(ResizeEvent.class::cast)
                    .map(ResizeEvent::getNodeId)
                    .filter(borderNodeLayoutData.getId()::equals)
                    .isPresent();
            // @formatter:on

            if (borderNodeLayoutData.getLabel().getPosition().getX() == -1 || isBorderNodeMoved || isBorderNodeResized) {
                if (RectangleSide.NORTH.equals(side)) {
                    label.setPosition(Position.at(-label.getTextBounds().getSize().getWidth(), -label.getTextBounds().getSize().getHeight()));
                } else if (RectangleSide.EAST.equals(side)) {
                    label.setPosition(Position.at(borderNodeLayoutData.getSize().getWidth(), borderNodeLayoutData.getSize().getHeight()));
                } else {
                    label.setPosition(Position.at(-label.getTextBounds().getSize().getWidth(), borderNodeLayoutData.getSize().getHeight()));
                }
            }
        }
    }
}
