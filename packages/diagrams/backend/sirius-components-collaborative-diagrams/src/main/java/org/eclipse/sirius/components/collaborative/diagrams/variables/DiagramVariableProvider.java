/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.DIAGRAM_DROP_NODES;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.EDGE_BEGIN_LABEL;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.EDGE_END_LABEL;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.EDGE_LABEL;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.EDGE_PRECONDITION;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.EDGE_SEMANTIC_CANDIDATES;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.EDGE_SOURCE_NODES;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.EDGE_TARGET_NODES;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.NODE_HEIGHT_COMPUTATION;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.NODE_LABEL;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.NODE_PRECONDITION;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.NODE_SEMANTIC_CANDIDATES;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.NODE_WIDTH_COMPUTATION;

import java.util.List;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.core.api.variables.CommonVariables;
import org.eclipse.sirius.components.core.api.variables.IVariableProvider;
import org.eclipse.sirius.components.core.api.variables.Variable;
import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.renderer.DiagramRenderingCache;
import org.eclipse.sirius.components.representations.Element;
import org.springframework.stereotype.Service;

/**
 * Used to provide the variables available for all diagram operations.
 *
 * @author sbegaudeau
 */
@Service
public class DiagramVariableProvider implements IVariableProvider {

    public static final Variable LABEL = new Variable("label", List.of(String.class), "The label of the diagram");
    public static final Variable DIAGRAM_EVENT = new Variable("diagramEvent", List.of(IDiagramEvent.class), "Indicates the potential event which has triggered a new rendering");
    public static final Variable DIAGRAM_CONTEXT = new Variable("diagramContext", List.of(DiagramContext.class), "Used to retrieve the diagram context which contains the diagram, the view creation and deletion requests and the diagram events");
    public static final Variable PREVIOUS_DIAGRAM = new Variable("previousDiagram", List.of(Diagram.class), "The diagram rendered during the previous refresh");
    public static final Variable COLLAPSING_STATE = new Variable("collapsingState", List.of(CollapsingState.class), "Indicates if a node is collapsed or expanded");
    public static final Variable SEMANTIC_ELEMENT_IDS = new Variable("semanticElementIds", List.of(List.class), "The list of the identifiers of all semantic elements which should appear in the diagram with the current description");
    public static final Variable SEMANTIC_EDGE_SOURCE = new Variable("semanticEdgeSource", List.of(Object.class), "The semantic element at the source of the edge");
    public static final Variable SEMANTIC_EDGE_TARGET = new Variable("semanticEdgeTarget", List.of(Object.class), "The semantic element at the target of the edge");
    public static final Variable GRAPHICAL_EDGE_SOURCE = new Variable("graphicalEdgeSource", List.of(Element.class), "The virtual diagram element at the source of the edge");
    public static final Variable GRAPHICAL_EDGE_TARGET = new Variable("graphicalEdgeTarget", List.of(Element.class), "The virtual diagram element at the target of the edge");
    public static final Variable CACHE = new Variable("cache", List.of(DiagramRenderingCache.class), "An internal cache used to retrieve some internal data during the rendering");
    public static final Variable ANCESTORS = new Variable("ancestors", List.of(Object.class), "The semantic ancestors of the node");
    public static final Variable DROPPED_ELEMENTS = new Variable("droppedElements", List.of(List.class), "All the semantic objects being dropped");
    public static final Variable DROPPED_NODES = new Variable("droppedNodes", List.of(List.class), "All the graphical elements being dropped");
    public static final Variable TARGET_ELEMENT = new Variable("targetElement", List.of(Object.class), "The semantic object in which the element is being dropped");
    public static final Variable TARGET_NODE = new Variable("targetNode", List.of(Node.class), "The graphical element in which the element is being dropped");

    /**
     * The semantic element being dropped.
     *
     * @technical-debt This variable should stop being used in favor of {@link DiagramVariableProvider#DROPPED_ELEMENTS}
     * since it will not give you access to all the semantic elements being dropped. Relying on this variable will create
     * bugs in your code.
     */
    @Deprecated(forRemoval = true)
    public static final Variable DROPPED_ELEMENT = new Variable("droppedElement", List.of(Object.class), "The semantic object being dropped");

    /**
     * The graphical element being dropped.
     *
     * @technical-debt This variable should stop being used in favor of {@link DiagramVariableProvider#DROPPED_NODES}
     * since it will not give you access to all the graphical elements being dropped. Relying on this variable will create
     * bugs in your code.
     */
    @Deprecated(forRemoval = true)
    public static final Variable DROPPED_NODE = new Variable("droppedNode", List.of(Node.class), "The graphical element being dropped");


    @Override
    public List<Variable> getVariables(String operation) {
        return switch (operation) {
            case DIAGRAM_DROP_NODES -> this.diagramDropNodes();
            case NODE_SEMANTIC_CANDIDATES -> this.nodeSemanticCandidates();
            case NODE_PRECONDITION -> this.nodePrecondition();
            case NODE_LABEL -> this.nodeLabel();
            case NODE_WIDTH_COMPUTATION, NODE_HEIGHT_COMPUTATION -> this.nodeWidthAndHeight();
            case EDGE_SEMANTIC_CANDIDATES -> this.edgeSemanticCandidates();
            case EDGE_SOURCE_NODES, EDGE_TARGET_NODES -> this.edgeSourceAndTargetNodes();
            case EDGE_PRECONDITION -> this.edgePrecondition();
            case EDGE_BEGIN_LABEL, EDGE_LABEL, EDGE_END_LABEL -> this.edgeLabels();
            default -> this.noVariables();
        };
    }

    private List<Variable> diagramDropNodes() {
        return List.of(CommonVariables.EDITING_CONTEXT, CommonVariables.ENVIRONMENT, DIAGRAM_CONTEXT, DROPPED_ELEMENTS, DROPPED_NODES, DROPPED_ELEMENT, DROPPED_NODE, TARGET_ELEMENT, TARGET_NODE);
    }

    private List<Variable> nodeSemanticCandidates() {
        return List.of(CommonVariables.SELF, COLLAPSING_STATE, CommonVariables.EDITING_CONTEXT, SEMANTIC_ELEMENT_IDS, DIAGRAM_EVENT, PREVIOUS_DIAGRAM, LABEL, CommonVariables.ENVIRONMENT, ANCESTORS);
    }

    private List<Variable> nodePrecondition() {
        return List.of(CommonVariables.SELF, CommonVariables.EDITING_CONTEXT, CommonVariables.ENVIRONMENT, ANCESTORS);
    }

    private List<Variable> nodeLabel() {
        return List.of(CommonVariables.SELF, COLLAPSING_STATE, CommonVariables.EDITING_CONTEXT, CommonVariables.ENVIRONMENT, ANCESTORS);
    }

    private List<Variable> nodeWidthAndHeight() {
        return List.of(CommonVariables.SELF, CommonVariables.EDITING_CONTEXT, CommonVariables.ENVIRONMENT, ANCESTORS);
    }

    private List<Variable> edgeSemanticCandidates() {
        return List.of(CommonVariables.SELF, CommonVariables.EDITING_CONTEXT, CommonVariables.ENVIRONMENT);
    }

    private List<Variable> edgeSourceAndTargetNodes() {
        return List.of(CommonVariables.SELF, CommonVariables.EDITING_CONTEXT, CommonVariables.ENVIRONMENT);
    }

    private List<Variable> edgePrecondition() {
        return List.of(CommonVariables.SELF, SEMANTIC_EDGE_SOURCE, SEMANTIC_EDGE_TARGET, GRAPHICAL_EDGE_SOURCE, GRAPHICAL_EDGE_TARGET, CommonVariables.EDITING_CONTEXT, DIAGRAM_EVENT, PREVIOUS_DIAGRAM, CACHE, LABEL, CommonVariables.ENVIRONMENT);
    }

    private List<Variable> edgeLabels() {
        return List.of(CommonVariables.SELF, SEMANTIC_EDGE_SOURCE, SEMANTIC_EDGE_TARGET, GRAPHICAL_EDGE_SOURCE, GRAPHICAL_EDGE_TARGET, CommonVariables.EDITING_CONTEXT, DIAGRAM_EVENT, PREVIOUS_DIAGRAM, CACHE, LABEL, CommonVariables.ENVIRONMENT);
    }
}
