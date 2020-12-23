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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.viewpoint.description.tool.Let;
import org.eclipse.sirius.viewpoint.description.tool.ModelOperation;
import org.eclipse.sirius.web.compat.api.IModelOperationHandler;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.Status;

/**
 * Handle the {@link Let} model operation.
 *
 * @author lfasani
 */
public class LetOperationHandler implements IModelOperationHandler {

    private final AQLInterpreter interpreter;

    private final ChildModelOperationHandler childModelOperationHandler;

    private final Let letOperation;

    public LetOperationHandler(AQLInterpreter interpreter, ChildModelOperationHandler childModelOperationHandler, Let letOperation) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.childModelOperationHandler = Objects.requireNonNull(childModelOperationHandler);
        this.letOperation = Objects.requireNonNull(letOperation);
    }

    @Override
    public Status handle(Map<String, Object> variables) {
        String valueExpression = this.letOperation.getValueExpression();
        String variableName = this.letOperation.getVariableName();
        Map<String, Object> childVariables = new HashMap<>(variables);

        if (valueExpression != null && !valueExpression.isBlank() && variableName != null && !variableName.isBlank()) {
            Optional<Object> optionalValueObject = this.interpreter.evaluateExpression(variables, valueExpression).asObject();

            if (optionalValueObject.isPresent()) {
                Object valueObject = optionalValueObject.get();

                childVariables.put(variableName, valueObject);
            }
        }

        List<ModelOperation> subModelOperations = this.letOperation.getSubModelOperations();
        return this.childModelOperationHandler.handle(this.interpreter, childVariables, subModelOperations);
    }

}
