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
import org.eclipse.sirius.viewpoint.description.tool.SetValue;
import org.eclipse.sirius.web.compat.api.IIdentifierProvider;
import org.eclipse.sirius.web.compat.api.IModelOperationHandler;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.IStatus;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Handle the set value model operation.
 *
 * @author sbegaudeau
 */
public class SetValueOperationHandler implements IModelOperationHandler {

    private final IObjectService objectService;

    private final IIdentifierProvider identifierProvider;

    private final AQLInterpreter interpreter;

    private final ChildModelOperationHandler childModelOperationHandler;

    private final SetValue setValue;

    public SetValueOperationHandler(IObjectService objectService, IIdentifierProvider identifierProvider, AQLInterpreter interpreter, ChildModelOperationHandler childModelOperationHandler,
            SetValue setValue) {
        this.objectService = Objects.requireNonNull(objectService);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.interpreter = Objects.requireNonNull(interpreter);
        this.childModelOperationHandler = Objects.requireNonNull(childModelOperationHandler);
        this.setValue = Objects.requireNonNull(setValue);
    }

    @Override
    public IStatus handle(Map<String, Object> variables) {
        String featureName = this.setValue.getFeatureName();
        if (featureName != null && !featureName.isBlank()) {
            String valueExpression = this.setValue.getValueExpression();
            Optional<Object> optionalValueObject = this.interpreter.evaluateExpression(variables, valueExpression).asObject();

            // @formatter:off
            Optional<EObject> optionalOwnerObject = Optional.ofNullable(variables.get(VariableManager.SELF))
                 .filter(EObject.class::isInstance)
                 .map(EObject.class::cast);
            // @formatter:on

            if (optionalValueObject.isPresent() && optionalOwnerObject.isPresent()) {
                EObject ownerObject = optionalOwnerObject.get();
                Object valueObject = optionalValueObject.get();

                new EcoreIntrinsicExtender().eAdd(ownerObject, featureName, valueObject);
            }
        }

        Map<String, Object> childVariables = new HashMap<>(variables);
        List<ModelOperation> subModelOperations = this.setValue.getSubModelOperations();
        return this.childModelOperationHandler.handle(this.objectService, this.identifierProvider, this.interpreter, childVariables, subModelOperations);
    }

}
