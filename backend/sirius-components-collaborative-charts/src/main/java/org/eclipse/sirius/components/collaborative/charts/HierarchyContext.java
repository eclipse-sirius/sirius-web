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
package org.eclipse.sirius.components.collaborative.charts;

import org.eclipse.sirius.components.charts.hierarchy.Hierarchy;

import java.util.Objects;

/**
 * Used to keep the hierarchy representation in memory.
 *
 * @author sbegaudeau
 */
public class HierarchyContext {

    private Hierarchy hierarchy;

    public HierarchyContext(Hierarchy hierarchy) {
        this.hierarchy = Objects.requireNonNull(hierarchy);
    }

    public Hierarchy getHierarchy() {
        return this.hierarchy;
    }

    public void update(Hierarchy newHierarchy) {
        this.hierarchy = newHierarchy;
    }
}
