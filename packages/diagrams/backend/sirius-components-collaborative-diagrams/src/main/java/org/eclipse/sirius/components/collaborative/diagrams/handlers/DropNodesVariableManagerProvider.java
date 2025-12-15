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
package org.eclipse.sirius.components.collaborative.diagrams.handlers;

import static org.eclipse.sirius.components.collaborative.diagrams.api.DiagramInteractionOperations.NODE_DROP;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.handlers.api.IDropNodesVariableManagerProvider;
import org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramVariables;
import org.eclipse.sirius.components.core.api.Environment;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.variables.CommonVariables;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.representations.IOperationValidator;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

/**
 * Used to compute the variable manager required to perform the drop nodes operation.
 *
 * @author sbegaudeau
 */
@Service
public class DropNodesVariableManagerProvider implements IDropNodesVariableManagerProvider {

    private final IObjectSearchService objectSearchService;

    private final IDiagramQueryService diagramQueryService;

    private final IOperationValidator operationValidator;

    public DropNodesVariableManagerProvider(IObjectSearchService objectSearchService, IDiagramQueryService diagramQueryService, IOperationValidator operationValidator) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.operationValidator = Objects.requireNonNull(operationValidator);
    }

    @Override
    public Optional<VariableManager> getVariableManager(IEditingContext editingContext, DiagramContext diagramContext, String targetElementId, List<String> droppedElementIds) {
        Diagram diagram = diagramContext.diagram();

        var droppedNodes = droppedElementIds.stream()
                .flatMap(droppedElementId -> this.diagramQueryService.findNodeById(diagram, droppedElementId).stream())
                .toList();
        var optionalDropTarget = Optional.ofNullable(targetElementId).flatMap(elementId -> this.diagramQueryService.findNodeById(diagram, elementId));

        var optionalTargetElement = this.objectSearchService.getObject(editingContext, optionalDropTarget
                .map(Node::getTargetObjectId)
                .orElse(diagram.getTargetObjectId()));

        var droppedElements = droppedNodes.stream()
                .map(droppedNode -> this.objectSearchService.getObject(editingContext, droppedNode.getTargetObjectId()))
                .flatMap(Optional::stream)
                .toList();

        if (optionalTargetElement.isPresent() && droppedElements.size() == droppedNodes.size()) {
            var targetElement = optionalTargetElement.get();

            var droppedElement = droppedElements.stream()
                    .findFirst()
                    .orElse(null);

            var droppedNode = droppedNodes.stream()
                    .findFirst()
                    .orElse(null);

            VariableManager variableManager = new VariableManager();
            variableManager.put(CommonVariables.EDITING_CONTEXT.name(), editingContext);
            variableManager.put(CommonVariables.ENVIRONMENT.name(), new Environment(Environment.SIRIUS_COMPONENTS));
            variableManager.put(DiagramVariables.DIAGRAM_CONTEXT.name(), diagramContext);
            variableManager.put(DiagramVariables.DIAGRAM_SERVICES.name(), new DiagramService(diagramContext));
            variableManager.put(DiagramVariables.DROPPED_ELEMENTS.name(), droppedElements);
            variableManager.put(DiagramVariables.DROPPED_NODES.name(), droppedNodes);
            variableManager.put(DiagramVariables.DROPPED_ELEMENT.name(), droppedElement);
            variableManager.put(DiagramVariables.DROPPED_NODE.name(), droppedNode);
            variableManager.put(DiagramVariables.TARGET_ELEMENT.name(), targetElement);
            variableManager.put(DiagramVariables.TARGET_NODE.name(), optionalDropTarget.orElse(null));

            this.operationValidator.validate(NODE_DROP, variableManager.getVariables());
            return Optional.of(variableManager);
        }

        return Optional.empty();
    }
}
