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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.sirius.components.diagrams.layout.api.Offsets;
import org.eclipse.sirius.components.diagrams.layout.api.Rectangle;
import org.eclipse.sirius.components.diagrams.layoutdata.Position;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;

/**
 * Represents the layout information for a single-level set of nodes.
 *
 * @author pcdavid
 */
public class Canvas {

    private final Map<String, Rectangle> bounds = new LinkedHashMap<>();

    public void setBounds(String nodeId, Rectangle nodeBounds) {
        this.bounds.put(nodeId, nodeBounds);
    }

    public Position place(String nodeId, Position position, Size size) {
        Rectangle requestedBounds = new Rectangle(position.x(), position.y(), size.width(), size.height());
        Rectangle actualBounds = new OverlapHandler().findNearestFreeLocation(requestedBounds, this.bounds.values());
        this.bounds.put(nodeId, actualBounds);
        return position;
    }

    public boolean hasBounds(String nodeId) {
        return this.bounds.containsKey(nodeId);
    }

    public Optional<Rectangle> getBounds(String nodeId) {
        return Optional.ofNullable(this.bounds.get(nodeId));
    }

    public Map<String, Rectangle> getAllBounds() {
        return this.bounds;
    }

    public Collection<Rectangle> getOccupiedFootprints(Function<String, Offsets> marginProvider) {
        return this.bounds.keySet().stream()
                .map(nodeId -> this.bounds.get(nodeId).expand(marginProvider.apply(nodeId)))
                .toList();
    }
}
