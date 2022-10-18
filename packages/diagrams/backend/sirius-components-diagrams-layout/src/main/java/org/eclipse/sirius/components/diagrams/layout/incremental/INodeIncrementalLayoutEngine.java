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

import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;

/**
 * The Incremental layout engine for Node.
 *
 * <p>
 * An implementation should exist for each type of {@link NodeType}.
 * </p>
 *
 * @author gcoutable
 */
public interface INodeIncrementalLayoutEngine {

    NodeLayoutData layout(Optional<IDiagramEvent> optionalDiagramEvent, NodeLayoutData nodeLayoutData, ISiriusWebLayoutConfigurator layoutConfigurator);

    NodeLayoutData layout(Optional<IDiagramEvent> optionalDiagramEvent, NodeLayoutData nodeLayoutData, ISiriusWebLayoutConfigurator layoutConfigurator, double maxWidth);

    /**
     * Return the width of the node. This may lay the node out
     *
     * @param optionalDiagramEvent
     *            The optional diagram event
     * @param nodeLayoutData
     *            The node we need the width
     * @param layoutConfigurator
     *            The {@link ISiriusWebLayoutConfigurator}
     * @return The node width
     */
    double getNodeWidth(Optional<IDiagramEvent> optionalDiagramEvent, NodeLayoutData nodeLayoutData, ISiriusWebLayoutConfigurator layoutConfigurator);

    double getNodeMinimalWidth(NodeLayoutData node, ISiriusWebLayoutConfigurator layoutConfigurator);

}
