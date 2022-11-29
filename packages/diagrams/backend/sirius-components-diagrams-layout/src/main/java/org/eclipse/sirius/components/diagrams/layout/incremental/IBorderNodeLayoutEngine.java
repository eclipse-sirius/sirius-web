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

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.layout.api.Bounds;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;

/**
 * The engine used to layout border nodes.
 *
 * @author gcoutable
 */
public interface IBorderNodeLayoutEngine {

    /**
     * Update the border nodes position according to the side length change where it is located. The aim is to keep the
     * positioning ratio of the border node on its side.
     */
    List<BorderNodesOnSide> layoutBorderNodes(Optional<IDiagramEvent> optionalDiagramElementEvent, List<NodeLayoutData> borderNodesLayoutData, Bounds initialNodeBounds, Bounds newNodeBounds,
            Map<String, ElkGraphElement> elementId2ElkElement);

}
