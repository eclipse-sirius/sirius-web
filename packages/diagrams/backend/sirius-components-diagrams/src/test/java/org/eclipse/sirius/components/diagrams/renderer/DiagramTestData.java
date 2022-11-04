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
package org.eclipse.sirius.components.diagrams.renderer;

import java.util.List;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;

/**
 * Data structure used for tests.
 *
 * @author sbegaudeau
 */
@Immutable
public class DiagramTestData {
    private final Diagram diagram;

    private final List<NodeDescription> nodeDescriptions;

    private final List<EdgeDescription> edgeDescription;

    public DiagramTestData(Diagram diagram, List<NodeDescription> nodeDescriptions, List<EdgeDescription> edgeDescriptions) {
        this.diagram = diagram;
        this.nodeDescriptions = nodeDescriptions;
        this.edgeDescription = edgeDescriptions;
    }

    public Diagram getDiagram() {
        return this.diagram;
    }

    public List<NodeDescription> getNodeDescriptions() {
        return this.nodeDescriptions;
    }

    public List<EdgeDescription> getEdgeDescriptions() {
        return this.edgeDescription;
    }
}
