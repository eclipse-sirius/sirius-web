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
package org.eclipse.sirius.components.diagrams.events;

import java.util.List;
import java.util.Objects;

/**
 * Represents an event to remove an edge.
 *
 * @author gcoutable
 */
public class RemoveEdgeEvent implements IDiagramEvent {

    private final List<String> edgeIds;

    public RemoveEdgeEvent(List<String> edgeIds) {
        this.edgeIds = Objects.requireNonNull(edgeIds);
    }

    public List<String> getEdgeIds() {
        return this.edgeIds;
    }
}
