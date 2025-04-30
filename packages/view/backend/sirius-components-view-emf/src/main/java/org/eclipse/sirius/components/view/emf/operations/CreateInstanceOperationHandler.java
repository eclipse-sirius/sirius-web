/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.operations;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.CreateInstance;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.emf.operations.api.IAddExecutor;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationHandler;
import org.eclipse.sirius.components.view.emf.operations.api.OperationEvaluationResult;
import org.eclipse.sirius.components.view.emf.operations.api.OperationExecutionStatus;
import org.springframework.stereotype.Service;

/**
 * Used to handle the create instance operation.
 *
 * @author sbegaudeau
 */
@Service
public class CreateInstanceOperationHandler implements IOperationHandler {

    private final IAddExecutor addExecutor;

    public CreateInstanceOperationHandler(IAddExecutor addExecutor) {
        this.addExecutor = Objects.requireNonNull(addExecutor);
    }

    @Override
    public boolean canHandle(AQLInterpreter interpreter, VariableManager variableManager, Operation operation) {
        return operation instanceof CreateInstance;
    }

    @Override
    public OperationEvaluationResult handle(AQLInterpreter interpreter, VariableManager variableManager, Operation operation) {
        var optionalSelf = variableManager.get(VariableManager.SELF, EObject.class);
        var optionalEditingDomain = variableManager.get(IEditingContext.EDITING_CONTEXT, IEMFEditingContext.class)
                .map(IEMFEditingContext::getDomain);
        if (operation instanceof CreateInstance createInstanceOperation && optionalSelf.isPresent() && optionalEditingDomain.isPresent()) {
            var self = optionalSelf.get();
            var editingDomain = optionalEditingDomain.get();
            var optionalNewInstance = this.resolveType(editingDomain, createInstanceOperation.getTypeName()).map(EcoreUtil::create);
            if (optionalNewInstance.isPresent()) {
                EObject newInstance = optionalNewInstance.get();
                Object container = this.addExecutor.eAdd(self, createInstanceOperation.getReferenceName(), newInstance);
                if (container != null) {
                    VariableManager childVariableManager = variableManager.createChild();
                    childVariableManager.put(createInstanceOperation.getVariableName(), newInstance);

                    return new OperationEvaluationResult(OperationExecutionStatus.SUCCESS, List.of(childVariableManager), Map.of(createInstanceOperation.getVariableName(), newInstance));
                }
            }
        }
        return new OperationEvaluationResult(OperationExecutionStatus.FAILURE, List.of(variableManager), Map.of());
    }

    private Optional<EClass> resolveType(EditingDomain editingDomain, String domainType) {
        String[] parts = domainType.split("(::?|\\.)");
        if (parts.length == 2) {
            return editingDomain.getResourceSet().getPackageRegistry().values().stream()
                    .filter(EPackage.class::isInstance)
                    .map(EPackage.class::cast)
                    .filter(ePackage -> Objects.equals(ePackage.getName(), parts[0]))
                    .map(ePackage -> ePackage.getEClassifier(parts[1]))
                    .filter(EClass.class::isInstance)
                    .map(EClass.class::cast)
                    .findFirst();
        } else {
            return Optional.empty();
        }
    }
}
