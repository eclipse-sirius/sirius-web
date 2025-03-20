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
package org.eclipse.sirius.components.view.emf.diagram.actions;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IActionHandler;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramService;
import org.eclipse.sirius.components.core.api.Environment;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.diagram.Action;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.view.emf.ViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.emf.diagram.actions.api.IActionExecutor;
import org.springframework.stereotype.Service;

/**
 * Default handler for actions.
 *
 * @author arichard
 */
@Service
public class ViewActionHandler implements IActionHandler {

    private final ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    private final IObjectSearchService objectSearchService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IDiagramQueryService diagramQueryService;

    private final IActionExecutor actionExecutor;

    private final IViewAQLInterpreterFactory aqlInterpreterFactory;

    public ViewActionHandler(ViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate, IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService, IObjectSearchService objectSearchService,
            IRepresentationDescriptionSearchService representationDescriptionSearchService, IDiagramQueryService diagramQueryService, IActionExecutor actionExecutor, IViewAQLInterpreterFactory aqlInterpreterFactory) {
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.viewRepresentationDescriptionSearchService = Objects.requireNonNull(viewRepresentationDescriptionSearchService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.diagramQueryService = Objects.requireNonNull(diagramQueryService);
        this.actionExecutor = Objects.requireNonNull(actionExecutor);
        this.aqlInterpreterFactory = Objects.requireNonNull(aqlInterpreterFactory);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IDiagramContext diagramContext, IDiagramElement diagramElement, String actionId) {
        var diagramDescriptionId = diagramContext.getDiagram().getDescriptionId();
        var optionalDiagramDescription = this.representationDescriptionSearchService.findById(editingContext, diagramDescriptionId);
        if (optionalDiagramDescription.isPresent()) {
            var diagramDescription = optionalDiagramDescription.get();
            return this.viewRepresentationDescriptionPredicate.test(diagramDescription);
        }
        return false;
    }

    @Override
    public IStatus handle(IEditingContext editingContext, IDiagramContext diagramContext, IDiagramElement diagramElement, String actionId) {
        IStatus result = new Failure("");
        var diagram = diagramContext.getDiagram();
        var diagramDescriptionId = diagram.getDescriptionId();

        var optionalViewDiagramDescription = this.viewRepresentationDescriptionSearchService.findById(editingContext, diagramDescriptionId)
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast);
        var optionalViewAction = optionalViewDiagramDescription.flatMap(viewDiagramDescription -> this.getViewAction(viewDiagramDescription, actionId));

        if (optionalViewDiagramDescription.isPresent() && optionalViewAction.isPresent()) {
            var diagramElementId = diagramElement.getId();
            var optionalNode = this.diagramQueryService.findNodeById(diagram, diagramElementId);
            var optionalSelf = optionalNode.flatMap(node -> this.objectSearchService.getObject(editingContext, node.getTargetObjectId()));

            if (optionalSelf.isPresent() && optionalNode.isPresent()) {
                var self = optionalSelf.get();
                var node = optionalNode.get();

                var variableManager = this.populateVariableManager(editingContext, diagramContext, node, self);
                var interpreter = this.aqlInterpreterFactory.createInterpreter(editingContext, (View) optionalViewDiagramDescription.get().eContainer());
                result = this.actionExecutor.executeAction(optionalViewAction.get(), interpreter, variableManager);
            }
        }
        return result;
    }

    private Optional<Action> getViewAction(DiagramDescription viewDiagramDescription, String actionId) {
        return viewDiagramDescription.getNodeDescriptions().stream()
                .map(nodeDescription -> this.getViewAction(nodeDescription, actionId))
                .flatMap(Optional::stream)
                .findFirst();
    }

    private Optional<Action> getViewAction(NodeDescription nodeDescription, String actionId) {
        Optional<Action> optionalAction = Optional.empty();

        optionalAction = nodeDescription.getActions().stream()
                .filter(action -> Objects.equals(UUID.nameUUIDFromBytes(action.getName().getBytes()).toString(), actionId))
                .findFirst();

        if (optionalAction.isEmpty()) {
            var childrenDescriptions = nodeDescription.getChildrenDescriptions();
            for (NodeDescription childrenDescription : childrenDescriptions) {
                var viewAction = this.getViewAction(childrenDescription, actionId);
                if (viewAction.isPresent()) {
                    optionalAction = viewAction;
                    break;
                }
            }
            var borderNodesDescriptions = nodeDescription.getBorderNodesDescriptions();
            for (NodeDescription borderNodesDescription : borderNodesDescriptions) {
                var viewAction = this.getViewAction(borderNodesDescription, actionId);
                if (viewAction.isPresent()) {
                    optionalAction = viewAction;
                    break;
                }
            }
        }
        return optionalAction;
    }

    private VariableManager populateVariableManager(IEditingContext editingContext, IDiagramContext diagramContext, Node node, Object self) {
        var variableManager = new VariableManager();
        variableManager.put(IDiagramContext.DIAGRAM_CONTEXT, diagramContext);
        variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
        variableManager.put(Environment.ENVIRONMENT, new Environment(Environment.SIRIUS_COMPONENTS));
        variableManager.put(IDiagramService.DIAGRAM_SERVICES, new DiagramService(diagramContext));
        variableManager.put(VariableManager.SELF, self);
        variableManager.put(Node.SELECTED_NODE, node);
        return variableManager;
    }
}
