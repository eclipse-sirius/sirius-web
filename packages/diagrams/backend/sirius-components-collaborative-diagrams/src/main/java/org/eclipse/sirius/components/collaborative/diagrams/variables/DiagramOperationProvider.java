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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sirius.components.core.api.variables.IOperationProvider;
import org.eclipse.sirius.components.core.api.variables.Operation;
import org.springframework.stereotype.Service;

/**
 * Used to provide all the diagram operations.
 *
 * @author sbegaudeau
 */
@Service
public class DiagramOperationProvider implements IOperationProvider {

    public static final String NODE_SEMANTIC_CANDIDATES = "Node#semanticCandidates";
    public static final Operation NODE_SEMANTIC_CANDIDATES_OPERATION = new Operation(NODE_SEMANTIC_CANDIDATES, "Used to provide the semantic elements that will be rendered as nodes");

    public static final String NODE_PRECONDITION = "Node#precondition";
    public static final Operation NODE_PRECONDITION_OPERATION = new Operation(NODE_PRECONDITION, "Used to filter the semantic elements which should be rendered as nodes");

    public static final String NODE_LABEL = "Node#label";
    public static final Operation NODE_LABEL_OPERATION = new Operation(NODE_LABEL, "Used to compute the label of a node");

    public static final String NODE_WIDTH_COMPUTATION = "Node#widthComputation";
    public static final Operation NODE_WIDTH_COMPUTATION_OPERATION = new Operation(NODE_WIDTH_COMPUTATION, "Used to provide a requested width for the node");

    public static final String NODE_HEIGHT_COMPUTATION = "Node#heightComputation";
    public static final Operation NODE_HEIGHT_COMPUTATION_OPERATION = new Operation(NODE_HEIGHT_COMPUTATION, "Used to provide a requested height for the node");

    public static final String EDGE_SEMANTIC_CANDIDATES = "Edge#semanticCandidates";
    public static final Operation EDGE_SEMANTIC_CANDIDATES_OPERATION = new Operation(EDGE_SEMANTIC_CANDIDATES, "Used to provide the semantic elements that will be rendered as edges");

    public static final String EDGE_PRECONDITION = "Edge#precondition";
    public static final Operation EDGE_PRECONDITION_OPERATION = new Operation(EDGE_PRECONDITION, "Used to filter the semantic elements which should be rendered as edges");

    public static final String EDGE_LABEL = "Edge#label";
    public static final Operation EDGE_LABEL_OPERATION = new Operation(EDGE_LABEL, "Used to compute the label at the center of an edge");

    public static final String EDGE_BEGIN_LABEL = "Edge#beginLabel";
    public static final Operation EDGE_BEGIN_LABEL_OPERATION = new Operation(EDGE_BEGIN_LABEL, "Used to compute the label at the beginning of an edge");

    public static final String EDGE_END_LABEL = "Edge#endLabel";
    public static final Operation EDGE_END_LABEL_OPERATION = new Operation(EDGE_END_LABEL, "Used to compute the label at the end of an edge");

    public static final String EDGE_SOURCE_NODES = "Edge#sourceNodes";
    public static final Operation EDGE_SOURCE_NODES_OPERATION = new Operation(EDGE_SOURCE_NODES, "Used to compute the nodes which should be considered as source of the edge");

    public static final String EDGE_TARGET_NODES = "Edge#targetNodes";
    public static final Operation EDGE_TARGET_NODES_OPERATION = new Operation(EDGE_TARGET_NODES, "Used to compute the nodes which should be considered as target of the edge");

    @Override
    public List<Operation> getOperations() {
        List<Operation> operations = new ArrayList<>();

        operations.add(NODE_SEMANTIC_CANDIDATES_OPERATION);
        operations.add(NODE_PRECONDITION_OPERATION);
        operations.add(NODE_LABEL_OPERATION);
        operations.add(NODE_WIDTH_COMPUTATION_OPERATION);
        operations.add(NODE_HEIGHT_COMPUTATION_OPERATION);

        operations.add(EDGE_SEMANTIC_CANDIDATES_OPERATION);
        operations.add(EDGE_PRECONDITION_OPERATION);
        operations.add(EDGE_BEGIN_LABEL_OPERATION);
        operations.add(EDGE_LABEL_OPERATION);
        operations.add(EDGE_END_LABEL_OPERATION);
        operations.add(EDGE_SOURCE_NODES_OPERATION);
        operations.add(EDGE_TARGET_NODES_OPERATION);

        return operations;
    }
}
