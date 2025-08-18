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
package org.eclipse.sirius.components.view.emf.diagram.operations;

import java.util.List;
import java.util.Map;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.diagram.DeleteView;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationHandler;
import org.eclipse.sirius.components.view.emf.operations.api.OperationEvaluationResult;
import org.eclipse.sirius.components.view.emf.operations.api.OperationExecutionStatus;
import org.springframework.stereotype.Service;

/**
 * Used to handle the delete view operation.
 *
 * @author sbegaudeau
 */
@Service
public class DeleteViewOperationHandler implements IOperationHandler {
    @Override
    public boolean canHandle(AQLInterpreter interpreter, VariableManager variableManager, Operation operation) {
        return operation instanceof DeleteView;
    }

    @Override
    public OperationEvaluationResult handle(AQLInterpreter interpreter, VariableManager variableManager, Operation operation) {
        var optionalDiagramContext = variableManager.get(DiagramContext.DIAGRAM_CONTEXT, DiagramContext.class);
        if (operation instanceof DeleteView deleteViewOperation && optionalDiagramContext.isPresent()) {
            var diagramContext = optionalDiagramContext.get();

            var optionalElement = interpreter.evaluateExpression(variableManager.getVariables(), deleteViewOperation.getViewExpression()).asObject();
            if (optionalElement.isPresent()) {
                var element = optionalElement.get();

                String elementId = null;
                if (element instanceof Node node) {
                    elementId = node.getId();
                } else if (element instanceof Edge edge) {
                    elementId = edge.getId();
                }
                if (elementId != null) {
                    ViewDeletionRequest viewDeletionRequest = ViewDeletionRequest.newViewDeletionRequest().elementId(elementId).build();
                    diagramContext.viewDeletionRequests().add(viewDeletionRequest);
                }
            }

            return new OperationEvaluationResult(OperationExecutionStatus.SUCCESS, List.of(variableManager), Map.of());
        }
        return new OperationEvaluationResult(OperationExecutionStatus.FAILURE, List.of(variableManager), Map.of());
    }
}
