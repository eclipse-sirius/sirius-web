/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.experimental;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.diagrams.layout.api.experimental.Offsets;
import org.eclipse.sirius.components.diagrams.layout.api.experimental.Rectangle;
import org.eclipse.sirius.components.diagrams.layoutdata.Position;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;

/**
 * Lays out a set of nodes on an unbounded canvas.
 *
 * @author pcdavid
 */
public class CanvasLayoutEngine {

    private final Collection<String> nodeIds;

    private final Function<String, Size> sizeProvider;

    private final Function<String, Offsets> marginProvider;

    public CanvasLayoutEngine(Collection<String> nodeIds, Function<String, Size> sizeProvider, Function<String, Offsets> marginProvider) {
        this.nodeIds = Objects.requireNonNull(nodeIds);
        this.sizeProvider = Objects.requireNonNull(sizeProvider);
        this.marginProvider = Objects.requireNonNull(marginProvider);
    }

    public Map<String, Rectangle> getLeftToRightLayout(Collection<Rectangle> occupied) {
        Map<String, Rectangle> layout = new HashMap<>();
        var nextOrigin = new Position(0.0, 0.0);
        double previousMargin = 0.0;
        boolean firstNode = true;
        for (String nodeId: this.nodeIds) {
            Size nodeSize = this.sizeProvider.apply(nodeId);
            Offsets nodeMargin = this.marginProvider.apply(nodeId);
            Position nodePosition;
            if (firstNode) {
                nodePosition = nextOrigin;
                firstNode = false;
            } else {
                // Collapse this node's left margin with the right margin of the previous one
                nodePosition = nextOrigin.translate(Math.max(previousMargin, nodeMargin.left()), 0);
            }
            Rectangle bounds = new Rectangle(nodePosition, nodeSize);
            Set<Rectangle> overlapping = occupied.stream().filter(bounds::overlaps).collect(Collectors.toSet());
            if (!overlapping.isEmpty()) {
                bounds = new OverlapHandler().findNearestFreeLocation(bounds, overlapping);
            }
            layout.put(nodeId, bounds);

            nextOrigin = bounds.topRight();
            previousMargin = nodeMargin.right();
        }
        return layout;
    }
}
