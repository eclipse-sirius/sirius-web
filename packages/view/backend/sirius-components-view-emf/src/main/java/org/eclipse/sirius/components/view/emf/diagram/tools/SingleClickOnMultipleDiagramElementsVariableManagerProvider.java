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
package org.eclipse.sirius.components.view.emf.diagram.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramService;
import org.eclipse.sirius.components.collaborative.diagrams.api.DiagramInteractionOperations;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolVariable;
import org.eclipse.sirius.components.collaborative.diagrams.variables.DiagramVariables;
import org.eclipse.sirius.components.core.api.Environment;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.variables.CommonVariables;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.representations.IOperationValidator;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.diagram.ViewDiagramConversionData;
import org.eclipse.sirius.components.view.emf.diagram.ViewDiagramDescriptionConverter;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.ISingleClickOnMultipleDiagramElementsVariableManagerProvider;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.IToolVariableHandler;
import org.eclipse.sirius.components.view.emf.editingcontext.api.IViewEditingContext;
import org.springframework.stereotype.Service;

/**
 * Used to provide the variable manager used to evaluate single click on multiple diagram elements tools.
 *
 * @author sbegaudeau
 */
@Service
public class SingleClickOnMultipleDiagramElementsVariableManagerProvider implements ISingleClickOnMultipleDiagramElementsVariableManagerProvider {

    private final IObjectSearchService objectSearchService;

    private final IDiagramQueryService diagramQueryService;

    private final IToolVariableHandler toolVariableHandler;

    private final IOperationValidator operationValidator;

    public SingleClickOnMultipleDiagramElementsVariableManagerProvider(IObjectSearchService objectSearchService, IDiagramQueryService diagramQueryService, IToolVariableHandler toolVariableHandler, IOperationValidator operationValidator) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.toolVariableHandler = Objects.requireNonNull(toolVariableHandler);
        this.operationValidator = Objects.requireNonNull(operationValidator);
    }

    @Override
    public Optional<VariableManager> getVariableManager(IEditingContext editingContext, DiagramContext diagramContext, List<String> diagramElementIds, List<ToolVariable> variables) {
        List<IDiagramElement> diagramElements = diagramElementIds.stream()
                .map(diagramElementId -> this.diagramQueryService.findDiagramElementById(diagramContext.diagram(), diagramElementId))
                .flatMap(Optional::stream)
                .toList();

        List<Object> self = this.getSelf(editingContext, diagramElements);
        var nodes = diagramElements.stream().filter(Node.class::isInstance).map(Node.class::cast).toList();
        var edges = diagramElements.stream().filter(Edge.class::isInstance).map(Edge.class::cast).toList();
        if (!self.isEmpty()) {
            VariableManager variableManager = new VariableManager();
            variableManager.put(CommonVariables.SELF.name(), self);
            variableManager.put(CommonVariables.EDITING_CONTEXT.name(), editingContext);
            variableManager.put(CommonVariables.ENVIRONMENT.name(), new Environment(Environment.SIRIUS_COMPONENTS));
            variableManager.put(DiagramVariables.DIAGRAM_CONTEXT.name(), diagramContext);
            variableManager.put(DiagramVariables.DIAGRAM_SERVICES.name(), new DiagramService(diagramContext));
            variableManager.put(DiagramVariables.SELECTED_NODES.name(), nodes);
            variableManager.put(DiagramVariables.SELECTED_EDGES.name(), edges);

            this.getViewDiagramConversionData(editingContext, diagramContext.diagram().getDescriptionId()).ifPresent(viewDiagramConversionData -> variableManager.put(ViewDiagramDescriptionConverter.CONVERTED_NODES_VARIABLE, viewDiagramConversionData.convertedNodes()));

            this.toolVariableHandler.addToolVariablesInVariableManager(editingContext, variableManager, variables);

            this.operationValidator.validate(DiagramInteractionOperations.GROUP_TOOL, variableManager.getVariables());
            return Optional.of(variableManager);
        }

        return Optional.empty();
    }

    private List<Object> getSelf(IEditingContext editingContext, List<IDiagramElement> diagramElements) {
        List<Object> targetObjects = new ArrayList<>();
        for (IDiagramElement diagramElement : diagramElements) {
            Optional<Object> targetObject = Optional.empty();
            if (diagramElement instanceof Node node) {
                targetObject = this.objectSearchService.getObject(editingContext, node.getTargetObjectId());
            } else if (diagramElement instanceof Edge edge) {
                targetObject = this.objectSearchService.getObject(editingContext, edge.getTargetObjectId());
            }
            targetObject.ifPresent(targetObjects::add);
        }
        return targetObjects;
    }

    private Optional<ViewDiagramConversionData> getViewDiagramConversionData(IEditingContext editingContext, String diagramDescriptionId) {
        return Optional.of(editingContext)
                .filter(IViewEditingContext.class::isInstance)
                .map(IViewEditingContext.class::cast)
                .map(IViewEditingContext::getViewConversionData)
                .map(viewConversionData -> viewConversionData.get(diagramDescriptionId))
                .filter(Objects::nonNull)
                .filter(ViewDiagramConversionData.class::isInstance)
                .map(ViewDiagramConversionData.class::cast);
    }
}
