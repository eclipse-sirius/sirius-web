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
import org.eclipse.sirius.web.diagrams.ViewCreationRequest;
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

    private final INodesRequestor nodesRequestor;

    private final boolean isBorderNode;

    private final DiagramRenderingCache cache;

    private final List<ViewCreationRequest> viewCreationRequests;

    private final String parentElementId;

    public NodeComponentProps(VariableManager variableManager, NodeDescription nodeDescription, INodesRequestor nodesRequestor, boolean isBorderNode, DiagramRenderingCache cache,
            List<ViewCreationRequest> viewCreationRequests, String parentElementId) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.nodeDescription = Objects.requireNonNull(nodeDescription);
        this.nodesRequestor = Objects.requireNonNull(nodesRequestor);
        this.isBorderNode = isBorderNode;
        this.cache = Objects.requireNonNull(cache);
        this.viewCreationRequests = List.copyOf(Objects.requireNonNull(viewCreationRequests));
        this.parentElementId = Objects.requireNonNull(parentElementId);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public NodeDescription getNodeDescription() {
        return this.nodeDescription;
    }

    public INodesRequestor getNodesRequestor() {
        return this.nodesRequestor;
    }

    public boolean isBorderNode() {
        return this.isBorderNode;
    }

    public DiagramRenderingCache getCache() {
        return this.cache;
    }

    public List<ViewCreationRequest> getViewCreationRequests() {
        return this.viewCreationRequests;
    }

    public String getParentElementId() {
        return this.parentElementId;
    }

}
