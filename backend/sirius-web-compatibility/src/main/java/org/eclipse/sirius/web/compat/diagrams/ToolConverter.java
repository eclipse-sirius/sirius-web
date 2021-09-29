/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.compat.diagrams;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.sirius.diagram.description.tool.DeleteElementDescription;
import org.eclipse.sirius.diagram.description.tool.DirectEditLabel;
import org.eclipse.sirius.viewpoint.description.tool.InitialOperation;
import org.eclipse.sirius.web.compat.api.IModelOperationHandlerSwitchProvider;
import org.eclipse.sirius.web.core.api.IEditService;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.Failure;
import org.eclipse.sirius.web.representations.IStatus;
import org.eclipse.sirius.web.representations.Success;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Converts Sirius Diagrams tools definitions into plain Java functions that can be easily invoked without depending on
 * Sirius.
 *
 * @author pcdavid
 */
public class ToolConverter {
    private final AQLInterpreter interpreter;

    private final IEditService editService;

    private final IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider;

    public ToolConverter(AQLInterpreter interpreter, IEditService editService, IModelOperationHandlerSwitchProvider modelOperationHandlerSwitchProvider) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.editService = Objects.requireNonNull(editService);
        this.modelOperationHandlerSwitchProvider = Objects.requireNonNull(modelOperationHandlerSwitchProvider);
    }

    public BiFunction<VariableManager, String, IStatus> createDirectEditToolHandler(DirectEditLabel labelEditDescription) {
        var optionalInitialOperation = Optional.ofNullable(labelEditDescription).map(DirectEditLabel::getInitialOperation);
        if (optionalInitialOperation.isPresent()) {
            InitialOperation initialOperation = optionalInitialOperation.get();
            return (variableManager, newText) -> {
                Map<String, Object> variables = variableManager.getVariables();
                variables.put("arg0", newText); //$NON-NLS-1$
                var modelOperationHandlerSwitch = this.modelOperationHandlerSwitchProvider.getModelOperationHandlerSwitch(this.interpreter);
                return modelOperationHandlerSwitch.apply(initialOperation.getFirstModelOperations()).map(handler -> {
                    return handler.handle(variables);
                }).orElse(new Failure("")); //$NON-NLS-1$
            };
        } else {
            // If no direct edit tool is defined, nothing to do but consider this OK.
            return (variableManager, newText) -> new Success();
        }
    }

    public Function<VariableManager, IStatus> createDeleteToolHandler(DeleteElementDescription deleteDescription) {
        var optionalInitialOperation = Optional.ofNullable(deleteDescription).map(DeleteElementDescription::getInitialOperation);
        if (optionalInitialOperation.isPresent()) {
            InitialOperation initialOperation = optionalInitialOperation.get();
            return variableManager -> {
                Map<String, Object> variables = variableManager.getVariables();
                // Sirius Desktop Delete Tools expect an "element" variable to be available with the value
                // of the initial invocation context (self).
                variables.put("element", variables.get(VariableManager.SELF)); //$NON-NLS-1$
                var modelOperationHandlerSwitch = this.modelOperationHandlerSwitchProvider.getModelOperationHandlerSwitch(this.interpreter);
                return modelOperationHandlerSwitch.apply(initialOperation.getFirstModelOperations()).map(handler -> {
                    return handler.handle(variables);
                }).orElse(new Failure("")); //$NON-NLS-1$
            };
        } else {
            // If no delete tool is defined, execute the default behavior: delete the underlying semantic element.
            return variableManager -> {
                Optional.of(variableManager.getVariables().get(VariableManager.SELF)).ifPresent(this.editService::delete);
                return new Success();
            };
        }
    }
}
