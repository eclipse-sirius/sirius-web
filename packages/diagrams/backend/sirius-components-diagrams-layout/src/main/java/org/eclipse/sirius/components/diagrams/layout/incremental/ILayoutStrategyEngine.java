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
package org.eclipse.sirius.components.diagrams.layout.incremental;

import java.util.Optional;

import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;

/**
 * Implementation of this interface will lay out the children of a node.
 *
 * @author gcoutable
 */
public interface ILayoutStrategyEngine {

    /**
     * Lays out the given node children's and returns the size of the area children needed to be laid out correctly.
     *
     * <p>
     * NOTE: The parent node will translate its children to put them in the intended area.
     * </p>
     *
     * @param optionalDiagramEvent
     *            The optional diagram event
     * @param node
     *            The node whose children will be laid out
     * @param layoutConfigurator
     *            the layout configurator
     * @return The size of the area children needed to be laid out correctly
     */
    Size layoutChildren(Optional<IDiagramEvent> optionalDiagramEvent, NodeLayoutData node, ISiriusWebLayoutConfigurator layoutConfigurator);

    /**
     * Lays out the given node children's and returns the size of the area children needed to be laid out correctly.
     *
     * <p>
     * NOTE: The parent node will translate its children to put them in the intended area.
     * </p>
     *
     * @param optionalDiagramEvent
     *            The optional diagram event
     * @param node
     *            The node whose children will be laid out
     * @param layoutConfigurator
     *            the layout configurator
     * @param width
     *            The width children have to do their layout
     * @return The size of the area children needed to be laid out correctly
     */
    Size layoutChildren(Optional<IDiagramEvent> optionalDiagramEvent, NodeLayoutData node, ISiriusWebLayoutConfigurator layoutConfigurator, double width);
}
