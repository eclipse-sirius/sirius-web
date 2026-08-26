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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sirius.components.collaborative.diagrams.api.DiagramInteractionOperations;
import org.eclipse.sirius.components.core.api.variables.IOperationProvider;
import org.eclipse.sirius.components.diagrams.variables.DiagramRenderingOperations;
import org.eclipse.sirius.components.representations.Operation;
import org.springframework.stereotype.Service;

/**
 * Used to provide all the diagram operations.
 *
 * @author sbegaudeau
 */
@Service
public class DiagramOperationProvider implements IOperationProvider {

    public static final String DIAGRAM_DESCRIPTION_DROP_NODES = "DiagramDescription#dropNodes";
    public static final Operation DIAGRAM_DESCRIPTION_DROP_NODES_OPERATION = new Operation(DIAGRAM_DESCRIPTION_DROP_NODES, "Used to drop node from one containing element to another");

    public static final String NODE_DESCRIPTION_PRECONDITION = "NodeDescription#precondition";
    public static final Operation NODE_DESCRIPTION_PRECONDITION_OPERATION = new Operation(NODE_DESCRIPTION_PRECONDITION, "Used to filter the semantic elements which should be rendered as nodes");

    public static final String NODE_DESCRIPTION_LABEL = "NodeDescription#label";
    public static final Operation NODE_DESCRIPTION_LABEL_OPERATION = new Operation(NODE_DESCRIPTION_LABEL, "Used to compute the label of a node");

    public static final String NODE_DESCRIPTION_WIDTH_COMPUTATION = "NodeDescription#widthComputation";
    public static final Operation NODE_DESCRIPTION_WIDTH_COMPUTATION_OPERATION = new Operation(NODE_DESCRIPTION_WIDTH_COMPUTATION, "Used to provide a requested width for the node");

    public static final String NODE_DESCRIPTION_HEIGHT_COMPUTATION = "NodeDescription#heightComputation";
    public static final Operation NODE_DESCRIPTION_HEIGHT_COMPUTATION_OPERATION = new Operation(NODE_DESCRIPTION_HEIGHT_COMPUTATION, "Used to provide a requested height for the node");

    public static final String EDGE_DESCRIPTION_LABEL = "EdgeDescription#label";
    public static final Operation EDGE_DESCRIPTION_LABEL_OPERATION = new Operation(EDGE_DESCRIPTION_LABEL, "Used to compute the label at the center of an edge");

    public static final String EDGE_DESCRIPTION_BEGIN_LABEL = "EdgeDescription#beginLabel";
    public static final Operation EDGE_DESCRIPTION_BEGIN_LABEL_OPERATION = new Operation(EDGE_DESCRIPTION_BEGIN_LABEL, "Used to compute the label at the beginning of an edge");

    public static final String EDGE_DESCRIPTION_END_LABEL = "EdgeDescription#endLabel";
    public static final Operation EDGE_DESCRIPTION_END_LABEL_OPERATION = new Operation(EDGE_DESCRIPTION_END_LABEL, "Used to compute the label at the end of an edge");

    public static final String EDGE_DESCRIPTION_SOURCE_NODES = "EdgeDescription#sourceNodes";
    public static final Operation EDGE_DESCRIPTION_SOURCE_NODES_OPERATION = new Operation(EDGE_DESCRIPTION_SOURCE_NODES, "Used to compute the nodes which should be considered as source of the edge");

    public static final String EDGE_DESCRIPTION_TARGET_NODES = "EdgeDescription#targetNodes";
    public static final Operation EDGE_DESCRIPTION_TARGET_NODES_OPERATION = new Operation(EDGE_DESCRIPTION_TARGET_NODES, "Used to compute the nodes which should be considered as target of the edge");

    @Override
    public List<Operation> getOperations() {
        List<Operation> operations = new ArrayList<>();

        // Rendering
        operations.add(DiagramRenderingOperations.NODE_DESCRIPTION_SEMANTIC_CANDIDATES_OPERATION);
        operations.add(DiagramRenderingOperations.EDGE_DESCRIPTION_SEMANTIC_CANDIDATES_OPERATION);
        operations.add(DiagramRenderingOperations.EDGE_DESCRIPTION_PRECONDITION_OPERATION);

        operations.add(NODE_DESCRIPTION_PRECONDITION_OPERATION);
        operations.add(NODE_DESCRIPTION_LABEL_OPERATION);
        operations.add(NODE_DESCRIPTION_WIDTH_COMPUTATION_OPERATION);
        operations.add(NODE_DESCRIPTION_HEIGHT_COMPUTATION_OPERATION);

        operations.add(EDGE_DESCRIPTION_BEGIN_LABEL_OPERATION);
        operations.add(EDGE_DESCRIPTION_LABEL_OPERATION);
        operations.add(EDGE_DESCRIPTION_END_LABEL_OPERATION);
        operations.add(EDGE_DESCRIPTION_SOURCE_NODES_OPERATION);
        operations.add(EDGE_DESCRIPTION_TARGET_NODES_OPERATION);


        // Operations
        operations.add(DiagramInteractionOperations.SINGLE_CLICK_TOOL_OPERATION);
        operations.add(DiagramInteractionOperations.GROUP_TOOL_OPERATION);
        operations.add(DiagramInteractionOperations.NODE_DROP_OPERATION);
        operations.add(DiagramInteractionOperations.EDGE_TOOL_OPERATION);

        operations.add(DIAGRAM_DESCRIPTION_DROP_NODES_OPERATION);

        return operations;
    }
}
