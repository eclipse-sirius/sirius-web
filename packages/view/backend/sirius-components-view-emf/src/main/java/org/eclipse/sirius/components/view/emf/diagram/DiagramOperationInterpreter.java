/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
import java.util.LinkedHashMap;
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
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.representations.WorkbenchSelection;
import org.eclipse.sirius.components.representations.WorkbenchSelectionEntry;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.diagram.Tool;
import org.eclipse.sirius.components.view.diagram.util.DiagramSwitch;
import org.eclipse.sirius.components.view.emf.IOperationInterpreter;
import org.eclipse.sirius.components.view.emf.OperationInterpreterViewSwitch;

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

    private final Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> convertedNodes;

    public DiagramOperationInterpreter(AQLInterpreter interpreter, IObjectService objectService, IEditService editService, IDiagramContext diagramContext,
            Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> convertedNodes, IFeedbackMessageService feedbackMessageService) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
        this.diagramContext = diagramContext;
        this.convertedNodes = Objects.requireNonNull(convertedNodes);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
    }

    public IStatus executeTool(Tool tool, VariableManager variableManager) {
        VariableManager toolVariableManager = variableManager.createChild();
        // Used to collect the new semantic elements created during the execution of the tool.
        // All operations executed inside the body of the tool are guaranteed to see this variable and
        // are allowed to add new (named) instances to it.
        var newInstancesCollector = new LinkedHashMap<>();
        toolVariableManager.put(OperationInterpreterViewSwitch.NEW_INSTANCES_COLLECTOR, newInstancesCollector);
        Optional<VariableManager> optionalVariableManager = this.executeOperations(tool.getBody(), toolVariableManager);

        List<Message> feedbackStackedMessages = new ArrayList<>(this.feedbackMessageService.getFeedbackMessages());
        if (optionalVariableManager.isEmpty()) {
            List<Message> errorMessages = new ArrayList<>();
            errorMessages.add(new Message(String.format("Something went wrong while executing the tool '%s'", tool.getName()), MessageLevel.ERROR));
            errorMessages.addAll(feedbackStackedMessages);
            return new Failure(errorMessages);
        } else {
            // Create a default NEW_SELECTION based on the new instances created, but also expose these instances with their names in the returned Status
            // so that they can be used when evaluating an explicit elementsToSelectExpression.
            var selectionEntries = newInstancesCollector.values().stream()
                    .map(newInstance -> new WorkbenchSelectionEntry(this.objectService.getId(newInstance), this.objectService.getKind(newInstance)))
                    .toList();
            var parameters = Map.of(Success.NEW_SELECTION, new WorkbenchSelection(selectionEntries),
                                    OperationInterpreterViewSwitch.NEW_INSTANCES_COLLECTOR, newInstancesCollector);
            return new Success("", parameters, this.feedbackMessageService.getFeedbackMessages());
        }
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
        DiagramSwitch<Optional<VariableManager>> dispatcher = new DiagramOperationInterpreterViewSwitch(variableManager, this.interpreter, this.objectService, this.editService, this.diagramContext,
                this.convertedNodes, this);
        return dispatcher.doSwitch(operation);
    }
}
