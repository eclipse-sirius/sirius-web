/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo and others.
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
import org.eclipse.sirius.components.core.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.components.core.api.WorkbenchSelection;
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

    public IStatus handle(IObjectService objectService, IRepresentationMetadataSearchService representationMetadataSearchService, IIdentifierProvider identifierProvider, AQLInterpreter interpreter,
            Map<String, Object> variables, List<ModelOperation> modelOperations) {
        Success success = new Success();
        boolean hasBeenSuccessfullyExecuted = true;

        ModelOperationHandlerSwitch modelOperationHandlerSwitch = new ModelOperationHandlerSwitch(objectService, representationMetadataSearchService, identifierProvider,
                this.externalJavaActionProviders, interpreter);
        for (ModelOperation modelOperation : modelOperations) {
            Optional<IModelOperationHandler> optionalModelOperationHandler = modelOperationHandlerSwitch.apply(modelOperation);

            IStatus status = optionalModelOperationHandler.map(handler -> {
                return handler.handle(variables);
            }).orElse(new Failure("")); //$NON-NLS-1$

            hasBeenSuccessfullyExecuted = hasBeenSuccessfullyExecuted && status instanceof Success;

            if (hasBeenSuccessfullyExecuted) {
                // @formatter:off
                var optionalChildModelOperationNewSelection = Optional.of(status)
                        .filter(Success.class::isInstance)
                        .map(Success.class::cast)
                        .map(result -> result.getParameters().get(Success.NEW_SELECTION))
                        .filter(WorkbenchSelection.class::isInstance)
                        .map(WorkbenchSelection.class::cast);
                // @formatter:on

                if (optionalChildModelOperationNewSelection.isPresent()) {
                    WorkbenchSelection childWorkbenchSelection = optionalChildModelOperationNewSelection.get();

                    Object newSelection = success.getParameters().get(Success.NEW_SELECTION);
                    if (newSelection instanceof WorkbenchSelection) {
                        ((WorkbenchSelection) newSelection).getEntries().addAll(childWorkbenchSelection.getEntries());
                    } else if (newSelection == null) {
                        success.getParameters().put(Success.NEW_SELECTION, new WorkbenchSelection(childWorkbenchSelection.getEntries()));
                    }
                }
            }
        }

        if (hasBeenSuccessfullyExecuted) {
            return success;
        }
        return new Failure(""); //$NON-NLS-1$
    }
}
