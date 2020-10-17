/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.compat.operations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.viewpoint.description.tool.For;
import org.eclipse.sirius.viewpoint.description.tool.ModelOperation;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.Status;

/**
 * Handle the {@link For} model operation.
 *
 * @author lfasani
 */
public class ForOperationHandler implements IModelOperationHandler {

    private final AQLInterpreter interpreter;

    private final ChildModelOperationHandler childModelOperationHandler;

    private final For forOperation;

    public ForOperationHandler(AQLInterpreter interpreter, ChildModelOperationHandler childModelOperationHandler, For forOperation) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.childModelOperationHandler = Objects.requireNonNull(childModelOperationHandler);
        this.forOperation = Objects.requireNonNull(forOperation);
    }

    @Override
    public Status handle(Map<String, Object> variables) {
        String expression = this.forOperation.getExpression();
        String iteratedVariableName = this.forOperation.getIteratorName();

        if (expression != null && !expression.isBlank() && iteratedVariableName != null && !iteratedVariableName.isBlank()) {
            Optional<List<Object>> optionalObjectsToIterate = this.interpreter.evaluateExpression(variables, expression).asObjects();

            if (optionalObjectsToIterate.isPresent()) {
                List<Object> objectsToIterate = optionalObjectsToIterate.get();

                for (Object object : objectsToIterate) {
                    Map<String, Object> childVariables = new HashMap<>(variables);
                    childVariables.put(iteratedVariableName, object);

                    List<ModelOperation> subModelOperations = this.forOperation.getSubModelOperations();
                    this.childModelOperationHandler.handle(this.interpreter, childVariables, subModelOperations);
                }
            }
        }

        return Status.OK;
    }

}
