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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.ecore.extender.business.internal.accessor.ecore.EcoreIntrinsicExtender;
import org.eclipse.sirius.viewpoint.description.tool.ModelOperation;
import org.eclipse.sirius.viewpoint.description.tool.MoveElement;
import org.eclipse.sirius.web.compat.api.IModelOperationHandler;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.Status;
import org.eclipse.sirius.web.representations.VariableManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handle the {@link MoveElement} model operation.
 *
 * @author lfasani
 */
public class MoveElementOperationHandler implements IModelOperationHandler {
    private final Logger logger = LoggerFactory.getLogger(MoveElementOperationHandler.class);

    private final AQLInterpreter interpreter;

    private final ChildModelOperationHandler childModelOperationHandler;

    private final MoveElement moveElementOperation;

    public MoveElementOperationHandler(AQLInterpreter interpreter, ChildModelOperationHandler childModelOperationHandler, MoveElement moveElementOperation) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.childModelOperationHandler = Objects.requireNonNull(childModelOperationHandler);
        this.moveElementOperation = Objects.requireNonNull(moveElementOperation);
    }

    @Override
    public Status handle(Map<String, Object> variables) {
        String newContainerExpression = this.moveElementOperation.getNewContainerExpression();
        String featureName = this.moveElementOperation.getFeatureName();
        if (featureName != null && !featureName.isBlank() && newContainerExpression != null && !newContainerExpression.isBlank()) {
            // @formatter:off
            Optional<EObject> optionalNewContainer = this.interpreter.evaluateExpression(variables, newContainerExpression).asObject()
                    .filter(EObject.class::isInstance)
                    .map(EObject.class::cast);

            if (optionalNewContainer.isPresent()) {
                Optional<EObject> optionalValueObject = Optional.ofNullable(variables.get(VariableManager.SELF))
                     .filter(EObject.class::isInstance)
                     .map(EObject.class::cast);
                // @formatter:on

                if (optionalValueObject.isPresent()) {
                    this.doMoveElement(optionalNewContainer.get(), optionalValueObject.get(), featureName);

                }
            }
        }

        Map<String, Object> childVariables = new HashMap<>(variables);
        List<ModelOperation> subModelOperations = this.moveElementOperation.getSubModelOperations();
        return this.childModelOperationHandler.handle(this.interpreter, childVariables, subModelOperations);
    }

    /**
     * This implementation is based on implementations of
     * org.eclipse.sirius.ecore.extender.business.api.accessor.ModelAccessor.eIsMany(EObject, String) and
     * org.eclipse.sirius.ecore.extender.business.api.accessor.CompositeMetamodelExtender.eIsMany(EObject, String)
     */
    private void doMoveElement(EObject newContainer, EObject valueObject, String featureName) {
        EcoreIntrinsicExtender ecoreIntrinsicExtender = new EcoreIntrinsicExtender();
        Boolean isMany = ecoreIntrinsicExtender.eIsMany(newContainer, featureName);
        if (isMany == null) {
            this.logger.warn("The feature {} is unknown on the object {}", featureName, newContainer); //$NON-NLS-1$
        } else {
            if (!isMany) {
                Object currentValueOnContainer = ecoreIntrinsicExtender.eGet(newContainer, featureName);
                if (currentValueOnContainer == null) {
                    this.logger.warn("Impossible to add a value to the reference {} of the object {}", featureName, newContainer); //$NON-NLS-1$
                    return;
                }
            } else {
                ecoreIntrinsicExtender.eAdd(newContainer, featureName, valueObject);
            }
        }
    }
}
