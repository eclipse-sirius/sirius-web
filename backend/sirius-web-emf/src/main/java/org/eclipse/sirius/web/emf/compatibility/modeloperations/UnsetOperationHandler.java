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
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.ecore.extender.business.internal.accessor.ecore.EcoreIntrinsicExtender;
import org.eclipse.sirius.viewpoint.description.tool.ModelOperation;
import org.eclipse.sirius.viewpoint.description.tool.Unset;
import org.eclipse.sirius.web.compat.api.IModelOperationHandler;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.Status;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Handle the {@link Unset} model operation.
 *
 * @author lfasani
 */
public class UnsetOperationHandler implements IModelOperationHandler {

    private final AQLInterpreter interpreter;

    private final ChildModelOperationHandler childModelOperationHandler;

    private final Unset unsetOperation;

    public UnsetOperationHandler(AQLInterpreter interpreter, ChildModelOperationHandler childModelOperationHandler, Unset unsetOperation) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.childModelOperationHandler = Objects.requireNonNull(childModelOperationHandler);
        this.unsetOperation = Objects.requireNonNull(unsetOperation);
    }

    @Override
    public Status handle(Map<String, Object> variables) {
        String featureName = this.unsetOperation.getFeatureName();
        String elementExpression = this.unsetOperation.getElementExpression();

        if (featureName != null && !featureName.isBlank()) {
            // @formatter:off
            Optional<EObject> optionalOwnerObject = Optional.ofNullable(variables.get(VariableManager.SELF))
                    .filter(EObject.class::isInstance)
                    .map(EObject.class::cast);

            if (optionalOwnerObject.isPresent()) {
                EObject ownerObject = optionalOwnerObject.get();
                List<EObject> elementsToUnset = null;
                if (elementExpression != null && !elementExpression.isBlank()) {
                    Optional<List<Object>> optionalObjectsToUnset = this.interpreter.evaluateExpression(variables, elementExpression).asObjects();
                    if (optionalObjectsToUnset.isPresent()) {
                        elementsToUnset = optionalObjectsToUnset.get().stream()
                            .filter(EObject.class::isInstance)
                            .map(EObject.class::cast)
                            .collect(Collectors.toList());
                    }
                    // @formatter:on
                }

                // This implementation is the one in Sirius
                EcoreIntrinsicExtender ecoreIntrinsicExtender = new EcoreIntrinsicExtender();
                Boolean eIsMany = ecoreIntrinsicExtender.eIsMany(ownerObject, featureName);
                if (eIsMany) {
                    ecoreIntrinsicExtender.eAdd(ownerObject, featureName, elementsToUnset);
                } else {
                    ecoreIntrinsicExtender.eSet(ownerObject, featureName, elementsToUnset);
                }
            }
        }

        Map<String, Object> childVariables = new HashMap<>(variables);
        List<ModelOperation> subModelOperations = this.unsetOperation.getSubModelOperations();
        return this.childModelOperationHandler.handle(this.interpreter, childVariables, subModelOperations);
    }

}
