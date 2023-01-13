/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.incremental;

import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.ChildrenAreaLaidOutData;

/**
 * Implementation of this interface will lay out the children of a node.
 *
 * @author gcoutable
 */
public interface ILayoutStrategyEngine {

    /**
     * Returns the children area layout data that should be used by the node to positions its children and calculates
     * its size.
     *
     * @param childrenAreaLayoutContext
     *            The context used to get the children area layout data
     * @param layoutConfigurator
     *            the layout configurator
     * @return The children area laid out data to use to update the parent node
     */
    ChildrenAreaLaidOutData layoutChildrenArea(ChildrenAreaLayoutContext childrenAreaLayoutContext, ISiriusWebLayoutConfigurator layoutConfigurator);
}
