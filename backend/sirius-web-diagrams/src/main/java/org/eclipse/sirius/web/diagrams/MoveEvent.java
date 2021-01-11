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
import java.util.Set;
import java.util.UUID;

/**
 * Represent an immutable move event.
 *
 * @author wpiers
 */
public class MoveEvent {

    private final UUID nodeId;

    private final Position newPosition;

    private final Set<UUID> allChildrenIds;

    public MoveEvent(UUID nodeId, Position newPosition, Set<UUID> allChildrenIds) {
        this.nodeId = Objects.requireNonNull(nodeId);
        this.newPosition = Objects.requireNonNull(newPosition);
        this.allChildrenIds = Objects.requireNonNull(allChildrenIds);
    }

    public UUID getNodeId() {
        return this.nodeId;
    }

    public Position getNewPosition() {
        return this.newPosition;
    }

    public Set<UUID> getAllChildrenIds() {
        return this.allChildrenIds;
    }

}
