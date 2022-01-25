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

/**
 * Represent an immutable node creation event.
 *
 * @author fbarbin
 */
public class NodeCreationEvent implements IDiagramEvent {

    private final Position startingPosition;

    public NodeCreationEvent(Position startingPosition) {
        this.startingPosition = Objects.requireNonNull(startingPosition);
    }

    public Position getStartingPosition() {
        return this.startingPosition;
    }
}
