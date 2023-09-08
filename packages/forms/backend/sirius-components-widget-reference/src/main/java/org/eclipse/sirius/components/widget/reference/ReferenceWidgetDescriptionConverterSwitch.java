/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.widget.reference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.common.notify.AdapterFactory;
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
import org.eclipse.sirius.components.compatibility.forms.WidgetIdProvider;
import org.eclipse.sirius.components.compatibility.utils.StringValueProvider;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.emf.OperationInterpreter;
import org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription;
import org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescriptionStyle;
import org.eclipse.sirius.components.widgets.reference.util.ReferenceSwitch;

/**
 * Converts a View-based ReferenceWidgetDescription into its API equivalent.
 *
 * @author pcdavid
 */
public class ReferenceWidgetDescriptionConverterSwitch extends ReferenceSwitch<AbstractWidgetDescription> {

    private final AQLInterpreter interpreter;

    private final IObjectService objectService;

    private final IEditService editService;

    private final IFeedbackMessageService feedbackMessageService;

    private final AdapterFactory adapterFactory;

    private final Function<VariableManager, String> semanticTargetIdProvider;

    public ReferenceWidgetDescriptionConverterSwitch(AQLInterpreter interpreter, IObjectService objectService, IEditService editService, IFeedbackMessageService feedbackMessageService, ComposedAdapterFactory composedAdapterFactory) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = editService;
        this.feedbackMessageService = feedbackMessageService;
        this.adapterFactory = composedAdapterFactory;
        this.semanticTargetIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(objectService::getId).orElse(null);
    }

    @Override
    public AbstractWidgetDescription caseReferenceWidgetDescription(ReferenceWidgetDescription referenceDescription) {
        String descriptionId = this.getDescriptionId(referenceDescription);

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
                .iconURLProvider(variableManager -> "")
                .isReadOnlyProvider(this.getReadOnlyValueProvider(referenceDescription.getIsEnabledExpression()))
                .itemsProvider(variableManager -> this.getReferenceValue(referenceDescription, variableManager))
                .optionsProvider(variableManager -> this.getReferenceOptions(referenceDescription, variableManager))
                .itemIdProvider(this::getItemId)
                .itemKindProvider(this::getItemKind)
                .itemLabelProvider(this::getItemLabel)
                .itemImageURLProvider(this::getItemIconURL)
                .settingProvider(variableManager -> this.resolveSetting(referenceDescription, variableManager))
                .ownerIdProvider(variableManager -> this.getOwnerId(referenceDescription, variableManager))
                .clearHandlerProvider(variableManager -> this.handleClearReference(variableManager, referenceDescription))
                .styleProvider(styleProvider);

        if (referenceDescription.getHelpExpression() != null && !referenceDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(this.getStringValueProvider(referenceDescription.getHelpExpression()));
        }
        if (!referenceDescription.getBody().isEmpty()) {
            builder.itemClickHandlerProvider(variableManager -> this.handleItemClick(variableManager, referenceDescription.getBody()));
        }

        return builder.build();
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
        var optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
        EObject owner = this.getReferenceOwner(variableManager, referenceDescription.getReferenceOwnerExpression());
        String referenceName = this.getStringValueProvider(referenceDescription.getReferenceNameExpression()).apply(variableManager);
        if (optionalEObject.isPresent() && owner != null && owner.eClass().getEStructuralFeature(referenceName) instanceof EReference eReference) {
            EObject eObject = optionalEObject.get();
            Object adapter = this.adapterFactory.adapt(eObject, IItemPropertySource.class);
            if (adapter instanceof IItemPropertySource itemPropertySource) {
                IItemPropertyDescriptor descriptor = itemPropertySource.getPropertyDescriptor(eObject, eReference);
                List<?> referenceOptions;
                if (descriptor != null) {
                    referenceOptions = descriptor.getChoiceOfValues(eObject).stream()
                            .filter(Objects::nonNull)
                            .toList();
                } else {
                    referenceOptions = Arrays.asList(ItemPropertyDescriptor.getReachableObjectsOfType(eObject, eReference.getEReferenceType()).toArray());
                }
                return referenceOptions.stream().filter(option -> !EcoreUtil.isAncestor((EObject) option, eObject)).toList();
            }
        }
        return new ArrayList<>();
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

    private String getItemIconURL(VariableManager variableManager) {
        return this.getItem(variableManager).map(this.objectService::getImagePath).orElse("");
    }

    private String getItemKind(VariableManager variableManager) {
        return this.getItem(variableManager).map(this.objectService::getKind).orElse("");
    }

    private String getItemId(VariableManager variableManager) {
        return this.getItem(variableManager).map(this.objectService::getId).orElse("");
    }

    private String getDescriptionId(EObject description) {
        String descriptionURI = EcoreUtil.getURI(description).toString();
        return UUID.nameUUIDFromBytes(descriptionURI.getBytes()).toString();
    }

    private IStatus handleItemClick(VariableManager variableManager, List<Operation> operations) {
        OperationInterpreter operationInterpreter = new OperationInterpreter(this.interpreter, this.editService);
        Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(operations, variableManager);
        if (optionalVariableManager.isEmpty()) {
            List<Message> errorMessages = new ArrayList<>();
            errorMessages.add(new Message("Something went wrong while handling the item click.", MessageLevel.ERROR));
            errorMessages.addAll(this.feedbackMessageService.getFeedbackMessages());
            return new Failure(errorMessages);
        } else {
            return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
        }
    }

    private boolean matches(String condition, VariableManager variableManager) {
        return this.interpreter.evaluateExpression(variableManager.getVariables(), condition).asBoolean().orElse(Boolean.FALSE);
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
            List<Message> errorMessages = new ArrayList<>();
            errorMessages.add(new Message("Something went wrong while clearing the reference.", MessageLevel.ERROR));
            errorMessages.addAll(this.feedbackMessageService.getFeedbackMessages());
            return new Failure(errorMessages);
        }
        return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of(), this.feedbackMessageService.getFeedbackMessages());
    }
}
