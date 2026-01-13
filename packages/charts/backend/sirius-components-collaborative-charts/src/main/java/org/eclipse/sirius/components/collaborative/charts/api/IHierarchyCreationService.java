/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.charts.api;

import org.eclipse.sirius.components.charts.hierarchy.Hierarchy;
import org.eclipse.sirius.components.charts.hierarchy.descriptions.HierarchyDescription;
import org.eclipse.sirius.components.collaborative.charts.HierarchyContext;
import org.eclipse.sirius.components.core.api.IEditingContext;

/**
 * Used to create hierarchy representations.
 *
 * @author sbegaudeau
 */
public interface IHierarchyCreationService {
    Hierarchy create(IEditingContext editingContext, HierarchyDescription hierarchyDescription, Object targetObject, HierarchyContext hierarchyContext);
}
