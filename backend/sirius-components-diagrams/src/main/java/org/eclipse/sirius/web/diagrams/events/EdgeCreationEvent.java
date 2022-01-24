/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.diagrams.events;

import java.util.Objects;

import org.eclipse.sirius.web.diagrams.Position;

/**
 * Represent an immutable edge creation event.
 *
 * @author gcoutable
 */
public class EdgeCreationEvent implements IDiagramEvent {

    private final Position sourcePosition;

    private final Position targetPosition;

    public EdgeCreationEvent(Position sourcePosition, Position targetPosition) {
        this.sourcePosition = Objects.requireNonNull(sourcePosition);
        this.targetPosition = Objects.requireNonNull(targetPosition);
    }

    public Position getSourcePosition() {
        return this.sourcePosition;
    }

    public Position getTargetPosition() {
        return this.targetPosition;
    }
}
