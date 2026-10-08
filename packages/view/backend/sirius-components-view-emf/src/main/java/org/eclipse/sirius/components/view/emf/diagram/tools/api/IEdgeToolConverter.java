/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
package org.eclipse.sirius.components.view.emf.diagram.tools.api;

import java.util.List;

import org.eclipse.sirius.components.collaborative.diagrams.dto.SingleClickOnTwoDiagramElementsCandidate;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.palette.dto.ITool;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.NodeTool;

/**
 * Used to create edge tools.
 *
 * @author sbegaudeau
 */
public interface IEdgeToolConverter {
    ITool createEdgeTool(AQLInterpreter interpreter, EdgeTool viewEdgeTool, DiagramDescription diagramDescription, IDiagramElementDescription diagramElementDescription, VariableManager variableManager);

    /**
     * Returns an edge tool from a node tool declared inside the palette of an edge tool.
     *
     * @param viewNodeTool The node tool
     * @param viewEdgeTool The edge tool parent
     * @param diagramDescription The diagram description
     * @param diagramElementDescription The diagram element description
     * @param variableManager The variableManager
     * @param interpreter The AQL interpreter
     * @return The candidates of the edge tool
     * @since v2026.11.0
     */
    ITool createEdgeToolFromNodeTool(NodeTool viewNodeTool, EdgeTool viewEdgeTool, DiagramDescription diagramDescription, IDiagramElementDescription diagramElementDescription, VariableManager variableManager, AQLInterpreter interpreter);

    /**
     * Returns the candidates of the given edge tool.
     *
     * @param viewEdgeTool The edge tool
     * @param diagramDescription The diagram description
     * @param diagramElementDescription The diagram element description
     * @return The candidates of the edge tool
     * @since v2026.11.0
     */
    List<SingleClickOnTwoDiagramElementsCandidate> getConnectorToolsCandidates(EdgeTool viewEdgeTool, DiagramDescription diagramDescription, IDiagramElementDescription diagramElementDescription);
}
