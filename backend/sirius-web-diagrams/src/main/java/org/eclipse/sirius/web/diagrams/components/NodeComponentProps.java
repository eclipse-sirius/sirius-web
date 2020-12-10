/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.diagrams.components;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.components.IProps;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;
import org.eclipse.sirius.web.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * The properties of the node component.
 *
 * @author sbegaudeau
 */
public class NodeComponentProps implements IProps {

    private final VariableManager variableManager;

    private final NodeDescription nodeDescription;

    private final boolean isBorderNode;

    private final DiagramRenderingCache cache;

    private final List<Node> previousNodes;

    public NodeComponentProps(VariableManager variableManager, NodeDescription nodeDescription, boolean isBorderNode, DiagramRenderingCache cache, List<Node> prevNodes) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.nodeDescription = Objects.requireNonNull(nodeDescription);
        this.isBorderNode = isBorderNode;
        this.cache = Objects.requireNonNull(cache);
        this.previousNodes = Objects.requireNonNull(prevNodes);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public NodeDescription getNodeDescription() {
        return this.nodeDescription;
    }

    public boolean isBorderNode() {
        return this.isBorderNode;
    }

    public DiagramRenderingCache getCache() {
        return this.cache;
    }

    public List<Node> getPreviousNodes() {
        return this.previousNodes;
    }
}
