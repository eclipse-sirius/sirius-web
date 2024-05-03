/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
package org.eclipse.sirius.components.view.emf;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.CreateInstance;
import org.eclipse.sirius.components.view.DeleteElement;
import org.eclipse.sirius.components.view.For;
import org.eclipse.sirius.components.view.If;
import org.eclipse.sirius.components.view.Let;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.SetValue;
import org.eclipse.sirius.components.view.UnsetValue;
import org.eclipse.sirius.components.view.util.ViewSwitch;
import org.eclipse.sirius.ecore.extender.business.internal.accessor.ecore.EcoreIntrinsicExtender;

/**
 * This switch allows to handle common {@link Operation}s without dependencies to a specific representation.
 *
 * @author fbarbin
 */
public class OperationInterpreterViewSwitch extends ViewSwitch<Optional<VariableManager>> {
    private final VariableManager variableManager;

    private final AQLInterpreter interpreter;

    private final IEditService editService;

    private final IOperationInterpreter operationInterpreter;

    private final EcoreIntrinsicExtender ecore;

    /**
     * Default constructor.
     *
     * @param variableManager
     *            the current {@link VariableManager}.
     * @param interpreter
     *            the {@link AQLInterpreter}.
     * @param editService
     *            the {@link IEditService}.
     * @param operationInterpreter
     *            the {@link IOperationInterpreter} used for delegating sub operations executions. It is the
     *            responsibility of the {@link IOperationInterpreter} to delegate each operation execution to the
     *            appropriate switch according to the concrete representation.
     */
    public OperationInterpreterViewSwitch(VariableManager variableManager, AQLInterpreter interpreter, IEditService editService, IOperationInterpreter operationInterpreter) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.interpreter = Objects.requireNonNull(interpreter);
        this.editService = Objects.requireNonNull(editService);
        this.operationInterpreter = Objects.requireNonNull(operationInterpreter);
        this.ecore = new EcoreIntrinsicExtender();
    }

    @Override
    public Optional<VariableManager> caseChangeContext(ChangeContext changeContextOperation) {
        Optional<Object> newContext = this.interpreter.evaluateExpression(this.variableManager.getVariables(), changeContextOperation.getExpression()).asObject();
        if (newContext.isPresent()) {
            VariableManager childVariableManager = this.variableManager.createChild();
            childVariableManager.put(VariableManager.SELF, newContext.get());
            return this.operationInterpreter.executeOperations(changeContextOperation.getChildren(), childVariableManager);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<VariableManager> caseIf(If ifOperation) {
        Optional<Boolean> testResult = this.interpreter.evaluateExpression(this.variableManager.getVariables(), ifOperation.getConditionExpression()).asBoolean();
        if (testResult.isPresent() && Boolean.TRUE.equals(testResult.get())) {
            return this.operationInterpreter.executeOperations(ifOperation.getChildren(), this.variableManager);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<VariableManager> caseFor(For forOperation) {
        Optional<List<Object>> optionalList = this.interpreter.evaluateExpression(this.variableManager.getVariables(), forOperation.getExpression()).asObjects();
        if (optionalList.isPresent()) {
            for (Object object : optionalList.get()) {
                VariableManager childVariableManager = this.variableManager.createChild();
                childVariableManager.put(forOperation.getIteratorName(), object);
                this.operationInterpreter.executeOperations(forOperation.getChildren(), childVariableManager);
            }
            return Optional.of(this.variableManager);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<VariableManager> caseLet(Let object) {
        VariableManager childVariableManager = this.variableManager.createChild();
        Optional<Object> variableValue = this.interpreter.evaluateExpression(this.variableManager.getVariables(), object.getValueExpression()).asObject();
        if (variableValue.isPresent()) {
            childVariableManager.put(object.getVariableName(), variableValue.get());
            return this.operationInterpreter.executeOperations(object.getChildren(), childVariableManager);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<VariableManager> caseCreateInstance(CreateInstance creatInstanceOperation) {
        var optionalSelf = this.variableManager.get(VariableManager.SELF, EObject.class);
        var editingDomain = this.variableManager.get(IEditingContext.EDITING_CONTEXT, IEMFEditingContext.class).map(IEMFEditingContext::getDomain);
        if (optionalSelf.isPresent() && editingDomain.isPresent()) {
            var optionalNewInstance = this.createSemanticInstance(editingDomain.get(), creatInstanceOperation.getTypeName());
            if (optionalNewInstance.isPresent()) {
                Object container = this.ecore.eAdd(optionalSelf.get(), creatInstanceOperation.getReferenceName(), optionalNewInstance.get());
                if (container != null) {
                    VariableManager childVariableManager = this.variableManager.createChild();
                    childVariableManager.put(creatInstanceOperation.getVariableName(), optionalNewInstance.get());
                    return this.operationInterpreter.executeOperations(creatInstanceOperation.getChildren(), childVariableManager);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<VariableManager> caseSetValue(SetValue setValueOperation) {
        var optionalSelf = this.variableManager.get(VariableManager.SELF, EObject.class);
        if (optionalSelf.isPresent()) {
            Result newValue = this.interpreter.evaluateExpression(this.variableManager.getVariables(), setValueOperation.getValueExpression());
            Object instance = null;
            if (newValue.asObject().isPresent()) {
                instance = this.ecore.eAdd(optionalSelf.get(), setValueOperation.getFeatureName(), newValue.asObject().get());
            } else {
                instance = this.ecore.eClear(optionalSelf.get(), setValueOperation.getFeatureName());
            }
            if (instance != null) {
                return this.operationInterpreter.executeOperations(setValueOperation.getChildren(), this.variableManager);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<VariableManager> caseUnsetValue(UnsetValue unsetValueOperation) {
        var optionalSelf = this.variableManager.get(VariableManager.SELF, EObject.class);
        if (optionalSelf.isPresent()) {
            var self = optionalSelf.get();
            EStructuralFeature feature = self.eClass().getEStructuralFeature(unsetValueOperation.getFeatureName());
            if (feature != null) {
                List<EObject> elementsToUnset = this.computeElementsToUnset(this.variableManager.getVariables(), unsetValueOperation.getElementExpression());
                this.unset(self, feature, elementsToUnset);
                return this.operationInterpreter.executeOperations(unsetValueOperation.getChildren(), this.variableManager);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<VariableManager> caseDeleteElement(DeleteElement deleteElementOperation) {
        var optionalSelf = this.variableManager.get(VariableManager.SELF, EObject.class);
        if (optionalSelf.isPresent()) {
            this.editService.delete(optionalSelf.get());
            return this.operationInterpreter.executeOperations(deleteElementOperation.getChildren(), this.variableManager);
        }
        return Optional.empty();
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
                                      .toList();
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

    private Optional<EObject> createSemanticInstance(EditingDomain editingDomain, String domainType) {
        Optional<EClass> optionalEClass = this.resolveType(editingDomain, domainType);
        if (optionalEClass.isPresent()) {
            return Optional.of(EcoreUtil.create(optionalEClass.get()));
        } else {
            return Optional.empty();
        }
    }

    private Optional<EClass> resolveType(EditingDomain editingDomain, String domainType) {
        String[] parts = domainType.split("(::?|\\.)");
        if (parts.length == 2) {
            // @formatter:off
            return editingDomain.getResourceSet().getPackageRegistry().values().stream()
                         .filter(EPackage.class::isInstance)
                         .map(EPackage.class::cast)
                         .filter(ePackage -> Objects.equals(ePackage.getName(), parts[0]))
                         .map(ePackage -> ePackage.getEClassifier(parts[1]))
                         .filter(EClass.class::isInstance)
                         .map(EClass.class::cast)
                         .findFirst();
            // @formatter:on
        } else {
            return Optional.empty();
        }
    }
}
