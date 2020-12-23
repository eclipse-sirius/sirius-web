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
package org.eclipse.sirius.web.emf.compatibility.modeloperations;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.sirius.viewpoint.description.tool.ModelOperation;
import org.eclipse.sirius.web.compat.api.IModelOperationHandler;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.Status;

/**
 * Used to execute some child model operations.
 *
 * @author sbegaudeau
 */
public class ChildModelOperationHandler {
    public Status handle(AQLInterpreter interpreter, Map<String, Object> variables, List<ModelOperation> modelOperations) {
        boolean hasBeenSuccessfullyExecuted = true;

        ModelOperationHandlerSwitch modelOperationHandlerSwitch = new ModelOperationHandlerSwitch(interpreter);
        for (ModelOperation modelOperation : modelOperations) {
            Optional<IModelOperationHandler> optionalModelOperationHandler = modelOperationHandlerSwitch.apply(modelOperation);

            Status status = optionalModelOperationHandler.map(handler -> {
                return handler.handle(variables);
            }).orElse(Status.ERROR);

            hasBeenSuccessfullyExecuted = hasBeenSuccessfullyExecuted && Status.OK.equals(status);
        }

        if (hasBeenSuccessfullyExecuted) {
            return Status.OK;
        }
        return Status.ERROR;
    }
}
