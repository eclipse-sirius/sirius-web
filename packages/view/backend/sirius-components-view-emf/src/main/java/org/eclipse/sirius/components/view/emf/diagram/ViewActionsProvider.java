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
import org.eclipse.sirius.components.collaborative.diagrams.dto.Action;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Status;
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

    private final IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate;

    private final IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService;

    private final IViewAQLInterpreterFactory aqlInterpreterFactory;

    public ViewActionsProvider(IViewRepresentationDescriptionPredicate viewRepresentationDescriptionPredicate, IViewDiagramDescriptionSearchService viewDiagramDescriptionSearchService,
            IViewAQLInterpreterFactory aqlInterpreterFactory) {
        this.viewRepresentationDescriptionPredicate = Objects.requireNonNull(viewRepresentationDescriptionPredicate);
        this.viewDiagramDescriptionSearchService = Objects.requireNonNull(viewDiagramDescriptionSearchService);
        this.aqlInterpreterFactory = Objects.requireNonNull(aqlInterpreterFactory);
    }

    @Override
    public boolean canHandle(DiagramDescription diagramDescription) {
        return this.viewRepresentationDescriptionPredicate.test(diagramDescription);
    }

    @Override
    public List<Action> handle(Object targetElement, Object diagramElement, Object diagramElementDescription, DiagramDescription diagramDescription, IEditingContext editingContext) {
        List<Action> actions = null;
        VariableManager variableManager = new VariableManager();
        variableManager.put(VariableManager.SELF, targetElement);
        var optionalDiagramDescription = this.viewDiagramDescriptionSearchService.findById(editingContext, diagramDescription.getId());
        if (optionalDiagramDescription.isPresent()) {
            org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription = optionalDiagramDescription.get();
            var interpreter = this.aqlInterpreterFactory.createInterpreter(editingContext, (View) viewDiagramDescription.eContainer());
            if (diagramElement instanceof Node && diagramElementDescription instanceof NodeDescription nodeDescription) {
                variableManager.put(Node.SELECTED_NODE, diagramElement);
                actions = this.getNodeActions(editingContext, diagramDescription, diagramElement, nodeDescription, variableManager, interpreter);
            }
        }
        return actions;
    }

    private List<Action> getNodeActions(IEditingContext editingContext, DiagramDescription diagramDescription, Object diagramElement, NodeDescription nodeDescription, VariableManager variableManager,
            AQLInterpreter interpreter) {
        List<Action> actions = new ArrayList<>();
        var optionalNodeDescription = this.viewDiagramDescriptionSearchService.findViewNodeDescriptionById(editingContext, nodeDescription.getId());
        if (optionalNodeDescription.isPresent()) {
            optionalNodeDescription.get().getActions().stream().filter(viewAction -> this.checkPrecondition(viewAction, variableManager, interpreter))
                    .map(viewAction -> this.createAction(viewAction, variableManager, interpreter)).forEach(actions::add);
        }
        return actions;
    }

    private Action createAction(org.eclipse.sirius.components.view.diagram.Action viewAction, VariableManager variableManager, AQLInterpreter interpreter) {
        var id = UUID.nameUUIDFromBytes(viewAction.getName().getBytes()).toString();
        var iconURLProvider = this.actionIconURLProvider(viewAction, interpreter, variableManager);
        var label = interpreter.evaluateExpression(variableManager.getVariables(), viewAction.getLabelExpression()).asString().orElse("");

        return Action.newAction(id).label(label).iconURL(iconURLProvider).build();
    }

    private boolean checkPrecondition(org.eclipse.sirius.components.view.diagram.Action viewAction, VariableManager variableManager, AQLInterpreter interpreter) {
        String precondition = viewAction.getPreconditionExpression();
        if (precondition != null && !precondition.isBlank()) {
            var result = interpreter.evaluateExpression(variableManager.getVariables(), precondition);
            return result.getStatus().compareTo(Status.WARNING) <= 0 && result.asBoolean().orElse(Boolean.FALSE);
        }
        return true;
    }

    private List<String> actionIconURLProvider(org.eclipse.sirius.components.view.diagram.Action viewAction, AQLInterpreter interpreter, VariableManager variableManager) {
        List<String> iconURL = null;
        var iconURLsExpression = viewAction.getIconURLsExpression();
        if (iconURLsExpression == null || iconURLsExpression.isBlank()) {
            //TODO
            iconURL = List.of(ViewToolImageProvider.NODE_CREATION_TOOL_ICON);
        } else {
            iconURL = this.evaluateListString(interpreter, variableManager, iconURLsExpression);
        }
        return iconURL;
    }

    private List<String> evaluateListString(AQLInterpreter interpreter, VariableManager variableManager, String expression) {
        var objects = interpreter.evaluateExpression(variableManager.getVariables(), expression).asObjects().orElse(List.of());
        return objects.stream().filter(String.class::isInstance).map(String.class::cast).toList();
    }
}
