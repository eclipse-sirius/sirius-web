/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.emf.view;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.ecore.extender.business.internal.accessor.ecore.EcoreIntrinsicExtender;
import org.eclipse.sirius.web.core.api.IEditService;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.interpreter.Result;
import org.eclipse.sirius.web.representations.Status;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.view.ChangeContext;
import org.eclipse.sirius.web.view.CreateInstance;
import org.eclipse.sirius.web.view.DeleteElement;
import org.eclipse.sirius.web.view.Operation;
import org.eclipse.sirius.web.view.SetValue;
import org.eclipse.sirius.web.view.Tool;
import org.eclipse.sirius.web.view.UnsetValue;
import org.eclipse.sirius.web.view.util.ViewSwitch;

/**
 * Executes the body of a tool as defined by a set of {@link Operation}s.
 *
 * @author pcdavid
 */
public class ToolInterpreter {

    private final AQLInterpreter interpreter;

    private final EcoreIntrinsicExtender ecore;

    private final IEditService editService;

    public ToolInterpreter(AQLInterpreter interpreter, IEditService editService) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.editService = Objects.requireNonNull(editService);
        this.ecore = new EcoreIntrinsicExtender();
    }

    public Status executeTool(Tool tool, VariableManager variableManager) {
        Optional<VariableManager> result = this.executeOperations(tool.getBody(), variableManager);
        if (result.isPresent()) {
            return Status.OK;
        } else {
            return Status.ERROR;
        }
    }

    private Optional<VariableManager> executeOperations(List<Operation> operations, VariableManager variableManager) {
        VariableManager currentContext = variableManager;
        for (Operation operation : operations) {
            Optional<VariableManager> newContext = this.executeOperation(operation, currentContext);
            if (newContext.isEmpty()) {
                return Optional.empty();
            } else {
                currentContext = newContext.get();
            }
        }
        return Optional.of(currentContext);
    }

    private Optional<VariableManager> executeOperation(Operation operation, VariableManager variableManager) {
        ViewSwitch<Optional<VariableManager>> dispatcher = new ViewSwitch<>() {
            @Override
            public Optional<VariableManager> caseChangeContext(ChangeContext op) {
                return ToolInterpreter.this.executeChangeContext(variableManager, op);
            }

            @Override
            public Optional<VariableManager> caseCreateInstance(CreateInstance op) {
                return ToolInterpreter.this.executeCreateInstance(variableManager, op);
            }

            @Override
            public Optional<VariableManager> caseSetValue(SetValue op) {
                return ToolInterpreter.this.executeSetValue(variableManager, op);
            }

            @Override
            public Optional<VariableManager> caseUnsetValue(UnsetValue op) {
                return ToolInterpreter.this.executeUnsetValue(variableManager, op);
            }

            @Override
            public Optional<VariableManager> caseDeleteElement(DeleteElement op) {
                return ToolInterpreter.this.executeDeleteElement(variableManager, op);
            }
        };
        return dispatcher.doSwitch(operation);
    }

    private Optional<VariableManager> executeChangeContext(VariableManager variableManager, ChangeContext changeContextOperation) {
        Optional<Object> newContext = this.interpreter.evaluateExpression(variableManager.getVariables(), changeContextOperation.getExpression()).asObject();
        if (newContext.isPresent()) {
            VariableManager childVariableManager = variableManager.createChild();
            childVariableManager.put(VariableManager.SELF, newContext.get());
            return this.executeOperations(changeContextOperation.getChildren(), childVariableManager);
        } else {
            return Optional.empty();
        }
    }

    private Optional<VariableManager> executeSetValue(VariableManager variableManager, SetValue setValueOperation) {
        var optionalSelf = variableManager.get(VariableManager.SELF, EObject.class);
        if (optionalSelf.isPresent()) {
            Result newValue = this.interpreter.evaluateExpression(variableManager.getVariables(), setValueOperation.getValueExpression());
            if (newValue.asObject().isPresent()) {
                Object instance = this.ecore.eAdd(optionalSelf.get(), setValueOperation.getFeatureName(), newValue.asObject().get());
                if (instance != null) {
                    return this.executeOperations(setValueOperation.getChildren(), variableManager);
                }
            }
        }
        return Optional.empty();
    }

    private Optional<VariableManager> executeUnsetValue(VariableManager variableManager, UnsetValue unsetValueOperation) {
        var optionalSelf = variableManager.get(VariableManager.SELF, EObject.class);
        if (optionalSelf.isPresent()) {
            var self = optionalSelf.get();
            EStructuralFeature feature = self.eClass().getEStructuralFeature(unsetValueOperation.getFeatureName());
            if (feature != null) {
                List<EObject> elementsToUnset = this.computeElementsToUnset(variableManager.getVariables(), unsetValueOperation.getElementExpression());
                this.unset(self, feature, elementsToUnset);
                return this.executeOperations(unsetValueOperation.getChildren(), variableManager);
            }
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

    private Optional<VariableManager> executeCreateInstance(VariableManager variableManager, CreateInstance creatInstanceOperation) {
        var optionalSelf = variableManager.get(VariableManager.SELF, EObject.class);
        if (optionalSelf.isPresent()) {
            var newInstance = this.createSemanticInstance(optionalSelf.get(), creatInstanceOperation.getTypeName());
            if (newInstance != null) {
                Object container = this.ecore.eAdd(optionalSelf.get(), creatInstanceOperation.getReferenceName(), newInstance);
                if (container != null) {
                    VariableManager childVariableManager = variableManager.createChild();
                    childVariableManager.put(VariableManager.SELF, newInstance);
                    return this.executeOperations(creatInstanceOperation.getChildren(), childVariableManager);
                }
            }
        }
        return Optional.empty();
    }

    private EObject createSemanticInstance(EObject self, String domainType) {
        EPackage ePackage = self.eClass().getEPackage();
        // @formatter:off
        EClass klass = ePackage
                      .getEClassifiers().stream()
                      .filter(classifier -> classifier instanceof EClass && Objects.equals(domainType, classifier.getName()))
                      .map(EClass.class::cast)
                      .findFirst()
                      .get();
        // @formatter:on
        return ePackage.getEFactoryInstance().create(klass);
    }

    private Optional<VariableManager> executeDeleteElement(VariableManager variableManager, DeleteElement deleteElementOperation) {
        var optionalSelf = variableManager.get(VariableManager.SELF, EObject.class);
        if (optionalSelf.isPresent()) {
            this.editService.delete(optionalSelf.get());
            return this.executeOperations(deleteElementOperation.getChildren(), variableManager);
        }
        return Optional.empty();
    }

}
