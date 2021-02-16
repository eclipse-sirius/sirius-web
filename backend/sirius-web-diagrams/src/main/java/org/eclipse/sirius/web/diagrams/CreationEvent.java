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
package org.eclipse.sirius.web.diagrams;

import java.util.Objects;

/**
 * Represent an immutable creation event.
 *
 * @author fbarbin
 */
public class CreationEvent implements IDiagramElementEvent {

    private final Position startingPosition;

    public CreationEvent(Position startingPosition) {
        this.startingPosition = Objects.requireNonNull(startingPosition);
    }

    public Position getStartingPosition() {
        return this.startingPosition;
    }
}
