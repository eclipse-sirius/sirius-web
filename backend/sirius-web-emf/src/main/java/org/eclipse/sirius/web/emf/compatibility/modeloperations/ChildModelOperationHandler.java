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
import org.eclipse.sirius.web.compat.api.IIdentifierProvider;
import org.eclipse.sirius.web.compat.api.IModelOperationHandler;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.Failure;
import org.eclipse.sirius.web.representations.IStatus;
import org.eclipse.sirius.web.representations.Success;

/**
 * Used to execute some child model operations.
 *
 * @author sbegaudeau
 */
public class ChildModelOperationHandler {
    public IStatus handle(IObjectService objectService, IIdentifierProvider identifierProvider, AQLInterpreter interpreter, Map<String, Object> variables, List<ModelOperation> modelOperations) {
        boolean hasBeenSuccessfullyExecuted = true;

        ModelOperationHandlerSwitch modelOperationHandlerSwitch = new ModelOperationHandlerSwitch(objectService, identifierProvider, interpreter);
        for (ModelOperation modelOperation : modelOperations) {
            Optional<IModelOperationHandler> optionalModelOperationHandler = modelOperationHandlerSwitch.apply(modelOperation);

            IStatus status = optionalModelOperationHandler.map(handler -> {
                return handler.handle(variables);
            }).orElse(new Failure("")); //$NON-NLS-1$

            hasBeenSuccessfullyExecuted = hasBeenSuccessfullyExecuted && status instanceof Success;
        }

        if (hasBeenSuccessfullyExecuted) {
            return new Success();
        }
        return new Failure(""); //$NON-NLS-1$
    }
}
