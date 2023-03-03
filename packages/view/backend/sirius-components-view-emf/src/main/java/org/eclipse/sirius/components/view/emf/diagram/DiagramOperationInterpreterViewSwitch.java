/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.components.diagrams.components.NodeContainmentKind;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.CreateView;
import org.eclipse.sirius.components.view.DeleteView;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.emf.IOperationInterpreter;
import org.eclipse.sirius.components.view.emf.OperationInterpreterViewSwitch;
import org.eclipse.sirius.components.view.util.ViewSwitch;

/**
 * A specific OperationInterpreterViewSwitch for diagram {@link Operation}s.
 *
 * @author fbarbin
 */
public class DiagramOperationInterpreterViewSwitch extends ViewSwitch<Optional<VariableManager>> {
    private final Map<org.eclipse.sirius.components.view.NodeDescription, NodeDescription> convertedNodes;

    private final IDiagramContext diagramContext;

    private final IObjectService objectService;

    private final OperationInterpreterViewSwitch operationInterpreterViewSwitch;

    private final IOperationInterpreter operationInterpreter;

    private final AQLInterpreter interpreter;

    private final VariableManager variableManager;

    /**
     * Default constructor.
     *
     * @param variableManager
     *            the current {@link VariableManager}.
     * @param interpreter
     *            the {@link AQLInterpreter}.
     * @param objectService
     *            the {@link IObjectService}.
     * @param editService
     *            the {@link IEditService}.
     * @param diagramContext
     *            the {@link IDiagramContext} (optional).
     * @param operationInterpreter
     *            the {@link IOperationInterpreter} used for delegating sub operations executions. It is the
     *            responsibility of the {@link IOperationInterpreter} to delegate each operation execution to the
     *            appropriate switch according to the concrete representation.
     */
    public DiagramOperationInterpreterViewSwitch(VariableManager variableManager, AQLInterpreter interpreter, IObjectService objectService, IEditService editService, IDiagramContext diagramContext,
            Map<org.eclipse.sirius.components.view.NodeDescription, NodeDescription> convertedNodes, IOperationInterpreter operationInterpreter) {
        this.operationInterpreterViewSwitch = new OperationInterpreterViewSwitch(variableManager, interpreter, editService, operationInterpreter);
        this.variableManager = Objects.requireNonNull(variableManager);
        this.convertedNodes = Objects.requireNonNull(convertedNodes);
        this.objectService = Objects.requireNonNull(objectService);
        this.interpreter = Objects.requireNonNull(interpreter);
        this.operationInterpreter = Objects.requireNonNull(operationInterpreter);
        this.diagramContext = diagramContext;
    }

    @Override
    public Optional<VariableManager> caseCreateView(CreateView createViewOperation) {
        if (this.diagramContext == null) {
            return Optional.empty();
        }
        Map<String, Object> variables = this.variableManager.getVariables();
        // @formatter:off
        var optionalParentNode  = this.interpreter.evaluateExpression(variables, createViewOperation.getParentViewExpression())
                .asObject()
                .filter(Node.class::isInstance)
                .map(Node.class::cast);
        var optionalSemanticElement = this.interpreter.evaluateExpression(variables, createViewOperation.getSemanticElementExpression())
                .asObject()
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast);
        // @formatter:on

        if (optionalSemanticElement.isPresent()) {
            this.createView(optionalParentNode, this.convertedNodes.get(createViewOperation.getElementDescription()), optionalSemanticElement.get(), createViewOperation.getContainmentKind());
        }
        return this.operationInterpreter.executeOperations(createViewOperation.getChildren(), this.variableManager);
    }

    @Override
    public Optional<VariableManager> caseDeleteView(DeleteView deleteViewOperation) {
        var optionalElement = this.interpreter.evaluateExpression(this.variableManager.getVariables(), deleteViewOperation.getViewExpression()).asObject();
        if (optionalElement.isPresent()) {
            this.deleteView(optionalElement.get());
        }
        return this.operationInterpreter.executeOperations(deleteViewOperation.getChildren(), this.variableManager);
    }

    @Override
    public Optional<VariableManager> defaultCase(EObject object) {
        return this.operationInterpreterViewSwitch.doSwitch(object);
    }

    private void createView(Optional<Node> optionalParentNode, NodeDescription nodeDescription, EObject semanticElement, org.eclipse.sirius.components.view.NodeContainmentKind containmentKind) {
        String parentElementId = optionalParentNode.map(Node::getId).orElse(this.diagramContext.getDiagram().getId());

        NodeContainmentKind nodeContainmentKind = NodeContainmentKind.CHILD_NODE;
        if (containmentKind == org.eclipse.sirius.components.view.NodeContainmentKind.BORDER_NODE) {
            nodeContainmentKind = NodeContainmentKind.BORDER_NODE;
        }

        // @formatter:off
        ViewCreationRequest viewCreationRequest = ViewCreationRequest.newViewCreationRequest()
                .parentElementId(parentElementId)
                .targetObjectId(this.objectService.getId(semanticElement))
                .descriptionId(nodeDescription.getId())
                .containmentKind(nodeContainmentKind)
                .build();
        // @formatter:on
        this.diagramContext.getViewCreationRequests().add(viewCreationRequest);
    }

    private void deleteView(Object element) {
        if (this.diagramContext != null) {
            String elementId = null;
            if (element instanceof Node) {
                elementId = ((Node) element).getId();
            } else if (element instanceof Edge) {
                elementId = ((Edge) element).getId();
            }
            if (elementId != null) {
                ViewDeletionRequest viewDeletionRequest = ViewDeletionRequest.newViewDeletionRequest().elementId(elementId).build();
                this.diagramContext.getViewDeletionRequests().add(viewDeletionRequest);
            }
        }
    }

}
