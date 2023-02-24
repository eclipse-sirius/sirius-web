/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import org.eclipse.sirius.components.core.api.variables.CommonVariables;
import org.eclipse.sirius.components.core.api.variables.IVariableProvider;
import org.eclipse.sirius.components.core.api.variables.Variable;
import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.Diagram;
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

    public static final Variable OWNER_ID = new Variable("ownerId", List.of(String.class), "The identifier of the node");
    public static final Variable LABEL = new Variable("label", List.of(String.class), "The label of the diagram");
    public static final Variable DIAGRAM_EVENT = new Variable("diagramEvent", List.of(IDiagramEvent.class), "Indicates the potential event which has triggered a new rendering");
    public static final Variable PREVIOUS_DIAGRAM = new Variable("previousDiagram", List.of(Diagram.class), "The diagram rendered during the previous refresh");
    public static final Variable COLLAPSING_STATE = new Variable("collapsingState", List.of(CollapsingState.class), "Indicates if a node is collapsed or expanded");
    public static final Variable SEMANTIC_ELEMENT_IDS = new Variable("semanticElementIds", List.of(List.class), "The list of the identifiers of all semantic elements which should appear in the diagram with the current description");
    public static final Variable SEMANTIC_EDGE_SOURCE = new Variable("semanticEdgeSource", List.of(Object.class), "The semantic element at the source of the edge");
    public static final Variable SEMANTIC_EDGE_TARGET = new Variable("semanticEdgeTarget", List.of(Object.class), "The semantic element at the target of the edge");
    public static final Variable GRAPHICAL_EDGE_SOURCE = new Variable("graphicalEdgeSource", List.of(Element.class), "The virtual diagram element at the source of the edge");
    public static final Variable GRAPHICAL_EDGE_TARGET = new Variable("graphicalEdgeTarget", List.of(Element.class), "The virtual diagram element at the target of the edge");
    public static final Variable CACHE = new Variable("cache", List.of(DiagramRenderingCache.class), "An internal cache used to retrieve some internal data during the rendering");

    @Override
    public List<Variable> getVariables(String operation) {
        return switch (operation) {
            case NODE_SEMANTIC_CANDIDATES -> nodeSemanticCandidates();
            case NODE_PRECONDITION -> nodePrecondition();
            case NODE_LABEL -> nodeLabel();
            case NODE_WIDTH_COMPUTATION, NODE_HEIGHT_COMPUTATION -> nodeWidthAndHeight();
            case EDGE_SEMANTIC_CANDIDATES -> edgeSemanticCandidates();
            case EDGE_SOURCE_NODES, EDGE_TARGET_NODES -> edgeSourceAndTargetNodes();
            case EDGE_PRECONDITION -> edgePrecondition();
            case EDGE_BEGIN_LABEL, EDGE_LABEL, EDGE_END_LABEL -> edgeLabels();
            default -> noVariables();
        };
    }

    private List<Variable> nodeSemanticCandidates() {
        return List.of(CommonVariables.SELF, COLLAPSING_STATE, CommonVariables.EDITING_CONTEXT, SEMANTIC_ELEMENT_IDS, DIAGRAM_EVENT, PREVIOUS_DIAGRAM, LABEL, OWNER_ID, CommonVariables.ENVIRONMENT);
    }

    private List<Variable> nodePrecondition() {
        return List.of(CommonVariables.SELF, CommonVariables.EDITING_CONTEXT, CommonVariables.ENVIRONMENT);
    }

    private List<Variable> nodeLabel() {
        return List.of(CommonVariables.SELF, COLLAPSING_STATE, CommonVariables.EDITING_CONTEXT, CommonVariables.ENVIRONMENT);
    }

    private List<Variable> nodeWidthAndHeight() {
        return List.of(CommonVariables.SELF, CommonVariables.EDITING_CONTEXT, CommonVariables.ENVIRONMENT);
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
        return List.of(CommonVariables.SELF, SEMANTIC_EDGE_SOURCE, SEMANTIC_EDGE_TARGET, GRAPHICAL_EDGE_SOURCE, GRAPHICAL_EDGE_TARGET, CommonVariables.EDITING_CONTEXT, DIAGRAM_EVENT, PREVIOUS_DIAGRAM, CACHE,  LABEL, CommonVariables.ENVIRONMENT);
    }
}
