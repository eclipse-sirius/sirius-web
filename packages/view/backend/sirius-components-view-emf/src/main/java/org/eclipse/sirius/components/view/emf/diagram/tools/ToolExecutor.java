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
import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.representations.WorkbenchSelection;
import org.eclipse.sirius.components.representations.WorkbenchSelectionEntry;
import org.eclipse.sirius.components.view.diagram.Tool;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.IToolExecutor;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationExecutor;
import org.eclipse.sirius.components.view.emf.operations.api.OperationExecutionStatus;
import org.springframework.stereotype.Service;

/**
 * Used to execute a tool.
 *
 * @author sbegaudeau
 */
@Service
public class ToolExecutor implements IToolExecutor {

    public static final String NAMED_INSTANCES = "NAMED_INSTANCES";

    private final IIdentityService identityService;

    private final IOperationExecutor operationExecutor;

    private final IFeedbackMessageService feedbackMessageService;

    public ToolExecutor(IIdentityService identityService, IOperationExecutor operationExecutor, IFeedbackMessageService feedbackMessageService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.operationExecutor = Objects.requireNonNull(operationExecutor);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
    }

    @Override
    public IStatus executeTool(Tool tool, AQLInterpreter interpreter, VariableManager variableManager) {
        var result = this.operationExecutor.execute(interpreter, variableManager, tool.getBody());

        List<Message> feedbackStackedMessages = new ArrayList<>(this.feedbackMessageService.getFeedbackMessages());
        if (result.status() == OperationExecutionStatus.FAILURE) {
            List<Message> errorMessages = new ArrayList<>();
            errorMessages.add(new Message(String.format("Something went wrong while executing the tool '%s'", tool.getName()), MessageLevel.ERROR));
            errorMessages.addAll(feedbackStackedMessages);
            return new Failure(errorMessages);
        }

        var selectionEntries = result.newInstances().values().stream()
                .map(newInstance -> new WorkbenchSelectionEntry(this.identityService.getId(newInstance), this.identityService.getKind(newInstance)))
                .toList();
        Map<String, Object> parameters = Map.of(
                Success.NEW_SELECTION, new WorkbenchSelection(selectionEntries),
                NAMED_INSTANCES, result.newInstances()
        );
        return new Success("", parameters, this.feedbackMessageService.getFeedbackMessages());
    }
}
