/*******************************************************************************
 * Copyright (c) 2021 Obeo and others.
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
package org.eclipse.sirius.components.collaborative.diagrams.api;

import java.util.Optional;

import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;

/**
 * Interface used to query diagrams.
 *
 * @author sbegaudeau
 */
public interface IDiagramQueryService {

    Optional<Node> findNodeById(Diagram diagram, String nodeId);

    Optional<Node> findNodeByLabelId(Diagram diagram, String labelId);

    Optional<Edge> findEdgeById(Diagram diagram, String edgeId);

    Optional<Edge> findEdgeByLabelId(Diagram diagram, String labelId);

}
