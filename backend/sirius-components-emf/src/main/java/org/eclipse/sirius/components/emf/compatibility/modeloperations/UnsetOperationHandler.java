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
package org.eclipse.sirius.components.emf.compatibility.modeloperations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.compatibility.api.IModelOperationHandler;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.viewpoint.description.tool.ModelOperation;
import org.eclipse.sirius.viewpoint.description.tool.Unset;

/**
 * Handle the {@link Unset} model operation.
 *
 * @author lfasani
 */
public class UnsetOperationHandler implements IModelOperationHandler {

    private final IObjectService objectService;

    private final IIdentifierProvider identifierProvider;

    private final AQLInterpreter interpreter;

    private final ChildModelOperationHandler childModelOperationHandler;

    private final Unset unsetOperation;

    public UnsetOperationHandler(IObjectService objectService, IIdentifierProvider identifierProvider, AQLInterpreter interpreter, ChildModelOperationHandler childModelOperationHandler,
            Unset unsetOperation) {
        this.objectService = Objects.requireNonNull(objectService);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.interpreter = Objects.requireNonNull(interpreter);
        this.childModelOperationHandler = Objects.requireNonNull(childModelOperationHandler);
        this.unsetOperation = Objects.requireNonNull(unsetOperation);
    }

    @Override
    public IStatus handle(Map<String, Object> variables) {
        var optionalContext = this.getContext(variables);
        var optionalFeature = optionalContext.flatMap(context -> this.computeFeature(context, variables));
        if (optionalContext.isPresent() && optionalFeature.isPresent()) {
            List<EObject> elementsToUnset = this.computeElementsToUnset(variables, this.unsetOperation.getElementExpression());
            this.unset(optionalContext.get(), optionalFeature.get(), elementsToUnset);
        }
        return this.executeChildrenOperations(variables);
    }

    private Optional<EObject> getContext(Map<String, Object> variables) {
        // @formatter:off
        return Optional.ofNullable(variables.get(VariableManager.SELF))
                       .filter(EObject.class::isInstance)
                       .map(EObject.class::cast);
        // @formatter:on
    }

    private Optional<EStructuralFeature> computeFeature(EObject context, Map<String, Object> variables) {
        String featureNameExpression = this.unsetOperation.getFeatureName();
        if (featureNameExpression != null && !featureNameExpression.isBlank()) {
            // @formatter:off
            return this.interpreter.evaluateExpression(variables, featureNameExpression).asString()
                                   .map(featureName -> context.eClass().getEStructuralFeature(featureName));
            // @formatter:on
        } else {
            return Optional.empty();
        }
    }

    private List<EObject> computeElementsToUnset(Map<String, Object> variables, String elementExpression) {
        List<EObject> elementsToUnset = null;
        if (elementExpression != null && !elementExpression.isBlank()) {
            Optional<List<Object>> optionalObjectsToUnset = this.interpreter.evaluateExpression(variables, elementExpression).asObjects();
            if (optionalObjectsToUnset.isPresent()) {
                // @formatter:off
                elementsToUnset = optionalObjectsToUnset.get().stream()
                                      .filter(EObject.class::isInstance)
                                      .map(EObject.class::cast)
                                      .collect(Collectors.toList());
                // @formatter:on
            }
        }
        return elementsToUnset;
    }

    private void unset(EObject context, EStructuralFeature featureToEdit, List<EObject> elementsToUnset) {
        if (elementsToUnset == null) {
            if (featureToEdit.isMany()) {
                EList<?> values = (EList<?>) context.eGet(featureToEdit);
                values.clear();
            } else {
                context.eSet(featureToEdit, null);
            }
        } else {
            elementsToUnset.stream().forEach(value -> EcoreUtil.remove(context, featureToEdit, value));
        }
    }

    private IStatus executeChildrenOperations(Map<String, Object> variables) {
        Map<String, Object> childVariables = new HashMap<>(variables);
        List<ModelOperation> subModelOperations = this.unsetOperation.getSubModelOperations();
        return this.childModelOperationHandler.handle(this.objectService, this.identifierProvider, this.interpreter, childVariables, subModelOperations);
    }

}
