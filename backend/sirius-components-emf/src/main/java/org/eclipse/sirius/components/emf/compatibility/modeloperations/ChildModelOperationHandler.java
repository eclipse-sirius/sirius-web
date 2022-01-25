/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo and others.
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
package org.eclipse.sirius.components.emf.compatibility.modeloperations;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.compatibility.api.IModelOperationHandler;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.compatibility.api.IExternalJavaActionProvider;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.viewpoint.description.tool.ModelOperation;

/**
 * Used to execute some child model operations.
 *
 * @author sbegaudeau
 */
public class ChildModelOperationHandler {

    private final List<IExternalJavaActionProvider> externalJavaActionProviders;

    public ChildModelOperationHandler(List<IExternalJavaActionProvider> externalJavaActionProviders) {
        this.externalJavaActionProviders = Objects.requireNonNull(externalJavaActionProviders);
    }

    public IStatus handle(IObjectService objectService, IIdentifierProvider identifierProvider, AQLInterpreter interpreter, Map<String, Object> variables, List<ModelOperation> modelOperations) {
        boolean hasBeenSuccessfullyExecuted = true;

        ModelOperationHandlerSwitch modelOperationHandlerSwitch = new ModelOperationHandlerSwitch(objectService, identifierProvider, this.externalJavaActionProviders, interpreter);
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
