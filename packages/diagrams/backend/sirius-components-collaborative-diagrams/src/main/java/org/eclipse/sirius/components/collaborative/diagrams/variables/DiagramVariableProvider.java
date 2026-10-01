/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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

import static org.eclipse.sirius.components.collaborative.diagrams.api.DiagramInteractionOperations.EDGE_TOOL;
import static org.eclipse.sirius.components.collaborative.diagrams.api.DiagramInteractionOperations.GROUP_TOOL;
import static org.eclipse.sirius.components.collaborative.diagrams.api.DiagramInteractionOperations.NODE_DROP;
import static org.eclipse.sirius.components.collaborative.diagrams.api.DiagramInteractionOperations.OBJECT_DROP;
import static org.eclipse.sirius.components.collaborative.diagrams.api.DiagramInteractionOperations.SINGLE_CLICK_TOOL;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.DIAGRAM_DESCRIPTION_DROP_NODES;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.EDGE_DESCRIPTION_BEGIN_LABEL;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.EDGE_DESCRIPTION_END_LABEL;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.EDGE_DESCRIPTION_LABEL;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.EDGE_DESCRIPTION_SOURCE_NODES;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.EDGE_DESCRIPTION_TARGET_NODES;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.NODE_DESCRIPTION_HEIGHT_COMPUTATION;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.NODE_DESCRIPTION_LABEL;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.NODE_DESCRIPTION_PRECONDITION;
import static org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramOperationProvider.NODE_DESCRIPTION_WIDTH_COMPUTATION;
import static org.eclipse.sirius.components.diagrams.variables.DiagramRenderingOperations.EDGE_DESCRIPTION_PRECONDITION;
import static org.eclipse.sirius.components.diagrams.variables.DiagramRenderingOperations.EDGE_DESCRIPTION_SEMANTIC_CANDIDATES;
import static org.eclipse.sirius.components.diagrams.variables.DiagramRenderingOperations.NODE_DESCRIPTION_SEMANTIC_CANDIDATES;

import java.util.List;
import java.util.stream.Stream;

import org.eclipse.sirius.components.core.api.variables.CoreVariables;
import org.eclipse.sirius.components.core.api.variables.IVariableProvider;
import org.eclipse.sirius.components.representations.RepresentationVariables;
import org.eclipse.sirius.components.representations.VariableUsage;
import org.springframework.stereotype.Service;

/**
 * Used to provide the variables available for all diagram operations.
 *
 * @author sbegaudeau
 */
@Service
public class DiagramVariableProvider implements IVariableProvider {

    @Override
    public List<VariableUsage> getVariables(String operation) {
        return switch (operation) {
            case NODE_DESCRIPTION_SEMANTIC_CANDIDATES -> this.nodeSemanticCandidates();
            case NODE_DESCRIPTION_PRECONDITION -> this.nodePrecondition();
            case NODE_DESCRIPTION_LABEL -> this.nodeLabel();
            case NODE_DESCRIPTION_WIDTH_COMPUTATION, NODE_DESCRIPTION_HEIGHT_COMPUTATION -> this.nodeWidthAndHeight();
            case EDGE_DESCRIPTION_SEMANTIC_CANDIDATES -> this.edgeSemanticCandidates();
            case EDGE_DESCRIPTION_SOURCE_NODES, EDGE_DESCRIPTION_TARGET_NODES -> this.edgeSourceAndTargetNodes();
            case EDGE_DESCRIPTION_PRECONDITION -> this.edgePrecondition();
            case EDGE_DESCRIPTION_BEGIN_LABEL, EDGE_DESCRIPTION_LABEL, EDGE_DESCRIPTION_END_LABEL -> this.edgeLabels();
            case DIAGRAM_DESCRIPTION_DROP_NODES -> this.diagramDropNodes();
            case SINGLE_CLICK_TOOL -> this.singleClickTool();
            case GROUP_TOOL -> this.groupTool();
            case NODE_DROP -> this.nodeDrop();
            case OBJECT_DROP -> this.objectDrop();
            case EDGE_TOOL -> this.edgeTool();
            default -> this.noVariables();
        };
    }

    private List<VariableUsage> nodeSemanticCandidates() {
        return Stream.of(RepresentationVariables.SELF, DiagramVariables.COLLAPSING_STATE, CoreVariables.EDITING_CONTEXT, DiagramVariables.SEMANTIC_ELEMENT_IDS, DiagramVariables.DIAGRAM_EVENT, DiagramVariables.PREVIOUS_DIAGRAM, DiagramVariables.LABEL, CoreVariables.ENVIRONMENT, DiagramVariables.ANCESTORS)
                .map(VariableUsage::new).toList();
    }

    private List<VariableUsage> nodePrecondition() {
        return Stream.of(RepresentationVariables.SELF, CoreVariables.EDITING_CONTEXT, CoreVariables.ENVIRONMENT, DiagramVariables.ANCESTORS)
                .map(VariableUsage::new).toList();
    }

    private List<VariableUsage> nodeLabel() {
        return Stream.of(RepresentationVariables.SELF, DiagramVariables.COLLAPSING_STATE, CoreVariables.EDITING_CONTEXT, CoreVariables.ENVIRONMENT, DiagramVariables.ANCESTORS)
                .map(VariableUsage::new).toList();
    }

    private List<VariableUsage> nodeWidthAndHeight() {
        return Stream.of(RepresentationVariables.SELF, CoreVariables.EDITING_CONTEXT, CoreVariables.ENVIRONMENT, DiagramVariables.ANCESTORS)
                .map(VariableUsage::new).toList();
    }

    private List<VariableUsage> edgeSemanticCandidates() {
        return Stream.of(RepresentationVariables.SELF, CoreVariables.EDITING_CONTEXT, CoreVariables.ENVIRONMENT)
                .map(VariableUsage::new).toList();
    }

    private List<VariableUsage> edgeSourceAndTargetNodes() {
        return Stream.of(RepresentationVariables.SELF, CoreVariables.EDITING_CONTEXT, CoreVariables.ENVIRONMENT)
                .map(VariableUsage::new).toList();
    }

    private List<VariableUsage> edgePrecondition() {
        return Stream.of(RepresentationVariables.SELF, DiagramVariables.SEMANTIC_EDGE_SOURCE, DiagramVariables.SEMANTIC_EDGE_TARGET, DiagramVariables.GRAPHICAL_EDGE_SOURCE, DiagramVariables.GRAPHICAL_EDGE_TARGET, CoreVariables.EDITING_CONTEXT, DiagramVariables.DIAGRAM_EVENT, DiagramVariables.PREVIOUS_DIAGRAM, DiagramVariables.CACHE, DiagramVariables.LABEL, CoreVariables.ENVIRONMENT)
                .map(VariableUsage::new).toList();
    }

    private List<VariableUsage> edgeLabels() {
        return Stream.of(RepresentationVariables.SELF, DiagramVariables.SEMANTIC_EDGE_SOURCE, DiagramVariables.SEMANTIC_EDGE_TARGET, DiagramVariables.GRAPHICAL_EDGE_SOURCE, DiagramVariables.GRAPHICAL_EDGE_TARGET, CoreVariables.EDITING_CONTEXT, DiagramVariables.DIAGRAM_EVENT, DiagramVariables.PREVIOUS_DIAGRAM, DiagramVariables.CACHE, DiagramVariables.LABEL, CoreVariables.ENVIRONMENT)
                .map(VariableUsage::new).toList();
    }

    private List<VariableUsage> diagramDropNodes() {
        return Stream.of(CoreVariables.EDITING_CONTEXT, CoreVariables.ENVIRONMENT, DiagramVariables.DIAGRAM_CONTEXT, DiagramVariables.DROPPED_ELEMENTS, DiagramVariables.DROPPED_NODES, DiagramVariables.DROPPED_ELEMENT, DiagramVariables.DROPPED_NODE, DiagramVariables.TARGET_ELEMENT, DiagramVariables.TARGET_NODE)
                .map(VariableUsage::new).toList();
    }

    private List<VariableUsage> singleClickTool() {
        var requiredVariables = Stream.of(RepresentationVariables.SELF, CoreVariables.EDITING_CONTEXT, CoreVariables.ENVIRONMENT, DiagramVariables.DIAGRAM_CONTEXT, DiagramVariables.DIAGRAM_SERVICES)
                .map(VariableUsage::new);
        var optionalVariables = Stream.of(DiagramVariables.SELECTED_NODE, DiagramVariables.SELECTED_EDGE)
                .map(variable -> new VariableUsage(variable, true));
        return Stream.concat(requiredVariables, optionalVariables).toList();
    }

    private List<VariableUsage> groupTool() {
        return Stream.of(RepresentationVariables.SELF, CoreVariables.EDITING_CONTEXT, CoreVariables.ENVIRONMENT, DiagramVariables.DIAGRAM_CONTEXT, DiagramVariables.DIAGRAM_SERVICES, DiagramVariables.SELECTED_NODES, DiagramVariables.SELECTED_EDGES)
                .map(VariableUsage::new).toList();
    }

    private List<VariableUsage> nodeDrop() {
        return Stream.of(CoreVariables.EDITING_CONTEXT, CoreVariables.ENVIRONMENT, DiagramVariables.DIAGRAM_CONTEXT, DiagramVariables.DIAGRAM_SERVICES, DiagramVariables.DROPPED_ELEMENTS, DiagramVariables.DROPPED_NODES, DiagramVariables.DROPPED_ELEMENT, DiagramVariables.DROPPED_NODE, DiagramVariables.TARGET_ELEMENT, DiagramVariables.TARGET_NODE)
                .map(VariableUsage::new).toList();
    }

    private List<VariableUsage> objectDrop() {
        return Stream.of(RepresentationVariables.SELF, CoreVariables.EDITING_CONTEXT, CoreVariables.ENVIRONMENT, DiagramVariables.DIAGRAM_CONTEXT, DiagramVariables.DIAGRAM_SERVICES, DiagramVariables.SELECTED_NODE)
                .map(VariableUsage::new).toList();
    }

    private List<VariableUsage> edgeTool() {
        return Stream.of(RepresentationVariables.SELF, CoreVariables.EDITING_CONTEXT, CoreVariables.ENVIRONMENT, DiagramVariables.DIAGRAM_CONTEXT, DiagramVariables.DIAGRAM_SERVICES, DiagramVariables.SEMANTIC_EDGE_SOURCE, DiagramVariables.SEMANTIC_EDGE_TARGET, DiagramVariables.EDGE_SOURCE, DiagramVariables.EDGE_TARGET, DiagramVariables.SELECTED_NODE, DiagramVariables.SELECTED_EDGE)
                .map(VariableUsage::new).toList();
    }
}
