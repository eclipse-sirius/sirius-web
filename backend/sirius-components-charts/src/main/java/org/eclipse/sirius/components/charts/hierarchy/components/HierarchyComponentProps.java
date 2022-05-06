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

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.charts.hierarchy.Hierarchy;
import org.eclipse.sirius.components.charts.hierarchy.descriptions.HierarchyDescription;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The props of the hierarchy component.
 *
 * @author sbegaudeau
 */
public class HierarchyComponentProps implements IProps {

    private final VariableManager variableManager;

    private final HierarchyDescription hierarchyDescription;

    private final Optional<Hierarchy> previousHierarchy;

    public HierarchyComponentProps(VariableManager variableManager, HierarchyDescription hierarchyDescription, Optional<Hierarchy> previousHierarchy) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.hierarchyDescription = Objects.requireNonNull(hierarchyDescription);
        this.previousHierarchy = Objects.requireNonNull(previousHierarchy);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public HierarchyDescription getHierarchyDescription() {
        return this.hierarchyDescription;
    }

    public Optional<Hierarchy> getPreviousHierarchy() {
        return this.previousHierarchy;
    }

}
