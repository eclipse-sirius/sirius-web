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

import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Edge;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.description.EdgeDescription;
import org.eclipse.sirius.web.diagrams.description.NodeDescription;

/**
 * Used to find some elements in the previous diagram.
 *
 * @author sbegaudeau
 */
public interface IDiagramElementRequestor {
    List<Node> getRootNodes(Diagram diagram, NodeDescription nodeDescription);

    List<Node> getBorderNodes(Node node, NodeDescription nodeDescription);

    List<Node> getChildNodes(Node node, NodeDescription nodeDescription);

    List<Edge> getEdges(Diagram diagram, EdgeDescription edgeDescription);
}
