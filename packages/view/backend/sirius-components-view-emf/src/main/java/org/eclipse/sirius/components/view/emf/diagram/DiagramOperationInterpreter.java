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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.Tool;
import org.eclipse.sirius.components.view.emf.IOperationInterpreter;
import org.eclipse.sirius.components.view.util.ViewSwitch;

/**
 * Executes the body of a diagram tool as defined by a set of {@link Operation}s.
 *
 * @author pcdavid
 */
public class DiagramOperationInterpreter implements IOperationInterpreter {

    private final AQLInterpreter interpreter;

    private final IObjectService objectService;

    private final IEditService editService;

    private final IDiagramContext diagramContext;

    private final IFeedbackMessageService feedbackMessageService;

    private final Map<org.eclipse.sirius.components.view.NodeDescription, NodeDescription> convertedNodes;

    public DiagramOperationInterpreter(AQLInterpreter interpreter, IObjectService objectService, IEditService editService, IDiagramContext diagramContext,
            Map<org.eclipse.sirius.components.view.NodeDescription, NodeDescription> convertedNodes, IFeedbackMessageService feedbackMessageService) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
        this.diagramContext = diagramContext;
        this.convertedNodes = Objects.requireNonNull(convertedNodes);
        this.feedbackMessageService = feedbackMessageService;
    }

    public IStatus executeTool(Tool tool, VariableManager variableManager) {
        Optional<VariableManager> optionalVariableManager = this.executeOperations(tool.getBody(), variableManager);
        if (optionalVariableManager.isEmpty()) {
            var feedbackMessages = new ArrayList<>(List.of(String.format("Something went wrong while executing the tool '%s'", tool.getName())));
            if (Objects.nonNull(this.feedbackMessageService)) {
                feedbackMessages.addAll(this.feedbackMessageService.getFeedbackMessages());
            }
            return new Failure(String.join(", ", feedbackMessages));
        }
        return new Success();
    }

    @Override
    public Optional<VariableManager> executeOperations(List<Operation> operations, VariableManager variableManager) {
        VariableManager currentContext = variableManager;
        for (Operation operation : operations) {
            Optional<VariableManager> newContext = this.executeOperation(operation, currentContext);
            if (newContext.isEmpty()) {
                return Optional.empty();
            } else {
                currentContext = newContext.get();
            }
        }
        return Optional.of(currentContext);

    }

    private Optional<VariableManager> executeOperation(Operation operation, VariableManager variableManager) {
        ViewSwitch<Optional<VariableManager>> dispatcher = new DiagramOperationInterpreterViewSwitch(variableManager, this.interpreter, this.objectService, this.editService, this.diagramContext,
                this.convertedNodes, this);
        return dispatcher.doSwitch(operation);
    }
}
