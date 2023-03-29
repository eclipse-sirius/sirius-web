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
package org.eclipse.sirius.components.diagrams.layout.api;

import org.eclipse.sirius.components.diagrams.layoutdata.Position;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;

/**
 * Possible values for anchoring a box inside a container.
 *
 * @author pcdavid
 */
public enum Anchor {
    TOP_LEFT,
    TOP_CENTER,
    TOP_RIGHT,

    MIDDLE_LEFT,
    MIDDLE_CENTER,
    MIDDLE_RIGHT,

    BOTTOM_LEFT,
    BOTTOM_CENTER,
    BOTTOM_RIGHT;

    public Position apply(Rectangle container, Size boxSize) {
        return switch (this) {
            case TOP_LEFT -> container.topLeft();
            case TOP_CENTER -> container.topCenter().translate(-boxSize.width() / 2.0, 0);
            case TOP_RIGHT -> container.topRight().translate(-boxSize.width(), 0.0);

            case MIDDLE_LEFT -> container.middleLeft().translate(0.0, -boxSize.height() / 2.0);
            case MIDDLE_CENTER -> container.middleCenter().translate(-boxSize.width() / 2.0, -boxSize.height() / 2.0);
            case MIDDLE_RIGHT -> container.middleRight().translate(-boxSize.width(), -boxSize.height() / 2.0);

            case BOTTOM_LEFT -> container.bottomLeft().translate(0.0, -boxSize.height());
            case BOTTOM_CENTER -> container.bottomCenter().translate(-boxSize.width() / 2.0, -boxSize.height());
            case BOTTOM_RIGHT -> container.bottomRight().translate(-boxSize.width(), -boxSize.height());
        };
    }
}
