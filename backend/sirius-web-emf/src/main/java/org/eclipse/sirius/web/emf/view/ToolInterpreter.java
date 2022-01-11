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
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.ecore.extender.business.internal.accessor.ecore.EcoreIntrinsicExtender;
import org.eclipse.sirius.web.core.api.IEditService;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.emf.services.EditingContext;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.interpreter.Result;
import org.eclipse.sirius.web.representations.Failure;
import org.eclipse.sirius.web.representations.IStatus;
import org.eclipse.sirius.web.representations.Success;
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

    public IStatus executeTool(Tool tool, VariableManager variableManager) {
        Optional<VariableManager> result = this.executeOperations(tool.getBody(), variableManager);
        if (result.isPresent()) {
            return new Success();
        } else {
            return new Failure(""); //$NON-NLS-1$
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
        var editingDomain = variableManager.get(IEditingContext.EDITING_CONTEXT, EditingContext.class).map(EditingContext::getDomain);
        if (optionalSelf.isPresent() && editingDomain.isPresent()) {
            var optionalNewInstance = this.createSemanticInstance(editingDomain.get(), creatInstanceOperation.getTypeName());
            if (optionalNewInstance.isPresent()) {
                Object container = this.ecore.eAdd(optionalSelf.get(), creatInstanceOperation.getReferenceName(), optionalNewInstance.get());
                if (container != null) {
                    VariableManager childVariableManager = variableManager.createChild();
                    childVariableManager.put(creatInstanceOperation.getVariableName(), optionalNewInstance.get());
                    return this.executeOperations(creatInstanceOperation.getChildren(), childVariableManager);
                }
            }
        }
        return Optional.empty();
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
        String[] parts = domainType.split("(::?|\\.)"); //$NON-NLS-1$
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

    private Optional<VariableManager> executeDeleteElement(VariableManager variableManager, DeleteElement deleteElementOperation) {
        var optionalSelf = variableManager.get(VariableManager.SELF, EObject.class);
        if (optionalSelf.isPresent()) {
            this.editService.delete(optionalSelf.get());
            return this.executeOperations(deleteElementOperation.getChildren(), variableManager);
        }
        return Optional.empty();
    }

}
