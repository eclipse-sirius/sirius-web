/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.diagrams.tests.navigation;

import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.Node;

/**
 * Used to navigate from an edge.
 *
 * @author gdaniel
 */
public class EdgeNavigator {

    private final Edge edge;

    private final NavigatorCache cache;

    public EdgeNavigator(Edge edge, NavigatorCache cache) {
        this.edge = edge;
        this.cache = cache;
    }

    public Edge getEdge() {
        return this.edge;
    }

    public NodeNavigator sourceNode() {
        Node node = this.cache.getIdToNode().get(this.edge.getSourceId());
        return new NodeNavigator(node, this.cache);
    }

    public NodeNavigator targetNode() {
        Node node = this.cache.getIdToNode().get(this.edge.getTargetId());
        return new NodeNavigator(node, this.cache);
    }

    public Label findCenterLabel() {
        return this.edge.getCenterLabel();
    }

    public Label findBeginLabel() {
        return this.edge.getBeginLabel();
    }

    public Label findEndLabel() {
        return this.edge.getEndLabel();
    }

}
