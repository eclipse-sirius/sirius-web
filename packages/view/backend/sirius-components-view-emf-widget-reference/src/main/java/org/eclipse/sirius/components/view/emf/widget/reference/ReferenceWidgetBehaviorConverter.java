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
package org.eclipse.sirius.components.view.emf.widget.reference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.emf.operations.api.IOperationExecutor;
import org.eclipse.sirius.components.view.emf.operations.api.OperationExecutionStatus;
import org.eclipse.sirius.components.view.emf.widget.reference.api.IReferenceWidgetBehaviorConverter;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription;
import org.eclipse.sirius.components.widget.reference.ReferenceWidgetComponent;
import org.eclipse.sirius.components.widget.reference.ReferenceWidgetDescription.Builder;
import org.springframework.stereotype.Service;

/**
 * Used to convert the behavior of the reference widget.
 *
 * @author sbegaudeau
 */
@Service
public class ReferenceWidgetBehaviorConverter implements IReferenceWidgetBehaviorConverter {

    private final IOperationExecutor operationExecutor;

    private final IFeedbackMessageService feedbackMessageService;

    public ReferenceWidgetBehaviorConverter(IOperationExecutor operationExecutor, IFeedbackMessageService feedbackMessageService) {
        this.operationExecutor = Objects.requireNonNull(operationExecutor);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
    }

    @Override
    public void convert(Builder referenceWidgetDescriptionBuilder, ReferenceWidgetDescription viewReferenceWidgetDescription, AQLInterpreter interpreter) {
        referenceWidgetDescriptionBuilder.clearHandlerProvider(variableManager -> this.handleClearReference(interpreter, variableManager, viewReferenceWidgetDescription))
                .itemRemoveHandlerProvider(variableManager -> this.handleItemRemove(interpreter, variableManager, viewReferenceWidgetDescription))
                .moveHandlerProvider(variableManager -> this.handleMoveReferenceValue(interpreter, variableManager, viewReferenceWidgetDescription));

        if (viewReferenceWidgetDescription.getHelpExpression() != null && !viewReferenceWidgetDescription.getHelpExpression().isBlank()) {
            referenceWidgetDescriptionBuilder.helpTextProvider(new StringValueProvider(interpreter, Optional.ofNullable(viewReferenceWidgetDescription.getHelpExpression()).orElse("")));
        }
        if (viewReferenceWidgetDescription.getBody().isEmpty()) {
            referenceWidgetDescriptionBuilder.setHandlerProvider(variableManager -> this.handleSetReference(interpreter, variableManager, viewReferenceWidgetDescription));
            referenceWidgetDescriptionBuilder.addHandlerProvider(variableManager -> this.handleAddReference(interpreter, variableManager, viewReferenceWidgetDescription));
        } else {
            referenceWidgetDescriptionBuilder.setHandlerProvider(variableManager -> this.newValueHandler(interpreter, variableManager, viewReferenceWidgetDescription.getBody()));
            referenceWidgetDescriptionBuilder.addHandlerProvider(variableManager -> this.newValueHandler(interpreter, variableManager, viewReferenceWidgetDescription.getBody()));
        }
    }

    private EObject getReferenceOwner(AQLInterpreter interpreter, VariableManager variableManager, String referenceOwnerExpression) {
        String safeValueExpression = Optional.ofNullable(referenceOwnerExpression).orElse("");
        EObject referenceOwner = variableManager.get(VariableManager.SELF, EObject.class).orElse(null);
        if (!safeValueExpression.isBlank()) {
            Result result = interpreter.evaluateExpression(variableManager.getVariables(), safeValueExpression);
            referenceOwner = result.asObject().filter(EObject.class::isInstance).map(EObject.class::cast).orElse(referenceOwner);
        }
        return referenceOwner;
    }

    private IStatus handleClearReference(AQLInterpreter interpreter, VariableManager variableManager, ReferenceWidgetDescription referenceDescription) {
        EObject owner = this.getReferenceOwner(interpreter, variableManager, referenceDescription.getReferenceOwnerExpression());
        String referenceName = new StringValueProvider(interpreter, Optional.ofNullable(referenceDescription.getReferenceNameExpression()).orElse("")).apply(variableManager);

        if (owner != null && owner.eClass().getEStructuralFeature(referenceName) instanceof EReference reference) {
            if (reference.isMany()) {
                ((List<?>) owner.eGet(reference)).clear();
            } else {
                owner.eUnset(reference);
            }
        } else {
            return this.createErrorStatus("Something went wrong while clearing the reference.");
        }
        return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
    }

    private IStatus handleItemRemove(AQLInterpreter interpreter, VariableManager variableManager, ReferenceWidgetDescription referenceDescription) {
        EObject owner = this.getReferenceOwner(interpreter, variableManager, referenceDescription.getReferenceOwnerExpression());
        String referenceName = new StringValueProvider(interpreter, Optional.ofNullable(referenceDescription.getReferenceNameExpression()).orElse("")).apply(variableManager);
        Optional<Object> item = variableManager.get(ReferenceWidgetComponent.ITEM_VARIABLE, Object.class);

        if (owner != null && owner.eClass().getEStructuralFeature(referenceName) instanceof EReference reference) {
            if (reference.isMany()) {
                ((List<?>) owner.eGet(reference)).remove(item.get());
            } else {
                owner.eUnset(reference);
            }
        } else {
            return this.createErrorStatus("Something went wrong while removing a reference value.");
        }
        return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
    }

    private IStatus handleMoveReferenceValue(AQLInterpreter interpreter, VariableManager variableManager, ReferenceWidgetDescription referenceDescription) {
        IStatus result = this.createErrorStatus("Something went wrong while reordering reference values.");
        EObject owner = this.getReferenceOwner(interpreter, variableManager, referenceDescription.getReferenceOwnerExpression());
        String referenceName = new StringValueProvider(interpreter, Optional.ofNullable(referenceDescription.getReferenceNameExpression()).orElse("")).apply(variableManager);
        Optional<Object> item = variableManager.get(ReferenceWidgetComponent.ITEM_VARIABLE, Object.class);
        Optional<Integer> fromIndex = variableManager.get(ReferenceWidgetComponent.MOVE_FROM_VARIABLE, Integer.class);
        Optional<Integer> toIndex = variableManager.get(ReferenceWidgetComponent.MOVE_TO_VARIABLE, Integer.class);

        if (owner != null && owner.eClass().getEStructuralFeature(referenceName) instanceof EReference reference) {
            if (item.isPresent() && fromIndex.isPresent() && toIndex.isPresent()) {
                if (reference.isMany()) {
                    List<Object> values = (List<Object>) owner.eGet(reference);
                    var valueItem = values.get(fromIndex.get().intValue());
                    if (valueItem != null && valueItem.equals(item.get()) && (values instanceof EList<Object> eValues)) {
                        eValues.move(toIndex.get().intValue(), fromIndex.get().intValue());
                        result = new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
                    }
                } else {
                    result = this.createErrorStatus("Only values of multiple-valued references can be reordered.");
                }
            }
        }
        return result;
    }

    private IStatus handleSetReference(AQLInterpreter interpreter, VariableManager variableManager, ReferenceWidgetDescription referenceDescription) {
        IStatus result = new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
        EObject owner = this.getReferenceOwner(interpreter, variableManager, referenceDescription.getReferenceOwnerExpression());
        String referenceName = new StringValueProvider(interpreter, Optional.ofNullable(referenceDescription.getReferenceNameExpression()).orElse("")).apply(variableManager);
        Optional<Object> item = variableManager.get(ReferenceWidgetComponent.NEW_VALUE, Object.class);

        if (owner != null && owner.eClass().getEStructuralFeature(referenceName) instanceof EReference reference) {
            if (reference.isMany()) {
                result = this.createErrorStatus("Multiple-valued reference can only accept a list of values");
            } else {
                owner.eSet(reference, item.get());
            }
        } else {
            result = this.createErrorStatus("Something went wrong while setting the reference value.");
        }
        return result;
    }

    private IStatus handleAddReference(AQLInterpreter interpreter, VariableManager variableManager, ReferenceWidgetDescription referenceDescription) {
        IStatus result = new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
        EObject owner = this.getReferenceOwner(interpreter, variableManager, referenceDescription.getReferenceOwnerExpression());
        String referenceName = new StringValueProvider(interpreter, Optional.ofNullable(referenceDescription.getReferenceNameExpression()).orElse("")).apply(variableManager);
        Optional<List<Object>> newValues = variableManager.get(ReferenceWidgetComponent.NEW_VALUE, (Class<List<Object>>) (Class<?>) List.class);

        if (newValues.isEmpty()) {
            result = this.createErrorStatus("Something went wrong while adding reference values.");
        } else if (owner != null && owner.eClass().getEStructuralFeature(referenceName) instanceof EReference reference) {
            if (reference.isMany()) {
                ((List<Object>) owner.eGet(reference)).addAll(newValues.get());
            } else {
                new Failure("Single-valued reference can only accept a single value");
            }
        } else {
            result = this.createErrorStatus("Something went wrong while adding reference values.");
        }
        return result;
    }

    private IStatus newValueHandler(AQLInterpreter interpreter, VariableManager variableManager, List<Operation> operations) {
        var result = this.operationExecutor.execute(interpreter, variableManager, operations);
        if (result.status() == OperationExecutionStatus.FAILURE) {
            List<Message> errorMessages = new ArrayList<>();
            errorMessages.add(new Message("Something went wrong while setting the reference value.", MessageLevel.ERROR));
            errorMessages.addAll(this.feedbackMessageService.getFeedbackMessages());
            return new Failure(errorMessages);
        }
        return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
    }

    private IStatus createErrorStatus(String message) {
        List<Message> errorMessages = new ArrayList<>();
        errorMessages.add(new Message(message, MessageLevel.ERROR));
        errorMessages.addAll(this.feedbackMessageService.getFeedbackMessages());
        return new Failure(errorMessages);
    }
}
