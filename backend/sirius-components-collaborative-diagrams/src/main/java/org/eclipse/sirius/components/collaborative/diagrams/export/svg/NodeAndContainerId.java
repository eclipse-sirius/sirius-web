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
package org.eclipse.sirius.components.collaborative.diagrams.export.svg;

import java.util.Objects;

import org.eclipse.sirius.components.diagrams.Node;

/**
 * Associates a node to its containerId.
 *
 * @author gcoutable
 */
public final class NodeAndContainerId {
    private final String containerId;

    private final Node node;

    public NodeAndContainerId(String containerId, Node node) {
        this.containerId = Objects.requireNonNull(containerId);
        this.node = Objects.requireNonNull(node);
    }

    public String getContainerId() {
        return this.containerId;
    }

    public Node getNode() {
        return this.node;
    }
}
