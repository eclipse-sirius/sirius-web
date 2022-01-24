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
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.viewpoint.description.tool.If;
import org.eclipse.sirius.viewpoint.description.tool.ModelOperation;
import org.eclipse.sirius.web.compat.api.IIdentifierProvider;
import org.eclipse.sirius.web.compat.api.IModelOperationHandler;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.IStatus;
import org.eclipse.sirius.web.representations.Success;

/**
 * Handle the {@link If} model operation.
 *
 * @author lfasani
 */
public class IfOperationHandler implements IModelOperationHandler {

    private final IObjectService objectService;

    private final IIdentifierProvider identifierProvider;

    private final AQLInterpreter interpreter;

    private final ChildModelOperationHandler childModelOperationHandler;

    private final If ifOperation;

    public IfOperationHandler(IObjectService objectService, IIdentifierProvider identifierProvider, AQLInterpreter interpreter, ChildModelOperationHandler childModelOperationHandler, If ifOperation) {
        this.objectService = Objects.requireNonNull(objectService);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.interpreter = Objects.requireNonNull(interpreter);
        this.childModelOperationHandler = Objects.requireNonNull(childModelOperationHandler);
        this.ifOperation = Objects.requireNonNull(ifOperation);
    }

    @Override
    public IStatus handle(Map<String, Object> variables) {
        String conditionExpression = this.ifOperation.getConditionExpression();
        if (conditionExpression != null && !conditionExpression.isBlank()) {
            Optional<Boolean> optionalValueObject = this.interpreter.evaluateExpression(variables, conditionExpression).asBoolean();

            if (optionalValueObject.isPresent() && optionalValueObject.get()) {
                List<ModelOperation> subModelOperations = this.ifOperation.getSubModelOperations();
                return this.childModelOperationHandler.handle(this.objectService, this.identifierProvider, this.interpreter, variables, subModelOperations);
            }
        }

        return new Success();
    }

}
