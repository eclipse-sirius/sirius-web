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
package org.eclipse.sirius.components.view.emf.diagram;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.api.IActionsProvider;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.diagrams.IDiagramElement;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.collaborative.diagrams.dto.Action;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.BooleanValueProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.api.IViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewDiagramDescriptionSearchService;
import org.springframework.stereotype.Service;

/**
 * Used to provide the actions of a node created from a view description.
 *
 * @author arichard
 */
@Service
public class ViewActionsProvider implements IActionsProvider {

    public static final String ACTION_ICON = "/img/DefaultActionIcon.svg";

    private final IObjectSearchService objectSearchService;

    private final IDiagramDescriptionService diagramDescriptionService;

    private final IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService;

    private final IViewAQLInterpreterFactory aqlInterpreterFactory;

    public ViewActionsProvider(IObjectSearchService objectSearchService, IDiagramDescriptionService diagramDescriptionService, IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate, IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService,
            IViewAQLInterpreterFactory aqlInterpreterFactory) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.diagramDescriptionService = Objects.requireNonNull(diagramDescriptionService);
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.viewDiagramDescriptionSearchService = Objects.requireNonNull(viewDiagramDescriptionSearchService);
        this.aqlInterpreterFactory = Objects.requireNonNull(aqlInterpreterFactory);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, DiagramDescription diagramDescription, IDiagramElement diagramElement) {
        return this.viewRepresentationDescriptionPredicate.test(diagramDescription);
    }

    @Override
    public List<Action> handle(IEditingContext editingContext, DiagramDescription diagramDescription, IDiagramElement diagramElement) {
        List<Action> actions = null;



        if (diagramElement instanceof Node node) {
            var optionalTargetElement = this.objectSearchService.getObject(editingContext, node.getTargetObjectId());
            var optionalDiagramDescription = this.viewDiagramDescriptionSearchService.findById(editingContext, diagramDescription.getId());
            if (optionalTargetElement.isPresent() && optionalDiagramDescription.isPresent()) {
                var targetElement = optionalTargetElement.get();
                var viewDiagramDescription = optionalDiagramDescription.get();
                var diagramElementDescription = this.diagramDescriptionService.findNodeDescriptionById(diagramDescription, node.getDescriptionId());
                if (diagramElementDescription.isPresent()) {
                    VariableManager variableManager = new VariableManager();
                    variableManager.put(VariableManager.SELF, targetElement);
                    variableManager.put(Node.SELECTED_NODE, diagramElement);

                    var interpreter = this.aqlInterpreterFactory.createInterpreter(editingContext, (View) viewDiagramDescription.eContainer());
                    actions = this.getNodeActions(editingContext, diagramElementDescription.get(), variableManager, interpreter);
                }
            }
        }
        return actions;
    }

    private List<Action> getNodeActions(IEditingContext editingContext, NodeDescription nodeDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        List<Action> actions = new ArrayList<>();
        var optionalViewNodeDescription = this.viewDiagramDescriptionSearchService.findViewNodeDescriptionById(editingContext, nodeDescription.getId());
        if (optionalViewNodeDescription.isPresent()) {
            var viewNodeDescription = optionalViewNodeDescription.get();
            viewNodeDescription.getActions().stream()
                    .filter(viewAction -> {
                        var preconditionExpression = viewAction.getPreconditionExpression();
                        if (preconditionExpression == null || preconditionExpression.isBlank()) {
                            return true;
                        }
                        return new BooleanValueProvider(interpreter, preconditionExpression).apply(variableManager);
                    })
                    .map(viewAction -> this.createAction(viewAction, variableManager, interpreter))
                    .forEach(actions::add);
        }
        return actions;
    }

    private Action createAction(org.eclipse.sirius.components.view.diagram.Action viewAction, VariableManager variableManager, AQLInterpreter interpreter) {
        var id = UUID.nameUUIDFromBytes(viewAction.getName().getBytes()).toString();
        var iconURLs = this.getActionIconURLs(viewAction, interpreter, variableManager);
        var tooltip = interpreter.evaluateExpression(variableManager.getVariables(), viewAction.getTooltipExpression()).asString().orElse("");

        return new Action(id, iconURLs, tooltip);
    }

    private List<String> getActionIconURLs(org.eclipse.sirius.components.view.diagram.Action viewAction, AQLInterpreter interpreter, VariableManager variableManager) {
        List<String> iconURL = null;
        var iconURLsExpression = viewAction.getIconURLsExpression();
        if (iconURLsExpression == null || iconURLsExpression.isBlank()) {
            iconURL = List.of(ACTION_ICON);
        } else {
            iconURL = this.evaluateListString(interpreter, variableManager, iconURLsExpression);
            if (iconURL.isEmpty()) {
                iconURL = List.of(ACTION_ICON);
            }
        }
        return iconURL;
    }

    private List<String> evaluateListString(AQLInterpreter interpreter, VariableManager variableManager, String expression) {
        var objects = interpreter.evaluateExpression(variableManager.getVariables(), expression).asObjects().orElse(List.of());
        return objects.stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .toList();
    }
}
