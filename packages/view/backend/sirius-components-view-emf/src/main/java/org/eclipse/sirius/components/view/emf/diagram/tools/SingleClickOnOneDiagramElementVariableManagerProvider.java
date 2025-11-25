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
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolVariable;
import org.eclipse.sirius.components.core.api.Environment;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.diagram.ViewDiagramConversionData;
import org.eclipse.sirius.components.view.emf.diagram.ViewDiagramDescriptionConverter;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.ISingleClickOnOneDiagramElementVariableManagerProvider;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.IToolVariableHandler;
import org.eclipse.sirius.components.view.emf.editingcontext.api.IViewEditingContext;
import org.springframework.stereotype.Service;

/**
 * Used to provide the variable manager used to evaluate single click on one diagram element tools.
 *
 * @author sbegaudeau
 */
@Service
public class SingleClickOnOneDiagramElementVariableManagerProvider implements ISingleClickOnOneDiagramElementVariableManagerProvider {

    private final IObjectSearchService objectSearchService;

    private final IDiagramQueryService diagramQueryService;

    private final IToolVariableHandler toolVariableHandler;

    public SingleClickOnOneDiagramElementVariableManagerProvider(IObjectSearchService objectSearchService, IDiagramQueryService diagramQueryService, IToolVariableHandler toolVariableHandler) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.toolVariableHandler = Objects.requireNonNull(toolVariableHandler);
    }

    @Override
    public Optional<VariableManager> getVariableManager(IEditingContext editingContext, DiagramContext diagramContext, List<String> diagramElementIds, List<ToolVariable> variables) {
        List<IDiagramElement> diagramElements = new ArrayList<>();
        diagramElementIds.stream().map(diagramElementId -> this.diagramQueryService.findDiagramElementById(diagramContext.diagram(), diagramElementId))
                .filter(Optional::isPresent)
                .forEach(diagramElement -> diagramElements.add(diagramElement.get()));

        List<Object> self = this.getSelf(editingContext, diagramContext.diagram(), diagramElementIds, diagramElements);
        var nodes = diagramElements.stream().filter(Node.class::isInstance).map(Node.class::cast).toList();
        var edges = diagramElements.stream().filter(Edge.class::isInstance).map(Edge.class::cast).toList();
        if (!self.isEmpty()) {
            VariableManager variableManager = new VariableManager();
            variableManager.put(DiagramContext.DIAGRAM_CONTEXT, diagramContext);
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
            variableManager.put(Environment.ENVIRONMENT, new Environment(Environment.SIRIUS_COMPONENTS));
            variableManager.put(IDiagramService.DIAGRAM_SERVICES, new DiagramService(diagramContext));
            if (nodes.size() == 1) {
                variableManager.put(Node.SELECTED_NODE, nodes.get(0));
                variableManager.put(Node.SELECTED_NODES, nodes);
            } else {
                variableManager.put(Node.SELECTED_NODES, nodes);
            }
            if (edges.size() == 1) {
                variableManager.put(Edge.SELECTED_EDGE, edges.get(0));
            } else {
                variableManager.put(Edge.SELECTED_EDGES, edges);
            }
            if (self.size() == 1) {
                variableManager.put(VariableManager.SELF, self.get(0));
            } else {
                variableManager.put(VariableManager.SELF, self);
            }

            this.getViewDiagramConversionData(editingContext, diagramContext.diagram().getDescriptionId()).ifPresent(viewDiagramConversionData -> variableManager.put(ViewDiagramDescriptionConverter.CONVERTED_NODES_VARIABLE, viewDiagramConversionData.convertedNodes()));

            this.toolVariableHandler.addToolVariablesInVariableManager(editingContext, variableManager, variables);

            return Optional.of(variableManager);
        }

        return Optional.empty();
    }

    private List<Object> getSelf(IEditingContext editingContext, Diagram diagram, List<String> diagramElementIds, List<IDiagramElement> diagramElements) {
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
        if (diagramElementIds.size() == 1 && Objects.equals(diagram.getId(), diagramElementIds.get(0))) {
            var diagramTargetObject = this.objectSearchService.getObject(editingContext, diagram.getTargetObjectId());
            diagramTargetObject.ifPresent(targetObjects::add);
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
