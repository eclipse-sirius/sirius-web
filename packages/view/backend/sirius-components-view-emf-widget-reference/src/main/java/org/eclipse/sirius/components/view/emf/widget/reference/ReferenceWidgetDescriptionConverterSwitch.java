/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.services.api.IEMFKindService;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
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
import org.eclipse.sirius.components.view.emf.OperationInterpreter;
import org.eclipse.sirius.components.view.emf.form.IFormIdProvider;
import org.eclipse.sirius.components.view.form.FormElementDescription;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescriptionStyle;
import org.eclipse.sirius.components.view.widget.reference.util.ReferenceSwitch;
import org.eclipse.sirius.components.widget.reference.ReferenceWidgetComponent;
import org.eclipse.sirius.components.widget.reference.ReferenceWidgetStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Converts a View-based ReferenceWidgetDescription into its API equivalent.
 *
 * @author pcdavid
 */
public class ReferenceWidgetDescriptionConverterSwitch extends ReferenceSwitch<Optional<AbstractWidgetDescription>> {

    private final Logger logger = LoggerFactory.getLogger(ReferenceWidgetDescriptionConverterSwitch.class);

    private final AQLInterpreter interpreter;

    private final IObjectService objectService;

    private final IEditService editService;

    private final IFeedbackMessageService feedbackMessageService;

    private final AdapterFactory adapterFactory;

    private final Function<VariableManager, String> semanticTargetIdProvider;

    private final IFormIdProvider widgetIdProvider;

    private final IEMFKindService emfKindService;

    public ReferenceWidgetDescriptionConverterSwitch(AQLInterpreter interpreter, IObjectService objectService, IEditService editService,
            IEMFKindService emfKindService, IFeedbackMessageService feedbackMessageService, ComposedAdapterFactory composedAdapterFactory, IFormIdProvider widgetIdProvider) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.objectService = Objects.requireNonNull(objectService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
        this.editService = Objects.requireNonNull(editService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.adapterFactory = composedAdapterFactory;
        this.semanticTargetIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(objectService::getId).orElse(null);
        this.emfKindService = Objects.requireNonNull(emfKindService);
    }

    @Override
    public Optional<AbstractWidgetDescription> caseReferenceWidgetDescription(ReferenceWidgetDescription referenceDescription) {
        String descriptionId = this.getDescriptionId(referenceDescription);

        if (referenceDescription.getReferenceNameExpression().isEmpty()) {
            this.logger.warn("Invalid empty Reference Name Expression on widget {}", referenceDescription.getName());
            return Optional.empty();
        }

        Function<VariableManager, ReferenceWidgetStyle> styleProvider = variableManager -> {
            var effectiveStyle = referenceDescription.getConditionalStyles().stream()
                    .filter(style -> this.matches(style.getCondition(), variableManager))
                    .map(ReferenceWidgetDescriptionStyle.class::cast)
                    .findFirst()
                    .orElseGet(referenceDescription::getStyle);
            if (effectiveStyle == null) {
                return null;
            }
            return new ReferenceWidgetStyleProvider(effectiveStyle).apply(variableManager);
        };

        var builder = org.eclipse.sirius.components.widget.reference.ReferenceWidgetDescription.newReferenceWidgetDescription(descriptionId)
                .targetObjectIdProvider(this.semanticTargetIdProvider)
                .idProvider(new WidgetIdProvider())
                .labelProvider(variableManager -> this.getReferenceLabel(referenceDescription, variableManager))
                .iconURLProvider(variableManager -> List.of())
                .isReadOnlyProvider(this.getReadOnlyValueProvider(referenceDescription.getIsEnabledExpression()))
                .itemsProvider(variableManager -> this.getReferenceValue(referenceDescription, variableManager))
                .optionsProvider(variableManager -> this.getReferenceOptions(referenceDescription, variableManager))
                .itemIdProvider(this::getItemId).itemKindProvider(this::getItemKind)
                .itemLabelProvider(this::getItemLabel).itemIconURLProvider(this::getItemIconURL)
                .ownerKindProvider(variableManager -> this.getOwnerKind(variableManager, referenceDescription))
                .referenceKindProvider(variableManager -> this.getReferenceKind(variableManager, referenceDescription))
                .isContainmentProvider(variableManager -> this.isContainment(variableManager, referenceDescription))
                .isManyProvider(variableManager -> this.isMany(variableManager, referenceDescription))
                .ownerIdProvider(variableManager -> this.getOwnerId(referenceDescription, variableManager))
                .styleProvider(styleProvider)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(object -> "")
                .messageProvider(object -> "")
                .clearHandlerProvider(variableManager -> this.handleClearReference(variableManager, referenceDescription))
                .itemRemoveHandlerProvider(variableManager -> this.handleItemRemove(variableManager, referenceDescription))
                .moveHandlerProvider(variableManager -> this.handleMoveReferenceValue(variableManager, referenceDescription));

        if (referenceDescription.getHelpExpression() != null && !referenceDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(this.getStringValueProvider(referenceDescription.getHelpExpression()));
        }
        if (referenceDescription.getBody().isEmpty()) {
            builder.setHandlerProvider(variableManager -> this.handleSetReference(variableManager, referenceDescription));
            builder.addHandlerProvider(variableManager -> this.handleAddReference(variableManager, referenceDescription));
        } else {
            builder.setHandlerProvider(variableManager -> this.newValueHandler(variableManager, referenceDescription.getBody()));
            builder.addHandlerProvider(variableManager -> this.newValueHandler(variableManager, referenceDescription.getBody()));
        }

        return Optional.of(builder.build());
    }

    private EObject getReferenceOwner(VariableManager variableManager, String referenceOwnerExpression) {
        String safeValueExpression = Optional.ofNullable(referenceOwnerExpression).orElse("");
        EObject referenceOwner = variableManager.get(VariableManager.SELF, EObject.class).orElse(null);
        if (!safeValueExpression.isBlank()) {
            Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), safeValueExpression);
            referenceOwner = result.asObject().filter(EObject.class::isInstance).map(EObject.class::cast).orElse(referenceOwner);
        }
        return referenceOwner;
    }

    private String getOwnerId(ReferenceWidgetDescription referenceDescription, VariableManager variableManager) {
        EObject owner = this.getReferenceOwner(variableManager, referenceDescription.getReferenceOwnerExpression());
        return this.objectService.getId(owner);
    }

    private Setting resolveSetting(ReferenceWidgetDescription referenceDescription, VariableManager variableManager) {
        EObject owner = this.getReferenceOwner(variableManager, referenceDescription.getReferenceOwnerExpression());
        String referenceName = this.getStringValueProvider(referenceDescription.getReferenceNameExpression()).apply(variableManager);

        if (owner != null && owner.eClass().getEStructuralFeature(referenceName) instanceof EReference reference) {
            return ((InternalEObject) owner).eSetting(reference);
        } else {
            return null;
        }
    }

    private String getReferenceLabel(ReferenceWidgetDescription referenceDescription, VariableManager variableManager) {
        return new StringValueProvider(this.interpreter, referenceDescription.getLabelExpression()).apply(variableManager);
    }

    private List<?> getReferenceValue(ReferenceWidgetDescription referenceDescription, VariableManager variableManager) {
        List<?> value = List.of();
        Setting setting = this.resolveSetting(referenceDescription, variableManager);
        if (setting != null) {
            var rawValue = setting.get(true);
            if (setting.getEStructuralFeature().isMany()) {
                value = (List<?>) rawValue;
            } else if (rawValue != null) {
                value = List.of(rawValue);
            } else {
                value = List.of();
            }
        }
        return value;
    }

    private List<?> getReferenceOptions(ReferenceWidgetDescription referenceDescription, VariableManager variableManager) {
        EObject owner = this.getReferenceOwner(variableManager, referenceDescription.getReferenceOwnerExpression());
        String referenceName = this.getStringValueProvider(referenceDescription.getReferenceNameExpression()).apply(variableManager);
        if (owner != null && owner.eClass().getEStructuralFeature(referenceName) instanceof EReference eReference) {
            Object adapter = this.adapterFactory.adapt(owner, IItemPropertySource.class);
            if (adapter instanceof IItemPropertySource itemPropertySource) {
                IItemPropertyDescriptor descriptor = itemPropertySource.getPropertyDescriptor(owner, eReference);
                List<?> referenceOptions;
                if (descriptor != null) {
                    referenceOptions = descriptor.getChoiceOfValues(owner).stream()
                            .filter(Objects::nonNull)
                            .toList();
                } else {
                    referenceOptions = Arrays.asList(ItemPropertyDescriptor.getReachableObjectsOfType(owner, eReference.getEReferenceType()).toArray());
                }
                return referenceOptions.stream()
                        .filter(option -> !this.isOptionAncestorAndContainmentReference((EObject) option, owner, eReference))
                        .toList();
            }
        }
        return new ArrayList<>();
    }

    private boolean isOptionAncestorAndContainmentReference(EObject option, EObject self, EReference eReference) {
        return eReference.isContainment() && EcoreUtil.isAncestor(option, self);
    }

    private StringValueProvider getStringValueProvider(String valueExpression) {
        String safeValueExpression = Optional.ofNullable(valueExpression).orElse("");
        return new StringValueProvider(this.interpreter, safeValueExpression);
    }

    private Function<VariableManager, Boolean> getReadOnlyValueProvider(String expression) {
        return variableManager -> {
            if (expression != null && !expression.isBlank()) {
                Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), expression);
                return result.asBoolean().map(value -> !value).orElse(Boolean.FALSE);
            }
            return Boolean.FALSE;
        };
    }

    private Optional<Object> getItem(VariableManager variableManager) {
        return variableManager.get(ReferenceWidgetComponent.ITEM_VARIABLE, Object.class);
    }

    private String getItemLabel(VariableManager variableManager) {
        return this.getItem(variableManager).map(this.objectService::getLabel).orElse("");
    }

    private List<String> getItemIconURL(VariableManager variableManager) {
        return this.getItem(variableManager).map(this.objectService::getImagePath).orElse(List.of());
    }

    private String getItemKind(VariableManager variableManager) {
        return this.getItem(variableManager).map(this.objectService::getKind).orElse("");
    }

    private String getItemId(VariableManager variableManager) {
        return this.getItem(variableManager).map(this.objectService::getId).orElse("");
    }

    private String getDescriptionId(FormElementDescription description) {
        return this.widgetIdProvider.getFormElementDescriptionId(description);
    }

    private IStatus newValueHandler(VariableManager variableManager, List<Operation> operations) {
        OperationInterpreter operationInterpreter = new OperationInterpreter(this.interpreter, this.editService);
        Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(operations, variableManager);
        if (optionalVariableManager.isEmpty()) {
            List<Message> errorMessages = new ArrayList<>();
            errorMessages.add(new Message("Something went wrong while setting the reference value.", MessageLevel.ERROR));
            errorMessages.addAll(this.feedbackMessageService.getFeedbackMessages());
            return new Failure(errorMessages);
        } else {
            return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
        }
    }

    private boolean matches(String condition, VariableManager variableManager) {
        return this.interpreter.evaluateExpression(variableManager.getVariables(), condition).asBoolean().orElse(Boolean.FALSE);
    }

    private IStatus createErrorStatus(String message) {
        List<Message> errorMessages = new ArrayList<>();
        errorMessages.add(new Message(message, MessageLevel.ERROR));
        errorMessages.addAll(this.feedbackMessageService.getFeedbackMessages());
        return new Failure(errorMessages);
    }

    private IStatus handleClearReference(VariableManager variableManager, ReferenceWidgetDescription referenceDescription) {
        EObject owner = this.getReferenceOwner(variableManager, referenceDescription.getReferenceOwnerExpression());
        String referenceName = this.getStringValueProvider(referenceDescription.getReferenceNameExpression()).apply(variableManager);

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

    private IStatus handleItemRemove(VariableManager variableManager, ReferenceWidgetDescription referenceDescription) {
        EObject owner = this.getReferenceOwner(variableManager, referenceDescription.getReferenceOwnerExpression());
        String referenceName = this.getStringValueProvider(referenceDescription.getReferenceNameExpression()).apply(variableManager);
        Optional<Object> item = this.getItem(variableManager);

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

    private IStatus handleSetReference(VariableManager variableManager, ReferenceWidgetDescription referenceDescription) {
        IStatus result = new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
        EObject owner = this.getReferenceOwner(variableManager, referenceDescription.getReferenceOwnerExpression());
        String referenceName = this.getStringValueProvider(referenceDescription.getReferenceNameExpression()).apply(variableManager);
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

    private IStatus handleAddReference(VariableManager variableManager, ReferenceWidgetDescription referenceDescription) {
        IStatus result = new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
        EObject owner = this.getReferenceOwner(variableManager, referenceDescription.getReferenceOwnerExpression());
        String referenceName = this.getStringValueProvider(referenceDescription.getReferenceNameExpression()).apply(variableManager);
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

    private IStatus handleMoveReferenceValue(VariableManager variableManager, ReferenceWidgetDescription referenceDescription) {
        IStatus result = this.createErrorStatus("Something went wrong while reordering reference values.");
        EObject owner = this.getReferenceOwner(variableManager, referenceDescription.getReferenceOwnerExpression());
        String referenceName = this.getStringValueProvider(referenceDescription.getReferenceNameExpression()).apply(variableManager);
        Optional<Object> item = this.getItem(variableManager);
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

    private Optional<EReference> getReference(VariableManager variableManager, ReferenceWidgetDescription referenceDescription) {
        EObject owner = this.getReferenceOwner(variableManager, referenceDescription.getReferenceOwnerExpression());
        String referenceName = this.getStringValueProvider(referenceDescription.getReferenceNameExpression()).apply(variableManager);
        return Optional.ofNullable(owner)
                .map(EObject::eClass)
                .map(klass -> klass.getEStructuralFeature(referenceName))
                .filter(EReference.class::isInstance)
                .map(EReference.class::cast);
    }

    private String getOwnerKind(VariableManager variableManager, ReferenceWidgetDescription referenceDescription) {
        return variableManager.get(VariableManager.SELF, EObject.class).map(self -> this.emfKindService.getKind(self.eClass())).orElse("");
    }

    private String getReferenceKind(VariableManager variableManager, ReferenceWidgetDescription referenceDescription) {
        return this.getReference(variableManager, referenceDescription).flatMap(reference -> Optional.of(this.emfKindService.getKind(reference.getEReferenceType()))).orElse("");
    }

    private boolean isContainment(VariableManager variableManager, ReferenceWidgetDescription referenceDescription) {
        return this.getReference(variableManager, referenceDescription).flatMap(reference -> Optional.of(reference.isContainment())).orElse(false);
    }

    private boolean isMany(VariableManager variableManager, ReferenceWidgetDescription referenceDescription) {
        return this.getReference(variableManager, referenceDescription).flatMap(reference -> Optional.of(reference.isMany())).orElse(false);
    }
}
