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
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.HeaderSeparatorDisplayMode;
import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.InsideLabelLocation;
import org.eclipse.sirius.components.diagrams.LabelOverflowStrategy;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.LabelTextAlign;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.ViewCreationRequest;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.components.NodeContainmentKind;
import org.eclipse.sirius.components.diagrams.components.NodeIdProvider;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.diagram.CreateView;
import org.eclipse.sirius.components.view.emf.diagram.ViewDiagramDescriptionConverter;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationHandler;
import org.eclipse.sirius.components.view.emf.operations.api.OperationEvaluationResult;
import org.eclipse.sirius.components.view.emf.operations.api.OperationExecutionStatus;
import org.springframework.stereotype.Service;

/**
 * Used to execute the create view operation.
 *
 * @author sbegaudeau
 */
@Service
public class CreateViewOperationHandler implements IOperationHandler {

    private final IIdentityService identityService;

    private final ILabelService labelService;

    public CreateViewOperationHandler(IIdentityService identityService, ILabelService labelService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.labelService = Objects.requireNonNull(labelService);
    }

    @Override
    public boolean canHandle(AQLInterpreter interpreter, VariableManager variableManager, Operation operation) {
        return operation instanceof CreateView;
    }

    @Override
    public OperationEvaluationResult handle(AQLInterpreter interpreter, VariableManager variableManager, Operation operation) {
        var optionalDiagramContext = variableManager.get(IDiagramContext.DIAGRAM_CONTEXT, IDiagramContext.class);
        var optionalConvertedNodes = variableManager.get(ViewDiagramDescriptionConverter.CONVERTED_NODES_VARIABLE, Map.class);

        if (operation instanceof CreateView createViewOperation && optionalDiagramContext.isPresent() && optionalConvertedNodes.isPresent()) {
            var diagramContext = optionalDiagramContext.get();
            Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> convertedNodes = optionalConvertedNodes.get();

            Map<String, Object> variables = variableManager.getVariables();
            var optionalParentNode = interpreter.evaluateExpression(variables, createViewOperation.getParentViewExpression())
                    .asObject()
                    .filter(Node.class::isInstance)
                    .map(Node.class::cast);
            var optionalSemanticElement = interpreter.evaluateExpression(variables, createViewOperation.getSemanticElementExpression())
                    .asObject()
                    .filter(EObject.class::isInstance)
                    .map(EObject.class::cast);

            if (optionalSemanticElement.isPresent()) {
                var newNode = this.createView(diagramContext, optionalParentNode, convertedNodes.get(createViewOperation.getElementDescription()), optionalSemanticElement.get(), createViewOperation.getContainmentKind());

                var childVariableManager = variableManager;
                if (createViewOperation.getVariableName() != null && createViewOperation.getVariableName().trim().length() > 0) {
                    childVariableManager = variableManager.createChild();
                    childVariableManager.put(createViewOperation.getVariableName(), newNode);
                }

                return new OperationEvaluationResult(OperationExecutionStatus.SUCCESS, List.of(childVariableManager), Map.of());
            }
        }
        return new OperationEvaluationResult(OperationExecutionStatus.FAILURE, List.of(variableManager), Map.of());
    }

    private Node createView(IDiagramContext diagramContext, Optional<Node> optionalParentNode, NodeDescription nodeDescription, EObject semanticElement, org.eclipse.sirius.components.view.diagram.NodeContainmentKind containmentKind) {
        String parentElementId = optionalParentNode.map(Node::getId).orElse(diagramContext.getDiagram().getId());

        NodeContainmentKind nodeContainmentKind = NodeContainmentKind.CHILD_NODE;
        if (containmentKind == org.eclipse.sirius.components.view.diagram.NodeContainmentKind.BORDER_NODE) {
            nodeContainmentKind = NodeContainmentKind.BORDER_NODE;
        }

        var targetObjectId = this.identityService.getId(semanticElement);
        var targetObjectKind = this.identityService.getKind(semanticElement);
        var targetObjectLabel = this.labelService.getLabel(semanticElement);

        ViewCreationRequest viewCreationRequest = ViewCreationRequest.newViewCreationRequest()
                .parentElementId(parentElementId)
                .targetObjectId(targetObjectId)
                .descriptionId(nodeDescription.getId())
                .containmentKind(nodeContainmentKind)
                .build();
        diagramContext.getViewCreationRequests().add(viewCreationRequest);

        // Since we have everything to compute the identifier of the node which will be created in the
        // future, we can create a fake node which will have the proper id in order to let the specifier
        // use nested create view model operations. The specifier should not try to do anything else
        // than that with this returned node.
        var nodeId = new NodeIdProvider().getNodeId(parentElementId, nodeDescription.getId(), nodeContainmentKind, targetObjectId);

        var labelStyle = LabelStyle.newLabelStyle()
                .color("")
                .fontSize(14)
                .iconURL(List.of())
                .background("transparent")
                .borderColor("black")
                .borderSize(0)
                .borderStyle(LineStyle.Solid)
                .build();

        var insideLabel = InsideLabel.newLabel("")
                .text("")
                .insideLabelLocation(InsideLabelLocation.TOP_CENTER)
                .style(labelStyle)
                .isHeader(false)
                .headerSeparatorDisplayMode(HeaderSeparatorDisplayMode.NEVER)
                .overflowStrategy(LabelOverflowStrategy.NONE)
                .textAlign(LabelTextAlign.CENTER)
                .customizedStyleProperties(Set.of())
                .build();

        var nodeStyle = RectangularNodeStyle.newRectangularNodeStyle()
                .background("")
                .borderColor("")
                .borderStyle(LineStyle.Solid)
                .childrenLayoutStrategy(new FreeFormLayoutStrategy())
                .build();

        return Node.newNode(nodeId)
                .type("")
                .targetObjectId(targetObjectId)
                .targetObjectKind(targetObjectKind)
                .targetObjectLabel(targetObjectLabel)
                .descriptionId(nodeDescription.getId())
                .borderNode(nodeContainmentKind == NodeContainmentKind.BORDER_NODE)
                .modifiers(Set.of())
                .state(ViewModifier.Normal)
                .collapsingState(CollapsingState.EXPANDED)
                .insideLabel(insideLabel)
                .style(nodeStyle)
                .borderNodes(List.of())
                .childNodes(List.of())
                .customizedStyleProperties(Set.of())
                .build();
    }
}
