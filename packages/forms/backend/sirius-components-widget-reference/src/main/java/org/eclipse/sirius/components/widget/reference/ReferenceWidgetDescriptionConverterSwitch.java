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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.compatibility.forms.WidgetIdProvider;
import org.eclipse.sirius.components.compatibility.utils.StringValueProvider;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.widgets.reference.ReferenceWidgetDescription;
import org.eclipse.sirius.components.widgets.reference.util.ReferenceSwitch;

/**
 * Converts a View-based ReferenceWidgetDescription into its API equivalent.
 *
 * @author pcdavid
 */
public class ReferenceWidgetDescriptionConverterSwitch extends ReferenceSwitch<AbstractWidgetDescription> {
    private final AQLInterpreter interpreter;

    private final IObjectService objectService;

    public ReferenceWidgetDescriptionConverterSwitch(AQLInterpreter interpreter, IObjectService objectService) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.objectService = Objects.requireNonNull(objectService);
    }

    @Override
    public AbstractWidgetDescription caseReferenceWidgetDescription(ReferenceWidgetDescription referenceDescription) {
        String descriptionId = this.getDescriptionId(referenceDescription);

        var builder = org.eclipse.sirius.components.widget.reference.ReferenceWidgetDescription.newReferenceWidgetDescription(descriptionId)
                .idProvider(new WidgetIdProvider())
                .labelProvider(variableManager -> this.getReferenceLabel(referenceDescription, variableManager))
                .iconURLProvider(variableManager -> "")
                .isReadOnlyProvider(this.getReadOnlyValueProvider(referenceDescription.getIsEnabledExpression()))
                .isManyValuedProvider(variableManager -> this.getReferenceIsMany(referenceDescription, variableManager))
                .isContainerProvider(variableManager -> this.getReferenceIsContainer(referenceDescription, variableManager))
                .itemsProvider(variableManager -> this.getReferenceValue(referenceDescription, variableManager))
                .itemIdProvider(this::getItemId)
                .itemKindProvider(this::getItemKind)
                .itemLabelProvider(this::getItemLabel)
                .itemImageURLProvider(this::getItemIconURL)
                .settingProvider(variableManager -> this.resolveSetting(referenceDescription, variableManager));

        if (referenceDescription.getHelpExpression() != null && !referenceDescription.getHelpExpression().isBlank()) {
            builder.helpTextProvider(this.getStringValueProvider(referenceDescription.getHelpExpression()));
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

    private boolean getReferenceIsMany(ReferenceWidgetDescription referenceDescription, VariableManager variableManager) {
        Setting setting = this.resolveSetting(referenceDescription, variableManager);
        return setting != null && setting.getEStructuralFeature().isMany();
    }

    private boolean getReferenceIsContainer(ReferenceWidgetDescription referenceDescription, VariableManager variableManager) {
        Setting setting = this.resolveSetting(referenceDescription, variableManager);
        return setting != null && setting.getEStructuralFeature() instanceof EReference reference && reference.isContainment();
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
}
