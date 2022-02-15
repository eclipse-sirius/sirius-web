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
package org.eclipse.sirius.components.diagrams.layout.incremental.utils;

import java.util.Objects;

import org.eclipse.sirius.components.diagrams.Position;

/**
 * This class represents a point on a given side of a rectangle.
 *
 * @author lfasani
 */
public class PointOnRectangleInfo {
    private final Position position;

    private final RectangleSide side;

    public PointOnRectangleInfo(Position position, RectangleSide side) {
        this.position = Objects.requireNonNull(position);
        this.side = Objects.requireNonNull(side);
    }

    public Position getPosition() {
        return this.position;
    }

    public RectangleSide getSide() {
        return this.side;
    }
}
