/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
 * Represent an event using two positions, a source and a target.
 *
 * @author gcoutable
 */
public record DoublePositionEvent(String sourceId, String targetId, Position sourcePosition, Position targetPosition) implements IDiagramEvent {


    public DoublePositionEvent {
        Objects.requireNonNull(sourceId);
        Objects.requireNonNull(targetId);
        Objects.requireNonNull(sourcePosition);
        Objects.requireNonNull(targetPosition);
    }
}
