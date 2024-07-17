/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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

/**
 * Represents a reconnect edge event.
 *
 * @author gcoutable
 */
public class ReconnectEdgeEvent implements IDiagramEvent {

    private String edgeId;

    private final ReconnectEdgeKind kind;

    public ReconnectEdgeEvent(ReconnectEdgeKind kind, String previousEdgeId) {
        this.kind = Objects.requireNonNull(kind);
        this.edgeId = Objects.requireNonNull(previousEdgeId);
    }

    public ReconnectEdgeKind getKind() {
        return this.kind;
    }

    public String getEdgeId() {
        return this.edgeId;
    }

    public void setEdgeId(String reconnectedEdgeId) {
        this.edgeId = Objects.requireNonNull(reconnectedEdgeId);
    }
}
