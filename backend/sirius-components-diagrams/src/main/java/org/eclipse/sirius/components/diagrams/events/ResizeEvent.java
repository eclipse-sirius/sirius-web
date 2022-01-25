/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.components.diagrams.events;

import java.util.Objects;

import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;

/**
 * Represent an immutable resize event.
 *
 * @author fbarbin
 */
public class ResizeEvent implements IDiagramEvent {

    private final String nodeId;

    private final Position positionDelta;

    private final Size newSize;

    public ResizeEvent(String nodeId, Position positionDelta, Size newSize) {
        this.nodeId = Objects.requireNonNull(nodeId);
        this.positionDelta = Objects.requireNonNull(positionDelta);
        this.newSize = Objects.requireNonNull(newSize);
    }

    public String getNodeId() {
        return this.nodeId;
    }

    public Position getPositionDelta() {
        return this.positionDelta;
    }

    public Size getNewSize() {
        return this.newSize;
    }

}
