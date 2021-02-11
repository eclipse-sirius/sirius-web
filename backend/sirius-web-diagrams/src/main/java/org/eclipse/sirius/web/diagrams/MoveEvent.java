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

import java.util.Set;
import java.util.UUID;

/**
 * Represent an immutable move event.
 *
 * @author wpiers
 */
public class MoveEvent {

    private UUID nodeId;

    private Position newPosition;

    private Set<UUID> allChildrenIds;

    public MoveEvent(UUID nodeId, Position newPosition, Set<UUID> allChildrenIds) {
        super();
        this.nodeId = nodeId;
        this.newPosition = newPosition;
        this.allChildrenIds = allChildrenIds;
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
