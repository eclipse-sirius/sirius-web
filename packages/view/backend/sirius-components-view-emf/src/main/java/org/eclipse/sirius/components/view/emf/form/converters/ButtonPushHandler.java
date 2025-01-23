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
package org.eclipse.sirius.components.view.emf.form.converters;

import static org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverterSwitch.VARIABLE_MANAGER;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.emf.OperationInterpreter;

/**
 * Used to handle button push.
 *
 * @author sbegaudeau
 */
public class ButtonPushHandler implements Function<VariableManager, IStatus> {

    private final AQLInterpreter interpreter;

    private final IEditService editService;

    private final IFeedbackMessageService feedbackMessageService;

    private final List<Operation> operations;

    public ButtonPushHandler(AQLInterpreter interpreter, IEditService editService, IFeedbackMessageService feedbackMessageService, List<Operation> operations) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.editService = Objects.requireNonNull(editService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.operations = Objects.requireNonNull(operations);
    }

    @Override
    public IStatus apply(VariableManager variableManager) {
        VariableManager childVariableManager = variableManager.createChild();
        childVariableManager.put(VARIABLE_MANAGER, variableManager);
        OperationInterpreter operationInterpreter = new OperationInterpreter(this.interpreter, this.editService);
        Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(operations, childVariableManager);
        if (optionalVariableManager.isEmpty()) {
            List<Message> errorMessages = new ArrayList<>();
            errorMessages.add(new Message("Something went wrong while handling the widget operations execution.", MessageLevel.ERROR));
            errorMessages.addAll(this.feedbackMessageService.getFeedbackMessages());
            return new Failure(errorMessages);
        }
        return new Success(this.feedbackMessageService.getFeedbackMessages());
    }
}
