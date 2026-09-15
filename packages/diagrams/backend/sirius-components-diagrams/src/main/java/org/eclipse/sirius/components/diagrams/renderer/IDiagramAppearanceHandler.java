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
package org.eclipse.sirius.components.diagrams.renderer;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.components.EdgeAppearance;
import org.eclipse.sirius.components.diagrams.components.NodeAppearance;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Used to handle diagram graphical element appearance.
 *
 * @author gcoutable
 */
public interface IDiagramAppearanceHandler {
    NodeAppearance getNodeAppearance(VariableManager variableManager, NodeDescription nodeDescription, List<IDiagramEvent> diagramEvents, String nodeId, Optional<Node> optionalPreviousNode);

    EdgeAppearance getEdgeAppearance(VariableManager variableManager, EdgeDescription edgeDescription, List<IDiagramEvent> diagramEvents, String edgeId, Optional<Edge> optionalPreviousEdge);

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author gcoutable
     */
    class NoOp implements IDiagramAppearanceHandler {

        @Override
        public NodeAppearance getNodeAppearance(VariableManager variableManager, NodeDescription nodeDescription, List<IDiagramEvent> diagramEvents, String nodeId,
                Optional<Node> optionalPreviousNode) {
            INodeStyle providedStyle = nodeDescription.getStyleProvider().apply(variableManager);
            return new NodeAppearance(providedStyle, new LinkedHashSet<>());
        }

        @Override
        public EdgeAppearance getEdgeAppearance(VariableManager variableManager, EdgeDescription edgeDescription, List<IDiagramEvent> diagramEvents, String edgeId,
                Optional<Edge> optionalPreviousEdge) {
            EdgeStyle providedStyle = edgeDescription.getStyleProvider().apply(variableManager);
            return new EdgeAppearance(providedStyle, new LinkedHashSet<>());
        }
    }
}
