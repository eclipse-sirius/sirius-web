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
package org.eclipse.sirius.components.diagrams.variables;

import org.eclipse.sirius.components.representations.Operation;

/**
 * Operations used while rendering diagrams.
 *
 * @author sbegaudeau
 * @since v2026.1.0
 */
public class DiagramRenderingOperations {

    public static final String NODE_SEMANTIC_CANDIDATES = "Node#semanticCandidates";
    public static final Operation NODE_SEMANTIC_CANDIDATES_OPERATION = new Operation(NODE_SEMANTIC_CANDIDATES, "Used to provide the semantic elements that will be rendered as nodes");

    public static final String EDGE_SEMANTIC_CANDIDATES = "Edge#semanticCandidates";
    public static final Operation EDGE_SEMANTIC_CANDIDATES_OPERATION = new Operation(EDGE_SEMANTIC_CANDIDATES, "Used to provide the semantic elements that will be rendered as edges");

    public static final String EDGE_PRECONDITION = "Edge#precondition";
    public static final Operation EDGE_PRECONDITION_OPERATION = new Operation(EDGE_PRECONDITION, "Used to filter the semantic elements which should be rendered as edges");
}
