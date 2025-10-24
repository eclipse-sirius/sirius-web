/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.diagrams.api.IReconnectionToolsExecutor;
import org.eclipse.sirius.components.collaborative.diagrams.api.ReconnectionToolInterpreterData;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.events.ReconnectEdgeKind;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewDiagramDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationExecutor;
import org.eclipse.sirius.components.view.emf.operations.api.OperationExecutionStatus;
import org.springframework.stereotype.Service;

/**
 * The reconnection tools executor for the view layer.
 *
 * @author gcoutable
 */
@Service
public class ViewReconnectionToolsExecutor implements IReconnectionToolsExecutor {

    private final IOperationExecutor operationExecutor;

    private final IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService;

    private final IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IViewAQLInterpreterFactory aqlInterpreterFactory;

    public ViewReconnectionToolsExecutor(IOperationExecutor operationExecutor, IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService, IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate, IViewAQLInterpreterFactory aqlInterpreterFactory) {
        this.operationExecutor = Objects.requireNonNull(operationExecutor);
        this.viewDiagramDescriptionSearchService = Objects.requireNonNull(viewDiagramDescriptionSearchService);
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.aqlInterpreterFactory = Objects.requireNonNull(aqlInterpreterFactory);
    }

    @Override
    public boolean canExecute(DiagramDescription diagramDescription) {
        return this.viewRepresentationDescriptionPredicate.test(diagramDescription);
    }

    @Override
    public IStatus execute(IEditingContext editingContext, ReconnectionToolInterpreterData toolInterpreterData, Edge edge, EdgeDescription edgeDescription, ReconnectEdgeKind reconnectEdgeKind,
            DiagramDescription diagramDescription) {
        IStatus status = new Failure("");

        var optionalDiagramDescription = this.viewDiagramDescriptionSearchService.findById(editingContext, diagramDescription.getId());
        if (optionalDiagramDescription.isPresent()) {
            org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription = optionalDiagramDescription.get();

            var optionalViewEdgeDescription = this.viewDiagramDescriptionSearchService.findViewEdgeDescriptionById(editingContext, edgeDescription.getId());
            if (optionalViewEdgeDescription.isPresent()) {
                var viewEdgeDescription = optionalViewEdgeDescription.get();

                List<org.eclipse.sirius.components.view.diagram.EdgeReconnectionTool> edgeReconnectionTools = new ToolFinder().findReconnectionTools(viewEdgeDescription, toolInterpreterData.getKind());
                for (org.eclipse.sirius.components.view.diagram.EdgeReconnectionTool edgeReconnectionTool : edgeReconnectionTools) {
                    VariableManager variableManager = this.createVariableManager(toolInterpreterData, editingContext);

                    AQLInterpreter interpreter = this.aqlInterpreterFactory.createInterpreter(editingContext, (View) viewDiagramDescription.eContainer());
                    var result = this.operationExecutor.execute(interpreter, variableManager, edgeReconnectionTool.getBody());
                    if (result.status() == OperationExecutionStatus.SUCCESS) {
                        status = new Success();
                    }
                }
            }
        }
        return status;
    }

    private VariableManager createVariableManager(ReconnectionToolInterpreterData toolInterpreterData, IEditingContext editingContext) {
        VariableManager variableManager = new VariableManager();
        variableManager.put("diagram", toolInterpreterData.getDiagramContext().diagram());
        variableManager.put("semanticReconnectionSource", toolInterpreterData.getSemanticReconnectionSource());
        variableManager.put("reconnectionSourceView", toolInterpreterData.getReconnectionSourceView());
        variableManager.put("semanticReconnectionTarget", toolInterpreterData.getSemanticReconnectionTarget());
        variableManager.put("reconnectionTargetView", toolInterpreterData.getReconnectionTargetView());
        variableManager.put("edgeSemanticElement", toolInterpreterData.getSemanticElement());
        variableManager.put("otherEnd", toolInterpreterData.getOtherEdgeEnd());
        variableManager.put("semanticOtherEnd", toolInterpreterData.getSemanticOtherEdgeEnd());
        variableManager.put("edgeView", toolInterpreterData.getEdgeView());
        variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
        return variableManager;
    }

}
