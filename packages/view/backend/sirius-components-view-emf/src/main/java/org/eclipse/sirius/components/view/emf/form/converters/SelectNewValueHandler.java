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
import java.util.function.BiFunction;

import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.emf.OperationInterpreter;
import org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverter;

/**
 * Used to update the value of a select.
 *
 * @author sbegaudeau
 */
public class SelectNewValueHandler implements BiFunction<VariableManager, String, IStatus> {

    private final AQLInterpreter interpreter;

    private final IObjectService objectService;

    private final IEditService editService;

    private final IFeedbackMessageService feedbackMessageService;

    private final List<Operation> operations;

    public SelectNewValueHandler(AQLInterpreter interpreter, IObjectService objectService, IEditService editService, IFeedbackMessageService feedbackMessageService, List<Operation> operations) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.operations = Objects.requireNonNull(operations);
    }

    @Override
    public IStatus apply(VariableManager variableManager, String newValue) {
        Object newValueObject = null;
        if (newValue != null && !newValue.isBlank()) {
            newValueObject = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class)
                    .flatMap(editingContext -> this.objectService.getObject(editingContext, newValue))
                    .orElse(newValue);
        }
        VariableManager childVariableManager = variableManager.createChild();
        childVariableManager.put(ViewFormDescriptionConverter.NEW_VALUE, newValueObject);
        childVariableManager.put(VARIABLE_MANAGER, variableManager);
        OperationInterpreter operationInterpreter = new OperationInterpreter(this.interpreter, this.editService);
        Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(operations, childVariableManager);
        if (optionalVariableManager.isEmpty()) {
            List<Message> errorMessages = new ArrayList<>();
            errorMessages.add(new Message("Something went wrong while handling the Select widget new value.", MessageLevel.ERROR));
            errorMessages.addAll(this.feedbackMessageService.getFeedbackMessages());
            return new Failure(errorMessages);
        }
        return new Success(this.feedbackMessageService.getFeedbackMessages());
    }
}
