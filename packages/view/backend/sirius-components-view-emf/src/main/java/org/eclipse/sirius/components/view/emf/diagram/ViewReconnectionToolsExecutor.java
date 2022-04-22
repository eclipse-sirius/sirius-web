/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.diagrams.api.IReconnectionToolsExecutor;
import org.eclipse.sirius.components.collaborative.diagrams.api.ReconnectionToolInterpreterData;
import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.events.ReconnectEdgeKind;
import org.eclipse.sirius.components.diagrams.tools.EdgeReconnectionTool;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

/**
 * The reconnection tools executor for the view layer.
 *
 * @author gcoutable
 */
@Service
public class ViewReconnectionToolsExecutor implements IReconnectionToolsExecutor {

    private final IIdentifierProvider identifierProvider;

    public ViewReconnectionToolsExecutor(IIdentifierProvider identifierProvider) {
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
    }

    @Override
    public boolean canExecute(DiagramDescription diagramDescription) {
        return this.identifierProvider.findVsmElementId(diagramDescription.getId()).isEmpty();
    }

    @Override
    public IStatus execute(ReconnectionToolInterpreterData toolInterpreterData, Edge edge, EdgeDescription edgeDescription, ReconnectEdgeKind reconnectEdgeKind,
            DiagramDescription diagramDescription) {
        IStatus status = new Failure(""); //$NON-NLS-1$

        // @formatter:off
        var optionalEdgeReconnectionTool = diagramDescription.getTools().stream()
                .filter(EdgeReconnectionTool.class::isInstance)
                .map(EdgeReconnectionTool.class::cast)
                .filter(tool -> reconnectEdgeKind.equals(tool.getKind()))
                .findFirst();
        // @formatter:on

        if (optionalEdgeReconnectionTool.isPresent()) {
            EdgeReconnectionTool tool = optionalEdgeReconnectionTool.get();
            VariableManager variableManager = this.createVariableManager(toolInterpreterData);
            status = tool.getHandler().apply(variableManager);
        }

        return status;
    }

    private VariableManager createVariableManager(ReconnectionToolInterpreterData toolInterpreterData) {
        VariableManager variableManager = new VariableManager();
        variableManager.put("diagram", toolInterpreterData.getDiagram()); //$NON-NLS-1$
        variableManager.put("semanticReconnectionSource", toolInterpreterData.getSemanticReconnectionSource()); //$NON-NLS-1$
        variableManager.put("reconnectionSourceView", toolInterpreterData.getReconnectionSourceView()); //$NON-NLS-1$
        variableManager.put("semanticReconnectionTarget", toolInterpreterData.getSemanticReconnectionTarget()); //$NON-NLS-1$
        variableManager.put("reconnectionTargetView", toolInterpreterData.getReconnectionTargetView()); //$NON-NLS-1$
        variableManager.put("edgeSemanticElement", toolInterpreterData.getSemanticElement()); //$NON-NLS-1$
        return variableManager;
    }

}
