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

import static org.eclipse.sirius.components.collaborative.diagrams.api.DiagramInteractionOperations.GROUP_TOOL;
import static org.eclipse.sirius.components.collaborative.diagrams.api.DiagramInteractionOperations.NODE_DROP;
import static org.eclipse.sirius.components.collaborative.diagrams.api.DiagramInteractionOperations.OBJECT_DROP;
import static org.eclipse.sirius.components.collaborative.diagrams.api.DiagramInteractionOperations.SINGLE_CLICK_TOOL;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.DIAGRAM_DROP_NODES;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.EDGE_BEGIN_LABEL;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.EDGE_END_LABEL;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.EDGE_LABEL;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.EDGE_SOURCE_NODES;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.EDGE_TARGET_NODES;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.NODE_HEIGHT_COMPUTATION;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.NODE_LABEL;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.NODE_PRECONDITION;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.NODE_WIDTH_COMPUTATION;
import static org.eclipse.sirius.components.diagrams.variables.DiagramRenderingOperations.EDGE_PRECONDITION;
import static org.eclipse.sirius.components.diagrams.variables.DiagramRenderingOperations.EDGE_SEMANTIC_CANDIDATES;
import static org.eclipse.sirius.components.diagrams.variables.DiagramRenderingOperations.NODE_SEMANTIC_CANDIDATES;

import java.util.List;

import org.eclipse.sirius.components.core.api.variables.CommonVariables;
import org.eclipse.sirius.components.core.api.variables.IVariableProvider;
import org.eclipse.sirius.components.representations.Variable;
import org.springframework.stereotype.Service;

/**
 * Used to provide the variables available for all diagram operations.
 *
 * @author sbegaudeau
 */
@Service
public class DiagramVariableProvider implements IVariableProvider {

    @Override
    public List<Variable> getVariables(String operation) {
        return switch (operation) {
            case NODE_SEMANTIC_CANDIDATES -> this.nodeSemanticCandidates();
            case NODE_PRECONDITION -> this.nodePrecondition();
            case NODE_LABEL -> this.nodeLabel();
            case NODE_WIDTH_COMPUTATION, NODE_HEIGHT_COMPUTATION -> this.nodeWidthAndHeight();
            case EDGE_SEMANTIC_CANDIDATES -> this.edgeSemanticCandidates();
            case EDGE_SOURCE_NODES, EDGE_TARGET_NODES -> this.edgeSourceAndTargetNodes();
            case EDGE_PRECONDITION -> this.edgePrecondition();
            case EDGE_BEGIN_LABEL, EDGE_LABEL, EDGE_END_LABEL -> this.edgeLabels();
            case DIAGRAM_DROP_NODES -> this.diagramDropNodes();
            case SINGLE_CLICK_TOOL -> this.singleClickTool();
            case GROUP_TOOL -> this.groupTool();
            case NODE_DROP -> this.nodeDrop();
            case OBJECT_DROP -> this.objectDrop();
            default -> this.noVariables();
        };
    }

    private List<Variable> nodeSemanticCandidates() {
        return List.of(CommonVariables.SELF, DiagramVariables.COLLAPSING_STATE, CommonVariables.EDITING_CONTEXT, DiagramVariables.SEMANTIC_ELEMENT_IDS, DiagramVariables.DIAGRAM_EVENT, DiagramVariables.PREVIOUS_DIAGRAM, DiagramVariables.LABEL, CommonVariables.ENVIRONMENT, DiagramVariables.ANCESTORS);
    }

    private List<Variable> nodePrecondition() {
        return List.of(CommonVariables.SELF, CommonVariables.EDITING_CONTEXT, CommonVariables.ENVIRONMENT, DiagramVariables.ANCESTORS);
    }

    private List<Variable> nodeLabel() {
        return List.of(CommonVariables.SELF, DiagramVariables.COLLAPSING_STATE, CommonVariables.EDITING_CONTEXT, CommonVariables.ENVIRONMENT, DiagramVariables.ANCESTORS);
    }

    private List<Variable> nodeWidthAndHeight() {
        return List.of(CommonVariables.SELF, CommonVariables.EDITING_CONTEXT, CommonVariables.ENVIRONMENT, DiagramVariables.ANCESTORS);
    }

    private List<Variable> edgeSemanticCandidates() {
        return List.of(CommonVariables.SELF, CommonVariables.EDITING_CONTEXT, CommonVariables.ENVIRONMENT);
    }

    private List<Variable> edgeSourceAndTargetNodes() {
        return List.of(CommonVariables.SELF, CommonVariables.EDITING_CONTEXT, CommonVariables.ENVIRONMENT);
    }

    private List<Variable> edgePrecondition() {
        return List.of(CommonVariables.SELF, DiagramVariables.SEMANTIC_EDGE_SOURCE, DiagramVariables.SEMANTIC_EDGE_TARGET, DiagramVariables.GRAPHICAL_EDGE_SOURCE, DiagramVariables.GRAPHICAL_EDGE_TARGET, CommonVariables.EDITING_CONTEXT, DiagramVariables.DIAGRAM_EVENT, DiagramVariables.PREVIOUS_DIAGRAM, DiagramVariables.CACHE, DiagramVariables.LABEL, CommonVariables.ENVIRONMENT);
    }

    private List<Variable> edgeLabels() {
        return List.of(CommonVariables.SELF, DiagramVariables.SEMANTIC_EDGE_SOURCE, DiagramVariables.SEMANTIC_EDGE_TARGET, DiagramVariables.GRAPHICAL_EDGE_SOURCE, DiagramVariables.GRAPHICAL_EDGE_TARGET, CommonVariables.EDITING_CONTEXT, DiagramVariables.DIAGRAM_EVENT, DiagramVariables.PREVIOUS_DIAGRAM, DiagramVariables.CACHE, DiagramVariables.LABEL, CommonVariables.ENVIRONMENT);
    }

    private List<Variable> diagramDropNodes() {
        return List.of(CommonVariables.EDITING_CONTEXT, CommonVariables.ENVIRONMENT, DiagramVariables.DIAGRAM_CONTEXT, DiagramVariables.DROPPED_ELEMENTS, DiagramVariables.DROPPED_NODES, DiagramVariables.DROPPED_ELEMENT, DiagramVariables.DROPPED_NODE, DiagramVariables.TARGET_ELEMENT, DiagramVariables.TARGET_NODE);
    }

    private List<Variable> singleClickTool() {
        return List.of(CommonVariables.SELF, CommonVariables.EDITING_CONTEXT, CommonVariables.ENVIRONMENT, DiagramVariables.DIAGRAM_CONTEXT, DiagramVariables.DIAGRAM_SERVICES, DiagramVariables.SELECTED_NODE, DiagramVariables.SELECTED_EDGE);
    }

    private List<Variable> groupTool() {
        return List.of(CommonVariables.SELF, CommonVariables.EDITING_CONTEXT, CommonVariables.ENVIRONMENT, DiagramVariables.DIAGRAM_CONTEXT, DiagramVariables.DIAGRAM_SERVICES, DiagramVariables.SELECTED_NODES, DiagramVariables.SELECTED_EDGES);
    }

    private List<Variable> nodeDrop() {
        return List.of(CommonVariables.EDITING_CONTEXT, CommonVariables.ENVIRONMENT, DiagramVariables.DIAGRAM_CONTEXT, DiagramVariables.DIAGRAM_SERVICES, DiagramVariables.DROPPED_ELEMENTS, DiagramVariables.DROPPED_NODES, DiagramVariables.DROPPED_ELEMENT, DiagramVariables.DROPPED_NODE, DiagramVariables.TARGET_ELEMENT, DiagramVariables.TARGET_NODE);
    }

    private List<Variable> objectDrop() {
        return List.of(CommonVariables.SELF, CommonVariables.EDITING_CONTEXT, CommonVariables.ENVIRONMENT, DiagramVariables.DIAGRAM_CONTEXT, DiagramVariables.DIAGRAM_SERVICES, DiagramVariables.SELECTED_NODE);
    }
}
