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
package org.eclipse.sirius.components.charts.hierarchy.components;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.charts.hierarchy.HierarchyNode;
import org.eclipse.sirius.components.charts.hierarchy.descriptions.HierarchyDescription;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The props of the node component.
 *
 * @author sbegaudeau
 */
public class HierarchyNodeComponentProps implements IProps {

    private final VariableManager variableManager;

    private final HierarchyDescription hierarchyDescription;

    private final List<HierarchyNode> previousNodes;

    private final String parentElementId;

    public HierarchyNodeComponentProps(VariableManager variableManager, HierarchyDescription hierarchyDescription, List<HierarchyNode> previousNodes, String parentElementId) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.hierarchyDescription = Objects.requireNonNull(hierarchyDescription);
        this.previousNodes = Objects.requireNonNull(previousNodes);
        this.parentElementId = Objects.requireNonNull(parentElementId);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public HierarchyDescription getHierarchyDescription() {
        return this.hierarchyDescription;
    }

    public List<HierarchyNode> getPreviousNodes() {
        return this.previousNodes;
    }

    public String getParentElementId() {
        return this.parentElementId;
    }

}
