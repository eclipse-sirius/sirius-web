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

import org.eclipse.emf.common.util.EList;
import org.eclipse.sirius.viewpoint.description.tool.Case;
import org.eclipse.sirius.viewpoint.description.tool.Default;
import org.eclipse.sirius.viewpoint.description.tool.ModelOperation;
import org.eclipse.sirius.viewpoint.description.tool.Switch;
import org.eclipse.sirius.web.compat.api.IModelOperationHandler;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.Status;

/**
 * Handle the {@link Switch} model operation.
 *
 * @author lfasani
 */
public class SwitchOperationHandler implements IModelOperationHandler {

    private final AQLInterpreter interpreter;

    private final ChildModelOperationHandler childModelOperationHandler;

    private final Switch switchOperation;

    public SwitchOperationHandler(AQLInterpreter interpreter, ChildModelOperationHandler childModelOperationHandler, Switch switchOperation) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.childModelOperationHandler = Objects.requireNonNull(childModelOperationHandler);
        this.switchOperation = Objects.requireNonNull(switchOperation);
    }

    @Override
    public Status handle(Map<String, Object> variables) {
        EList<Case> switchCases = this.switchOperation.getCases();
        Map<String, Object> childVariables = new HashMap<>(variables);

        Status status = Status.OK;
        boolean oneCaseHasBeenExecuted = false;
        for (Case switchCase : switchCases) {
            String conditionExpression = switchCase.getConditionExpression();
            if (conditionExpression != null) {
                Optional<Boolean> optionalValueObject = this.interpreter.evaluateExpression(variables, conditionExpression).asBoolean();

                if (optionalValueObject.isPresent() && optionalValueObject.get()) {
                    List<ModelOperation> subModelOperations = switchCase.getSubModelOperations();
                    status = this.childModelOperationHandler.handle(this.interpreter, childVariables, subModelOperations);

                    oneCaseHasBeenExecuted = true;
                    break;
                }
            }
        }

        if (!oneCaseHasBeenExecuted) {
            Default defaultCase = this.switchOperation.getDefault();
            if (defaultCase != null) {
                List<ModelOperation> subModelOperations = defaultCase.getSubModelOperations();
                status = this.childModelOperationHandler.handle(this.interpreter, childVariables, subModelOperations);
            }
        }

        return status;
    }
}
