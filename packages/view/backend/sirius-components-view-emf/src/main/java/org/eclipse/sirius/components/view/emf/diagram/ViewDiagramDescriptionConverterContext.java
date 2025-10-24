/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.diagram;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;

/**
 * Represents the context used while converting a View DiagramDescription.
 *
 * @author fbarbin
 */
public class ViewDiagramDescriptionConverterContext {

    private final Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> convertedNodes;

    private final Map<org.eclipse.sirius.components.view.diagram.EdgeDescription, EdgeDescription> convertedEdges;

    private final AQLInterpreter interpreter;

    public ViewDiagramDescriptionConverterContext(AQLInterpreter interpreter) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.convertedNodes = new LinkedHashMap<>();
        this.convertedEdges = new LinkedHashMap<>();
    }

    public Map<org.eclipse.sirius.components.view.diagram.EdgeDescription, EdgeDescription> getConvertedEdges() {
        return this.convertedEdges;
    }

    public Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> getConvertedNodes() {
        return this.convertedNodes;
    }

    public Map<org.eclipse.sirius.components.view.diagram.DiagramElementDescription, IDiagramElementDescription> getConvertedElements() {
        // in case of key collisions, which should never happen, we take the value of the node.
        return Stream.concat(this.convertedEdges.entrySet().stream(), this.convertedNodes.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (edges, nodes) -> nodes, LinkedHashMap::new));
    }

    public AQLInterpreter getInterpreter() {
        return this.interpreter;
    }

}
