/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.variables;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramService;
import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Variable;

/**
 * Variables used while rendering and iteracting with diagrams.
 *
 * @author sbegaudeau
 */
public class DiagramVariables {
    public static final Variable LABEL = new Variable("label", String.class, false, "The label of the diagram");
    public static final Variable DIAGRAM_EVENT = new Variable("diagramEvent", IDiagramEvent.class, false, "Indicates the potential event which has triggered a new rendering");
    public static final Variable DIAGRAM_CONTEXT = new Variable("diagramContext", DiagramContext.class, false, "Used to retrieve the diagram context which contains the diagram, the view creation and deletion requests and the diagram events");
    public static final Variable DIAGRAM_SERVICES = new Variable("diagramServices", IDiagramService.class, false, "Used to access generic diagram services");
    public static final Variable PREVIOUS_DIAGRAM = new Variable("previousDiagram", Diagram.class, false, "The diagram rendered during the previous refresh");
    public static final Variable COLLAPSING_STATE = new Variable("collapsingState", CollapsingState.class, false, "Indicates if a node is collapsed or expanded");
    public static final Variable SEMANTIC_ELEMENT_IDS = new Variable("semanticElementIds", String.class, true, "The list of the identifiers of all semantic elements which should appear in the diagram with the current description");
    public static final Variable SEMANTIC_EDGE_SOURCE = new Variable("semanticEdgeSource", Object.class, false, "The semantic element at the source of the edge");
    public static final Variable SEMANTIC_EDGE_TARGET = new Variable("semanticEdgeTarget", Object.class, false, "The semantic element at the target of the edge");
    public static final Variable GRAPHICAL_EDGE_SOURCE = new Variable("graphicalEdgeSource", Element.class, false, "The virtual diagram element at the source of the edge");
    public static final Variable GRAPHICAL_EDGE_TARGET = new Variable("graphicalEdgeTarget", Element.class, false, "The virtual diagram element at the target of the edge");
    public static final Variable CACHE = new Variable("cache", DiagramRenderingCache.class, false, "An internal cache used to retrieve some internal data during the rendering");
    public static final Variable ANCESTORS = new Variable("ancestors", Object.class, true, "The semantic ancestors of the node");
    public static final Variable DROPPED_ELEMENTS = new Variable("droppedElements", Object.class, true, "All the semantic objects being dropped");
    public static final Variable DROPPED_NODES = new Variable("droppedNodes", Node.class, true, "All the graphical elements being dropped");
    public static final Variable TARGET_ELEMENT = new Variable("targetElement", Object.class, false, "The semantic object in which the element is being dropped");
    public static final Variable TARGET_NODE = new Variable("targetNode", Node.class, false, "The node in which the element is being dropped");
    public static final Variable SELECTED_NODE = new Variable("selectedNode", Node.class, false, "The node on which the tool is being executed");
    public static final Variable SELECTED_EDGE = new Variable("selectedEdge", Edge.class, false, "The edge on which the tool is being executed");
    public static final Variable SELECTED_NODES = new Variable("selectedNodes", Node.class, true, "The nodes on which the tool are being executed");
    public static final Variable SELECTED_EDGES = new Variable("selectedEdges", Edge.class, true, "The edges on which the tool are being executed");

    /**
     * The semantic element being dropped.
     *
     * @technical-debt This variable should stop being used in favor of {@link DiagramVariables#DROPPED_ELEMENTS}
     * since it will not give you access to all the semantic elements being dropped. Relying on this variable will create
     * bugs in your code.
     */
    @Deprecated(forRemoval = true)
    public static final Variable DROPPED_ELEMENT = new Variable("droppedElement", Object.class, false, "The semantic object being dropped");

    /**
     * The graphical element being dropped.
     *
     * @technical-debt This variable should stop being used in favor of {@link DiagramVariables#DROPPED_NODES}
     * since it will not give you access to all the graphical elements being dropped. Relying on this variable will create
     * bugs in your code.
     */
    @Deprecated(forRemoval = true)
    public static final Variable DROPPED_NODE = new Variable("droppedNode", Node.class, false, "The graphical element being dropped");

}
